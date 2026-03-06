package com.masteringmixology;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("masteringmixology")
public interface MasteringMixologyConfig extends Config {
	
	@ConfigSection(
		name = "Spam Tech Settings",
		description = "Settings for the spam tech method",
		position = 0
	)
	String spamTechSection = "spamTech";

	@ConfigItem(
		keyName = "enableSpamTech",
		name = "Enable Spam Tech",
		description = "Enable the spam tech method (make 28 potions then spam conveyor)",
		section = spamTechSection,
		position = 0
	)
	default boolean enableSpamTech() {
		return true;
	}

	@ConfigItem(
		keyName = "targetPotionCount",
		name = "Target Potion Count",
		description = "Number of potions to make before spam clicking conveyor (recommended: 28)",
		section = spamTechSection,
		position = 1
	)
	default int targetPotionCount() {
		return 28;
	}

	@ConfigItem(
		keyName = "highlightConveyor",
		name = "Highlight Conveyor",
		description = "Highlight the conveyor belt when ready to spam",
		section = spamTechSection,
		position = 2
	)
	default boolean highlightConveyor() {
		return true;
	}

	@ConfigSection(
		name = "Recipe Priority",
		description = "Which recipes to prioritize",
		position = 1
	)
	String recipePrioritySection = "recipePriority";

	@ConfigItem(
		keyName = "prioritizeMAL",
		name = "Prioritize MAL",
		description = "Always prioritize MixALot (MAL) for best points",
		section = recipePrioritySection,
		position = 0
	)
	default boolean prioritizeMAL() {
		return true;
	}

	@ConfigItem(
		keyName = "skipSinglePaste",
		name = "Skip Single Paste",
		description = "Skip single-paste potions (AAA, MMM, LLL) unless MAL is present",
		section = recipePrioritySection,
		position = 1
	)
	default boolean skipSinglePaste() {
		return false;
	}

	@ConfigSection(
		name = "Display Settings",
		description = "UI and overlay settings",
		position = 2
	)
	String displaySection = "display";

	@ConfigItem(
		keyName = "showPotionTracker",
		name = "Show Potion Tracker",
		description = "Display overlay showing potions made and remaining",
		section = displaySection,
		position = 0
	)
	default boolean showPotionTracker() {
		return true;
	}

	@ConfigItem(
		keyName = "showRecipeSuggestions",
		name = "Show Recipe Suggestions",
		description = "Show suggested recipes based on current orders",
		section = displaySection,
		position = 1
	)
	default boolean showRecipeSuggestions() {
		return true;
	}

	@ConfigItem(
		keyName = "playSound",
		name = "Play Sound Alert",
		description = "Play sound when ready to spam conveyor belt",
		section = displaySection,
		position = 2
	)
	default boolean playSound() {
		return true;
	}

	@ConfigSection(
		name = "Highlighting",
		description = "Object highlighting settings",
		position = 3
	)
	String highlightingSection = "highlighting";

	@ConfigItem(
		keyName = "highlightNextStation",
		name = "Highlight Next Station",
		description = "Highlight the recommended processing station",
		section = highlightingSection,
		position = 0
	)
	default boolean highlightNextStation() {
		return true;
	}

	@ConfigItem(
		keyName = "highlightDigweed",
		name = "Highlight Digweed",
		description = "Highlight and notify when digweed spawns",
		section = highlightingSection,
		position = 1
	)
	default boolean highlightDigweed() {
		return true;
	}

	@ConfigItem(
		keyName = "digweedNotification",
		name = "Digweed Notification",
		description = "Send notification when digweed spawns",
		section = highlightingSection,
		position = 2
	)
	default boolean digweedNotification() {
		return true;
	}

	@ConfigSection(
		name = "Speed-Up Helper",
		description = "Visual cues for speed-up clicks",
		position = 4
	)
	String speedUpSection = "speedUp";

	@ConfigItem(
		keyName = "showSpeedUpIndicator",
		name = "Show Speed-Up Indicator",
		description = "Show visual cues for when to click for speed boosts",
		section = speedUpSection,
		position = 0
	)
	default boolean showSpeedUpIndicator() {
		return true;
	}

	@ConfigItem(
		keyName = "speedUpSound",
		name = "Speed-Up Sound",
		description = "Play sound when speed-up window is available",
		section = speedUpSection,
		position = 1
	)
	default boolean speedUpSound() {
		return false;
	}
}
