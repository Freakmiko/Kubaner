package Kubaner.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Kubaner.Logic.*;

public class SelectProfessor extends JFrame implements ActionListener {

	private int size, selection;
	private JPanel inputPanel, confirmPanel;
	private JLabel inputLabel;
	private SpinnerModel professorModel;
	private JSpinner professorSpinner;
	private JButton confirmButton, cancelButton;
	private PlanGenerator planGenerator;
	private Plan plan;

	SelectProfessor(Plan plan, PlanGenerator planGenerator) {

		this.planGenerator = planGenerator;
		this.plan = plan;
		size = planGenerator.getProfList().size();

		if (size == 0) {
			JOptionPane
					.showMessageDialog(
							null,
							"Es ist kein Professor vorhanden, der bearbeitet werden kann!",
							"Kein Professor vohanden",
							JOptionPane.CANCEL_OPTION);
		}

		setTitle("Professorauswahl");
		setLayout(new GridLayout(2, 1));
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2, 1));
		inputLabel
				.setText("Geben Sie die Nummer des Professors, die Sie der Professorübersicht entnehmen können.");
		professorModel = new SpinnerNumberModel(0, 0, size - 1, 1);
		professorSpinner = new JSpinner(professorModel);
		inputPanel.add(inputLabel);
		inputPanel.add(professorSpinner);
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
								"Es ist kein Professor vorhanden, der bearbeitet werden kann!",
								"Kein Professor vohanden",
								JOptionPane.CANCEL_OPTION);
			} else {
				selection = (int) professorSpinner.getValue();
				setVisible(false);
				dispose();
			}
		}

	}
}
