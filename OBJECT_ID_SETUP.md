# Object ID Setup Guide

The plugin needs actual object IDs from the game to enable highlighting features. Here's how to find them:

## Prerequisites

1. Install the **Developer Tools** plugin in RuneLite
2. Enable **Object Inspector** in the Developer Tools settings

## Finding Object IDs

### Step 1: Enter Mastering Mixology
Go to the Mastering Mixology minigame in Aldarin, Varlamore.

### Step 2: Enable Object Inspector
1. Open RuneLite settings
2. Find "Developer Tools"
3. Enable "Object Inspect"
4. Enable "Show IDs"

### Step 3: Right-Click Objects
Right-click each object and look for the ID in the menu or hover tooltip:

#### Processing Stations
- **Alembic** (Crystalise station) - Right-click and note the ID
- **Retort** (Concentrate station) - Right-click and note the ID
- **Agitator** (Homogenise station) - Right-click and note the ID

#### Core Objects
- **Refiner** - Where you put herbs to make paste
- **Hopper** - Stores the paste
- **Mixing Vessel** - Center object where you mix potions
- **Conveyor Belt** - Where you submit potions

#### Levers
- **Mox Lever** - Green lever
- **Aga Lever** - Red lever
- **Lye Lever** - Blue lever

#### Special
- **Digweed** - The plant that spawns randomly (wait for it to spawn)

### Step 4: Update the Plugin

Once you have the IDs, edit this file:
```
src/main/java/com/masteringmixology/MixologyObjectIDs.java
```

Replace the placeholder IDs (54_000, 54_001, etc.) with the actual IDs you found.

### Step 5: Find Region ID

1. Enable "Location" in Developer Tools
2. Look at your screen - it will show "Region: XXXXX"
3. Note this number
4. Edit `src/main/java/com/masteringmixology/MixologyRegions.java`
5. Replace `15_000` with the actual region ID

### Step 6: Rebuild

```bash
./gradlew clean build
```

## Example

If you find:
- Alembic ID: 54123
- Conveyor Belt ID: 54130
- Region ID: 15234

Update the files like this:

**MixologyObjectIDs.java:**
```java
public static final int ALEMBIC = 54123;
public static final int CONVEYOR_BELT = 54130;
```

**MixologyRegions.java:**
```java
public static final int MIXOLOGY_REGION = 15234;
```

## Testing

After updating and rebuilding:

1. Run the plugin
2. Enter Mastering Mixology
3. Check if highlighting works:
   - Make 27 potions (or adjust config)
   - Conveyor belt should highlight green
   - Next station should highlight cyan
   - Digweed should highlight yellow when it spawns

## Troubleshooting

**Nothing highlights?**
- Double-check the object IDs are correct
- Make sure you're in the Mixology area
- Check that highlighting is enabled in config

**Wrong objects highlight?**
- You may have copied the wrong ID
- Some objects have multiple IDs (different states)
- Try interacting with the object first, then check ID

**Region detection not working?**
- Verify the region ID is correct
- The region might span multiple IDs
- Check console logs for current region

## Alternative: Community Contribution

If you find the correct IDs, please share them! You can:
1. Create an issue on GitHub with the IDs
2. Submit a pull request with the updated values
3. Share in the RuneLite Discord

This will help other users get the plugin working immediately!

## Current Status

The plugin is fully functional for manual tracking. Object highlighting requires the correct IDs to be configured. All other features (potion tracking, recommendations, overlays) work without any setup!
