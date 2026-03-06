package com.masteringmixology;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
	name = "Mastering Mixology Helper",
	description = "Inventory helper for Mastering Mixology spam tech method",
	tags = {"herblore", "mixology", "minigame", "spam"}
)
public class MasteringMixologyPlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private MasteringMixologyConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private MasteringMixologyOverlay overlay;

	@Inject
	private InventorySetupOverlay inventorySetupOverlay;

	@Override
	protected void startUp() throws Exception {
		log.info("Mastering Mixology Helper started!");
		overlayManager.add(overlay);
		overlayManager.add(inventorySetupOverlay);
	}

	@Override
	protected void shutDown() throws Exception {
		log.info("Mastering Mixology Helper stopped!");
		overlayManager.remove(overlay);
		overlayManager.remove(inventorySetupOverlay);
	}

	@Provides
	MasteringMixologyConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(MasteringMixologyConfig.class);
	}
}
