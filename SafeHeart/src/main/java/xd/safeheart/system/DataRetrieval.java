/*
TODO: 1. You should add your FHIRManager code into here!!!!! (retrieve data from server & put it into class)
the list of the patient is lPatient and observation is lOb
*/
package xd.safeheart.system;

import java.util.ArrayList;
import xd.safeheart.model.*;

public class DataRetrieval extends AbstractDataRetrieval{

    private Patient person;
    private Observation ob;
    private Practitioner prac;
    
    public DataRetrieval(){
    //Constructor
    }
   
    public void startRetrieve(){
        // the retrieval process
        this.allMethod();
    }
    public Practitioner getPractitioner(){
        return this.prac;
    }

    private void allMethod(){
        this.prac = new Practitioner(999,"FamilyPrac","GivenPrac",100,"M");

        this.person = new Patient(1, "familyName1", "GivenName1", 1, "F" );
        this.lPatient.add(this.person);
        this.ob = new Observation(101,"Cho", "mg/L", this.person, "1Cho");
        this.lOb.add(this.ob);
        
        this.person = new Patient(2, "familyName2", "GivenName2", 2, "M" );
        this.lPatient.add(this.person);
        this.ob = new Observation(102,"Cho", "mg/L", this.person, "2Cho");
        this.lOb.add(this.ob);
        
        this.person = new Patient(3, "familyName3", "GivenName3", 3, "F" );
        this.lPatient.add(this.person);
        this.ob = new Observation(103,"Cho", "mg/L", this.person, "3Cho");
        this.lOb.add(this.ob);
        
        this.person = new Patient(4, "familyName4", "GivenName4", 4, "F" );
        this.lPatient.add(this.person);
        this.ob = new Observation(104,"Cho", "mg/L", this.person, "4Cho");
        this.lOb.add(this.ob);
        
        this.person = new Patient(5, "familyName5", "GivenName5", 5, "M" );
        this.lPatient.add(this.person);
        this.ob = new Observation(105,"Cho", "mg/L", this.person, "5Cho");
        this.lOb.add(this.ob);
        
        this.person = new Patient(6, "familyName6", "GivenName6", 6, "F" );
        this.lPatient.add(this.person);
        this.ob = new Observation(106,"Cho", "mg/L", this.person, "6Cho");
        this.lOb.add(this.ob);
    }
 
    public ArrayList <Observation> reGetObs(){
        this.lOb.clear();
        this.ob = new Observation(201,"Cho", "mg/L", this.person, "201Cho");
        this.lOb.add(this.ob);
        this.ob = new Observation(202,"Cho", "mg/L", this.person, "202Cho");
        this.lOb.add(this.ob);
        this.ob = new Observation(203,"Cho", "mg/L", this.person, "203Cho");
        this.lOb.add(this.ob);
        return this.lOb;
               
    }
}
