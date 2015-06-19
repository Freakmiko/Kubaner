package Kubaner.GUI;

import Kubaner.Logic.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class overviewMaskProfessor extends JFrame implements ActionListener {
	private JTable table;
	private JButton exitButton;
	private JPanel south;
	
	public overviewMaskProfessor(PlanGenerator planGenerator) {
		getContentPane().setLayout(new BorderLayout());
		setTitle("Professoren Uebersicht");
		setSize(200, 200);
		setLocationRelativeTo(null);
		
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
