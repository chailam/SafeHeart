/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.data;

import java.util.ArrayList;

/**
 *
 * @author caila
 */
public class Data {
  private Person person;
  private ArrayList <Person> lPerson = new ArrayList<>();
  private ArrayList <Ob> lOb = new ArrayList<>();
  private Ob ob;
  
    public Data(){
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
    
    public ArrayList getPersonList(){
        return this.lPerson;    
        }
    
    public ArrayList getObList(){
        return this.lOb;    
        }
}
