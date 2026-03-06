package com.masteringmixology;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Slf4j
public class MasteringMixologyPanel extends PluginPanel {

	private final MasteringMixologyPlugin plugin;
	private final JLabel potionCountLabel;
	private final JLabel statusLabel;
	private final JLabel nextPotionLabel;
	private final JPanel potionButtonsPanel;
	private ProcessStation selectedStation = ProcessStation.ALEMBIC;

	public MasteringMixologyPanel(MasteringMixologyPlugin plugin) {
		this.plugin = plugin;

		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));

		// Title
		JLabel titleLabel = new JLabel("Mastering Mixology Helper");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(titleLabel, BorderLayout.NORTH);

		// Main content panel
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

		// Status section
		JPanel statusPanel = new JPanel(new GridLayout(3, 1, 5, 5));
		potionCountLabel = new JLabel("Potions Made: 0 / 27");
		potionCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		statusLabel = new JLabel("Status: Make potions");
		statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
		nextPotionLabel = new JLabel("Next: MAL");
		nextPotionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
		nextPotionLabel.setForeground(Color.ORANGE);
		statusPanel.add(potionCountLabel);
		statusPanel.add(statusLabel);
		statusPanel.add(nextPotionLabel);
		contentPanel.add(statusPanel);

		contentPanel.add(Box.createVerticalStrut(15));

		// Station selector
		JLabel stationLabel = new JLabel("Select Processing Station:");
		stationLabel.setFont(new Font("Arial", Font.BOLD, 12));
		contentPanel.add(stationLabel);

		contentPanel.add(Box.createVerticalStrut(5));

		JPanel stationPanel = new JPanel(new GridLayout(1, 3, 5, 5));
		ButtonGroup stationGroup = new ButtonGroup();
		
		for (ProcessStation station : ProcessStation.values()) {
			JRadioButton radioButton = new JRadioButton(station.getStationName());
			radioButton.setSelected(station == ProcessStation.ALEMBIC);
			radioButton.addActionListener(e -> selectedStation = station);
			stationGroup.add(radioButton);
			stationPanel.add(radioButton);
		}
		contentPanel.add(stationPanel);

		contentPanel.add(Box.createVerticalStrut(10));

		// Quick add buttons
		JLabel quickAddLabel = new JLabel("Quick Add Potion:");
		quickAddLabel.setFont(new Font("Arial", Font.BOLD, 12));
		contentPanel.add(quickAddLabel);

		contentPanel.add(Box.createVerticalStrut(5));

		potionButtonsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
		for (PotionRecipe recipe : PotionRecipe.values()) {
			// Skip recipes not in spam tech strategy
			if (recipe == PotionRecipe.AAA) {
				continue;
			}
			
			JButton button = new JButton(recipe.getShortCode());
			button.setToolTipText(recipe.getDisplayName());
			button.addActionListener(e -> {
				plugin.addPotionMade(recipe, selectedStation);
				updateDisplay();
			});
			potionButtonsPanel.add(button);
		}
		contentPanel.add(potionButtonsPanel);

		contentPanel.add(Box.createVerticalStrut(15));

		// Control buttons
		JPanel controlPanel = new JPanel(new GridLayout(3, 1, 5, 5));
		
		JButton advanceButton = new JButton("Next Step");
		advanceButton.setToolTipText("Advance to next workflow step");
		advanceButton.addActionListener(e -> {
			plugin.advanceWorkflowStep();
			updateDisplay();
		});
		controlPanel.add(advanceButton);
		
		JButton resetButton = new JButton("Reset Counter");
		resetButton.addActionListener(e -> {
			plugin.resetTracking();
			updateDisplay();
		});
		controlPanel.add(resetButton);

		JButton submitButton = new JButton("Submit Potions");
		submitButton.addActionListener(e -> {
			plugin.submitPotions();
			updateDisplay();
		});
		controlPanel.add(submitButton);

		contentPanel.add(controlPanel);

		add(contentPanel, BorderLayout.CENTER);

		// Info section
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

		JLabel infoLabel = new JLabel("<html><center>Follow the highlighted objects in-game<br>or click 'Next Step' to advance manually</center></html>");
		infoLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		infoLabel.setForeground(Color.GRAY);
		infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoPanel.add(infoLabel);

		add(infoPanel, BorderLayout.SOUTH);

		updateDisplay();
	}

	public void updateDisplay() {
		SpamTechInventory inventory = plugin.getSpamTechInventory();
		WorkflowManager workflow = plugin.getWorkflowManager();
		
		int total = inventory.getTotalPotions();
		int target = 27;
		
		potionCountLabel.setText("Potions Made: " + total + " / " + target);

		if (plugin.isReadyToSpam()) {
			statusLabel.setText("Status: SPAM CONVEYOR!");
			statusLabel.setForeground(Color.GREEN);
			nextPotionLabel.setText("Ready to submit!");
			nextPotionLabel.setForeground(Color.GREEN);
		} else {
			int remaining = target - total;
			statusLabel.setText("Status: " + remaining + " more needed");
			statusLabel.setForeground(Color.ORANGE);
			
			if (workflow != null) {
				WorkflowStep currentStep = workflow.getCurrentStep();
				if (currentStep != null) {
					nextPotionLabel.setText(currentStep.getInstruction());
					nextPotionLabel.setForeground(Color.CYAN);
				} else {
					nextPotionLabel.setText("Workflow complete");
					nextPotionLabel.setForeground(Color.GREEN);
				}
			}
		}

		revalidate();
		repaint();
	}
}
