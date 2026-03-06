package com.masteringmixology;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PotionRecipe {
	// Format: Name(mox, aga, lye, level, xp, station)
	AAA("Alco-AugmentAtor", 0, 30, 0, 60, 190, ProcessStation.ALEMBIC),
	MMM("Mammoth-Might Mix", 30, 0, 0, 60, 190, ProcessStation.AGITATOR),
	LLL("LipLack Liquor", 0, 0, 30, 60, 190, ProcessStation.RETORT),
	MMA("Mystic Mana Amalgam", 20, 10, 0, 63, 215, ProcessStation.AGITATOR),
	MML("Marley's MoonLight", 20, 0, 10, 66, 240, ProcessStation.RETORT),
	AAM("Azure Aura Mix", 10, 20, 0, 69, 265, ProcessStation.ALEMBIC),
	ALA("AquaLux Amalgam", 0, 20, 10, 72, 290, ProcessStation.ALEMBIC),
	MLL("MegaLite Liquid", 10, 0, 20, 75, 315, ProcessStation.RETORT),
	ALL("Anti-Leech Lotion", 0, 10, 20, 78, 340, ProcessStation.ALEMBIC),
	MAL("MixALot", 10, 10, 10, 81, 365, ProcessStation.AGITATOR);

	private final String displayName;
	private final int moxAmount;
	private final int agaAmount;
	private final int lyeAmount;
	private final int levelRequired;
	private final int xpReward;
	private final ProcessStation station;

	public String getShortCode() {
		return this.name();
	}

	public int getTotalPaste() {
		return moxAmount + agaAmount + lyeAmount;
	}
}
