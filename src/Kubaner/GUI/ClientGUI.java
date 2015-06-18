package Kubaner.GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Kubaner.Logic.Plan;
import Kubaner.Logic.PlanGenerator;

public class ClientGUI extends JFrame implements ActionListener{
	
	private JMenuItem open, save, exit;
	private JMenuItem createStudent, createProf, createSubject, studentSummary, profSummary, subjSummary, studentEdit, profEdit, subjEdit;
	private JMenuItem createPlan, editPlan, addPause, createPdf;
	
	private Plan plan = null;
	private PlanGenerator planGen = new PlanGenerator();
	
	public void actionPerformed ( ActionEvent e ) {
		if(e.getSource() == open){
			
			JFileChooser fileChooser = new JFileChooser();
			ObjectInputStream stream = null;
			
			int result = fileChooser.showOpenDialog(null);
			
			if (result == JFileChooser.APPROVE_OPTION) {
				File fileToOpen = fileChooser.getSelectedFile();
				
				 if(!fileToOpen.exists()){
					 
				 }
				 
				 try {
			            stream = new ObjectInputStream(new FileInputStream(fileToOpen.getAbsolutePath()));
			            plan = (Plan) stream.readObject();
			            
			            stream.close();
			        } catch (IOException ex) {
			            ex.printStackTrace();
			        } catch (ClassNotFoundException ex) {
			            ex.printStackTrace();
			        }
			}
			
		}else if(e.getSource() == save){
			
			JFileChooser fileChooser = new JFileChooser();
			
			int result = fileChooser.showSaveDialog(null);
			
            if(result == JFileChooser.APPROVE_OPTION)
            {
            	File fileToSave = fileChooser.getSelectedFile();
            	
            	 ObjectOutputStream stream = null;
                 try {
                	 
                	 if(fileToSave.exists()){
                		 stream = new ObjectOutputStream(new FileOutputStream(fileToSave.getAbsolutePath()));
                	 }else{
                		stream = new ObjectOutputStream(new FileOutputStream(fileToSave.getAbsolutePath()+".txt"));
                	 }
                    
                     stream.writeObject(plan);
                     stream.close();
                 } catch (IOException ex) {
                     ex.printStackTrace();
                 }
            }
             
			
		}else if(e.getSource() == exit){
			System.exit(0);
		}else if(e.getSource() == createStudent){
			
			new InputMaskStudent(plan, planGen).setVisible(true);
			
		}else if(e.getSource() == createProf){
			
		}else if(e.getSource() == createSubject){
			
			new InputMaskSubject(planGen).setVisible(true);
			
		}else if(e.getSource() == studentSummary){
			
		}else if(e.getSource() == profSummary){
			
		}else if(e.getSource() == subjSummary){
			
		}else if(e.getSource() == studentEdit){
			
		}else if(e.getSource() == profEdit){
			
		}else if(e.getSource() == subjEdit){
			
		}else if(e.getSource() == createPlan){
			
		}else if(e.getSource() == editPlan){
		
		}else if(e.getSource() == addPause){
			
		}else if(e.getSource() == createPdf){
			
		}
	}
	
	ClientGUI(){
		//---------------------------Menu---------------------------
		//-------Datei-------
		JMenu fileMenu = new JMenu("Datei");
		
		
		open = new JMenuItem("Öffnen...");
		open.addActionListener(this);
		fileMenu.add(open);
		save = new JMenuItem("Speichern");
		save.addActionListener(this);
		fileMenu.add(save);
		fileMenu.addSeparator();
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		fileMenu.add(exit);
		
		//-------Daten-------
		JMenu sourceMenu = new JMenu("Daten");
		
		createStudent = new JMenuItem("Student anlegen");
		createStudent.addActionListener(this);
		sourceMenu.add(createStudent);
		createProf = new JMenuItem("Dozent anlegen");
		createProf.addActionListener(this);
		sourceMenu.add(createProf);
		createSubject = new JMenuItem("Fach anlegen");
		createSubject.addActionListener(this);
		sourceMenu.add(createSubject);
		sourceMenu.addSeparator();	
		studentSummary = new JMenuItem("Studentenübersicht");
		studentSummary.addActionListener(this);
		sourceMenu.add(studentSummary);
		profSummary = new JMenuItem("Dozentenübersicht");
		profSummary.addActionListener(this);
		sourceMenu.add(profSummary);
		subjSummary = new JMenuItem("Fachübersicht");
		subjSummary.addActionListener(this);
		sourceMenu.add(subjSummary);
		sourceMenu.addSeparator();	
		studentEdit = new JMenuItem("Student bearbeiten");
		studentEdit.addActionListener(this);
		sourceMenu.add(studentEdit);
		profEdit = new JMenuItem("Dozent bearbeiten");
		profEdit.addActionListener(this);
		sourceMenu.add(profEdit);
		subjEdit = new JMenuItem("Fach bearbeiten");
		subjEdit.addActionListener(this);
		sourceMenu.add(subjEdit);
		
		
		//-------Plan-------
		
		JMenu planMenu = new JMenu("Plan");
		
		createPlan = new JMenuItem("Prüfungsplan erstellen");
		createPlan.addActionListener(this);
		planMenu.add(createPlan);
		editPlan = new JMenuItem("Prüfungsplan bearbeiten");
		editPlan.addActionListener(this);
		planMenu.add(editPlan);
		addPause = new JMenuItem("Pauseeinfügen");
		addPause.addActionListener(this);
		planMenu.add(addPause);
		createPdf = new JMenuItem("Druckversion erstellen");
		createPdf.addActionListener(this);
		planMenu.add(createPdf);
		
		  
		
		JMenuBar bar = new JMenuBar();
		bar.add(fileMenu);
		bar.add(sourceMenu);
		bar.add(planMenu);
		
		setJMenuBar(bar);		
		
		
		//---------------------------Fenster---------------------------
		
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setSize(new Dimension(screen.width / 2, screen.height / 2));
		this.setLocation(screen.width/2-this.getSize().width/2, screen.height/2-this.getSize().height/2);
	}
	
	public static void main(String[] args) {
		new ClientGUI().setVisible(true);
	}

}
