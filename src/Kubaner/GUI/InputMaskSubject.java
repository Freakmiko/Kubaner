package Kubaner.GUI;

import Kubaner.Logic.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputMaskSubject extends JFrame implements ActionListener {
	private PlanGenerator planGenerator;
	SubjectList subjectList;
	Subject newSubject;
	private JButton setButton, exitButton;
	private JLabel subjectLabel;
	private JPanel south, subjectPanel;
	private JTextField subjectField;
	
	public InputMaskSubject(PlanGenerator planGenertor) {
		this.planGenerator = planGenerator;
		subjectList = planGenerator.getSubjectList();
		setTitle("Fächer-Eingabemaske");
		getContentPane().setLayout( new BorderLayout());
		setSize(190, 200);
		
		//Panel für die Facheingabe
		subjectPanel = new JPanel();
		subjectLabel = new JLabel("Name des Fachs: ");
		subjectField = new JTextField("                                         ");
		subjectPanel.add(subjectLabel);
		subjectPanel.add(subjectField);
		getContentPane().add( subjectPanel, BorderLayout.CENTER );
		
		//Panel für die Knöpfe
		south = new JPanel();
		setButton = new JButton("Anlegen");
		exitButton = new JButton("Exit");
		south.add(setButton);
		south.add(exitButton);
		getContentPane().add( south, BorderLayout.SOUTH );
	}
	
//	public static void main(String[] args) {
//		PlanGenerator planGenerator = new PlanGenerator();
//		new InputMaskSubject(planGenerator).setVisible(true);
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitButton){
			System.exit(0);
		}
		if(e.getSource() == setButton) {
			newSubject = subjectList.create(subjectField.getText());
		}
	}
}
