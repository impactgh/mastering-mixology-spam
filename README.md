# Mastering Mixology Helper Plugin

A RuneLite plugin that assists with the "spam tech" method for the Mastering Mixology minigame in OSRS.

## What is Spam Tech?

The spam tech method is an efficient strategy for Mastering Mixology discovered by the community:

**The Strategy:**
1. Create 27-28 potions with a specific recipe distribution
2. Process each recipe type at ALL 3 stations (Alembic, Retort, Agitator)
3. This gives you maximum flexibility - no matter what order appears, you have the right potion already processed
4. Spam-click the conveyor belt to submit potions rapidly

**Optimal Inventory Setup (from video):**
- 3x ALA (AquaLux Amalgam) - 1 per station
- 3x ALL (Anti-Leech Lotion) - 1 per station  
- 3x AAM (Azure Aura Mix) - 1 per station
- 3x MMA (Mystic Mana Amalgam) - 1 per station
- 3x MLL (MegaLite Liquid) - 1 per station
- 3x MMM or LLL - 1 per station
- 3x MML (Marley's MoonLight) - 1 per station
- 6x MAL (MixALot) - 2 per station (prioritized for best points)

**Total: 27 potions** covering all recipe types at all processing stations!

Based on the strategy from [this video](https://www.youtube.com/watch?v=tfVtxUWhFJE).

## Features

### Current Features
- **Spam Tech Inventory Tracker**: Tracks the exact 27-potion setup from the video
- **Station-Aware Tracking**: Knows which potions you've made at each station
- **Smart Recommendations**: Suggests next potion and which station to use
- **Progress Display**: Shows completion status for each recipe type
- **Ready Indicator**: Shows when you've completed the full inventory
- **Manual Tracking Panel**: Click buttons to track potions as you make potions
- **Object Highlighting**: Highlights machines, conveyor belt, and digweed (requires setup)
- **Speed-Up Indicators**: Visual cues for when to click for speed boosts
- **Digweed Notifications**: Alert when digweed spawns
- **Configurable Settings**: Customize all display and highlighting options

### Planned Features
- Automatic potion detection from inventory
- Widget-based order tracking
- Paste level monitoring
- Session statistics (XP/hr, potions/hr)
- Hotkey support

## Installation

### Method 1: Build from Source
1. Ensure you have Java 11 installed
2. Clone or download this repository
3. Navigate to the plugin directory:
   ```bash
   cd mastering-mixology-plugin
   ```
4. Build the plugin:
   ```bash
   ./gradlew build
   ```
5. The plugin JAR will be in `build/libs/`

### Method 2: RuneLite Plugin Hub
(Coming soon - plugin needs to be submitted to Plugin Hub)

## Configuration

Access the plugin settings in RuneLite's configuration panel:

### Spam Tech Settings
- **Enable Spam Tech**: Toggle the spam tech method on/off
- **Target Potion Count**: Number of potions before ready (default: 27)
- **Highlight Conveyor**: Highlight the conveyor belt when ready

### Recipe Priority
- **Prioritize MAL**: Always suggest MixALot (MAL) first
- **Skip Single Paste**: Skip AAA/MMM/LLL unless MAL is in order

### Display Settings
- **Show Potion Tracker**: Display the overlay with potion counts
- **Show Recipe Suggestions**: Show suggested recipes to make
- **Play Sound Alert**: Audio notification when ready to spam

### Highlighting (Requires Object ID Setup)
- **Highlight Next Station**: Highlight the recommended processing station
- **Highlight Digweed**: Highlight and notify when digweed spawns
- **Digweed Notification**: Send system notification for digweed

### Speed-Up Helper
- **Show Speed-Up Indicator**: Visual cues for when to click for speed boosts
- **Speed-Up Sound**: Audio cue for speed-up windows

## Object ID Setup

For highlighting features to work, you need to configure object IDs:

1. Install RuneLite's **Developer Tools** plugin
2. Enable **Object Inspector**
3. Find object IDs in-game (see [OBJECT_ID_SETUP.md](OBJECT_ID_SETUP.md))
4. Update `MixologyObjectIDs.java` with actual IDs
5. Rebuild the plugin

The plugin works perfectly for manual tracking without this setup!

## How to Use

1. **Start the Minigame**: Enter the Mastering Mixology area in Aldarin
2. **Open the Plugin Panel**: Find "Mastering Mixology Helper" in your RuneLite sidebar
3. **Select Station**: Choose which processing station you're using (Alembic/Retort/Agitator)
4. **Make Potions**: Follow the "Next" recommendation in the panel
5. **Click the Button**: After making each potion, click its button in the panel
6. **Watch Progress**: The overlay shows your progress (e.g., "ALA 2/3" means you need 1 more)
7. **Spam Conveyor**: When it says "SPAM CONVEYOR!", rapidly click the conveyor belt
8. **Repeat**: The tracker resets automatically after submission

**Key Point**: Make each recipe type at ALL 3 stations! For example:
- Make 1 MMA at Alembic (even though it's "wrong")
- Make 1 MMA at Retort (even though it's "wrong")  
- Make 1 MMA at Agitator (the "correct" station)

This gives you maximum flexibility when orders appear!

## Potion Recipes

All 10 potion types are tracked:

| Code | Name | Mox | Aga | Lye | Level | Station |
|------|------|-----|-----|-----|-------|---------|
| AAA | Alco-AugmentAtor | 0 | 30 | 0 | 60 | Alembic |
| MMM | Mammoth-Might Mix | 30 | 0 | 0 | 60 | Agitator |
| LLL | LipLack Liquor | 0 | 0 | 30 | 60 | Retort |
| MMA | Mystic Mana Amalgam | 20 | 10 | 0 | 63 | Agitator |
| MML | Marley's MoonLight | 20 | 0 | 10 | 66 | Retort |
| AAM | Azure Aura Mix | 10 | 20 | 0 | 69 | Alembic |
| ALA | AquaLux Amalgam | 0 | 20 | 10 | 72 | Alembic |
| MLL | MegaLite Liquid | 10 | 0 | 20 | 75 | Retort |
| ALL | Anti-Leech Lotion | 0 | 10 | 20 | 78 | Alembic |
| MAL | MixALot | 10 | 10 | 10 | 81 | Agitator |

## Strategy Tips

### Optimal Recipe Selection
- **MAL (MixALot)**: Always prioritize - best points per paste (2.8x with triple order)
- **Double-Paste Potions**: MMA, MML, AAM, ALA, MLL, ALL - good efficiency
- **Single-Paste Potions**: AAA, MMM, LLL - skip unless MAL is in order

### Efficient Pathing
1. **Alembic/Crystalise** → 1 tick to conveyor
2. **Retort/Concentrate** → 2 ticks to conveyor  
3. **Agitator/Homogenise** → 1 tick to conveyor

Group potions by station to minimize movement.

### Processing Tips
- **Agitator**: Spam click at the sound/visual cue for 66% speed boost
- **Alembic**: Click on the 5th pump for 33% speed boost
- **Retort**: Click every tick for 50% speed boost

## Development

### Project Structure
```
mastering-mixology-plugin/
├── src/main/java/com/masteringmixology/
│   ├── MasteringMixologyPlugin.java      # Main plugin class
│   ├── MasteringMixologyConfig.java      # Configuration interface
│   ├── MasteringMixologyOverlay.java     # UI overlay
│   ├── PotionRecipe.java                 # Potion definitions
│   └── ProcessStation.java               # Station enum
├── src/main/resources/
│   └── runelite-plugin.properties        # Plugin metadata
└── build.gradle                          # Build configuration
```

### Building
```bash
./gradlew build
```

### Running in Development
```bash
./gradlew run
```

## Contributing

Contributions are welcome! Areas for improvement:
- Region detection for Mixology area
- Widget interaction for conveyor belt
- Paste tracking from game state
- Order detection and tracking
- Click timing overlays for processing stations

## Credits

- Strategy based on the spam tech method
- Built for the OSRS community
- Uses the RuneLite API

## License

This project is licensed under the BSD 2-Clause License - see the LICENSE file for details.

## Disclaimer

This plugin is a tool to assist with the Mastering Mixology minigame. It does not automate any actions - all clicking and gameplay is manual. Use at your own discretion.
# mastering-mixology-spam
