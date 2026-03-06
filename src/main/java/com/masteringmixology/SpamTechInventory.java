package com.masteringmixology;

import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the optimal spam tech inventory setup from the video strategy.
 * Each potion type is made at all 3 processing stations for maximum flexibility.
 */
@Getter
public class SpamTechInventory {
	
	// Target counts for each recipe (total across all 3 stations)
	private static final Map<PotionRecipe, Integer> TARGET_COUNTS = new HashMap<>();
	
	static {
		TARGET_COUNTS.put(PotionRecipe.ALA, 3);  // 1 per station
		TARGET_COUNTS.put(PotionRecipe.ALL, 3);  // 1 per station
		TARGET_COUNTS.put(PotionRecipe.AAM, 3);  // 1 per station
		TARGET_COUNTS.put(PotionRecipe.MMA, 3);  // 1 per station
		TARGET_COUNTS.put(PotionRecipe.MLL, 3);  // 1 per station
		TARGET_COUNTS.put(PotionRecipe.MMM, 2);  // Alternating with LLL
		TARGET_COUNTS.put(PotionRecipe.LLL, 1);  // Alternating with MMM
		TARGET_COUNTS.put(PotionRecipe.MML, 3);  // 1 per station
		TARGET_COUNTS.put(PotionRecipe.MAL, 6);  // 2 per station (prioritized)
	}
	
	// Tracks potions made by recipe and station
	private final Map<PotionRecipe, Map<ProcessStation, Integer>> inventory = new HashMap<>();
	
	public SpamTechInventory() {
		reset();
	}
	
	public void reset() {
		inventory.clear();
		for (PotionRecipe recipe : PotionRecipe.values()) {
			Map<ProcessStation, Integer> stationCounts = new HashMap<>();
			stationCounts.put(ProcessStation.ALEMBIC, 0);
			stationCounts.put(ProcessStation.RETORT, 0);
			stationCounts.put(ProcessStation.AGITATOR, 0);
			inventory.put(recipe, stationCounts);
		}
	}
	
	public void addPotion(PotionRecipe recipe, ProcessStation station) {
		inventory.get(recipe).merge(station, 1, Integer::sum);
	}
	
	public int getTotalCount(PotionRecipe recipe) {
		return inventory.get(recipe).values().stream().mapToInt(Integer::intValue).sum();
	}
	
	public int getStationCount(PotionRecipe recipe, ProcessStation station) {
		return inventory.get(recipe).get(station);
	}
	
	public int getTotalPotions() {
		return inventory.values().stream()
			.flatMap(map -> map.values().stream())
			.mapToInt(Integer::intValue)
			.sum();
	}
	
	public int getTargetCount(PotionRecipe recipe) {
		return TARGET_COUNTS.getOrDefault(recipe, 0);
	}
	
	public boolean isRecipeComplete(PotionRecipe recipe) {
		return getTotalCount(recipe) >= getTargetCount(recipe);
	}
	
	public boolean isInventoryComplete() {
		// Check if we have at least 27-28 potions total
		return getTotalPotions() >= 27;
	}
	
	/**
	 * Gets the next potion that should be made based on the spam tech strategy.
	 * Priority: MAL > incomplete recipes > balanced station distribution
	 */
	public PotionRecipe getNextRecommendedPotion() {
		// Priority 1: Complete MAL (need 6 total, 2 per station)
		if (getTotalCount(PotionRecipe.MAL) < 6) {
			return PotionRecipe.MAL;
		}
		
		// Priority 2: Fill out other recipes (need 3 each, 1 per station)
		for (PotionRecipe recipe : TARGET_COUNTS.keySet()) {
			if (recipe == PotionRecipe.MAL) continue;
			if (recipe == PotionRecipe.LLL) continue; // Skip LLL if using MMM
			
			if (getTotalCount(recipe) < getTargetCount(recipe)) {
				return recipe;
			}
		}
		
		return null; // Inventory complete
	}
	
	/**
	 * Gets the next station to use for a given recipe to maintain balance.
	 */
	public ProcessStation getNextRecommendedStation(PotionRecipe recipe) {
		Map<ProcessStation, Integer> counts = inventory.get(recipe);
		
		// Find the station with the lowest count for this recipe
		return counts.entrySet().stream()
			.min(Map.Entry.comparingByValue())
			.map(Map.Entry::getKey)
			.orElse(ProcessStation.ALEMBIC);
	}
	
	/**
	 * Gets a summary of what's needed to complete the inventory.
	 */
	public String getProgressSummary() {
		int total = getTotalPotions();
		int target = 27;
		
		if (total >= target) {
			return "Complete! Ready to spam conveyor!";
		}
		
		return String.format("%d / %d potions (%d remaining)", total, target, target - total);
	}
}
