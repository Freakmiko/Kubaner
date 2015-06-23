package Kubaner.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableModel;

import Kubaner.Logic.*;

public class SelectSubject extends JFrame implements ActionListener {

	private JTable table;
	private JPanel actionPanel, selectPanel, tablePanel;
	private JButton confirmButton, cancelButton, deleteButton;
	private JLabel subjectLabel;
	private SpinnerNumberModel subjectModel;
	private JSpinner subjectSpinner;
	private int selection;
	private PlanGenerator planGenerator;
	private SubjectList list;

	public SelectSubject(Plan plan, PlanGenerator planGenerator)
			throws NoElementException {

		this.planGenerator = planGenerator;
		list = planGenerator.getSubjectList();

		if (list.size() == 0) {
			throw new NoElementException();
		}
		setLayout(new GridLayout(3, 1));
		setTitle("Fächer Übersicht");
		setLocationRelativeTo(null);

		// F�cher�bersicht
		tablePanel = new JPanel();
		tablePanel.setLayout(new GridLayout(1, 1));
		TableModel dataModel = new DataModel(list.size(), 3);
		table = new JTable(dataModel);

		for (int row = 0; row < list.size(); row++) {
			for (int col = 0; col < 1; col++) {
				dataModel.setValueAt("Prüfungsdauer: "
						+ list.get(row)
								.getExamLength() + "min", row, col + 2);
				dataModel.setValueAt("Fach: "
						+ list.get(row).getName(),
						row, col + 1);
				dataModel.setValueAt("Nummer: " + row, row, col);
			}
		}

		tablePanel.add(table);
		add(tablePanel);

		// Wahl des faches
		selectPanel = new JPanel();
		selectPanel.setLayout(new GridLayout(2, 1));
		selectPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		subjectLabel = new JLabel();
		subjectLabel
				.setText("Geben Sie die Nummer des Faches, die Sie der Fächerübersicht entnehmen können.");
		subjectModel = new SpinnerNumberModel(0, 0, list.size()-1, 1);
		subjectSpinner = new JSpinner(subjectModel);
		selectPanel.add(subjectLabel);
		selectPanel.add(subjectSpinner);
		add(selectPanel);

		// Actionskn�pfe
		actionPanel = new JPanel();
		actionPanel.setLayout(new GridLayout(1, 3));
		actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		confirmButton = new JButton("Bearbeiten");
		cancelButton = new JButton("Abbrechen");
		deleteButton = new JButton("Löschen");
		cancelButton.addActionListener(this);
		confirmButton.addActionListener(this);
		deleteButton.addActionListener(this);
		actionPanel.add(confirmButton);
		actionPanel.add(cancelButton);
		actionPanel.add(deleteButton);
		add(actionPanel);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton) {
			setVisible(false);
			dispose();
		}

		if (e.getSource() == confirmButton) {
			selection = (int) subjectSpinner.getValue();
			setVisible(false);
			new ChangeMaskSubject(planGenerator, selection).setVisible(true);
			dispose();
		}
		if (e.getSource() == deleteButton) {
			selection = (int) subjectSpinner.getValue();
			String name = list.get(selection).getName();
			
			if (subjectRemovabel(list.get(selection))){
			list.delete(selection);
			JOptionPane.showMessageDialog(null,
					"Sie haben das Fach " + name + " gelöscht!", "Fach gelöscht",
					JOptionPane.CANCEL_OPTION);
			setVisible(false);
			dispose();
			}
			else {
				JOptionPane.showMessageDialog(null,
						"Sie können das Fach " + name + " nicht löschen, weil mindestens ein Dozent oder Student nur dieses Fach hat!", "Kein Fach",
						JOptionPane.CANCEL_OPTION);
			}
	}
	}

	private boolean subjectRemovabel(Subject subject) {
		
		StudentList tempStudentList = planGenerator.getStudentList();
		ProfList tempProfList = planGenerator.getProfList();

		for (int i = 0; i != tempStudentList.size(); i++) {
			Student tempStudent = tempStudentList.get(i);
			for (int j = 0; j != tempStudent.getSubjectArray().length; j++) {
				Subject[] tempStudentSubject = tempStudent
						.getSubjectArray();
				if (subject.equals(tempStudentSubject[j]))
					if (tempStudent.getSubjectArray().length != 1)
						tempStudent.deleteSubject(j);
					else 
						return false;
				
			}
		}
		for (int i = 0; i != tempProfList.size(); i++) {
			Professor tempPorfessor = tempProfList.get(i);
			for (int j = 0; j != tempPorfessor
					.getSubjectArray().length; j++) {
				Subject[] tempProfSubject = tempPorfessor
						.getSubjectArray();
				if (subject.equals(tempProfSubject[j]))
					if (tempPorfessor.getSubjectArray().length != 1)
						tempPorfessor.deleteSubject(j);
					else 
						return false;
			}
		}

		
		return true;
	}
}