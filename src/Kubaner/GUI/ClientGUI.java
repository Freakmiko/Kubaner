package Kubaner.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.itextpdf.text.DocumentException;

import Kubaner.Logic.Plan;
import Kubaner.Logic.PlanGenerator;
import Kubaner.Logic.Professor;
import Kubaner.Logic.Time;

public class ClientGUI extends JFrame implements ActionListener{
	
	private JMenuItem open, save, exit;
	private JMenuItem createStudent, createProf, createSubject, studentSummary, profSummary, subjSummary, studentEdit, profEdit, subjEdit;
	private JMenuItem createPlan, addPause, createPdf;
	private JMenu editPlan;
	private JMenuItem deleteExam, switchExams;
	
	private JTable table;
	
	private Plan plan = null;
	private PlanGenerator planGen = new PlanGenerator();
	
	
	public void actionPerformed ( ActionEvent e ) {
		if(e.getSource() == open){
			
			JFileChooser fileChooser = new JFileChooser();
			
			int result = fileChooser.showOpenDialog(null);
			
			if (result == JFileChooser.APPROVE_OPTION) {
				File fileToOpen = fileChooser.getSelectedFile();
				
				 if(!fileToOpen.exists()){
					 
				 }
				 
				 try {
			           this.plan = planGen.loadPlan(fileToOpen.getAbsolutePath());
			            
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
            	
                 try {
                	 
                	 planGen.savePlan(plan, fileToSave.getAbsolutePath());
                	 
                 } catch (IOException ex) {
                     ex.printStackTrace();
                 }
            }
             
			
		}else if(e.getSource() == exit){
			System.exit(0);
		}else if(e.getSource() == createStudent){
			
			try{
				JFrame tempWindow;
				tempWindow = new InputMaskStudent(plan, planGen);
				tempWindow.setVisible(true);
			}catch(NoSubjectException ex){
				
				JOptionPane.showMessageDialog(
						null,
						"Erstellen Sie zuerst ein Fach bevor Sie einen Studenten erstellen.",
						"Kein Fach vorhanden", JOptionPane.CANCEL_OPTION);
			}
			
			
		}else if(e.getSource() == createProf){
			
			try{
				JFrame tempWindow;
				tempWindow = new InputMaskProfessor(plan, planGen);
				tempWindow.setVisible(true);
			}catch(NoSubjectException ex){
				
				JOptionPane.showMessageDialog(
						null,
						"Erstellen Sie zuerst ein Fach bevor Sie einen Dozenten erstellen.",
						"Kein Fach vorhanden", JOptionPane.CANCEL_OPTION);
			}
			
		}else if(e.getSource() == createSubject){
			
			new InputMaskSubject(planGen).setVisible(true);
			
		}else if(e.getSource() == studentSummary){
			
			try {
				new overviewMaskStudent(planGen).setVisible(true);
			} catch (NoElementException e1) {
				JOptionPane.showMessageDialog(
						null,
						"Erstellen Sie zuerst ein Studenten.",
						"Kein Student vorhanden", JOptionPane.CANCEL_OPTION);
			}
			
		}else if(e.getSource() == profSummary){
			
			try {
				new overviewMaskProfessor(planGen).setVisible(true);
			} catch (NoElementException e1) {
				JOptionPane.showMessageDialog(
						null,
						"Erstellen Sie zuerst ein Professor.",
						"Kein Professor vorhanden", JOptionPane.CANCEL_OPTION);
			}
			
		}else if(e.getSource() == subjSummary){
			
			try {
				new overviewMaskSubject(planGen).setVisible(true);
			} catch (NoElementException e1) {
				JOptionPane.showMessageDialog(
						null,
						"Erstellen Sie zuerst ein Fach.",
						"Kein Facher vorhanden", JOptionPane.CANCEL_OPTION);
			}
			
		}else if(e.getSource() == studentEdit){
			
			try {
				new SelectStudent(plan, planGen).setVisible(true);
			} catch (NoSubjectException | NoElementException e1) {
				JOptionPane.showMessageDialog(
						null,
						"Erstellen Sie zuerst einen Studenten.",
						"Kein Student vorhanden", JOptionPane.CANCEL_OPTION);
			}
			
		}else if(e.getSource() == profEdit){
			
			try {
				new SelectProfessor(plan, planGen).setVisible(true);
			} catch (NoSubjectException | NoElementException e1) {
				JOptionPane.showMessageDialog(
						null,
						"Erstellen Sie zuerst einen Dozenten.",
						"Kein Professor vorhanden", JOptionPane.CANCEL_OPTION);
			}
			
		}else if(e.getSource() == subjEdit){
			
			try {
				new SelectSubject(plan, planGen).setVisible(true);
			} catch (NoElementException e1) {
				JOptionPane.showMessageDialog(
						null,
						"Erstellen Sie zuerst eine Fach.",
						"Kein Fach vorhanden", JOptionPane.CANCEL_OPTION);
			}
			
		}else if(e.getSource() == createPlan){
			
			if(planGen.getProfList().size() != 0){
				if(planGen.getStudentList().size() != 0){
					
					InputSartTimeAndCreatPlan tempWindow;
					tempWindow = new InputSartTimeAndCreatPlan(planGen, this);
					tempWindow.setVisible(true);					
					
				}else{
					JOptionPane.showMessageDialog(
							null,
							"Bitte legen sie zuerst Studenten an bevor, sie einen Plan erstellen m\u00F6chten.",
							"Kein Student vorhanden", JOptionPane.CANCEL_OPTION);
				}
			}else{
				JOptionPane.showMessageDialog(
						null,
						"Bitte legen sie zuerst Profesoren an, bevor sie einen Plan erstellen m\u00F6chten.",
						"Kein Professor vorhanden", JOptionPane.CANCEL_OPTION);
			}
			
			
		}else if(e.getSource() == deleteExam){
			
			if(plan != null){
				new removeExam(plan, this).setVisible(true);
			}else{
				JOptionPane.showMessageDialog(
						null,
						"Bitte erstellen/laden Sie zuerst einen Plan.",
						"Kein Plan vorhanden", JOptionPane.CANCEL_OPTION);
			}
			
			
		}else if(e.getSource() == switchExams){
		
		}else if(e.getSource() == addPause){
			
			if(plan != null){
				new CreateBreak(plan, this).setVisible(true);
			}else{
				JOptionPane.showMessageDialog(
						null,
						"Bitte erstellen/laden Sie zuerst einen Plan.",
						"Kein Plan vorhanden", JOptionPane.CANCEL_OPTION);
			}
			
		}else if(e.getSource() == createPdf){
			
			if(plan != null){
			
				JFileChooser fileChooser = new JFileChooser();
				
				int result = fileChooser.showSaveDialog(null);
				
	            if(result == JFileChooser.APPROVE_OPTION)
	            {
	            	File fileToSave = fileChooser.getSelectedFile();
	            	
	            	try {
						plan.createPdf(fileToSave.getAbsolutePath(), 0, null);
						
						plan.createPdf(fileToSave.getAbsolutePath(), 1, null);
						
						for(Professor prof : planGen.getProfList()){
							plan.createPdf(fileToSave.getAbsolutePath(), 2, prof.getName());
						}
						
					} catch (FileNotFoundException | DocumentException e1) {
						JOptionPane.showMessageDialog(
								null,
								"Datei konnte nicht angelegt werden.",
								"Kein Pdf m\u00F6glich", JOptionPane.CANCEL_OPTION);
					}
	            	
	                
	            }
            
			}else{
				JOptionPane.showMessageDialog(
						null,
						"Bitte erstellen/laden Sie zuerst einen Plan.",
						"Kein Plan vorhanden", JOptionPane.CANCEL_OPTION);
			}
            
		}
	}
	
	ClientGUI(){
		//---------------------------Menu---------------------------
		//-------Datei-------
		JMenu fileMenu = new JMenu("Datei");
		
		
		open = new JMenuItem("\u00D6ffnen...");
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
		studentSummary = new JMenuItem("Studenten\u00FCbersicht");
		studentSummary.addActionListener(this);
		sourceMenu.add(studentSummary);
		profSummary = new JMenuItem("Dozenten\u00FCbersicht");
		profSummary.addActionListener(this);
		sourceMenu.add(profSummary);
		subjSummary = new JMenuItem("Fach\u00FCbersicht");
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
		
		createPlan = new JMenuItem("Pr\u00FCfungsplan erstellen");
		createPlan.addActionListener(this);
		planMenu.add(createPlan);
		
		editPlan = new JMenu("Pr\u00FCfungsplan bearbeiten");
		//editPlan.setMnemonic(KeyEvent.VK_S);
		
		deleteExam = new JMenuItem("Termin l\u00F6schen");
		deleteExam.setEnabled(false);
		editPlan.add(deleteExam);
		
		switchExams = new JMenuItem("Termin Vertauschen");
		switchExams.setEnabled(false);
		editPlan.add(switchExams);
		
		planMenu.add(editPlan);
		
		addPause = new JMenuItem("Pause einf\u00FCgen");
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
		
		setTitle("Pr\u00FCfungsplan Generator");
		
		getContentPane().setLayout( new BorderLayout() );
		
		TableModel dataModel = new DataModel(25,25);
		table = new JTable(dataModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		add( new JScrollPane( table ), BorderLayout.CENTER );
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setSize(new Dimension(screen.width / 2, screen.height / 2));
		this.setLocation(screen.width/2-this.getSize().width/2, screen.height/2-this.getSize().height/2);
	}
	
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
	public void showPlan(){
		TableModel dataModel = plan.createAbstractTableModel();
		table.setModel(dataModel);
		table.repaint();
	}
	
	public static void main(String[] args) {
		new ClientGUI().setVisible(true);
	}

}
