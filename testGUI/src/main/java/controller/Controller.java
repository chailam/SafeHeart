/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import my.view.simpleGUI;
import my.model.*;

/**
 *
 * @author caila
 */
public class Controller {
    private ArrayList <Person> lPerson;
    private ArrayList <Ob> lOb;
    private simpleGUI view;  //the view
  
    public Controller(ArrayList <Person> lPerson, ArrayList <Ob> lOb){
        this.lPerson = lPerson;
        this.lOb = lOb;
        this.view = new simpleGUI(this.lPerson,this.lOb);
    }
    
    public void startView(){
        this.view.setVisible(true);
        this.view.start();
    }
    /////Try to update value every 1 hours here
    
    public void updateView(){
        ///view.xxxxxx  (see it later)
    }
}