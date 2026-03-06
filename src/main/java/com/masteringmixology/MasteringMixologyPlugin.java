package com.masteringmixology;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@PluginDescriptor(
	name = "Mastering Mixology Helper",
	description = "Spam tech helper for Mastering Mixology minigame",
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
	private MixologySceneOverlay sceneOverlay;

	@Inject
	private SpeedUpOverlay speedUpOverlay;

	@Inject
	private Notifier notifier;

	@Getter
	private MasteringMixologyPanel panel;

	@Getter
	private final SpamTechInventory spamTechInventory = new SpamTechInventory();

	@Getter
	private WorkflowManager workflowManager;

	@Getter
	private final List<PotionRecipe> currentOrders = new ArrayList<>();

	@Getter
	private boolean readyToSpam = false;

	@Getter
	private boolean inMixologyArea = false;

	@Override
	protected void startUp() throws Exception {
		log.info("Mastering Mixology Helper started!");
		overlayManager.add(overlay);
		overlayManager.add(sceneOverlay);
		overlayManager.add(speedUpOverlay);
		panel = new MasteringMixologyPanel(this);
		workflowManager = new WorkflowManager(spamTechInventory);
		resetTracking();
	}

	@Override
	protected void shutDown() throws Exception {
		log.info("Mastering Mixology Helper stopped!");
		overlayManager.remove(overlay);
		overlayManager.remove(sceneOverlay);
		overlayManager.remove(speedUpOverlay);
		panel = null;
		resetTracking();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged) {
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN) {
			checkMixologyArea();
		}
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event) {
		if (!inMixologyArea) {
			return;
		}

		int objectId = event.getGameObject().getId();
		
		// Detect digweed spawn
		if (objectId == MixologyObjectIDs.DIGWEED) {
			onDigweedSpawned();
		}
	}

	private void onDigweedSpawned() {
		log.info("Digweed spawned!");
		
		if (config.digweedNotification()) {
			notifier.notify("Digweed has spawned!");
		}
	}

	@Subscribe
	public void onGameTick(GameTick tick) {
		checkMixologyArea();
		updateReadyStatus();
		speedUpOverlay.onGameTick();
	}

	private void checkMixologyArea() {
		// Check if player is in Mixology region
		if (client.getLocalPlayer() == null) {
			inMixologyArea = false;
			return;
		}

		int[] regions = client.getMapRegions();
		if (regions == null) {
			inMixologyArea = false;
			return;
		}

		for (int region : regions) {
			if (MixologyRegions.isInMixologyArea(region)) {
				inMixologyArea = true;
				return;
			}
		}

		inMixologyArea = false;
	}

	private void updateReadyStatus() {
		if (!config.enableSpamTech()) {
			readyToSpam = false;
			return;
		}

		readyToSpam = spamTechInventory.isInventoryComplete();
	}

	public void addPotionMade(PotionRecipe recipe, ProcessStation station) {
		spamTechInventory.addPotion(recipe, station);
		updateReadyStatus();

		// Advance workflow if using workflow mode
		if (workflowManager != null) {
			workflowManager.advanceStep();
		}

		if (panel != null) {
			panel.updateDisplay();
		}

		if (readyToSpam && config.playSound()) {
			// TODO: Play notification sound
			log.info("Ready to spam conveyor belt!");
		}
	}

	public void advanceWorkflowStep() {
		if (workflowManager != null) {
			workflowManager.advanceStep();
		}
	}

	public void resetTracking() {
		spamTechInventory.reset();
		currentOrders.clear();
		readyToSpam = false;
		if (workflowManager != null) {
			workflowManager.reset();
		}
	}

	public void submitPotions() {
		// Called when potions are submitted to conveyor
		resetTracking();
	}

	public List<PotionRecipe> getSuggestedRecipes() {
		List<PotionRecipe> suggestions = new ArrayList<>();

		// Get the next recommended potion from spam tech strategy
		PotionRecipe nextPotion = spamTechInventory.getNextRecommendedPotion();
		if (nextPotion != null) {
			suggestions.add(nextPotion);
		}

		return suggestions;
	}

	public ProcessStation getSuggestedStation(PotionRecipe recipe) {
		return spamTechInventory.getNextRecommendedStation(recipe);
	}

	@Provides
	MasteringMixologyConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(MasteringMixologyConfig.class);
	}
}
