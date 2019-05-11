/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.HashMap;
import my.view.simpleGUI;
import my.model.*;

/**
 *
 * @author caila
 */
public class Controller {
    private ArrayList <Person> lPerson;
    private ArrayList <Ob> lOb;
    private ArrayList <ArrayList<Ob>> diasBlood;
    private ArrayList <ArrayList<Ob>> sysBlood;
    private ArrayList <Ob> tobacOb;
    private simpleGUI view;  //the view
  
    public Controller(ArrayList <Person> lPerson, ArrayList <Ob> lOb, ArrayList <ArrayList<Ob>> diasBlood,ArrayList <ArrayList<Ob>> sysBlood, ArrayList<Ob> tobacOb){
        this.lPerson = lPerson;
        this.lOb = lOb;
        this.diasBlood = diasBlood;
        this.sysBlood = sysBlood;
        this.tobacOb = tobacOb;
        this.view = new simpleGUI(this.lPerson,this.lOb,this.diasBlood,this.sysBlood,this.tobacOb);
    }
    
    public void startView(){
        this.view.setVisible(true);
        this.view.start();
    }
    /////Try to update value every 1 hours here
    
    public void updateCholesterol(ArrayList <Ob> newlOb){
        this.view.updateView(newlOb);
    }
}