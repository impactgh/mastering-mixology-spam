# Mastering Mixology - Simple Inventory Helper

This plugin provides a minimal inventory helper for the Mastering Mixology spam tech method.

## What It Does

The plugin helps you fill your inventory with the correct 27 potions in the right order, then tracks your progress as you process them.

### Features

1. **Inventory Slot Highlighting**
   - Yellow border = Correct unprocessed potion in slot
   - Green border = Correct processed potion in slot  
   - Red border = Wrong potion or empty slot

2. **Simple Overlay Display**
   - Shows next potion to make (e.g., "ALA (slot 0)")
   - Shows current slot number
   - Counts processed vs unprocessed potions

### The 27-Potion Layout

The plugin expects this exact order (MAL potions mixed in with each batch):

```
Slots 0-8:   ALA, ALL, MMA, AAM, MLL, LLL, MML, MAL, MAL (Alembic)
Slots 9-17:  ALA, ALL, MMA, AAM, MLL, LLL, MML, MAL, MAL (Agitator)
Slots 18-26: ALA, ALL, MMA, AAM, MLL, LLL, MML, MAL, MAL (Retort)
```

### How to Use

1. Start the minigame
2. Follow the overlay instructions to make each potion
3. The plugin will highlight slots as you fill them
4. Once all 27 are made (yellow borders), process them:
   - Slots 0-8 → Alembic
   - Slots 9-17 → Agitator
   - Slots 18-26 → Retort
5. **IMPORTANT**: After all 27 are processed, move all 6 MAL potions to slots 21-26
   - The plugin will warn you: "MOVE 6 MAL TO SLOTS 21-26!"
   - This prevents them from being used during conveyor spam
   - Only proceed when the last 6 slots (21-26) are all green MAL potions
6. When ready, spam the conveyor belt!

### Configuration

Two simple options in the plugin settings:
- Show Inventory Helper (overlay on/off)
- Highlight Inventory Slots (highlighting on/off)

## Files

Core files:
- `MasteringMixologyPlugin.java` - Main plugin class
- `MasteringMixologyOverlay.java` - Text overlay showing next potion
- `InventorySetupOverlay.java` - Inventory slot highlighting
- `InventoryLayout.java` - Defines the 27-potion order
- `MixologyItemIDs.java` - All potion item IDs
- `PotionRecipe.java` - Potion recipe definitions
- `ProcessStation.java` - Station enum (Alembic/Agitator/Retort)
- `MasteringMixologyConfig.java` - Plugin settings

## Building

```bash
./gradlew build
```

The plugin JAR will be in `build/libs/`.
