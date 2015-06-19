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

public class overviewMaskProfessor extends JFrame implements ActionListener {
	private JTable table;
	private JButton exitButton;
	private JPanel south;
	ProfList list;
	public overviewMaskProfessor(PlanGenerator planGenerator) {
		list = planGenerator.getProfList();
		getContentPane().setLayout(new BorderLayout());
		setTitle("Professoren Uebersicht");
		setSize(400, 400);
		setLocationRelativeTo(null);
		
		TableModel dataModel = new DataModel(list.size(),3);
		table = new JTable(dataModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		add( new JScrollPane( table ), BorderLayout.CENTER );
		
		for ( int row = 0; row < list.size(); row++ )
			for ( int col = 0; col < 1; col++ ) {
				dataModel.setValueAt(list.get(row).getName(), row, col);
				dataModel.setValueAt(subjectsString(list.get(row).getSubjectArray()), row, col+1);
				dataModel.setValueAt(TimePeriodString(list.get(row).getTimePeriodArray()), row, col+2);
			}
		
		//Knopf anlegen
		south = new JPanel();
		exitButton = new JButton("verlassen");
		exitButton.addActionListener(this);
		south.add(exitButton);
		getContentPane().add(south, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exitButton) {
			setVisible(false);
			dispose();
		}
	}
	
	/**
	 * Function which creates a String with all subjects of a student.
	 * @param subjects list of subjects.
	 * @return A String with all subjects of a student.
	 */
	String subjectsString(Subject[] subjects) {
		String returnString = "";
		for(int i = 0; i < subjects.length; i++) {
			returnString += subjects[i].getName() + ", ";
		}
		return returnString;
	}
	
	/**
	 * Function which creates a String with all timeperiods of a student.
	 * @param period list of timeperiods.
	 * @return A String with all timeperiods of a student.
	 */
	String TimePeriodString(TimePeriod[] period) {		
		String returnString = "";
		for(int i = 0; i < period.length; i++) {
			returnString += "start: " + period[i].getStart().getHour() + ":" + period[i].getStart().getMinute() + " end: " + period[i].getEnd().getHour() + ":" + period[i].getEnd().getMinute() + ", ";
		}
		return returnString;
	}

}
