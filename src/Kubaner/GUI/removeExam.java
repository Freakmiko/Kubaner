package Kubaner.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import Kubaner.Logic.Plan;

public class removeExam  extends JFrame implements ActionListener {

	private Plan plan;
	private ClientGUI cGui;
	private JSpinner removeColumn;
	private JSpinner removePos;
	private JPanel controlPanel;
	private JButton confirmButton;
	private JButton cancelButton;


	public removeExam(Plan plan, ClientGUI cGui){
		
		this.plan = plan;
		this.cGui = cGui;
		
		int numberOfExam = 0;
		
		for(int i = 0; i < plan.getTimeLineNumber(); i++){
			if(plan.getTimeLine(i).size() > numberOfExam){
				numberOfExam = plan.getTimeLine(i).size();
			}
		}
		
		setTitle("Löschen einer Prüfung");
		
		getContentPane().setLayout(new BorderLayout());
		
		JLabel removeStartLabel = new JLabel("Bitte wählen sie die Spalte in der sie ein Termin löschen wollen(beginnent mit 0):");
		
		JPanel removeSetPanel = new JPanel();
		removeSetPanel.setLayout(new GridLayout(4, 1));
		
		SpinnerNumberModel removeColumnModel = new SpinnerNumberModel(0, 0, plan.getTimeLineNumber()-1, 1);
		removeColumn = new JSpinner(removeColumnModel);
		
		JLabel removePosLabel1 = new JLabel("Bitte geben sie die Position des Termin an der gelöscht werden soll");
		JLabel removePosLabel2 = new JLabel("Der erste Termin beginnt mit dem Index 0 und so fortlaufend.");
		
		SpinnerNumberModel removePosModel = new SpinnerNumberModel(0, 0, numberOfExam, 1);
		removePos = new JSpinner(removePosModel);
		
		removeSetPanel.add(removeColumn);
		removeSetPanel.add(removePosLabel1);
		removeSetPanel.add(removePosLabel2);
		removeSetPanel.add(removePos);
		
		controlPanel = new JPanel();
		confirmButton = new JButton("Löschen");
		confirmButton.addActionListener(this);
		cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(this);
		controlPanel.add(confirmButton);
		controlPanel.add(cancelButton);
		
		
		getContentPane().add(removeStartLabel, BorderLayout.NORTH);
		getContentPane().add(removeSetPanel, BorderLayout.CENTER);
		getContentPane().add(controlPanel, BorderLayout.SOUTH);
	
		pack();
		setLocationRelativeTo(null);

	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == cancelButton) {
			setVisible(false);
			dispose();
		} else if (e.getSource() == confirmButton) {
			if(!(plan.getTimeLine((int)removeColumn.getValue()).size() <= (int)removePos.getValue())){
				plan.getTimeLine((int)removeColumn.getValue()).delete((int)removePos.getValue());
				
				if(plan.getTimeLine((int)removeColumn.getValue()).size() == 0){
					plan.removeTimeLine((int)removeColumn.getValue());
				}
				
				cGui.showPlan();
				
				setVisible(false);
				dispose();
			}else{
				JOptionPane.showMessageDialog(
						null,
						"Die Spalte hat nicht so viele Einträge.",
						"Kein gültiger Index", JOptionPane.CANCEL_OPTION);
			}
		}
		
	}

}
