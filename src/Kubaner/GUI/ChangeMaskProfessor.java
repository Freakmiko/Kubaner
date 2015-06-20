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
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import Kubaner.Logic.Plan;
import Kubaner.Logic.PlanGenerator;
import Kubaner.Logic.ProfList;
import Kubaner.Logic.Professor;
import Kubaner.Logic.Subject;
import Kubaner.Logic.SubjectList;
import Kubaner.Logic.Time;
import Kubaner.Logic.TimePeriod;

public class ChangeMaskProfessor extends JFrame implements ActionListener {

	private Professor currentProf;
	private SubjectList subList;
	private ProfList currentProfList;
	private Time start, end;
	private TimePeriod[] periode = new TimePeriod[1];
	private int subjectListSize;
	private JButton confirmButton, cancelButton;
	private JRadioButton[] subjectListButtons;
	private JPanel itemsPanel, namePanel, subjectPanel, selectionSubjectPanel,
			timePanel, timeStartPanel, timeEndPanel, controlPanel;
	private JTextField nameField;
	private JLabel nameLabel, subjectLabel, timeStartLabel, timeEndLabel;
	private JSpinner startTimeHours, startTimeMinutes, endTimeHours,
			endTimeMinutes;
	private SpinnerNumberModel startTimeHoursModel, startTimeMinutesModel,
			endTimeHoursModel, endTimeMinutesModel;
	private String name;
	private int startHour, startMinute, endHour, endMinute, profListPosition;
	private Subject[] teachingSubject;

	/**
	 * 
	 * @param plan
	 *            The current plan.
	 * @param planGenerator
	 *            The current plangenerator.
	 * @param profListPosition
	 *            The position of the professor in the professorList, do you
	 *            want change.
	 * @throws NoSubjectException
	 */
	ChangeMaskProfessor(Plan plan, PlanGenerator planGenerator,
			int profListPosition) {

		this.profListPosition = profListPosition;
		subList = planGenerator.getSubjectList();
		currentProfList = planGenerator.getProfList();
		currentProf = currentProfList.get(profListPosition);
		teachingSubject = currentProf.getSubjectArray();
		subjectListSize = subList.size();
		subjectListButtons = new JRadioButton[subjectListSize];

		// Liest die Zeitdaten aus dem aktuellen Studenten.
		periode = currentProf.getTimePeriodArray();
		TimePeriod currentTimePeriod = periode[0];
		start = currentTimePeriod.getStart();
		end = currentTimePeriod.getEnd();
		startHour = start.getHour();
		startMinute = start.getMinute();
		endHour = end.getHour();
		endMinute = end.getMinute();

		setTitle("Dozent-Eingabemaske");
		setLayout(new BorderLayout());

		itemsPanel = new JPanel();
		itemsPanel.setLayout(new GridLayout(3, 1));

		// Erstellt ein Panel für die Namenseingabe
		namePanel = new JPanel();
		namePanel.setLayout(new GridLayout(2, 1));
		namePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		nameLabel = new JLabel("Name des Dozenten (Pflichtfeld): ");
		nameField = new JTextField(currentProf.getName());
		namePanel.add(nameLabel);
		namePanel.add(nameField);

		// Erstellt ein Panel für die Fachauswahl.
		subjectPanel = new JPanel();
		subjectPanel.setLayout(new GridLayout(2, 1));
		subjectPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		subjectLabel = new JLabel("Fach des Dozenten (Pflichtfeld): ");

		// Erstellt ein panel für die RadioButtons der Fächer.
		selectionSubjectPanel = new JPanel();
		selectionSubjectPanel
				.setLayout(new GridLayout((subjectListSize) / 2, 2));

		int j = 0;
		for (int i = 0; i != subjectListSize; i++) {
			if (subList.get(i).equals(teachingSubject[j])) {
				subjectListButtons[i] = new JRadioButton(
						(subList.get(i)).getName(), true);
				j++;
			} else {
				subjectListButtons[i] = new JRadioButton(
						(subList.get(i)).getName(), false);
			}
			selectionSubjectPanel.add(subjectListButtons[i]);
			subjectListButtons[i].addActionListener(this);
		}
		subjectPanel.add(subjectLabel);
		subjectPanel.add(selectionSubjectPanel);

		// Erstellt ein Panel für die Wunschzeit
		timePanel = new JPanel();
		timePanel.setLayout(new GridLayout(4, 1));
		timePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		timeStartLabel = new JLabel("Wunschstartzeit des Dozenten (optional): ");
		timePanel.add(timeStartLabel);

		// Erstellt ein Panel für die Startzeit.
		timeStartPanel = new JPanel();
		timeStartPanel.setLayout(new GridLayout(1, 2));
		startTimeHoursModel = new SpinnerNumberModel(startHour, 0, 23, 1);
		startTimeMinutesModel = new SpinnerNumberModel(startMinute, 0, 59, 1);
		startTimeHours = new JSpinner(startTimeHoursModel);
		startTimeHours.setValue(startHour);
		startTimeMinutes = new JSpinner(startTimeMinutesModel);
		startTimeMinutes.setValue(startMinute);
		timeStartPanel.add(startTimeHours);
		timeStartPanel.add(startTimeMinutes);
		timePanel.add(timeStartPanel);

		// Erstellt ein Panel für die Endzeit.
		timeEndLabel = new JLabel("Wunschendzeit des Dozenten (optional): ");
		timePanel.add(timeEndLabel);
		timeEndPanel = new JPanel();
		timeEndPanel.setLayout(new GridLayout(1, 2));
		endTimeHoursModel = new SpinnerNumberModel(endHour, 0, 23, 1);
		endTimeMinutesModel = new SpinnerNumberModel(endMinute, 0, 59, 1);
		endTimeHours = new JSpinner(endTimeHoursModel);
		endTimeHours.setValue(endHour);
		endTimeMinutes = new JSpinner(endTimeMinutesModel);
		endTimeMinutes.setValue(endMinute);
		timeEndPanel.add(endTimeHours);
		timeEndPanel.add(endTimeMinutes);
		timePanel.add(timeEndPanel);

		// Erstellt ein Pnale zum bestätigen und zum abbrechen der Eingabe
		controlPanel = new JPanel();
		confirmButton = new JButton("Anlegen");
		cancelButton = new JButton("Abbrechen");
		controlPanel.add(confirmButton);
		controlPanel.add(cancelButton);

		// Fügt ActionListener für die beiden knöpfe hinzu.
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
		if (e.getSource() == cancelButton) {
			setVisible(false);
			dispose();
		} else if (e.getSource() == confirmButton) {
			name = nameField.getText();

			// Easteregg f�r Herr Knauber :D
			if (name.contains("Knauber")) {
				name = "Prof. Dr. Kubaner";
			}

			if (name.equals("")) {
				JOptionPane.showMessageDialog(null,
						"Sie haben keinen Namen angegeben!", "Kein Name",
						JOptionPane.CANCEL_OPTION);
			}

			startHour = (int) startTimeHours.getValue();
			startMinute = (int) startTimeMinutes.getValue();
			endHour = (int) endTimeHours.getValue();
			endMinute = (int) endTimeMinutes.getValue();

			// Zählt die ausgewähleten Fächer.
			int counterSubjects = 0;
			for (int index = 0; index != subjectListSize; index++) {
				if (subjectListButtons[index].isSelected())
					counterSubjects++;
			}

			if (counterSubjects == 0) {
				JOptionPane.showMessageDialog(null,
						"Sie haben kein Fach ausgewaehlt!", "Kein Fach",
						JOptionPane.CANCEL_OPTION);
			} else {

				// Fügt die unterichtenden Fächer in eine FächerListe ein.
				teachingSubject = new Subject[counterSubjects];
				int counter = 0;

				for (int index = 0; index != subjectListSize; index++) {
					if (subjectListButtons[index].isSelected()) {
						Subject tempSub = subList.get(index);
						teachingSubject[counter] = tempSub;
						counter++;
					}
				}

				start = new Time(startHour, startMinute);
				end = new Time(endHour, endMinute);
				periode[0] = new TimePeriod(start, end);

				// Testausgabe
				// System.out.println("Name: " + professorName);
				// for (int test = 0; test != counterSubjects; test++)
				// System.out.println(test +"Fach: " +
				// teachingSubject[],getName());
				// System.out.println(test +"Fach: ");
				// System.out.println("StartZeit: "+ startHour + ":" +
				// startMinute);
				// System.out.println("EndZeit: "+ endHour + ":" + endMinute);

				try {
					currentProfList.delete(profListPosition);
					currentProfList.create(name, teachingSubject, periode);
					JOptionPane.showOptionDialog(null, "Der Dozent " + name
							+ " wurde erfolgreich bearbeitet.",
							"Erfolgreiche Eingabe", JOptionPane.CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, null, null);
					setVisible(false);
					dispose();
				} catch (IllegalArgumentException E) {
					JOptionPane.showMessageDialog(null,
							"Einer Ihrer Eingaben ist nicht korreckt!",
							"Fehlerhafte Eingabe", JOptionPane.CANCEL_OPTION);
				}
			}
		}
	}
}
