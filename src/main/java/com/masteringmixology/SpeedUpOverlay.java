package com.masteringmixology;

import net.runelite.api.Client;
import net.runelite.api.VarPlayer;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.*;

/**
 * Overlay showing visual cues for speed-up clicks at processing stations.
 */
public class SpeedUpOverlay extends Overlay {

	private final Client client;
	private final MasteringMixologyPlugin plugin;
	private final MasteringMixologyConfig config;
	private final PanelComponent panelComponent = new PanelComponent();

	// State tracking for speed-ups
	private ProcessStation currentStation = null;
	private int ticksSinceStart = 0;
	private boolean showSpeedUpCue = false;

	@Inject
	private SpeedUpOverlay(Client client, MasteringMixologyPlugin plugin, MasteringMixologyConfig config) {
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(OverlayPriority.HIGH);
	}

	@Override
	public Dimension render(Graphics2D graphics) {
		if (!config.showSpeedUpIndicator()) {
			return null;
		}

		if (!plugin.isInMixologyArea()) {
			return null;
		}

		panelComponent.getChildren().clear();

		// TODO: Detect which station player is using
		// This requires widget inspection or animation detection
		
		if (showSpeedUpCue) {
			renderSpeedUpCue(graphics);
		}

		return panelComponent.render(graphics);
	}

	private void renderSpeedUpCue(Graphics2D graphics) {
		// Large visual indicator
		panelComponent.getChildren().add(LineComponent.builder()
			.left(">>> CLICK NOW! <<<")
			.leftColor(Color.GREEN)
			.build());

		// Station-specific instructions
		if (currentStation != null) {
			String instruction = getStationInstruction(currentStation);
			panelComponent.getChildren().add(LineComponent.builder()
				.left(instruction)
				.leftColor(Color.YELLOW)
				.build());
		}
	}

	private String getStationInstruction(ProcessStation station) {
		switch (station) {
			case AGITATOR:
				return "Spam click at sound!";
			case ALEMBIC:
				return "Click on 5th pump!";
			case RETORT:
				return "Click every tick!";
			default:
				return "Click now!";
		}
	}

	public void onGameTick() {
		// TODO: Implement tick-based detection for speed-up windows
		// This requires detecting:
		// - Player animation
		// - Station interaction
		// - Timing windows for each station type
		
		ticksSinceStart++;
		
		// Example logic (needs actual game state detection):
		// if (currentStation == ProcessStation.ALEMBIC && ticksSinceStart == 5) {
		//     showSpeedUpCue = true;
		// }
	}

	public void reset() {
		currentStation = null;
		ticksSinceStart = 0;
		showSpeedUpCue = false;
	}

	public void setCurrentStation(ProcessStation station) {
		this.currentStation = station;
		this.ticksSinceStart = 0;
		this.showSpeedUpCue = false;
	}
}
