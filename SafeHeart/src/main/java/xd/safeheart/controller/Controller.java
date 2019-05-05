/*
 * FIT3077 Assignment 2 SafeHeart
 * Made by:
 *	 Aik Han Ng (28947991)
 *	 Chai Lam Loi (28136179)
 */
package xd.safeheart.controller;

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
        this.view.getInitButton().addActionListener(e -> getPatientbyPracId());
        this.view.getShowButton().addActionListener(e -> getObsByPatient());
        this.view.start();
    }
    
    /**
     * Gets all Patients of a Practitioner by its id, then updates the View
     */
    private void getPatientbyPracId()
    {
        JTextField pracIdText = this.view.getPracIdText();
        JScrollPane patientPane = this.view.getPatientPane();
        JLabel display = this.view.getDisplayText();
        JList<Patient> selectedPatientList = this.view.getSelectedPatientList();
        display.setText("");
        // clean view
        this.view.getSelectedPatient().clear();
        this.view.getSelectedOb().clear();
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
            selectedPatientList.setCellRenderer(this.view.getCheckBoxListCellRenderer());
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
        System.out.println("Getting all selected Patients cholesterol");
        List<Patient> selPat = this.view.getSelectedPatientList().getSelectedValuesList();
        // Clear all the data in list
        this.view.getSelectedPatient().clear();
        this.view.getSelectedOb().clear();
        // for every selected patient
        for (Patient p : selPat)
        {
            Observation result;
            // search for data in our program
            boolean foundInMap = false;
            for (Observation o : this.dR.getChoObsMap().values()) {
                if (o.getPatient().equals(p))
                {
                    result = o;
                    this.view.getSelectedPatient().add(p);
                    this.view.getSelectedOb().add(result);
                    foundInMap = true;
                }
            }
            // not found in program, get from server
            if(!foundInMap)
            {
                result = this.dR.getChoObsByPat(p);
                if (result != null)
                {
                    this.view.getSelectedPatient().add(p);
                    this.view.getSelectedOb().add(result);
                }
            }
        }
        // update table
        this.updateDetailTable();
    }
    
    /**
     * Update the table by the data stored
     */
    private void updateDetailTable()
    {
        JTable tab = this.view.getDetailTable();
        this.view.getDetailPane().setViewportView(tab);
        TableModel tableModel = new TableModel(this.view.getSelectedPatient(), this.view.getSelectedOb());
        tab.setModel(tableModel);
    }
    
    /**
     * Constantly updates Observations
     */
    public void updateObservations(){
        this.getObsByPatient();
    }
}