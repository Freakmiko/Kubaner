package Kubaner.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableModel;

import Kubaner.GUI.*;
import Kubaner.Logic.*;

public class SelectStudent extends JFrame implements ActionListener {

	private JTable table;
	private JPanel actionPanel, selectPanel, tablePanel;
	private int size;
	private JButton confirmButton, cancelButton, deleteButton;
	private JLabel subjectLabel;
	private SpinnerNumberModel subjectModel;
	private JSpinner subjectSpinner;
	private int selection;
	private PlanGenerator planGenerator;
	private Plan plan;
	StudentList list;

	public SelectStudent(Plan plan, PlanGenerator planGenerator)
			throws NoSubjectException, NoElementException {

		this.planGenerator = planGenerator;
		this.plan = plan;
		size = planGenerator.getStudentList().size();

		if (size == 0) {
			throw new NoElementException();
		}

		setLayout(new GridLayout(3, 1));
		setTitle("Studenten Übersicht");
		setLocationRelativeTo(null);

		list = planGenerator.getStudentList();
		// F�cher�bersicht
		tablePanel = new JPanel();
		tablePanel.setLayout(new GridLayout(1, 1));
		TableModel dataModel = new DataModel(planGenerator.getStudentList()
				.size(), 4);
		table = new JTable(dataModel);

		for (int row = 0; row < planGenerator.getStudentList().size(); row++) {
			for (int col = 0; col < 1; col++) {
				dataModel.setValueAt("Nummer: " + row, row, col);
				dataModel.setValueAt("Name: " + list.get(row).getName(), row,
						col + 1);
				dataModel.setValueAt("Fach: "
						+ subjectsString(list.get(row).getSubjectArray()), row,
						col + 2);
				dataModel.setValueAt("Zeit: "
						+ TimePeriodString(list.get(row).getTimePeriodArray())
						+ "min", row, col + 3);
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
				.setText("Geben Sie die Nummer des Studenten, den Sie der Studentenübersicht entnehmen können.");
		subjectModel = new SpinnerNumberModel(0, 0, size-1, 1);
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

	/**
	 * Function which creates a String with all subjects of a student.
	 * 
	 * @param subjects
	 *            list of subjects.
	 * @return A String with all subjects of a student.
	 */
	String subjectsString(Subject[] subjects) {
		String returnString = "";
		for (int i = 0; i < subjects.length; i++) {
			returnString += subjects[i].getName() + ", ";
		}
		return returnString;
	}

	/**
	 * Function which creates a String with all timeperiods of a student.
	 * 
	 * @param period
	 *            list of timeperiods.
	 * @return A String with all timeperiods of a student.
	 */
	String TimePeriodString(TimePeriod[] period) {
		String returnString = "";
		for (int i = 0; i < period.length; i++) {
			returnString += "Start: " + period[i].getStart().getHour() + ":"
					+ period[i].getStart().getMinute() + " Uhr" + " Ende: "
					+ period[i].getEnd().getHour() + ":"
					+ period[i].getEnd().getMinute() + " Uhr" + ", ";
		}
		return returnString;
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
			new ChangeMaskStudent(plan, planGenerator, selection)
					.setVisible(true);
			dispose();
		}
		if (e.getSource() == deleteButton) {
			selection = (int) subjectSpinner.getValue();
			String name = list.get(selection).getName();
			list.delete(selection);
			JOptionPane.showMessageDialog(null,
					"Sie haben den Student " + name + " gelöscht!", "Student gelöscht",
					JOptionPane.CANCEL_OPTION);
			setVisible(false);
			dispose();
		}

	}

}