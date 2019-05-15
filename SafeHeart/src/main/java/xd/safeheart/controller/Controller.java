/*
 * FIT3077 Assignment 2 SafeHeart
 * Made by:
 *	 Aik Han Ng (28947991)
 *	 Chai Lam Loi (28136179)
 */
package xd.safeheart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import xd.safeheart.view.*;
import xd.safeheart.model.*;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import xd.safeheart.system.DataRetrieval;

/**
 * Controller notifies the DataRetrieval (model) and updates the view
 * It also listens to any user interaction on the view
 * @author Chai Lam
 * @author Aik Han
 */
public class Controller {
    private final View view;  //the view
    private final DataRetrieval dR;
  
    // Constructor
    public Controller(){
        this.view = new View();
        
        String serverBaseUrl = "http://hapi-fhir.erc.monash.edu:8080/baseDstu3/";
        //Retrieve data from server
        dR = new DataRetrieval(serverBaseUrl);
    }
    
    // Getter
    public DataRetrieval getDR()
    {
        return this.dR;
    }
    
    // initialise view
    public void initView(){
        this.view.setVisible(true);
        this.view.getInitButton().addActionListener(e -> getPatientByPracId());
        this.view.getShowButton().addActionListener(e -> getObsByPatient());
        this.view.start();
    }
    
    /**
     * Gets all Patients of a Practitioner by its id, then updates the View
     */
    private void getPatientByPracId()
    {
        JTextField pracIdText = this.view.getPracIdText();
        JScrollPane patientPane = this.view.getPatientPane();
        JLabel display = this.view.getDisplayText();
        JList<Patient> selectedPatientList = this.view.getSelectedPatientJList();
        display.setText("");
        // clean view
        this.view.clearSelected();
        this.updateDetailTable();
        // practitioner's id
        String pracId = pracIdText.getText();
        // test if id a number
        try { 
            Integer.parseInt(pracId); 
        } catch(NumberFormatException e) { 
            display.setText("Id is not a number!");
            return; 
        } catch(NullPointerException e) {
            display.setText("Id is empty!");
            return;
        }
        // if data retrieval is successful
        if (this.dR.populateDataByPractitionerId(pracId))
        {
            Practitioner prac = this.dR.getPractitioner();
            // change PracNameText to display name of practitioner
            this.view.getPracNameText().setText("Practitioner: " + prac.getFamilyName() + " " + prac.getGivenName());
            HashMap<String, Patient> patientMap = this.dR.getPatientMap();
            // Reset the Model for list to show the value
            patientPane.setViewportView(selectedPatientList);
            DefaultListModel<Patient> listModel = new DefaultListModel<>();
            for (HashMap.Entry<String, Patient> entry : patientMap.entrySet()) {
                listModel.addElement(entry.getValue());
            }

            selectedPatientList.setModel(listModel);   
            // Make the list become Checkbox
            selectedPatientList.setCellRenderer(this.view.getChkBoxRenderer());
        }
        else
        {
            display.setText("Error finding Practitioner!");
        }
    }
    
    /**
     * Gets all Observers by a patient or a set of patients selected.
     */
    private void getObsByPatient()
    {
        //"2093-3" for "Total Cholesterol",    "8462-4" for "Diastolic Blood Pressure",    "8480-6"for "Systolic Blood Pressure",   "72166-2" for "Tobacco smoking status NHIS"
        System.out.println("Getting all Observations for selected Patients");
        List<Patient> selPat = this.view.getSelectedPatientJList().getSelectedValuesList();
        // Clear all the data in list
        this.view.clearSelected();
        for (Patient p : selPat)
        {
            boolean foundInMap = false;
            Observation obsResult;
            ArrayList<Observation> obsResultList;
            
            for (Observation o : this.dR.getObsMap().values()) {
                obsResult = o;
                // search for Cholesterol Data in our program
                if (o.getPatient().equals(p) && o.getType().equals("Total Cholesterol"))
                {
                    this.view.getSelectedChoObs().add(obsResult);
                    foundInMap = true;
                } else if (o.getPatient().equals(p) && o.getType().equals("Diastolic Blood Pressure"))  // search for Diastolic Blood Pressure Data in our program
                {
                    this.view.getSelectedBloodDiasObs().get(Integer.toString(p.getId())).add(o);
                    foundInMap = true;
                } else if (o.getPatient().equals(p) && o.getType().equals("Systolic Blood Pressure")) // search for Systolic Blood Pressure Data in our program
                {
                    this.view.getSelectedBloodSysObs().get(Integer.toString(p.getId())).add(o);
                    foundInMap = true;
                } else if (o.getPatient().equals(p) && o.getType().equals("Tobacco smoking status NHIS")) // search for Tobacco smoking status NHIS Data in our program
                {
                    this.view.getSelectedTobacObs().add(obsResult);
                    foundInMap = true;
                }
            }
            // not found in program, get from server
            //"2093-3" for "Total Cholesterol",    "8462-4" for "Diastolic Blood Pressure",    "8480-6"for "Systolic Blood Pressure",   "72166-2" for "Tobacco smoking status NHIS"
            if(!foundInMap)
            {
                obsResult = this.dR.getRecentObsByPat(p,"2093-3");
                if (obsResult != null)
                {
                    this.view.getSelectedChoObs().add(obsResult);
                } 
                obsResult = this.dR.getRecentObsByPat(p,"72166-2");
                if (obsResult != null)
                {
                    this.view.getSelectedTobacObs().add(obsResult);
                }
                obsResultList = this.dR.getAllHistoricObsByPat(p,"8462-4");
                if (obsResult != null)
                {
                    this.view.getSelectedBloodDiasObs().put(Integer.toString(p.getId()), obsResultList);
                }
                obsResultList = this.dR.getAllHistoricObsByPat(p,"8480-6");
                if (obsResult != null)
                {
                    this.view.getSelectedBloodSysObs().put(Integer.toString(p.getId()), obsResultList);
                }
            }
        }
        // update table
        this.updateDetailTable();
        
        // Show graph
        this.showGraph();
        
    }

    
    /**
     * Update the table by the data stored
     */
    private void updateDetailTable()
    {
        //Update cholesterol table
        JTable tab = this.view.getchoJTable();
        this.view.getchoPane().setViewportView(tab);
        TableModel1 tableModel = new TableModel1(this.view.getSelectedChoObs());
        tab.setModel(tableModel);
        
        //Upddate tobacco table
        JTable tobac = this.view.gettobJTable();
        this.view.gettobPane().setViewportView(tobac);
        TableModel2 tableModel2 = new TableModel2(this.view.getSelectedTobacObs());
        tab.setModel(tableModel);
    }
    
    private void showGraph()
    {
        
        for (HashMap.Entry<String,ArrayList<Observation>> entry : this.view.getSelectedBloodSysObs().entrySet()) 
        {
            System.out.println("Key = " + entry.getKey() +  ", Value = " + entry.getValue());
            LineChart chart = new LineChart(this.view.getSelectedBloodSysObs().get(entry.getKey()).get(0).getPatient().getId() + 
                    this.view.getSelectedBloodSysObs().get(entry.getKey()).get(0).getPatient().getFamilyName() + 
                    this.view.getSelectedBloodSysObs().get(entry.getKey()).get(0).getPatient().getGivenName(),
                    this.view.getSelectedBloodDiasObs().get(entry.getKey()),
                    this.view.getSelectedBloodSysObs().get(entry.getKey()));
            chart.setSize(800, 400);
            chart.setVisible(true);
        }
    }
    
    /**
     * Constantly updates Observations
     */
    public void updateObservations(){
        this.getObsByPatient();
    }
}