package Kubaner.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Kubaner.Logic.Plan;
import Kubaner.Logic.PlanGenerator;
import Kubaner.Logic.Subject;
import Kubaner.Logic.SubjectList;

public class ChangeMaskSubject extends JFrame implements ActionListener{

	private Subject subject;
	private SubjectList subjectList;
	private JButton setButton, exitButton;
	private JLabel subjectLabel;
	private JPanel south, subjectPanel;
	private JTextField subjectField;
	private String name;
	private int subjectListPosition;

	/**
	 * 
	 * @param planGenerator The current plangenerator.
	 * @param subjectListPosition The position of the subject in the subjectList, do you want change.
	 */
	ChangeMaskSubject(PlanGenerator planGenerator, int subjectListPosition) {

		this.subjectListPosition = subjectListPosition;
		subjectList = planGenerator.getSubjectList();
		subject = subjectList.get(subjectListPosition);
		setTitle("Fach-Eingabemaske");
		getContentPane().setLayout(new BorderLayout());
		setSize(190, 200);

		// Panel für die Facheingabe
		subjectPanel = new JPanel();
		subjectLabel = new JLabel("Name des Fachs: ");
		subjectField = new JTextField(subject.getName());
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
					subjectList.delete(subjectListPosition);
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
