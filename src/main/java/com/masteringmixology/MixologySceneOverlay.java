package com.masteringmixology;

import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;

/**
 * Overlay for highlighting objects in the Mastering Mixology area.
 * Note: Object IDs need to be verified in-game using Object Inspector plugin.
 */
public class MixologySceneOverlay extends Overlay {

	private final Client client;
	private final MasteringMixologyPlugin plugin;
	private final MasteringMixologyConfig config;

	@Inject
	private MixologySceneOverlay(Client client, MasteringMixologyPlugin plugin, 
								 MasteringMixologyConfig config) {
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
		setPriority(OverlayPriority.HIGH);
	}

	@Override
	public Dimension render(Graphics2D graphics) {
		if (!plugin.isInMixologyArea()) {
			return null;
		}

		if (!config.highlightNextStation()) {
			return null;
		}

		Scene scene = client.getTopLevelWorldView().getScene();
		if (scene == null) {
			return null;
		}

		// Get current workflow step
		WorkflowManager workflow = plugin.getWorkflowManager();
		if (workflow == null || workflow.isComplete()) {
			return null;
		}

		WorkflowStep currentStep = workflow.getCurrentStep();
		if (currentStep == null) {
			return null;
		}

		// Determine what to highlight based on current step
		WorkflowStep.HighlightTarget target = currentStep.getHighlightTarget();
		Color highlightColor = getColorForPhase(currentStep.getPhase());

		Tile[][][] tiles = scene.getTiles();
		int plane = client.getTopLevelWorldView().getPlane();

		for (int x = 0; x < Constants.SCENE_SIZE; x++) {
			for (int y = 0; y < Constants.SCENE_SIZE; y++) {
				Tile tile = tiles[plane][x][y];
				if (tile == null) {
					continue;
				}

				GameObject[] gameObjects = tile.getGameObjects();
				if (gameObjects == null) {
					continue;
				}

				for (GameObject gameObject : gameObjects) {
					if (gameObject == null) {
						continue;
					}

					if (shouldHighlightObject(gameObject, target)) {
						highlightObject(graphics, gameObject, highlightColor, 4);
					}
				}
			}
		}

		// Also check for digweed if enabled
		if (config.highlightDigweed()) {
			highlightDigweedIfPresent(graphics, tiles, plane);
		}

		return null;
	}

	private boolean shouldHighlightObject(GameObject gameObject, WorkflowStep.HighlightTarget target) {
		int objectId = gameObject.getId();

		switch (target) {
			case MOX_LEVER:
				return objectId == MixologyObjectIDs.MOX_LEVER;
			case AGA_LEVER:
				return objectId == MixologyObjectIDs.AGA_LEVER;
			case LYE_LEVER:
				return objectId == MixologyObjectIDs.LYE_LEVER;
			case MIXING_VESSEL:
				return objectId == MixologyObjectIDs.MIXING_VESSEL;
			case ALEMBIC:
				return objectId == MixologyObjectIDs.ALEMBIC;
			case AGITATOR:
				return objectId == MixologyObjectIDs.AGITATOR;
			case RETORT:
				return objectId == MixologyObjectIDs.RETORT;
			case CONVEYOR_BELT:
				return objectId == MixologyObjectIDs.CONVEYOR_BELT;
			default:
				return false;
		}
	}

	private Color getColorForPhase(WorkflowPhase phase) {
		switch (phase) {
			case MIXING:
				return Color.CYAN;
			case PROCESSING_ALEMBIC:
			case PROCESSING_AGITATOR:
			case PROCESSING_RETORT:
				return Color.ORANGE;
			case READY_TO_SUBMIT:
				return Color.GREEN;
			default:
				return Color.WHITE;
		}
	}

	private void highlightDigweedIfPresent(Graphics2D graphics, Tile[][][] tiles, int plane) {
		for (int x = 0; x < Constants.SCENE_SIZE; x++) {
			for (int y = 0; y < Constants.SCENE_SIZE; y++) {
				Tile tile = tiles[plane][x][y];
				if (tile == null) continue;

				GameObject[] gameObjects = tile.getGameObjects();
				if (gameObjects == null) continue;

				for (GameObject gameObject : gameObjects) {
					if (gameObject == null) continue;

					if (gameObject.getId() == MixologyObjectIDs.DIGWEED) {
						highlightObject(graphics, gameObject, Color.YELLOW, 4);
					}
				}
			}
		}
	}

	private void highlightObject(Graphics2D graphics, TileObject object, Color color, int strokeWidth) {
		Shape shape = object.getClickbox();
		if (shape != null) {
			graphics.setColor(color);
			graphics.setStroke(new BasicStroke(strokeWidth));
			graphics.draw(shape);
			graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 50));
			graphics.fill(shape);
		}
	}
}
