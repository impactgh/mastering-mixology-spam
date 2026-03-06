package com.masteringmixology;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProcessStation {
	ALEMBIC("Alembic", "Crystalise"),
	RETORT("Retort", "Concentrate"),
	AGITATOR("Agitator", "Homogenise");

	private final String stationName;
	private final String processName;
}
