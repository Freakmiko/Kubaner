package Kubaner.GUI;

import Kubaner.Logic.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputMaskSubject extends JFrame implements ActionListener {

	private SubjectList subjectList;
	private JButton setButton, exitButton;
	private JLabel subjectLabel;
	private JPanel south, subjectPanel;
	private JTextField subjectField;
	private String name;

	InputMaskSubject(PlanGenerator planGenerator) {

		subjectList = planGenerator.getSubjectList();
		setTitle("Fach-Eingabemaske");
		getContentPane().setLayout(new BorderLayout());
		setSize(200, 200);

		// Panel für die Facheingabe
		subjectPanel = new JPanel();
		subjectLabel = new JLabel("Name des Fachs: ");
		subjectField = new JTextField("Fachname");
		subjectPanel.add(subjectLabel);
		subjectPanel.add(subjectField);
		getContentPane().add(subjectPanel, BorderLayout.CENTER);

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
			if (name.equals(""))
				JOptionPane.showMessageDialog(null, "Fehlender Name!",
						"Sie haben keinen Namen angegeben.",
						JOptionPane.CANCEL_OPTION);
			else {
				try {
					subjectList.create(name);
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
