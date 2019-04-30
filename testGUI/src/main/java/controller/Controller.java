/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import my.view.simpleGUI;
import my.model.*;

/**
 *
 * @author caila
 */
public class Controller {
  private Person person;
  private Ob ob;
  private simpleGUI view;  //the view
  
    public Controller(Person person, simpleGUI view, Ob ob){
        this.person = person;
        this.view = view;
        this.ob = ob;
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