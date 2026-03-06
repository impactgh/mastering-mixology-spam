package com.masteringmixology;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the efficient workflow for spam tech method.
 * Guides the player through mixing and processing in optimal order.
 */
public class WorkflowManager {

	@Getter
	private final List<WorkflowStep> allSteps = new ArrayList<>();
	
	@Getter
	private int currentStepIndex = 0;

	private final SpamTechInventory inventory;

	// Optimal processing order
	private static final ProcessStation[] PROCESSING_ORDER = {
		ProcessStation.ALEMBIC,
		ProcessStation.AGITATOR,
		ProcessStation.RETORT
	};

	public WorkflowManager(SpamTechInventory inventory) {
		this.inventory = inventory;
		generateWorkflow();
	}

	/**
	 * Generates the complete workflow based on spam tech strategy.
	 */
	private void generateWorkflow() {
		allSteps.clear();

		// Phase 1: Mix all potions (don't process yet)
		addMixingSteps();

		// Phase 2: Process all potions by station (Alembic → Agitator → Retort)
		addProcessingSteps();

		// Phase 3: Submit to conveyor
		addSubmissionStep();
	}

	private void addMixingSteps() {
		// Get all recipes we need to make
		List<PotionRecipe> recipes = getRecipesInOrder();

		for (PotionRecipe recipe : recipes) {
			// For each recipe, add steps for the levers and vessel
			addMixingStepsForRecipe(recipe);
		}
	}

	private void addMixingStepsForRecipe(PotionRecipe recipe) {
		// Add lever pulls based on recipe requirements
		// Each lever adds 10 paste, so we need to pull multiple times for recipes requiring more
		
		int moxPulls = recipe.getMoxAmount() / 10;
		int agaPulls = recipe.getAgaAmount() / 10;
		int lyePulls = recipe.getLyeAmount() / 10;

		// Step 1: Mox lever pulls
		for (int i = 0; i < moxPulls; i++) {
			allSteps.add(new WorkflowStep(
				WorkflowPhase.MIXING,
				recipe,
				null,
				WorkflowStep.HighlightTarget.MOX_LEVER,
				String.format("Pull Mox lever for %s (%d/%d)", recipe.getShortCode(), i + 1, moxPulls)
			));
		}

		// Step 2: Aga lever pulls
		for (int i = 0; i < agaPulls; i++) {
			allSteps.add(new WorkflowStep(
				WorkflowPhase.MIXING,
				recipe,
				null,
				WorkflowStep.HighlightTarget.AGA_LEVER,
				String.format("Pull Aga lever for %s (%d/%d)", recipe.getShortCode(), i + 1, agaPulls)
			));
		}

		// Step 3: Lye lever pulls
		for (int i = 0; i < lyePulls; i++) {
			allSteps.add(new WorkflowStep(
				WorkflowPhase.MIXING,
				recipe,
				null,
				WorkflowStep.HighlightTarget.LYE_LEVER,
				String.format("Pull Lye lever for %s (%d/%d)", recipe.getShortCode(), i + 1, lyePulls)
			));
		}

		// Step 4: Take from mixing vessel
		allSteps.add(new WorkflowStep(
			WorkflowPhase.MIXING,
			recipe,
			null,
			WorkflowStep.HighlightTarget.MIXING_VESSEL,
			String.format("Take %s from vessel", recipe.getShortCode())
		));
	}

	private void addProcessingSteps() {
		// Process in order: Alembic → Agitator → Retort
		for (ProcessStation station : PROCESSING_ORDER) {
			WorkflowPhase phase = getPhaseForStation(station);
			List<PotionRecipe> recipesForStation = getRecipesForStation(station);

			for (PotionRecipe recipe : recipesForStation) {
				// Add one step per potion at this station
				allSteps.add(new WorkflowStep(
					phase,
					recipe,
					station,
					getHighlightTargetForStation(station),
					String.format("Process %s at %s", recipe.getShortCode(), station.getStationName())
				));
			}
		}
	}

	private void addSubmissionStep() {
		allSteps.add(new WorkflowStep(
			WorkflowPhase.READY_TO_SUBMIT,
			null,
			null,
			WorkflowStep.HighlightTarget.CONVEYOR_BELT,
			"Spam click conveyor belt!"
		));
	}

	/**
	 * Gets recipes in the order they should be made.
	 * Priority: MAL first, then others.
	 */
	private List<PotionRecipe> getRecipesInOrder() {
		List<PotionRecipe> recipes = new ArrayList<>();

		// Add MAL first (6 times - 2 per station)
		for (int i = 0; i < 6; i++) {
			recipes.add(PotionRecipe.MAL);
		}

		// Add other recipes (3 times each - 1 per station)
		PotionRecipe[] otherRecipes = {
			PotionRecipe.ALA, PotionRecipe.ALL, PotionRecipe.AAM,
			PotionRecipe.MMA, PotionRecipe.MLL, PotionRecipe.MMM, PotionRecipe.MML
		};

		for (PotionRecipe recipe : otherRecipes) {
			for (int i = 0; i < 3; i++) {
				recipes.add(recipe);
			}
		}

		return recipes;
	}

	/**
	 * Gets all recipes that need to be processed at a given station.
	 * Returns them in inventory order for efficient clicking.
	 */
	private List<PotionRecipe> getRecipesForStation(ProcessStation station) {
		List<PotionRecipe> recipes = new ArrayList<>();
		List<PotionRecipe> allRecipes = getRecipesInOrder();

		// Each recipe appears 3 times (once per station)
		// We want them in the order they appear in inventory
		for (PotionRecipe recipe : allRecipes) {
			recipes.add(recipe);
		}

		return recipes;
	}

	private WorkflowPhase getPhaseForStation(ProcessStation station) {
		switch (station) {
			case ALEMBIC:
				return WorkflowPhase.PROCESSING_ALEMBIC;
			case AGITATOR:
				return WorkflowPhase.PROCESSING_AGITATOR;
			case RETORT:
				return WorkflowPhase.PROCESSING_RETORT;
			default:
				return WorkflowPhase.MIXING;
		}
	}

	private WorkflowStep.HighlightTarget getHighlightTargetForStation(ProcessStation station) {
		switch (station) {
			case ALEMBIC:
				return WorkflowStep.HighlightTarget.ALEMBIC;
			case AGITATOR:
				return WorkflowStep.HighlightTarget.AGITATOR;
			case RETORT:
				return WorkflowStep.HighlightTarget.RETORT;
			default:
				return WorkflowStep.HighlightTarget.MIXING_VESSEL;
		}
	}

	public WorkflowStep getCurrentStep() {
		if (currentStepIndex >= allSteps.size()) {
			return null;
		}
		return allSteps.get(currentStepIndex);
	}

	public void advanceStep() {
		currentStepIndex++;
	}

	public void reset() {
		currentStepIndex = 0;
		generateWorkflow();
	}

	public boolean isComplete() {
		return currentStepIndex >= allSteps.size();
	}

	public WorkflowPhase getCurrentPhase() {
		WorkflowStep step = getCurrentStep();
		return step != null ? step.getPhase() : WorkflowPhase.COMPLETE;
	}

	public int getProgressPercentage() {
		if (allSteps.isEmpty()) {
			return 0;
		}
		return (currentStepIndex * 100) / allSteps.size();
	}

	public String getProgressString() {
		return String.format("%d / %d steps", currentStepIndex, allSteps.size());
	}
}
