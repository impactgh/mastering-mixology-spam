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
				panelComponent.getChildren().add(LineComponent.builder()
					.left("Next:")
					.right(String.format("%s (slot %d)", nextPotion.getShortCode(), nextSlot))
					.rightColor(Color.YELLOW)
					.build());
			}
		} else {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Status:")
				.right("All 27 slots filled!")
				.rightColor(Color.GREEN)
				.build());
		}

		// Count processed vs unprocessed
		int processed = 0;
		int unprocessed = 0;
		
		for (int i = 0; i < 27 && i < items.length; i++) {
			if (items[i] == null || items[i].getId() == -1) {
				continue;
			}
			
			int itemId = items[i].getId();
			if (MixologyItemIDs.isMixologyPotion(itemId)) {
				if (MixologyItemIDs.isProcessed(itemId)) {
					processed++;
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
}
