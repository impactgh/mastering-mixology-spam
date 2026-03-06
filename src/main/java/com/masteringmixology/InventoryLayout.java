package com.masteringmixology;

import lombok.Getter;

/**
 * Defines the optimal 27-potion inventory layout for spam tech.
 * Each slot specifies which potion should be there.
 */
@Getter
public class InventoryLayout {
	
	// The optimal order: MAL potions mixed in, but move them to slots 21-26 before conveyor
	// Changed middle LLL to MMM for better balance
	private static final PotionRecipe[] LAYOUT = {
		// First set (will process at Alembic)
		PotionRecipe.ALA,  // 0
		PotionRecipe.ALL,  // 1
		PotionRecipe.MMA,  // 2
		PotionRecipe.AAM,  // 3
		PotionRecipe.MLL,  // 4
		PotionRecipe.MMM,  // 5 - Changed from LLL
		PotionRecipe.MML,  // 6
		PotionRecipe.MAL,  // 7
		PotionRecipe.MAL,  // 8
		
		// Second set (will process at Agitator)
		PotionRecipe.ALA,  // 9
		PotionRecipe.ALL,  // 10
		PotionRecipe.MMA,  // 11
		PotionRecipe.AAM,  // 12
		PotionRecipe.MLL,  // 13
		PotionRecipe.LLL,  // 14
		PotionRecipe.MML,  // 15
		PotionRecipe.MAL,  // 16
		PotionRecipe.MAL,  // 17
		
		// Third set (will process at Retort)
		PotionRecipe.ALA,  // 18
		PotionRecipe.ALL,  // 19
		PotionRecipe.MMA,  // 20
		PotionRecipe.AAM,  // 21
		PotionRecipe.MLL,  // 22
		PotionRecipe.LLL,  // 23
		PotionRecipe.MML,  // 24
		PotionRecipe.MAL,  // 25
		PotionRecipe.MAL   // 26
	};
	
	/**
	 * Gets the recipe that should be in a specific inventory slot.
	 */
	public static PotionRecipe getRecipeForSlot(int slot) {
		if (slot < 0 || slot >= LAYOUT.length) {
			return null;
		}
		return LAYOUT[slot];
	}
	
	/**
	 * Gets the total number of slots in the layout.
	 */
	public static int getTotalSlots() {
		return LAYOUT.length;
	}
	
	/**
	 * Gets which station should process a given slot range.
	 */
	public static ProcessStation getStationForSlot(int slot) {
		if (slot >= 0 && slot <= 8) {
			return ProcessStation.ALEMBIC;
		} else if (slot >= 9 && slot <= 17) {
			return ProcessStation.AGITATOR;
		} else if (slot >= 18 && slot <= 26) {
			return ProcessStation.RETORT;
		}
		return null;
	}
	
	/**
	 * Gets the next slot that needs to be filled (first empty or incorrect slot).
	 */
	public static int getNextSlotToFill(Item[] inventoryItems) {
		for (int i = 0; i < LAYOUT.length; i++) {
			if (i >= inventoryItems.length || inventoryItems[i] == null) {
				return i;
			}
			
			int itemId = inventoryItems[i].getId();
			PotionRecipe expectedRecipe = LAYOUT[i];
			PotionRecipe actualRecipe = MixologyItemIDs.getRecipeFromItemId(itemId);
			
			if (actualRecipe != expectedRecipe) {
				return i;
			}
		}
		return -1; // All slots filled correctly
	}
	
	// Helper class to avoid importing RuneLite's Item
	public static class Item {
		private final int id;
		
		public Item(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
	}
}
