package Kubaner.GUI;

	import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

	import javax.swing.*;

import Kubaner.Logic.*;

	public class SelectSubject extends JFrame implements ActionListener {

		private int size, selection;
		private JPanel inputPanel, confirmPanel;
		private JLabel inputLabel;
		private SpinnerModel subjectModel;
		private JSpinner subjectSpinner;
		private JButton confirmButton, cancelButton;
		private PlanGenerator planGenerator;
		private Plan plan;

		SelectSubject(Plan plan, PlanGenerator planGenerator) {

			this.planGenerator = planGenerator;
			this.plan = plan;
			size = planGenerator.getSubjectList().size();

			if (size == 0) {
				JOptionPane
						.showMessageDialog(
								null,
								"Es ist kein Fach vorhanden, der bearbeitet werden kann!",
								"Kein Fach vohanden",
								JOptionPane.CANCEL_OPTION);
			}

			setTitle("Fachauswahl");
			setLayout(new GridLayout(2, 1));
			inputPanel = new JPanel();
			inputPanel.setLayout(new GridLayout(2, 1));
			inputLabel.setText("Geben Sie die Nummer des Faches, die Sie der Fächerübersicht entnehmen können.");
			subjectModel = new SpinnerNumberModel(0, 0, size - 1, 1);
			subjectSpinner = new JSpinner(subjectModel);
			inputPanel.add(inputLabel);
			inputPanel.add(subjectSpinner);
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
									"Es ist kein Fach vorhanden, der bearbeitet werden kann!",
									"Kein Fach vohanden",
									JOptionPane.CANCEL_OPTION);
				} else {
					selection = (int) subjectSpinner.getValue();
					setVisible(false);
					new ChangeMaskSubject(planGenerator, selection);
					dispose();
				}
			}

		}
	}