package com.masteringmixology;

/**
 * Item IDs for Mastering Mixology potions.
 * All IDs confirmed from in-game testing.
 */
public class MixologyItemIDs {
	// Unfinished potions (after mixing, before processing)
	public static final int ALA_UNFINISHED = 30015; // AquaLux Amalgam
	public static final int ALL_UNFINISHED = 30018; // Anti-Leech Lotion
	public static final int MMA_UNFINISHED = 30012; // Mystic Mana Amalgam
	public static final int AAM_UNFINISHED = 30016; // Azure Aura Mix
	public static final int MLL_UNFINISHED = 30019; // MegaLite Liquid
	public static final int LLL_UNFINISHED = 30017; // LipLack Liquor
	public static final int MML_UNFINISHED = 30013; // Marley's MoonLight
	public static final int MAL_UNFINISHED = 30020; // MixALot
	public static final int MMM_UNFINISHED = 30014; // Mammoth-Might Mix
	public static final int AAA_UNFINISHED = 30011; // Alco-AugmentAtor
	
	// Processed potions (after any station - same ID for all 3 stations)
	public static final int ALA_PROCESSED = 30025;
	public static final int ALL_PROCESSED = 30028;
	public static final int MMA_PROCESSED = 30022;
	public static final int AAM_PROCESSED = 30026;
	public static final int MLL_PROCESSED = 30029;
	public static final int LLL_PROCESSED = 30027;
	public static final int MML_PROCESSED = 30023;
	public static final int MAL_PROCESSED = 30030;
	public static final int MMM_PROCESSED = 30024;
	public static final int AAA_PROCESSED = 30021;
	
	/**
	 * Gets the recipe from an item ID.
	 */
	public static PotionRecipe getRecipeFromItemId(int itemId) {
		switch (itemId) {
			case 30015: case 30025: return PotionRecipe.ALA;
			case 30018: case 30028: return PotionRecipe.ALL;
			case 30012: case 30022: return PotionRecipe.MMA;
			case 30016: case 30026: return PotionRecipe.AAM;
			case 30019: case 30029: return PotionRecipe.MLL;
			case 30017: case 30027: return PotionRecipe.LLL;
			case 30013: case 30023: return PotionRecipe.MML;
			case 30020: case 30030: return PotionRecipe.MAL;
			case 30014: case 30024: return PotionRecipe.MMM;
			case 30011: case 30021: return PotionRecipe.AAA;
			default: return null;
		}
	}
	
	/**
	 * Checks if an item is processed (vs unfinished).
	 */
	public static boolean isProcessed(int itemId) {
		return itemId == 30025 || itemId == 30028 || itemId == 30022 || 
		       itemId == 30026 || itemId == 30029 || itemId == 30027 || 
		       itemId == 30023 || itemId == 30030 || itemId == 30024 || itemId == 30021;
	}
	
	/**
	 * Gets the unfinished item ID for a recipe.
	 */
	public static int getUnfinishedId(PotionRecipe recipe) {
		switch (recipe) {
			case ALA: return 30015;
			case ALL: return 30018;
			case MMA: return 30012;
			case AAM: return 30016;
			case MLL: return 30019;
			case LLL: return 30017;
			case MML: return 30013;
			case MAL: return 30020;
			case MMM: return 30014;
			case AAA: return 30011;
			default: return -1;
		}
	}
	
	/**
	 * Gets the processed item ID for a recipe.
	 */
	public static int getProcessedId(PotionRecipe recipe) {
		switch (recipe) {
			case ALA: return 30025;
			case ALL: return 30028;
			case MMA: return 30022;
			case AAM: return 30026;
			case MLL: return 30029;
			case LLL: return 30027;
			case MML: return 30023;
			case MAL: return 30030;
			case MMM: return 30024;
			case AAA: return 30021;
			default: return -1;
		}
	}
	
	/**
	 * Checks if an item ID is a mixology potion.
	 */
	public static boolean isMixologyPotion(int itemId) {
		return (itemId >= 30011 && itemId <= 30020) || (itemId >= 30021 && itemId <= 30030);
	}
}
