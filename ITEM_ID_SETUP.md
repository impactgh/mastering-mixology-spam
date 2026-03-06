# Item ID Setup for Automatic Inventory Detection

The plugin can automatically detect potions in your inventory once we find the item IDs!

## Why This Matters

With item IDs configured, the plugin will:
- ✅ Automatically count potions as you make them
- ✅ Update progress in real-time
- ✅ No need to click buttons manually
- ✅ Track which station each potion was processed at

## How to Find Item IDs

### Step 1: Enable Item Inspector

1. Install **Developer Tools** plugin in RuneLite
2. Enable "Item Inspect" in the settings
3. Enable "Show IDs"

### Step 2: Make Potions and Check IDs

For each potion type, you need to find **4 item IDs**:

1. **Unfinished** - After mixing, before processing
2. **Crystalised** - After processing at Alembic
3. **Concentrated** - After processing at Retort
4. **Homogenised** - After processing at Agitator

### Step 3: Record the IDs

Make a MAL potion and check its ID at each stage:

```
1. Mix MAL (10 Mox + 10 Aga + 10 Lye)
   → Hover over it in inventory
   → Note the ID (e.g., 29123)
   → This is MAL_UNFINISHED

2. Process at Alembic
   → Hover over it in inventory
   → Note the ID (e.g., 29124)
   → This is MAL_CRYSTALISED

3. Make another MAL, process at Retort
   → Note the ID (e.g., 29125)
   → This is MAL_CONCENTRATED

4. Make another MAL, process at Agitator
   → Note the ID (e.g., 29126)
   → This is MAL_HOMOGENISED
```

### Step 4: Repeat for All Recipes

You need to do this for all 10 potion types:
- MAL (MixALot)
- ALA (AquaLux Amalgam)
- ALL (Anti-Leech Lotion)
- AAM (Azure Aura Mix)
- MMA (Mystic Mana Amalgam)
- MLL (MegaLite Liquid)
- MMM (Mammoth-Might Mix)
- LLL (LipLack Liquor)
- MML (Marley's MoonLight)
- AAA (Alco-AugmentAtor) - optional, not used in spam tech

That's **40 item IDs total** (10 recipes × 4 variants each).

### Step 5: Update the Code

Edit `src/main/java/com/masteringmixology/MixologyItemIDs.java`:

```java
// Example:
public static final int MAL_UNFINISHED = 29123;
public static final int MAL_CRYSTALISED = 29124;
public static final int MAL_CONCENTRATED = 29125;
public static final int MAL_HOMOGENISED = 29126;
```

Then implement the mapping functions:

```java
public static PotionRecipe getRecipeFromItemId(int itemId) {
    // MAL
    if (itemId == 29123 || itemId == 29124 || itemId == 29125 || itemId == 29126) {
        return PotionRecipe.MAL;
    }
    // ... repeat for all recipes
    return null;
}

public static ProcessStation getStationFromItemId(int itemId) {
    // Crystalised = Alembic
    if (itemId == 29124 || itemId == ...) {
        return ProcessStation.ALEMBIC;
    }
    // Concentrated = Retort
    if (itemId == 29125 || itemId == ...) {
        return ProcessStation.RETORT;
    }
    // Homogenised = Agitator
    if (itemId == 29126 || itemId == ...) {
        return ProcessStation.AGITATOR;
    }
    return null;
}
```

### Step 6: Enable Auto-Detection

In `MasteringMixologyPlugin.java`, uncomment these lines:

```java
// In scanInventoryForPotions():
PotionRecipe recipe = MixologyItemIDs.getRecipeFromItemId(itemId);
ProcessStation station = MixologyItemIDs.getStationFromItemId(itemId);
if (recipe != null && station != null) {
    foundPotions.get(recipe).merge(station, 1, Integer::sum);
}

// At the end:
updateInventoryFromScan(foundPotions);
```

### Step 7: Rebuild and Test

```bash
./gradlew build
```

Now when you make potions, the plugin will automatically detect them!

## Quick Test

1. Start with empty inventory
2. Make 1 MAL potion
3. Check overlay - should show "MAL 1/6"
4. Process it at Alembic
5. Make another MAL, process at Retort
6. Check overlay - should show "MAL 2/6"

## Tips

### Finding IDs Faster

- Make all 10 unfinished potions first
- Note all their IDs
- Then process them one by one

### Pattern Recognition

Item IDs are usually sequential:
- If MAL_UNFINISHED is 29123
- Then MAL_CRYSTALISED might be 29124
- And MAL_CONCENTRATED might be 29125
- And MAL_HOMOGENISED might be 29126

### Verification

After updating the code:
- Test with one potion type first
- Make sure detection works
- Then add the rest

## Community Contribution

If you find all the item IDs, please share them!
- Create a GitHub issue with the IDs
- Submit a pull request
- Post in RuneLite Discord

This will help everyone use automatic detection immediately!

## Current Status

**Without Item IDs:**
- ✅ Manual tracking works (click buttons)
- ✅ Workflow guidance works
- ❌ Automatic inventory detection disabled

**With Item IDs:**
- ✅ Automatic inventory detection
- ✅ Real-time progress updates
- ✅ No manual clicking needed
- ✅ Perfect accuracy

The plugin is designed to work both ways - manual mode now, automatic mode once IDs are found!
