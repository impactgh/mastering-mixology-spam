package com.masteringmixology;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("masteringmixology")
public interface MasteringMixologyConfig extends Config {

	@ConfigItem(
		keyName = "showInventoryHelper",
		name = "Show Inventory Helper",
		description = "Display overlay showing next potion to make and progress",
		position = 0
	)
	default boolean showInventoryHelper() {
		return true;
	}

	@ConfigItem(
		keyName = "highlightInventorySlots",
		name = "Highlight Inventory Slots",
		description = "Highlight inventory slots: Yellow=unprocessed, Green=processed, Red=wrong/empty",
		position = 1
	)
	default boolean highlightInventorySlots() {
		return true;
	}
}
