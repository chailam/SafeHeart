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
import javax.swing.table.AbstractTableModel;
import observerPattern.Observer;
import xd.safeheart.system.AbstractDataRetrieval;
import xd.safeheart.system.DataRetrieval;

/**
 * Controller notifies the DataRetrieval (model) and updates the view
 * It also listens to any user interaction on the view
 * Controller for the MVC structure
 * @author Chai Lam
 * @author Aik Han
 */
public class Controller implements Observer{
    private final View view;  //the view
    private final AbstractDataRetrieval dR;
    private String sysAlert;  //the alert label for systolic blood pressure
    private String diasAlert;  //the alert label for diastolic blood pressure
    private HashMap <String, LineChart> chartMap = new HashMap<>();
    private int determineView;
  
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
        return (DataRetrieval) this.dR;
    }
    
    // initialise view
    public void initView(){
        this.view.setVisible(true);
        this.view.getInitButton().addActionListener(e -> getPatientByPracId());
        this.view.getShowChoButton().addActionListener(e -> getChoObsByPatient());
        this.view.getShowTobBloodButton().addActionListener(e->getTobacBloodObsByPatient());
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
     * Gets all Cholesterol Observation by a patient or a set of patients selected.
     */
    private void getChoObsByPatient()
    {
        // Set the determineView to 1 for viewing cholesterol
        determineView = 1;
        
        //"2093-3" for "Total Cholesterol",    "8462-4" for "Diastolic Blood Pressure",    "8480-6"for "Systolic Blood Pressure",   "72166-2" for "Tobacco smoking status NHIS"
        System.out.println("Getting all Cholesterol Observations for selected Patients");
        List<Patient> selPat = this.view.getSelectedPatientJList().getSelectedValuesList();
        // Clear all the data in list
        this.view.clearChoSelected();
        // Clear the graph
        this.clearGraph();
        
        // Search for selected observation
        for (Patient p : selPat)
        {
            boolean foundInMap = false;
            Observation obsResult;
            
            if (this.dR.getChoObsMap().containsKey(Integer.toString(p.getId())))
            {
                this.view.getSelectedChoObs().add(this.dR.getChoObsMap().get(Integer.toString(p.getId())));
                foundInMap = true;
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
            }
        }
        // update table
        this.updateChoTable();     
    }

    /**
     * Gets all Tobacco & Blood Pressure Observation by a patient or a set of patients selected.
     */
    private void getTobacBloodObsByPatient()
    {
        // Set the determineView to 2 for viewing cholesterol
        determineView = 2;
        
        //"2093-3" for "Total Cholesterol",    "8462-4" for "Diastolic Blood Pressure",    "8480-6"for "Systolic Blood Pressure",   "72166-2" for "Tobacco smoking status NHIS"
        System.out.println("Getting all Tobacco and Blood Pressure Observations for selected Patients");
        List<Patient> selPat = this.view.getSelectedPatientJList().getSelectedValuesList();
        // Clear all the data in list
        this.view.clearTobBloodSelected();
        // Clear the graph
        this.clearGraph();
        
        // Search for selected observation
        for (Patient p : selPat)
        {
            boolean foundInMap = false;
            Observation obsResult;
            ArrayList<ArrayList<Observation>> bloodPressureResultList;
            
            if (this.dR.getBloodDiasObsMap().containsKey(Integer.toString(p.getId())))
            {
                this.view.getSelectedBloodDiasObs().put(Integer.toString(p.getId()), new ArrayList<>(this.dR.getBloodDiasObsMap().get(Integer.toString(p.getId()))));
                foundInMap = true;
            }
            
            if (this.dR.getBloodSysObsMap().containsKey(Integer.toString(p.getId())))
            {
                this.view.getSelectedBloodSysObs().put(Integer.toString(p.getId()), new ArrayList<>(this.dR.getBloodSysObsMap().get(Integer.toString(p.getId()))));
                foundInMap = true;
            }
            
            if (this.dR.getTobacObsMap().containsKey(Integer.toString(p.getId())))
            {
                this.view.getSelectedTobacObs().add(this.dR.getTobacObsMap().get(Integer.toString(p.getId())));
                foundInMap = true;
            }
            
            
            // not found in program, get from server
            //"2093-3" for "Total Cholesterol",    "8462-4" for "Diastolic Blood Pressure",    "8480-6"for "Systolic Blood Pressure",   "72166-2" for "Tobacco smoking status NHIS"
            if(!foundInMap)
            {
                obsResult = this.dR.getRecentObsByPat(p,"72166-2");
                if (obsResult != null)
                {
                    this.view.getSelectedTobacObs().add(obsResult);
                }
                bloodPressureResultList = this.dR.getAllHistoricObsByPat(p, "55284-4");
                if (bloodPressureResultList != null)
                {
                    // diastolic
                    if (bloodPressureResultList.get(0) != null)
                    {
                        this.view.getSelectedBloodDiasObs().put(Integer.toString(p.getId()), bloodPressureResultList.get(0));
                    }
                    
                    // systolic
                    if (bloodPressureResultList.get(1) != null)
                    {
                        this.view.getSelectedBloodSysObs().put(Integer.toString(p.getId()), bloodPressureResultList.get(1));
                    }
                }
            }
        }
        // update table
        this.updateTobTable();
        
        // Show graph
        this.showGraph();
        
        // Show alert message
        this.bloodPressureAlert();
    }
    
    
    
    /**
     * Update the Cholesterol table by the data stored
     */
    private void updateChoTable()
    {
        //Update cholesterol table
        JTable tab = this.view.getchoJTable();
        this.view.getShowPane().setViewportView(tab);
        AbstractTableModel tableModel = new TableModel1(this.view.getSelectedChoObs());
        tab.setModel(tableModel);
    }
    
    /**
     * Update the Tobacco table by the data stored
     */
    private void updateTobTable()
    {
        //Update tobacco table
        JTable tobac = this.view.gettobJTable();
        this.view.getShowPane().setViewportView(tobac);
        AbstractTableModel tableModel2 = new TableModel2(this.view.getSelectedTobacObs());
        tobac.setModel(tableModel2);
    }
    
    /**
     * Show the graph of Blood Pressure
     */
    private void showGraph()
    {
        for (HashMap.Entry<String,ArrayList<Observation>> entry : this.view.getSelectedBloodSysObs().entrySet()) 
        {
            Patient p = this.view.getSelectedBloodSysObs().get(entry.getKey()).get(0).getPatient();
            LineChart chart = new LineChart(p.getId() + p.getFamilyName() + p.getGivenName(),
                    this.view.getSelectedBloodSysObs().get(entry.getKey()),
                    this.view.getSelectedBloodDiasObs().get(entry.getKey()));

            chart.setSize(800, 400);
            chart.setVisible(true);
            chartMap.put(Integer.toString(p.getId()),chart);
        }
    }
    
    /**
     * Clear the graph
     */
    private void clearGraph(){
        if (chartMap.size() > 0){
            for (HashMap.Entry<String, LineChart> entry : chartMap.entrySet()){
                entry.getValue().setVisible(false);
                entry.getValue().dispose();
            }
        }
        chartMap.clear();
    }
   
    /**
     * Display the blood pressure when it reaches a threshold
     */
    private void bloodPressureAlert(){
        this.sysAlert = "";
        this.diasAlert = "";
        this.view.getSysBloodPressureIDText().setText(this.sysAlert);
        this.view.getDiasBloodPressureIDText().setText(this.diasAlert);
                
        // loop systolic blood pressure
        for (HashMap.Entry<String,ArrayList<Observation>> entry : this.view.getSelectedBloodSysObs().entrySet()) 
        {
            ArrayList<Observation> sysList = entry.getValue();
            for (int j = 0; j < sysList.size();j++){
                // check if any of them reaches the tolerance value
                if (Float.parseFloat(sysList.get(j).getValue()) > 180){
                    sysAlert = sysAlert + Integer.toString(sysList.get(j).getPatient().getId()) + sysList.get(j).getPatient().getFamilyName() + sysList.get(j).getPatient().getGivenName() + ", ";
                    this.view.getSysBloodPressureIDText().setText(sysAlert);
                    break;
                }
            }
        }
        
        // loop diastolic blood pressure
        for (HashMap.Entry<String,ArrayList<Observation>> entry : this.view.getSelectedBloodDiasObs().entrySet()) 
        {
            ArrayList<Observation> diasList = entry.getValue();
            for (int j = 0; j < diasList.size();j++){
                // check if any of them reaches the tolerance value
                if (Float.parseFloat(diasList.get(j).getValue()) > 120){
                    diasAlert = diasAlert + Integer.toString(diasList.get(j).getPatient().getId()) + diasList.get(j).getPatient().getFamilyName() + diasList.get(j).getPatient().getGivenName() + ", ";
                    this.view.getDiasBloodPressureIDText().setText(diasAlert);
                    break;
                }
            }
        }
    }
    
    /**
     * Constantly updates Observations
     */
    @Override
    public void update(){
        this.dR.reGetObs();
        // if cholesterol view is opened
        if (determineView == 1){
            this.getChoObsByPatient();
        }
        // if Tobacco/Blood Pressure view is opened
        else if (determineView == 2){
        this.getTobacBloodObsByPatient();
        }
    }
}