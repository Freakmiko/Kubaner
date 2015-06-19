package Kubaner.GUI;

import Kubaner.Logic.*;

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
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class InputMaskSubject extends JFrame implements ActionListener {

	private SubjectList subjectList;
	private JButton setButton, exitButton;
	private JLabel subjectLabel, timeLabel;
	private JPanel south, subjectPanel, timePanel;
	private JTextField subjectField;
	private SpinnerModel subjectTimeModel;
	private JSpinner subjectTime;
	private String name;
	private int time;
	private Subject subject;

	InputMaskSubject(PlanGenerator planGenerator) {

		subjectList = planGenerator.getSubjectList();
		setTitle("Fach-Eingabemaske");
		getContentPane().setLayout(new GridLayout(3, 1));
		setSize(200, 200);

		// Panel für die Facheingabe
		subjectPanel = new JPanel();
		subjectPanel.setLayout(new GridLayout(2,1));
		subjectPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		subjectLabel = new JLabel("Name des Fachs (Pflichtfeld)");
		subjectField = new JTextField("Fachname");
		subjectPanel.add(subjectLabel);
		subjectPanel.add(subjectField);
		add(subjectPanel);

		//Panel f�r die Zeiteingabe
		timePanel = new JPanel();
		timePanel.setLayout(new GridLayout(2,1));
		timePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		timeLabel = new JLabel("Dauer der Pruefung (in Minuten)");
		timePanel.add(timeLabel);
		subjectTimeModel = new SpinnerNumberModel(10, 5, 10, 5);
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
			name = subjectField.getText();
			time = (int) subjectTime.getValue();
			System.out.println(time);
			if (name.equals(""))
				JOptionPane.showMessageDialog(null, "Fehlender Name!",
						"Sie haben keinen Namen angegeben.",
						JOptionPane.CANCEL_OPTION);
			else {
				try {
					subject = subjectList.create(name);
					subject.setExamLength(time);
					JOptionPane.showMessageDialog(null,
							"Erfolgreiche Eingabe!",
							"Das Fach wurde erfolgreich erstellt.",
							JOptionPane.CANCEL_OPTION);
					setVisible(false);
					dispose();
				} catch (IllegalArgumentException E) {
					JOptionPane.showMessageDialog(null, "Fehlerhafte Eingabe!",
							"Erstellungsfehler", JOptionPane.CANCEL_OPTION);
				}
			}

		}
	}
}
