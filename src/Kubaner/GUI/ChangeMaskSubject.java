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
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import Kubaner.Logic.*;

public class ChangeMaskSubject extends JFrame implements ActionListener {

	private PlanGenerator planGenerator;
	private Subject subject;
	private SubjectList subjectList;
	private JButton setButton, exitButton;
	private JLabel subjectLabel, timeLabel;
	private JPanel south, subjectPanel, timePanel;
	private JTextField subjectField;
	private String name;
	private int subjectListPosition;
	private SpinnerNumberModel subjectTimeModel;
	private JSpinner subjectTime;
	private int time;

	/**
	 * 
	 * @param planGenerator
	 *            The current plangenerator.
	 * @param subjectListPosition
	 *            The position of the subject in the subjectList, do you want
	 *            change.
	 */
	ChangeMaskSubject(PlanGenerator planGenerator, int subjectListPosition) {

		this.planGenerator = planGenerator;
		this.subjectListPosition = subjectListPosition;
		subjectList = planGenerator.getSubjectList();
		subject = subjectList.get(subjectListPosition);
		time = subject.getExamLength();
		setTitle("Fach-Eingabemaske");
		getContentPane().setLayout(new GridLayout(3, 1));
		setSize(190, 200);

		// Panel für die Facheingabe
		subjectPanel = new JPanel();
		subjectPanel.setLayout(new GridLayout(2, 1));
		subjectPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		subjectLabel = new JLabel("Name des Fachs: ");
		subjectField = new JTextField(subject.getName());
		subjectPanel.add(subjectLabel);
		subjectPanel.add(subjectField);
		add(subjectPanel, BorderLayout.CENTER);

		// Panel f�r die Zeiteingabe
		timePanel = new JPanel();
		timePanel.setLayout(new GridLayout(2, 1));
		timePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		timeLabel = new JLabel("Dauer der Pruefung (in Minuten)");
		timePanel.add(timeLabel);
		subjectTimeModel = new SpinnerNumberModel(time, 5, 10, 5);
		subjectTime = new JSpinner(subjectTimeModel);
		timePanel.add(subjectTime);
		add(timePanel);

		// Panel für die Knöpfe
		south = new JPanel();
		setButton = new JButton("Anlegen");
		exitButton = new JButton("Abbrechen");
		setButton.addActionListener(this);
		exitButton.addActionListener(this);
		south.add(setButton);
		south.add(exitButton);
		getContentPane().add(south, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitButton) {
			setVisible(false);
			dispose();
		}

		if (e.getSource() == setButton) {

			time = (int) subjectTime.getValue();
			name = subjectField.getText();
			if (name.equals(""))
				JOptionPane.showMessageDialog(null, "Fehlender Name!",
						"Sie haben keinen Namen angegeben.",
						JOptionPane.CANCEL_OPTION);
			else {
				try {
					if (subjectList.exists(name)) {
						int answer = JOptionPane
								.showOptionDialog(
										null,
										"Der Name des Faches existiert bereits. M\u00F6chten Sie das Fach trotzdem erstellen?",
										"Doppelter Name",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.PLAIN_MESSAGE, null, null,
										null);
						if (answer == 0) {
							Subject oldSubject = subjectList
									.get(subjectListPosition);

							subjectList.delete(subjectListPosition);
							subject = subjectList.create(name);
							subject.setExamLength(time);
							JOptionPane
									.showOptionDialog(null, "Das Fach " + name
											+ " wurde erfolgreich erstellt.",
											"Erfolgreiche Eingabe",
											JOptionPane.DEFAULT_OPTION,
											JOptionPane.PLAIN_MESSAGE, null,
											null, null);

							// Anpassen aller Betroffenen Studenten und Dozenten
							StudentList tempStudentList = planGenerator
									.getStudentList();
							ProfList tempProfList = planGenerator.getProfList();

							for (int i = 0; i != tempStudentList.size(); i++) {
								Student tempStudent = tempStudentList.get(i);
								for (int j = 0; j != tempStudent
										.getSubjectArray().length; j++) {
									Subject[] tempStudentSubject = tempStudent
											.getSubjectArray();
									if (oldSubject
											.equals(tempStudentSubject[j]))
										tempStudentSubject[j] = subject;
								}
							}
							for (int i = 0; i != tempProfList.size(); i++) {
								Professor tempPorfessor = tempProfList.get(i);
								for (int j = 0; j != tempPorfessor
										.getSubjectArray().length; j++) {
									Subject[] tempProfSubject = tempPorfessor
											.getSubjectArray();
									if (oldSubject.equals(tempProfSubject[j]))
										tempPorfessor.deleteSubject(j);
									tempPorfessor.addSubject(subject);
								}
							}
							setVisible(false);
							dispose();
						}
					} else {
						Subject oldSubject = subjectList
								.get(subjectListPosition);

						subjectList.delete(subjectListPosition);
						subject = subjectList.create(name);
						subject.setExamLength(time);
						JOptionPane.showOptionDialog(null, "Das Fach " + name
								+ " wurde erfolgreich erstellt.",
								"Erfolgreiche Eingabe!",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.PLAIN_MESSAGE, null, null, null);

						// Anpassen aller Betroffenen Studenten und Dozenten
						StudentList tempStudentList = planGenerator
								.getStudentList();
						ProfList tempProfList = planGenerator.getProfList();

						for (int i = 0; i != tempStudentList.size(); i++) {
							Student tempStudent = tempStudentList.get(i);
							for (int j = 0; j != tempStudent.getSubjectArray().length; j++) {
								Subject[] tempStudentSubject = tempStudent
										.getSubjectArray();
								if (oldSubject.equals(tempStudentSubject[j]))
									tempStudentSubject[j] = subject;
							}
						}
						for (int i = 0; i != tempProfList.size(); i++) {
							Professor tempPorfessor = tempProfList.get(i);
							for (int j = 0; j != tempPorfessor
									.getSubjectArray().length; j++) {
								Subject[] tempProfSubject = tempPorfessor
										.getSubjectArray();
								if (oldSubject.equals(tempProfSubject[j])) {
									tempPorfessor.deleteSubject(j);
									tempPorfessor.addSubject(subject);
								}
							}
						}

						setVisible(false);
						dispose();

					}

				} catch (IllegalArgumentException E) {
					JOptionPane.showMessageDialog(null, "Fehlerhafte Eingabe!",
							"Erstellungsfehler", JOptionPane.CANCEL_OPTION);
				}
			}

		}
	}
}
