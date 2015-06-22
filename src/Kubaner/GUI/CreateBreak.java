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
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import Kubaner.Logic.Break;
import Kubaner.Logic.Plan;

public class CreateBreak extends JFrame implements ActionListener {
	
	Plan plan;
	private JSpinner breakTime;
	private JSpinner breakPos;
	private JPanel controlPanel;
	private JButton confirmButton;
	private JButton cancelButton;
	private ClientGUI cGui;
	int numberOfExam;

	public CreateBreak(Plan plan, ClientGUI cGui){
		
		this.plan = plan;
		this.cGui = cGui;
		
		numberOfExam = 0;
		
		for(int i = 0; i < plan.getTimeLineNumber(); i++){
			if(plan.getTimeLine(i).size() > numberOfExam){
				numberOfExam = plan.getTimeLine(i).size();
			}
		}
		
		setTitle("Eingabe der Pause");
		
		getContentPane().setLayout(new BorderLayout());
		
		JLabel breakStartLabel = new JLabel("Bitte geben Sie die Länge der Pause(in Minuten) an:");
		
		JPanel brackSetPanel = new JPanel();
		brackSetPanel.setLayout(new GridLayout(4, 1));
		
		SpinnerNumberModel breakTimeModel = new SpinnerNumberModel(0, 0, 300, 1);
		breakTime = new JSpinner(breakTimeModel);
		
		JLabel breakPosLabel1 = new JLabel("Bitte geben sie die Position an, an dem die Pause eingefügt werden soll");
		JLabel breakPosLabel2 = new JLabel("Der erste Terim beginnt mit dem Index 0 und so fortlaufend.");
		
		SpinnerNumberModel breakPosModel = new SpinnerNumberModel(0, 0, numberOfExam, 1);
		breakPos = new JSpinner(breakPosModel);
		
		brackSetPanel.add(breakTime);
		brackSetPanel.add(breakPosLabel1);
		brackSetPanel.add(breakPosLabel2);
		brackSetPanel.add(breakPos);
		
		controlPanel = new JPanel();
		confirmButton = new JButton("Anlegen");
		confirmButton.addActionListener(this);
		cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(this);
		controlPanel.add(confirmButton);
		controlPanel.add(cancelButton);
		
		
		getContentPane().add(breakStartLabel, BorderLayout.NORTH);
		getContentPane().add(brackSetPanel, BorderLayout.CENTER);
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
			
			int tempTime = (int) breakTime.getValue();
			
			if(tempTime != 0){
				Break tempBreak = new Break(tempTime);
				
				
				
				
				if((int)breakPos.getValue() > 0 && (int)breakPos.getValue() < numberOfExam){
					for(int i = 0; i < plan.getTimeLineNumber(); i++){
						plan.getTimeLine(i).insert((int)breakPos.getValue(), tempBreak);
					}
				}else{
					JOptionPane.showMessageDialog(
							null,
							"Bitte geben Sie eine Position an die Im Plan liegt",
							"Kein gültige Zeit", JOptionPane.CANCEL_OPTION);
				}
				
				
				cGui.showPlan();
			}else{
				JOptionPane.showMessageDialog(
						null,
						"Bitte geben sie eine richtige Pausenläge an.",
						"Kein gültige Zeit", JOptionPane.CANCEL_OPTION);
			}
			
		}
		
	}

}
