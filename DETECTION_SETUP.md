# Automatic Detection Setup Guide

The plugin currently requires manual tracking (clicking buttons). To enable automatic detection, we need to find the game's internal IDs.

## What We Need

1. **Object IDs** - For highlighting objects
2. **Varbit IDs** - For detecting game state changes
3. **Region ID** - For detecting when you're in the minigame

## Step-by-Step Setup

### Part 1: Install Developer Tools

1. Open RuneLite
2. Click the wrench icon (Configuration)
3. Search for "Developer Tools"
4. Enable the plugin
5. Configure it:
   - Enable "Object Inspect"
   - Enable "Varbit Inspector"
   - Enable "Location"

### Part 2: Find Region ID

1. Enter the Mastering Mixology minigame
2. Look at your screen - you'll see "Region: XXXXX"
3. Note this number
4. Edit `src/main/java/com/masteringmixology/MixologyRegions.java`
5. Replace `15_000` with your region ID

### Part 3: Find Object IDs

Right-click each object and note the ID shown:

**Required Objects:**
- Mox Lever (green)
- Aga Lever (red)
- Lye Lever (blue)
- Mixing Vessel (center)
- Alembic (Crystalise station)
- Retort (Concentrate station)
- Agitator (Homogenise station)
- Conveyor Belt
- Refiner
- Hopper
- Digweed (wait for it to spawn)

Update `src/main/java/com/masteringmixology/MixologyObjectIDs.java` with these IDs.

### Part 4: Find Varbit IDs (Advanced)

This is the tricky part. Varbits track game state.

**Method 1: Watch Varbit Inspector**

1. Open the Varbit Inspector (Developer Tools)
2. Perform an action (e.g., pull Mox lever)
3. Watch which varbits change
4. Note the varbit ID and what it represents

**Actions to Test:**
- Pull each lever (Mox, Aga, Lye)
- Take potion from vessel
- Process potion at each station
- Submit potion to conveyor
- Wait for digweed to spawn

**Method 2: Use Existing Plugins as Reference**

Check if other Mixology plugins have found these IDs:
- Look at hex-agon's plugin source
- Check RuneLite's plugin hub
- Search Discord/Reddit for shared IDs

Update `src/main/java/com/masteringmixology/MixologyVarbits.java` with found IDs.

### Part 5: Enable Automatic Detection

Once you have the IDs, uncomment the detection code in:
- `MasteringMixologyPlugin.java` - `onVarbitChanged()` method
- `MixologySceneOverlay.java` - Object highlighting

### Part 6: Test

1. Rebuild: `./gradlew build`
2. Run the plugin
3. Enter Mixology
4. Pull a lever - it should auto-advance
5. Check console logs for any errors

## Current Status

**Working Without Setup:**
- ✅ Manual tracking (click buttons)
- ✅ Workflow guidance
- ✅ Progress display
- ✅ Recipe recommendations

**Requires Setup:**
- ❌ Automatic lever detection
- ❌ Object highlighting
- ❌ Digweed notifications
- ❌ Region detection

## Alternative: Manual Mode

If you can't find the IDs, the plugin works great in manual mode:

1. Follow the on-screen instructions
2. Click "Next Step" button after each action
3. Or click the potion buttons as you make them

The workflow system still guides you through the optimal order!

## Community Help

If you find the correct IDs, please share them!
- Create a GitHub issue
- Submit a pull request
- Share in RuneLite Discord

This will help everyone use the plugin immediately!

## Example: Finding Mox Lever

1. Stand near the Mox lever
2. Open Varbit Inspector
3. Note current varbit values
4. Pull the Mox lever
5. See which varbits changed
6. The one that increased by 10 is likely `VESSEL_MOX`
7. Update the code with that varbit ID

## Troubleshooting

**Varbit Inspector shows too many changes?**
- Filter by varbits that changed by exactly 10 (paste amount)
- Try pulling the lever multiple times
- Compare with other levers

**Object IDs keep changing?**
- Some objects have multiple IDs for different states
- Use the ID shown when the object is in its default state
- Test in-game to verify

**Region detection not working?**
- The minigame might span multiple regions
- Add all region IDs to the check
- Use `||` to check multiple regions

## Quick Reference

```java
// Example of what we're looking for:

// When you pull Mox lever:
Varbit 12345 changes from 0 → 10  // This is VESSEL_MOX

// When you pull Aga lever:
Varbit 12346 changes from 0 → 10  // This is VESSEL_AGA

// When you take from vessel:
Varbit 12345 changes from 10 → 0  // Vessel emptied

// When processing at Alembic:
Varbit 12350 changes from 0 → 1  // Station busy
Varbit 12350 changes from 1 → 0  // Station done
```

Good luck! The plugin is fully functional in manual mode while you search for IDs.
