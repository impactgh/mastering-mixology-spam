package com.masteringmixology;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class MasteringMixologyOverlay extends OverlayPanel {

	private final Client client;
	private final MasteringMixologyPlugin plugin;
	private final MasteringMixologyConfig config;

	@Inject
	private MasteringMixologyOverlay(Client client, MasteringMixologyPlugin plugin, MasteringMixologyConfig config) {
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.TOP_LEFT);
	}

	@Override
	public Dimension render(Graphics2D graphics) {
		if (!config.showPotionTracker()) {
			return null;
		}

		panelComponent.getChildren().clear();

		// Title
		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Mixology Spam Tech")
			.color(Color.CYAN)
			.build());

		WorkflowManager workflow = plugin.getWorkflowManager();
		if (workflow == null) {
			return super.render(graphics);
		}

		// Show current phase
		WorkflowPhase phase = workflow.getCurrentPhase();
		Color phaseColor = getPhaseColor(phase);

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Phase:")
			.right(phase.getDisplayName())
			.rightColor(phaseColor)
			.build());

		// Show current step instruction
		WorkflowStep currentStep = workflow.getCurrentStep();
		if (currentStep != null) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Next:")
				.right(currentStep.getInstruction())
				.rightColor(Color.YELLOW)
				.build());
		}

		// Show progress
		panelComponent.getChildren().add(LineComponent.builder()
			.left("Progress:")
			.right(workflow.getProgressString())
			.rightColor(Color.WHITE)
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("")
			.build());

		// Show inventory progress
		SpamTechInventory inventory = plugin.getSpamTechInventory();
		panelComponent.getChildren().add(LineComponent.builder()
			.left("Potions:")
			.right(inventory.getTotalPotions() + " / 27")
			.rightColor(inventory.isInventoryComplete() ? Color.GREEN : Color.WHITE)
			.build());

		// Show breakdown by recipe (compact)
		if (config.showRecipeSuggestions()) {
			panelComponent.getChildren().add(LineComponent.builder()
				.left("")
				.build());

			panelComponent.getChildren().add(LineComponent.builder()
				.left("Recipe Progress:")
				.leftColor(Color.GRAY)
				.build());

			for (PotionRecipe recipe : PotionRecipe.values()) {
				if (recipe == PotionRecipe.AAA) continue;

				int current = inventory.getTotalCount(recipe);
				int target = inventory.getTargetCount(recipe);
				
				if (target == 0) continue;

				Color countColor = current >= target ? Color.GREEN : Color.WHITE;
				String display = String.format("%d/%d", current, target);

				panelComponent.getChildren().add(LineComponent.builder()
					.left("  " + recipe.getShortCode())
					.right(display)
					.rightColor(countColor)
					.build());
			}
		}

		return super.render(graphics);
	}

	private Color getPhaseColor(WorkflowPhase phase) {
		switch (phase) {
			case MIXING:
				return Color.CYAN;
			case PROCESSING_ALEMBIC:
			case PROCESSING_AGITATOR:
			case PROCESSING_RETORT:
				return Color.ORANGE;
			case READY_TO_SUBMIT:
				return Color.GREEN;
			case COMPLETE:
				return Color.GRAY;
			default:
				return Color.WHITE;
		}
	}
}
