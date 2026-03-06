package com.masteringmixology;

import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;

/**
 * Simple overlay showing next potion to make and progress counts.
 */
public class MasteringMixologyOverlay extends OverlayPanel {

	private final Client client;
	private final MasteringMixologyConfig config;
	
	// Colors for paste types (matching in-game colors)
	private static final Color MOX_COLOR = new Color(100, 200, 255);  // Blue for Mox (M)
	private static final Color AGA_COLOR = new Color(50, 255, 150);   // Green for Aga (A)
	private static final Color LYE_COLOR = new Color(255, 100, 100);  // Red for Lye (L)
	
	private String currentPotionCode = null;
	private int currentSlot = -1;

	@Inject
	private MasteringMixologyOverlay(Client client, MasteringMixologyConfig config) {
		this.client = client;
		this.config = config;
		setPosition(OverlayPosition.TOP_LEFT);
	}

	@Override
	public Dimension render(Graphics2D graphics) {
		if (!config.showInventoryHelper()) {
			return null;
		}

		ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
		if (inventory == null) {
			return null;
		}

		panelComponent.getChildren().clear();

		// Title
		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Mixology Inventory")
			.color(Color.CYAN)
			.build());

		Item[] items = inventory.getItems();
		
		// Find next slot to fill
		int nextSlot = getNextSlotToFill(items);
		
		if (nextSlot >= 0 && nextSlot < 27) {
			PotionRecipe nextPotion = InventoryLayout.getRecipeForSlot(nextSlot);
			if (nextPotion != null) {
				currentPotionCode = nextPotion.getShortCode();
				currentSlot = nextSlot;
				
				// Add a simple line component that will be replaced by colored text
				panelComponent.getChildren().add(LineComponent.builder()
					.left("Next:")
					.right("") // Empty - we'll draw colored text over it
					.build());
			}
		} else {
			currentPotionCode = null;
			currentSlot = -1;
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Status:")
				.right("All 27 slots filled!")
				.rightColor(Color.GREEN)
				.build());
		}

		// Count processed vs unprocessed and check station progress
		int processed = 0;
		int unprocessed = 0;
		int[] stationProcessed = new int[3]; // Alembic (0-8), Agitator (9-17), Retort (18-26)
		
		for (int i = 0; i < 27 && i < items.length; i++) {
			if (items[i] == null || items[i].getId() == -1) {
				continue;
			}
			
			int itemId = items[i].getId();
			if (MixologyItemIDs.isMixologyPotion(itemId)) {
				if (MixologyItemIDs.isProcessed(itemId)) {
					processed++;
					// Track which station's potions are processed
					if (i <= 8) stationProcessed[0]++;
					else if (i <= 17) stationProcessed[1]++;
					else stationProcessed[2]++;
				} else {
					unprocessed++;
				}
			}
		}

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Processed:")
			.right(processed + " / 27")
			.rightColor(processed == 27 ? Color.GREEN : Color.WHITE)
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Unprocessed:")
			.right(String.valueOf(unprocessed))
			.rightColor(unprocessed > 0 ? Color.YELLOW : Color.GRAY)
			.build());
		
		// Check if we're on the last potion of a station and warn to move to next
		if (unprocessed == 0 && processed > 0) {
			// Check which station needs attention
			String nextStation = null;
			Color warningColor = Color.ORANGE;
			
			if (stationProcessed[0] == 9 && stationProcessed[1] < 9) {
				nextStation = "AGITATOR";
			} else if (stationProcessed[1] == 9 && stationProcessed[2] < 9) {
				nextStation = "RETORT";
			} else if (processed == 27) {
				// All potions processed, check if 6 MAL potions are in last 6 slots
				int malInLastSlots = 0;
				for (int i = 21; i < 27 && i < items.length; i++) {
					if (items[i] != null && items[i].getId() != -1) {
						PotionRecipe recipe = MixologyItemIDs.getRecipeFromItemId(items[i].getId());
						if (recipe == PotionRecipe.MAL && MixologyItemIDs.isProcessed(items[i].getId())) {
							malInLastSlots++;
						}
					}
				}
				
				if (malInLastSlots < 6) {
					nextStation = "MOVE 6 MAL TO SLOTS 21-26!";
					warningColor = new Color(255, 165, 0); // Bright orange
				} else {
					nextStation = "CONVEYOR BELT!";
					warningColor = Color.GREEN;
				}
			}
			
			if (nextStation != null) {
				panelComponent.getChildren().add(LineComponent.builder()
					.left("")
					.build());
				panelComponent.getChildren().add(LineComponent.builder()
					.left(">>> GO TO:")
					.right(nextStation)
					.rightColor(warningColor)
					.build());
			}
		}

		Dimension panelDimension = super.render(graphics);
		
		// Draw colored potion code on top of the panel
		if (currentPotionCode != null && currentSlot >= 0) {
			drawColoredPotionCode(graphics);
		}
		
		return panelDimension;
	}
	
	private void drawColoredPotionCode(Graphics2D graphics) {
		// Calculate position - we need to find where the "Next:" line is rendered
		Rectangle bounds = panelComponent.getBounds();
		int baseX = bounds.x + 10; // Left padding
		int baseY = bounds.y + 36; // Title (18) + spacing (4) + Next line baseline (18)
		
		FontMetrics metrics = graphics.getFontMetrics();
		
		// Calculate where "Next: " ends
		String label = "Next: ";
		int labelWidth = metrics.stringWidth(label);
		int x = baseX + labelWidth;
		
		// Draw each letter of the potion code in its color
		for (char c : currentPotionCode.toCharArray()) {
			Color color = getColorForLetter(c);
			graphics.setColor(color);
			graphics.drawString(String.valueOf(c), x, baseY);
			x += metrics.stringWidth(String.valueOf(c));
		}
		
		// Draw slot number in gray
		String slotText = String.format(" (slot %d)", currentSlot);
		graphics.setColor(Color.GRAY);
		graphics.drawString(slotText, x, baseY);
	}
	
	private Color getColorForLetter(char letter) {
		switch (letter) {
			case 'M': return MOX_COLOR;  // Mox = Blue
			case 'A': return AGA_COLOR;  // Aga = Green
			case 'L': return LYE_COLOR;  // Lye = Red
			default: return Color.WHITE;
		}
	}

	private int getNextSlotToFill(Item[] items) {
		for (int i = 0; i < 27; i++) {
			PotionRecipe expected = InventoryLayout.getRecipeForSlot(i);
			if (expected == null) continue;

			if (i >= items.length || items[i] == null || items[i].getId() == -1) {
				return i;
			}

			PotionRecipe actual = MixologyItemIDs.getRecipeFromItemId(items[i].getId());
			if (actual != expected) {
				return i;
			}
		}
		return -1;
	}
}
