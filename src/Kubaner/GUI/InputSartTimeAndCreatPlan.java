package Kubaner.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import Kubaner.Logic.Plan;
import Kubaner.Logic.PlanGenerator;
import Kubaner.Logic.Time;

public class InputSartTimeAndCreatPlan  extends JFrame implements ActionListener {

	private Time planTime;
	private JSpinner startTimeHours;
	private JSpinner startTimeMinutes;
	private JPanel controlPanel;
	private JButton confirmButton;
	private JButton cancelButton;
	private PlanGenerator planGen;
	private ClientGUI cGui;
	
	InputSartTimeAndCreatPlan(PlanGenerator planGen, ClientGUI cGui){
		
		this.planGen = planGen;
		this.cGui = cGui;
		
		setTitle("Eingabe der Startzeit");
		
		getContentPane().setLayout(new BorderLayout());
		
		JLabel timeStartLabel = new JLabel("Bitte geben sie DIe Uhrzeit an, an dem die Prüfungen Starten sollen: ");
		
		JPanel timeStartPanel = new JPanel();
		timeStartPanel.setLayout(new GridLayout(1, 2));
		
		
		SpinnerNumberModel startTimeHoursModel = new SpinnerNumberModel(0, 0, 23, 1);
		SpinnerNumberModel startTimeMinutesModel = new SpinnerNumberModel(0, 0, 59, 1);
		startTimeHours = new JSpinner(startTimeHoursModel);
		startTimeMinutes = new JSpinner(startTimeMinutesModel);
		timeStartPanel.add(startTimeHours);
		timeStartPanel.add(startTimeMinutes);
		
		controlPanel = new JPanel();
		confirmButton = new JButton("Anlegen");
		confirmButton.addActionListener(this);
		cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(this);
		controlPanel.add(confirmButton);
		controlPanel.add(cancelButton);
		
		getContentPane().add(timeStartLabel, BorderLayout.NORTH);
		getContentPane().add(timeStartPanel, BorderLayout.CENTER);
		getContentPane().add(controlPanel, BorderLayout.SOUTH);
	
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton) {
			setVisible(false);
			dispose();
		} else if (e.getSource() == confirmButton) {
			
			int tempHour = (int) startTimeHours.getValue();
			int tempMin = (int) startTimeMinutes.getValue();
			
			if(tempHour <= 23){
				if(tempHour <= 59){
					Plan tempPlan = planGen.generatePlan(new Time(tempHour, tempMin));
					cGui.setPlan(tempPlan);
					cGui.showPlan();
					
					this.setVisible(false);
					dispose();
				}
			}
			
		}
		
	}

	
}
