package com.masteringmixology;

/**
 * Region IDs for Mastering Mixology area.
 * These need to be verified in-game.
 */
public class MixologyRegions {
	public static final int MIXOLOGY_REGION = 15_000; // TODO: Verify actual region ID
	
	public static boolean isInMixologyArea(int regionId) {
		return regionId == MIXOLOGY_REGION;
	}
}
