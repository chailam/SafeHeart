/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.ArrayList;
import my.model.*;



public abstract class AbstractDataRetrieval {
    //Interface or Abstract???
    ArrayList <Person> lPerson;
    ArrayList <Ob> lOb;
    // all the lists...............observation, diagreport, encounter and all

    public AbstractDataRetrieval(){
        lPerson = new ArrayList<>();
        lOb = new ArrayList<>();
}
    
    public ArrayList <Person> getPersonList(){
        return this.lPerson;
    }
    
    public ArrayList <Ob> getObList(){
        return this.lOb;
    }
    
}
