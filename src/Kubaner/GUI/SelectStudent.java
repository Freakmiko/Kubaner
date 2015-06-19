package Kubaner.GUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Kubaner.GUI.*;
import Kubaner.Logic.*;


public class SelectStudent extends JFrame implements ActionListener{

		private int size, selection;
		private JPanel inputPanel, confirmPanel;
		private JLabel inputLabel;
		private SpinnerModel studentModel;
		private JSpinner studentSpinner;
		private JButton confirmButton, cancelButton;
		private PlanGenerator planGenerator;
		private Plan plan;

		SelectStudent(Plan plan, PlanGenerator planGenerator) {

			this.planGenerator = planGenerator;
			this.plan = plan;
			size = planGenerator.getStudentList().size();

			if (size == 0) {
				JOptionPane
						.showMessageDialog(
								null,
								"Es ist kein Student vorhanden, der bearbeitet werden kann!",
								"Kein Student vohanden",
								JOptionPane.CANCEL_OPTION);
			}

			setTitle("Studentenauswahl");
			setLayout(new GridLayout(2, 1));
			inputPanel = new JPanel();
			inputPanel.setLayout(new GridLayout(2, 1));
			inputLabel
					.setText("Geben Sie die Nummer des Studenten, die Sie der Studentenübersicht entnehmen können.");
			studentModel = new SpinnerNumberModel(0, 0, size - 1, 1);
			studentSpinner = new JSpinner(studentModel);
			inputPanel.add(inputLabel);
			inputPanel.add(studentSpinner);
			add(inputPanel);

			confirmPanel = new JPanel();
			confirmPanel.setLayout(new GridLayout(1, 2));
			confirmButton = new JButton("OK");
			confirmButton.addActionListener(this);
			cancelButton = new JButton("Abbrechen");
			cancelButton.addActionListener(this);
			confirmPanel.add(confirmButton);
			confirmPanel.add(cancelButton);
			add(confirmPanel);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == cancelButton) {
				setVisible(false);
				dispose();
			}

			if (e.getSource() == confirmButton) {
				if (size == 0) {
					JOptionPane
							.showMessageDialog(
									null,
									"Es ist kein Student vorhanden, der bearbeitet werden kann!",
									"Kein Student vohanden",
									JOptionPane.CANCEL_OPTION);
				} else {
					selection = (int) studentSpinner.getValue();
					setVisible(false);
					new ChangeMaskStudent(plan, planGenerator, selection);
					dispose();
				}
			}

		}
	}