/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xd.safeheart.controller;

import java.util.ArrayList;
import xd.safeheart.view.*;
import xd.safeheart.model.*;

/**
 *
 * @author caila
 */
public class Controller {
    private ArrayList <Patient> lPatient;
    private ArrayList <Observation> lOb;
    private View view;  //the view
    private Practitioner prac;
  
    public Controller(ArrayList <Patient> lPatient, ArrayList <Observation> lOb, Practitioner prac){
        this.lPatient = lPatient;
        this.lOb = lOb;
        this.prac = prac;
        this.view = new View (this.lPatient,this.lOb, this.prac);
    }
    
    public void startView(){
        this.view.setVisible(true);
        this.view.start();
    }
    /////Try to update value every 1 hours here
    
    public void updateCholesterol(ArrayList <Observation> newlOb){
        this.view.updateView(newlOb);
    }
}