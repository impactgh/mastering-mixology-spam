# Quick Start Guide

## Installation

1. **Install Java 11** (if not already installed)
   ```bash
   java -version
   ```

2. **Build the plugin**
   ```bash
   cd mastering-mixology-plugin
   ./gradlew build
   ```

3. **Install in RuneLite**
   - Copy the JAR from `build/libs/` to your RuneLite plugins folder
   - Or use `./gradlew run` to test directly

## Basic Usage

### Step 1: Configure
1. Open RuneLite settings
2. Find "Mastering Mixology Helper"
3. Enable "Enable Spam Tech"
4. Set "Target Potion Count" to 28 (recommended)

### Step 2: Play
1. Enter Mastering Mixology minigame
2. Make potions following the overlay suggestions
3. Watch the counter increase
4. When it says "SPAM CONVEYOR!" - spam click the conveyor belt
5. Repeat!

## Recommended Settings

### For Maximum XP
- Enable Spam Tech: ✓
- Target Potion Count: 28
- Prioritize MAL: ✓
- Skip Single Paste: ✗

### For Maximum Points
- Enable Spam Tech: ✓
- Target Potion Count: 28
- Prioritize MAL: ✓
- Skip Single Paste: ✓

## Tips

1. **Make diverse potions**: Try to make different types to maximize variety
2. **Use MAL when possible**: MixALot gives the best points per paste
3. **Group by station**: Make all Alembic potions together, then Retort, then Agitator
4. **Spam click efficiently**: Use mouse keys or rapid clicking when ready

## Troubleshooting

**Overlay not showing?**
- Check "Show Potion Tracker" is enabled in settings
- Make sure you're in the Mixology area

**Counter not increasing?**
- Currently requires manual tracking (automatic detection coming soon)
- Use the plugin panel to manually add potions

**Build errors?**
- Ensure Java 11 is installed
- Run `./gradlew clean build`

## Next Steps

- Read the full README.md for detailed information
- Check the strategy tips for optimal gameplay
- Customize settings to match your playstyle
