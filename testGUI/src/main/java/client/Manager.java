/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import my.view.simpleGUI;
import controller.Controller;
import my.model.*;
import java.util.ArrayList;


public class Manager {
  
        ArrayList <Person> lPerson = new ArrayList<>();
        ArrayList <Ob> lOb = new ArrayList<>();
        Person person;
        Ob ob;
        simpleGUI view;  //the view
        Controller controller;

    public void run(){
        /////put all method access to server here
        this.allMethod();
        view = new simpleGUI(lPerson,lOb);
        controller = new Controller(person,view,ob);
        controller.startView();
    }
    
    
     private void allMethod(){
        this.person = new Person(1, "familyName1", "GivenName1", 1, "F" );
        this.lPerson.add(this.person);
        this.ob = new Ob(101,"Cho", "mg/L", this.person, "1Cho");
        this.lOb.add(this.ob);
        
        this.person = new Person(2, "familyName2", "GivenName2", 2, "M" );
        this.lPerson.add(this.person);
        this.ob = new Ob(102,"Cho", "mg/L", this.person, "2Cho");
        this.lOb.add(this.ob);
        
        this.person = new Person(3, "familyName3", "GivenName3", 3, "F" );
        this.lPerson.add(this.person);
        this.ob = new Ob(103,"Cho", "mg/L", this.person, "3Cho");
        this.lOb.add(this.ob);
        
        this.person = new Person(4, "familyName4", "GivenName4", 4, "F" );
        this.lPerson.add(this.person);
        this.ob = new Ob(104,"Cho", "mg/L", this.person, "4Cho");
        this.lOb.add(this.ob);
        
        this.person = new Person(5, "familyName5", "GivenName5", 5, "M" );
        this.lPerson.add(this.person);
        this.ob = new Ob(105,"Cho", "mg/L", this.person, "5Cho");
        this.lOb.add(this.ob);
        
        this.person = new Person(6, "familyName6", "GivenName6", 6, "F" );
        this.lPerson.add(this.person);
        this.ob = new Ob(106,"Cho", "mg/L", this.person, "6Cho");
        this.lOb.add(this.ob);
    }
     
}
