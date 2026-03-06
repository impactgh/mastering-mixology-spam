package com.masteringmixology;

import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;

/**
 * Overlay that highlights inventory slots for the spam tech setup.
 * Yellow = Correct potion, unprocessed
 * Green = Correct potion, processed
 * Red = Wrong potion or empty
 */
public class InventorySetupOverlay extends Overlay {

	private final Client client;
	private final MasteringMixologyPlugin plugin;
	private final MasteringMixologyConfig config;

	@Inject
	private InventorySetupOverlay(Client client, MasteringMixologyPlugin plugin, MasteringMixologyConfig config) {
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
	}

	@Override
	public Dimension render(Graphics2D graphics) {
		if (!config.highlightInventorySlots()) {
			return null;
		}

		ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
		if (inventory == null) {
			return null;
		}

		Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
		if (inventoryWidget == null || inventoryWidget.isHidden()) {
			return null;
		}

		Item[] items = inventory.getItems();
		
		for (int i = 0; i < 27; i++) {
			Rectangle slotBounds = getInventorySlotBounds(inventoryWidget, i);
			if (slotBounds == null) {
				continue;
			}

			Color highlightColor = getSlotHighlightColor(i, items);
			if (highlightColor != null) {
				graphics.setColor(highlightColor);
				graphics.setStroke(new BasicStroke(2));
				graphics.draw(slotBounds);
			}
		}

		return null;
	}

	private Color getSlotHighlightColor(int slot, Item[] items) {
		PotionRecipe expectedRecipe = InventoryLayout.getRecipeForSlot(slot);
		if (expectedRecipe == null) {
			return null; // Slot not part of layout
		}

		// Check if slot is empty
		if (slot >= items.length || items[slot] == null || items[slot].getId() == -1) {
			return new Color(255, 0, 0, 100); // Red = empty/wrong
		}

		int itemId = items[slot].getId();
		PotionRecipe actualRecipe = MixologyItemIDs.getRecipeFromItemId(itemId);

		// Wrong potion in this slot
		if (actualRecipe != expectedRecipe) {
			return new Color(255, 0, 0, 100); // Red = wrong potion
		}

		// Correct potion - check if processed
		if (MixologyItemIDs.isProcessed(itemId)) {
			return new Color(0, 255, 0, 100); // Green = processed
		} else {
			return new Color(255, 255, 0, 100); // Yellow = unprocessed
		}
	}

	private Rectangle getInventorySlotBounds(Widget inventoryWidget, int slot) {
		Widget[] children = inventoryWidget.getChildren();
		if (children == null || slot >= children.length) {
			return null;
		}

		Widget slotWidget = children[slot];
		if (slotWidget == null) {
			return null;
		}

		return slotWidget.getBounds();
	}
}
