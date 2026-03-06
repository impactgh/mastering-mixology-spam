package com.masteringmixology;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents the current phase of the spam tech workflow.
 */
@Getter
@RequiredArgsConstructor
public enum WorkflowPhase {
	MIXING("Mixing Potions", "Mix all potions first"),
	PROCESSING_ALEMBIC("Processing at Alembic", "Process all Alembic potions"),
	PROCESSING_AGITATOR("Processing at Agitator", "Process all Agitator potions"),
	PROCESSING_RETORT("Processing at Retort", "Process all Retort potions"),
	READY_TO_SUBMIT("Ready to Submit", "Spam the conveyor belt!"),
	COMPLETE("Complete", "Inventory submitted");

	private final String displayName;
	private final String instruction;
}
