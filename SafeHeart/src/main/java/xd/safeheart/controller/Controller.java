/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xd.safeheart.controller;

import java.util.HashMap;
import xd.safeheart.view.*;
import xd.safeheart.model.*;

/**
 *
 * @author caila
 */
public class Controller {
    private final HashMap <String,Patient> patientMap;
    private final HashMap <String,Observation> choObsMap;
    private final View view;  //the view
    private final Practitioner prac;
  
    public Controller(HashMap <String,Patient> patientMap, HashMap <String,Observation> choObsMap, Practitioner prac){
        this.patientMap = patientMap;
        this.choObsMap = choObsMap;
        this.prac = prac;
        this.view = new View (this.patientMap,this.choObsMap, this.prac);
    }
    
    public void startView(){
        this.view.setVisible(true);
        this.view.start();
    }
    /////Try to update value every 1 hours here
    
    public void updateCholesterol(HashMap <String,Observation> newchoObsMap){
        this.view.updateView(newchoObsMap);
    }
}