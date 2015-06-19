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

public class overviewMaskStudent extends JFrame implements ActionListener {
	private JTable table;
	private JButton exitButton;
	private JPanel south;
	String subjects = "";
	Subject[] dummyArray;
	String times = "";
	StudentList list;
	
	public overviewMaskStudent(PlanGenerator planGenerator) {
		list = planGenerator.getStudentList();
		getContentPane().setLayout(new BorderLayout());
		setTitle("Studenten Uebersicht");
		setSize(200, 200);
		setLocationRelativeTo(null);
		
		TableModel dataModel = new DataModel(planGenerator.getSubjectList().size(),3);
		System.out.println(planGenerator.getSubjectList().size());
		table = new JTable(dataModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		add( new JScrollPane( table ), BorderLayout.CENTER );
		

		for ( int row = 0; row < planGenerator.getSubjectList().size(); row++ )
			for ( int col = 0; col < 1; col++ )
				dataModel.setValueAt(list.get(row).getName(), row, col);
		
		
		//Knopf anlegen
		south = new JPanel();
		exitButton = new JButton("verlassen");
		exitButton.addActionListener(this);
		south.add(exitButton);
		getContentPane().add(south, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
