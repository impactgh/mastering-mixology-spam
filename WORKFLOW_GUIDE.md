# Efficient Workflow Guide

The plugin now implements an optimized workflow that guides you through the spam tech method with maximum efficiency.

## Workflow Overview

The workflow is divided into phases that minimize clicks and movement:

### Phase 1: Mixing (Cyan Highlights)
Mix all 27 potions WITHOUT processing them yet. This fills your inventory.

**For each potion:**
1. Click highlighted **Mox Lever** (if needed)
2. Click highlighted **Aga Lever** (if needed)
3. Click highlighted **Lye Lever** (if needed)
4. Click highlighted **Mixing Vessel** to take the potion

**Result:** 27 unprocessed potions in your inventory

### Phase 2: Processing - Alembic (Orange Highlights)
Process ALL Alembic potions in sequence.

**Why this order?** Your inventory is arranged so clicking the Alembic station automatically processes the next Alembic potion. No need to click "Use" on each potion!

**Potions processed:** ALA, ALL, AAM (and any others that need Alembic)

### Phase 3: Processing - Agitator (Orange Highlights)
Process ALL Agitator potions in sequence.

**Potions processed:** MMA, MAL, MMM (and any others that need Agitator)

### Phase 4: Processing - Retort (Orange Highlights)
Process ALL Retort potions in sequence.

**Potions processed:** MLL, MML, LLL (and any others that need Retort)

### Phase 5: Submit (Green Highlight)
Spam click the conveyor belt!

**Result:** Massive XP and points!

## Why This Order is Optimal

### 1. Inventory Arrangement
By mixing all potions first, then processing by station, your inventory is naturally ordered so:
- All Alembic potions are together
- All Agitator potions are together  
- All Retort potions are together

### 2. Minimal Clicks
You can just spam-click the processing station without using "Use" on each potion. The game automatically processes them in inventory order.

### 3. Optimal Pathing
The station order (Alembic → Agitator → Retort → Conveyor) follows the most efficient path through the room:
- Alembic: 1 tick from mixing area
- Agitator: 1 tick from Alembic
- Retort: 2 ticks from Agitator
- Conveyor: 2 ticks from Retort

### 4. No Backtracking
You never need to return to a previous station or the mixing vessel during processing.

## Using the Plugin

### Automatic Mode (Recommended)
1. Enable "Highlight Next Station" in config
2. Follow the highlighted objects in-game
3. The plugin automatically advances through the workflow
4. Overlay shows current phase and next step

### Manual Mode
1. Use the side panel buttons to track progress
2. Click "Next Step" to advance manually
3. Useful if highlights aren't working (need Object IDs)

## Example Workflow

```
MIXING PHASE (27 steps)
├─ MAL #1: Mox → Aga → Lye → Vessel
├─ MAL #2: Mox → Aga → Lye → Vessel
├─ MAL #3: Mox → Aga → Lye → Vessel
├─ MAL #4: Mox → Aga → Lye → Vessel
├─ MAL #5: Mox → Aga → Lye → Vessel
├─ MAL #6: Mox → Aga → Lye → Vessel
├─ ALA #1: Aga → Lye → Vessel
├─ ALA #2: Aga → Lye → Vessel
├─ ALA #3: Aga → Lye → Vessel
├─ ALL #1: Aga → Lye → Vessel
├─ ALL #2: Aga → Lye → Vessel
├─ ALL #3: Aga → Lye → Vessel
├─ AAM #1: Mox → Aga → Vessel
├─ AAM #2: Mox → Aga → Vessel
├─ AAM #3: Mox → Aga → Vessel
├─ MMA #1: Mox → Aga → Vessel
├─ MMA #2: Mox → Aga → Vessel
├─ MMA #3: Mox → Aga → Vessel
├─ MLL #1: Mox → Lye → Vessel
├─ MLL #2: Mox → Lye → Vessel
├─ MLL #3: Mox → Lye → Vessel
├─ MMM #1: Mox → Vessel
├─ MMM #2: Mox → Vessel
├─ MMM #3: Mox → Vessel
├─ MML #1: Mox → Lye → Vessel
├─ MML #2: Mox → Lye → Vessel
└─ MML #3: Mox → Lye → Vessel

PROCESSING PHASE - ALEMBIC (9 steps)
├─ Process ALA #1
├─ Process ALA #2
├─ Process ALA #3
├─ Process ALL #1
├─ Process ALL #2
├─ Process ALL #3
├─ Process AAM #1
├─ Process AAM #2
└─ Process AAM #3

PROCESSING PHASE - AGITATOR (12 steps)
├─ Process MAL #1
├─ Process MAL #2
├─ Process MAL #3
├─ Process MAL #4
├─ Process MAL #5
├─ Process MAL #6
├─ Process MMA #1
├─ Process MMA #2
├─ Process MMA #3
├─ Process MMM #1
├─ Process MMM #2
└─ Process MMM #3

PROCESSING PHASE - RETORT (6 steps)
├─ Process MLL #1
├─ Process MLL #2
├─ Process MLL #3
├─ Process MML #1
├─ Process MML #2
└─ Process MML #3

SUBMIT PHASE
└─ Spam conveyor belt!
```

## Tips

### Speed-Up Clicks
The plugin shows when to click for speed boosts:
- **Agitator**: Spam click when you see/hear the cue
- **Alembic**: Click on the 5th pump
- **Retort**: Click every tick

### Digweed
If digweed spawns during mixing phase:
- Finish your current potion
- Grab the digweed
- Continue with the workflow

Use digweed on MAL potions for double XP/points!

### Mistakes
If you make a mistake:
- Click "Next Step" to skip the current step
- Or click "Reset Counter" to start over
- The workflow is forgiving!

## Comparison to Random Order

| Aspect | Random Order | Workflow Order |
|--------|--------------|----------------|
| Clicks per potion | 5-7 | 4-5 |
| Movement | Constant | Minimal |
| Thinking required | High | None |
| Mistakes | Common | Rare |
| Speed | Slow | Fast |
| Fun | Tedious | Smooth |

## Conclusion

The workflow system transforms spam tech from a complex juggling act into a smooth, guided experience. Just follow the highlights and enjoy the gains!
