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
		private int size;
		private JButton confirmButton, cancelButton;
		private JLabel subjectLabel;
		private SpinnerNumberModel subjectModel;
		private JSpinner subjectSpinner;
		private int selection;
		private PlanGenerator planGenerator;

		public SelectSubject(Plan plan ,PlanGenerator planGenerator) throws NoElementException {
			
			this.planGenerator = planGenerator;
			size = planGenerator.getSubjectList().size();
			
			if (size == 0){
				throw new NoElementException();
			}
			setLayout(new GridLayout(3,1));
			setTitle("Faecher Uebersicht");
			setLocationRelativeTo(null);
			
			//Fächerübersicht
			tablePanel = new JPanel();
			tablePanel.setLayout(new GridLayout(1,1));
			TableModel dataModel = new DataModel(planGenerator.getSubjectList().size(),3);
			table = new JTable(dataModel);
			
		for (int row = 0; row < planGenerator.getSubjectList().size(); row++) {
			for ( int col = 0; col < 1; col++ ){
				dataModel.setValueAt("Prüfungsdauer: " + planGenerator.getSubjectList().get(row).getExamLength() + "min", row, col+2);
				dataModel.setValueAt("Fach: " + planGenerator.getSubjectList().get(row).getName(), row, col+1);
				dataModel.setValueAt("Nummer: " + row, row, col);
		}
		}
			
			tablePanel.add(table);
			add(tablePanel);
			
			//Wahl des faches
			selectPanel = new JPanel();
			selectPanel.setLayout(new GridLayout(2, 1));
			selectPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			subjectLabel = new JLabel();
			subjectLabel
					.setText("Geben Sie die Nummer des Faches, die Sie der Faecherueberisicht entnehmen können.");
			subjectModel = new SpinnerNumberModel(0, 0, size, 1);
			subjectSpinner = new JSpinner(subjectModel);
			selectPanel.add(subjectLabel);
			selectPanel.add(subjectSpinner);
			add(selectPanel);
			
			//Actionsknöpfe
			actionPanel = new JPanel();
			actionPanel.setLayout(new GridLayout(1,2));
			actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			confirmButton = new JButton("Bearbeiten");
			cancelButton = new JButton("Abbrechen");
			cancelButton.addActionListener(this);
			confirmButton.addActionListener(this);
			actionPanel.add(confirmButton);
			actionPanel.add(cancelButton);
			add(actionPanel);
			pack();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == cancelButton) {
				setVisible(false);
				dispose();
			}
			
			if (e.getSource() == confirmButton) {
				selection = (int) subjectSpinner.getValue();
				setVisible(false);
				new ChangeMaskSubject(planGenerator, selection).setVisible(true);
				dispose();
			}
			
		} 

	}
