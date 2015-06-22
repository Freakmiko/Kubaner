package Kubaner.GUI;

import Kubaner.Logic.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class overviewMaskSubject extends JFrame implements ActionListener {
	private JTable table;
	private JButton exitButton;
	private JPanel south;
	private SubjectList list;

	public overviewMaskSubject(PlanGenerator planGenerator)
			throws NoElementException {
		getContentPane().setLayout(new BorderLayout());

		list = planGenerator.getSubjectList();
		if (list.size() == 0) {
			throw new NoElementException();
		}
		setTitle("Fächer Übersicht");
		setSize(200, 400);
		setLocationRelativeTo(null);

		TableModel dataModel = new DataModel(list.size(), 3);
		table = new JTable(dataModel);
		add(table);

		for (int row = 0; row < list.size(); row++) {
			for (int col = 0; col < 1; col++) {
				dataModel.setValueAt("Prüfungsdauer: "
						+ list.get(row).getExamLength() + "min", row, col + 2);
				dataModel.setValueAt("Fach: " + list.get(row).getName(), row,
						col + 1);
				dataModel.setValueAt("Nummer: " + row, row, col);
			}
		}
		// Knopf anlegen
		south = new JPanel();
		exitButton = new JButton("Verlassen");
		exitButton.addActionListener(this);
		south.add(exitButton);
		getContentPane().add(south, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitButton) {
			setVisible(false);
			dispose();
		}

	}

}
