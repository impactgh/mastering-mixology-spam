package com.masteringmixology;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a single step in the workflow with what to highlight.
 */
@Getter
@RequiredArgsConstructor
public class WorkflowStep {
	private final WorkflowPhase phase;
	private final PotionRecipe recipe;
	private final ProcessStation targetStation;
	private final HighlightTarget highlightTarget;
	private final String instruction;

	public enum HighlightTarget {
		MOX_LEVER,
		AGA_LEVER,
		LYE_LEVER,
		MIXING_VESSEL,
		ALEMBIC,
		AGITATOR,
		RETORT,
		CONVEYOR_BELT
	}
}
