package Kubaner.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;

import javax.swing.*;

public class InputMaskStudent extends JFrame implements ActionListener{
	
	public static void main (String[] args) {
		new InputMaskStudent().setVisible(true);
	}

	private int subjectListSize = SubjectList.size();
	private JButton confirmButton, cancelButton;
	private JRadioButton[] subjectListButtons = new JRadioButton[subjectListSize];
	private JPanel itemsPanel, namePanel, subjectPanel, selectionSubjectPanel, timePanel, timeStartPanel, timeEndPanel, controlPanel;
	private JTextField nameField;
	private JLabel nameLabel, subjectLabel, timeStartLabel, timeEndLabel ;
	private JSpinner startTimeHours, startTimeMinutes, endTimeHours, endTimeMinutes;
	private SpinnerNumberModel startTimeHoursModel, startTimeMinutesModel, endTimeHoursModel, endTimeMinutesModel;
	private String name;
	private int startHour, startMinute, endHour, endMinute;
	private Subject[] teachingSubject;
	
	InputMaskStudent(){
		setTitle("Studenten-Eingabemaske");
		setLayout(new BorderLayout());
		
		itemsPanel = new JPanel();
		itemsPanel.setLayout(new GridLayout(3,1));
		
		//Erstellt ein Panel für die Namenseingabe
		namePanel = new JPanel();
		namePanel.setLayout(new GridLayout(2,1));
		namePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		nameLabel = new JLabel("Name des Studenten (Pflichtfeld): ");
		nameField = new JTextField("Max Mustermann");
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		
		//Erstellt ein Panel für die Fachauswahl.
		subjectPanel = new JPanel();
		subjectPanel.setLayout(new GridLayout(2,1));
		subjectPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		subjectLabel = new JLabel("Fach des Studenten (Pflichtfeld): ");
		
		//Erstellt ein panel für die RadioButtons der Fächer.
		selectionSubjectPanel = new JPanel();
		selectionSubjectPanel.setLayout(new GridLayout((subjectListSize)/2, 2));
		for (int i = 0; i != subjectListSize; i++){
			subjectListButtons[i] = new JRadioButton((SubjectList.get(i)).getName());
			selectionSubjectPanel.add(subjectListButtons[i]);
			subjectListButtons[i].addActionListener(this);
		}
		subjectPanel.add(subjectLabel);
		subjectPanel.add(selectionSubjectPanel);
		
		//Erstellt ein Panel für die Wunschzeit
		timePanel = new JPanel();
		timePanel.setLayout(new GridLayout(4, 1));
		timePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		timeStartLabel = new JLabel ("Wunschstartzeit des Studenten (optional): ");
		timePanel.add(timeStartLabel);
		
		//Erstellt ein Panel für die Startzeit.
		timeStartPanel = new JPanel();
		timeStartPanel.setLayout(new GridLayout(1,2));
		startTimeHoursModel = new SpinnerNumberModel(0,0,23, 1);
		startTimeMinutesModel = new SpinnerNumberModel(0,0,59, 1);
		startTimeHours = new JSpinner(startTimeHoursModel);
		startTimeMinutes = new JSpinner(startTimeMinutesModel);
	    timeStartPanel.add(startTimeHours);
	    timeStartPanel.add(startTimeMinutes);
		timePanel.add(timeStartPanel);
		
		//Erstellt ein Panel für die Endzeit.
		timeEndLabel = new JLabel("Wunschendzeit des Studenten (optional): ");
		timePanel.add(timeEndLabel);
		timeEndPanel = new JPanel();
		timeEndPanel.setLayout(new GridLayout(1,2));
		endTimeHoursModel = new SpinnerNumberModel(0,0,23, 1);
		endTimeMinutesModel = new SpinnerNumberModel(0,0,59, 1);
		endTimeHours = new JSpinner(endTimeHoursModel);
		endTimeMinutes = new JSpinner(endTimeMinutesModel);
	    timeEndPanel.add(endTimeHours);
	    timeEndPanel.add(endTimeMinutes);
		timePanel.add(timeEndPanel);
		
		//Erstellt ein Pnale zum bestätigen und zum abbrechen der Eingabe
		controlPanel = new JPanel();
		confirmButton = new JButton("Bestätigen");
		cancelButton = new JButton("Abbrechen");
		controlPanel.add(confirmButton);
		controlPanel.add(cancelButton);
		
		//Fügt ActionListener für die beiden knöpfe hinzu.
		confirmButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		itemsPanel.add(namePanel);
		itemsPanel.add(subjectPanel);
		itemsPanel.add(timePanel);
		add(itemsPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton){
			System.exit(0);
		} else if (e.getSource() == confirmButton){
			name = nameField.getText();
			startHour = (int) startTimeHours.getValue();
			startMinute = (int) startTimeMinutes.getValue();
			endHour = (int) endTimeHours.getValue();
			endMinute = (int) endTimeMinutes.getValue();
			
			//Zählt die ausgewähleten Fächer.
			int counterSubjects = 0;
			for (int index = 0; index != subjectListSize ; index++){
				if(subjectListButtons[index].isSelected())
					counterSubjects++;
			}
			
			//Fügt die unterichtenden Fächer in eine FächerListe ein.
			//teachingSubject= new Subject[counterSubjects];
			int counter = 0;
			
			for (int index = 0; index != subjectListSize ; index++){
				if(subjectListButtons[index].isSelected())
					//teachingSubject[counter] = SubjectList.get(index); 
					counter++;
			}
			
			Time start = new Time(startHour, startMinute);
			Time end = new Time(endHour, endMinute);
			TimePeriod period = new TimePeriode(start, end);
			
//			Testausgabe
//			System.out.println("Name: " + professorName);
//			for (int test = 0; test != counterSubjects; test++)
//				System.out.println(test +"Fach: " + teachingSubject[],getName());
//				System.out.println(test +"Fach: ");
//			System.out.println("StartZeit: "+ startHour + ":" + startMinute);
//			System.out.println("EndZeit: "+ endHour + ":" + endMinute);
			
			StudentList.add(StudentList.create(name, teachingSubject, period));
			System.exit(0);
		}
		
	}

} 
