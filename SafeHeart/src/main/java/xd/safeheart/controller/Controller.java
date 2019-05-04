/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xd.safeheart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import xd.safeheart.view.*;
import xd.safeheart.model.*;

import javax.swing.JFrame;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import xd.safeheart.system.DataRetrieval;

/**
 *
 * @author caila
 */
public class Controller {
    private final View view;  //the view
    private final DataRetrieval dR;
  
    public Controller(){
        this.view = new View();
        
        String serverBaseUrl = "http://hapi-fhir.erc.monash.edu:8080/baseDstu3/";
        String id = "1";
        //Retrieve data from server
        dR = new DataRetrieval(serverBaseUrl);
    }
    
    public void initView(){
        this.view.setVisible(true);
        this.view.getInitButton().addActionListener(e -> getPatientbyPracId());
        this.view.getShowButton().addActionListener(e -> getObsByPatient());
        this.view.start();
    }
    
    private void getPatientbyPracId()
    {
        JTextField pracIdText = this.view.getPracIdText();
        JScrollPane patientPane = this.view.getPatientPane();
        JLabel display = this.view.getDisplayText();
        JList<Patient> selectedPatientList = this.view.getSelectedPatientList();
        display.setText("");
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
        if (this.dR.populateDataByPractitionerId(pracId))
        {
            HashMap<String, Patient> patientMap = this.dR.getPatientMap();
            ///// Reset the Model for list to show the value
            patientPane.setViewportView(selectedPatientList);
            DefaultListModel<Patient> listModel = new DefaultListModel<>();
            for (HashMap.Entry<String, Patient> entry : patientMap.entrySet()) {
                listModel.addElement(entry.getValue());
            }

            selectedPatientList.setModel(listModel);   
            ///// Make the list become Checkbox
            selectedPatientList.setCellRenderer(this.view.getNewCheckBoxListCellRenderer());
        }
    }
    
    private void getObsByPatient()
    {
        System.out.println("Getting all selected Patients cholesterol");
        List<Patient> selPat = this.view.getSelectedPatientList().getSelectedValuesList();
        // Clear all the data in list
        this.view.getSelectedPatient().clear();
        this.view.getSelectedOb().clear();
        for (Patient p : selPat)
        {
            Observation result = this.dR.getChoObsByPat(p);
            if (result != null)
            {
                this.view.getSelectedPatient().add(p);
                this.view.getSelectedOb().add(result);
            }
        }
        JTable tab = this.view.getDetailTable();
        this.view.getDetailPane().setViewportView(tab);
        TableModel tableModel = new TableModel(this.view.getSelectedPatient(), this.view.getSelectedOb());
        tab.setModel(tableModel);
    }
    /////Try to update value every 1 hours here
    
    public void updateCholesterol(HashMap <String,Observation> newchoObsMap){
        this.view.updateView(newchoObsMap);
    }
}