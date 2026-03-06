package com.masteringmixology;

import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;

import javax.inject.Inject;
import java.awt.*;

/**
 * Simple overlay showing next potion to make and progress counts.
 */
public class MasteringMixologyOverlay extends OverlayPanel {

	private final Client client;
	private final MasteringMixologyConfig config;
	
	// Colors for paste types
	private static final Color MOX_COLOR = new Color(255, 100, 100);  // Red for Mox (M)
	private static final Color AGA_COLOR = new Color(100, 200, 255);  // Cyan for Aga (A)
	private static final Color LYE_COLOR = new Color(50, 255, 150);   // Bright cyan-green for Lye (L) - distinct from yellow

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
				// Add colored potion code
				panelComponent.getChildren().add(new ColoredPotionLine(nextPotion.getShortCode(), nextSlot));
			}
		} else {
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
			if (stationProcessed[0] == 9 && stationProcessed[1] < 9) {
				nextStation = "AGITATOR";
			} else if (stationProcessed[1] == 9 && stationProcessed[2] < 9) {
				nextStation = "RETORT";
			} else if (stationProcessed[2] == 9 && processed == 27) {
				nextStation = "CONVEYOR BELT!";
			}
			
			if (nextStation != null) {
				panelComponent.getChildren().add(LineComponent.builder()
					.left("")
					.build());
				panelComponent.getChildren().add(LineComponent.builder()
					.left(">>> GO TO:")
					.right(nextStation)
					.rightColor(nextStation.contains("CONVEYOR") ? Color.GREEN : Color.ORANGE)
					.build());
			}
		}

		return super.render(graphics);
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
	
	/**
	 * Custom component to render potion code with colored letters.
	 */
	private static class ColoredPotionLine implements LayoutableRenderableEntity {
		private final String potionCode;
		private final int slot;
		private Dimension dimension;
		private Rectangle bounds;
		
		ColoredPotionLine(String potionCode, int slot) {
			this.potionCode = potionCode;
			this.slot = slot;
		}
		
		@Override
		public Dimension render(Graphics2D graphics) {
			FontMetrics metrics = graphics.getFontMetrics();
			int lineHeight = metrics.getHeight();
			
			// Draw "Next: "
			String label = "Next: ";
			graphics.setColor(Color.WHITE);
			int x = 0;
			graphics.drawString(label, x, lineHeight);
			x += metrics.stringWidth(label);
			
			// Draw each letter of the potion code in its color
			for (char c : potionCode.toCharArray()) {
				Color color = getColorForLetter(c);
				graphics.setColor(color);
				graphics.drawString(String.valueOf(c), x, lineHeight);
				x += metrics.stringWidth(String.valueOf(c));
			}
			
			// Draw slot number
			String slotText = String.format(" (slot %d)", slot);
			graphics.setColor(Color.GRAY);
			graphics.drawString(slotText, x, lineHeight);
			x += metrics.stringWidth(slotText);
			
			dimension = new Dimension(x, lineHeight);
			return dimension;
		}
		
		public Dimension getPreferredSize() {
			return dimension != null ? dimension : new Dimension(200, 20);
		}
		
		public void setPreferredLocation(Point position) {
			if (bounds == null) {
				bounds = new Rectangle(position, getPreferredSize());
			} else {
				bounds.setLocation(position);
			}
		}
		
		public void setPreferredSize(Dimension dimension) {
			this.dimension = dimension;
		}
		
		@Override
		public Rectangle getBounds() {
			return bounds != null ? bounds : new Rectangle(getPreferredSize());
		}
		
		private Color getColorForLetter(char letter) {
			switch (letter) {
				case 'M': return MOX_COLOR;  // Mox = Red
				case 'A': return AGA_COLOR;  // Aga = Cyan
				case 'L': return LYE_COLOR;  // Lye = Green
				default: return Color.WHITE;
			}
		}
	}
}
