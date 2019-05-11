/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import my.model.*;

public class DataRetrieval extends AbstractDataRetrieval{

    private Person person;
    private Ob ob;
    private ArrayList <Ob> diasBloodMap = new ArrayList<>();
    private ArrayList <Ob> sysBloodMap= new ArrayList<>();
    
    public DataRetrieval(){
    //Constructor
    }
   
    public void startRetrieve(){
        // the retrieval process
        this.allMethod();
    }
    
//    int id, String type, String unit, Person patient, String value
    private void allMethod(){
        this.person = new Person(1, "familyName1", "GivenName1", 1, "F" );
        this.lPerson.add(this.person);
        this.ob = new Ob(101,"Total Cholesterol", "mg/L", this.person, "1Cho");
        this.lOb.add(this.ob);
        this.ob = new Ob(101,"Tobacco smoking status NHIS", "mg/L", this.person, "1Tob");
        this.lOb.add(this.ob);
        
        
        this.person = new Person(2, "familyName2", "GivenName2", 2, "M" );
        this.lPerson.add(this.person);
        this.ob = new Ob(102,"Total Cholesterol", "mg/L", this.person, "2Cho");
        this.lOb.add(this.ob);
        this.ob = new Ob(102,"Tobacco smoking status NHIS", "mg/L", this.person, "2Tob");
        this.lOb.add(this.ob);
        
        this.person = new Person(3, "familyName3", "GivenName3", 3, "F" );
        this.lPerson.add(this.person);
        this.ob = new Ob(103,"Total Cholesterol", "mg/L", this.person, "3Cho");
        this.lOb.add(this.ob);
        this.ob = new Ob(103,"Tobacco smoking status NHIS", "mg/L", this.person, "3Tob");
        this.lOb.add(this.ob);
        
        this.person = new Person(4, "familyName4", "GivenName4", 4, "F" );
        this.lPerson.add(this.person);
        this.ob = new Ob(104,"Total Cholesterol", "mg/L", this.person, "4Cho");
        this.lOb.add(this.ob);
        this.ob = new Ob(104,"Tobacco smoking status NHIS", "mg/L", this.person, "4Tob");
        this.lOb.add(this.ob);
        
        this.person = new Person(5, "familyName5", "GivenName5", 5, "M" );
        this.lPerson.add(this.person);
        this.ob = new Ob(105,"Total Cholesterol", "mg/L", this.person, "5Cho");
        this.lOb.add(this.ob);
        this.ob = new Ob(105,"Tobacco smoking status NHIS", "mg/L", this.person, "5Tob");
        this.lOb.add(this.ob);
        
        this.person = new Person(6, "familyName6", "GivenName6", 6, "F" );
        this.lPerson.add(this.person);
        this.ob = new Ob(106,"Total Cholesterol", "mg/L", this.person, "6Cho");
        this.lOb.add(this.ob);
        this.ob = new Ob(106,"Tobacco smoking status NHIS", "mg/L", this.person, "6Tob");
        this.lOb.add(this.ob);
        
        
        // List of BloodPressure for same patient
        this.ob = new Ob(101,"Diastolic Blood Pressure", "mg/L", this.person, "100",LocalDate.of(2014, 2, 14));
         diasBloodMap.add(this.ob);
        this.ob = new Ob(101,"Diastolic Blood Pressure", "mg/L", this.person, "110",LocalDate.of(2014, 3, 14));
         diasBloodMap.add(this.ob);
        this.ob = new Ob(101,"Diastolic Blood Pressure", "mg/L", this.person, "150",LocalDate.of(2014, 4, 14));
         diasBloodMap.add(this.ob);
        this.ob = new Ob(101,"Diastolic Blood Pressure", "mg/L", this.person, "90",LocalDate.of(2014, 5, 14));
         diasBloodMap.add(this.ob);
        this.ob = new Ob(101,"Diastolic Blood Pressure", "mg/L", this.person, "80",LocalDate.of(2014, 6, 14));
        diasBloodMap.add(this.ob);
        
        this.ob = new Ob(101,"Systolic Blood Pressure", "mg/L", this.person, "100",LocalDate.of(2014, 2, 14));
         sysBloodMap.add(this.ob);
        this.ob = new Ob(101,"Systolic Blood Pressure", "mg/L", this.person, "110",LocalDate.of(2014, 3, 14));
         sysBloodMap.add(this.ob);
        this.ob = new Ob(101,"Systolic Blood Pressure", "mg/L", this.person, "150",LocalDate.of(2014, 4, 14));
        sysBloodMap.add(this.ob);
        this.ob = new Ob(101,"Systolic Blood Pressure", "mg/L", this.person, "90", LocalDate.of(2014, 5, 14));
         sysBloodMap.add(this.ob);
        this.ob = new Ob(101,"Systolic Blood Pressure", "mg/L", this.person, "80",LocalDate.of(2014, 6, 14));
        sysBloodMap.add(this.ob);
        
        // List of BloodPressure for same patient
        this.ob = new Ob(103,"Diastolic Blood Pressure", "mg/L", this.person, "100",LocalDate.of(2014, 2, 14));
         diasBloodMap.add(this.ob);
        this.ob = new Ob(103,"Diastolic Blood Pressure", "mg/L", this.person, "110",LocalDate.of(2014, 3, 14));
         diasBloodMap.add(this.ob);
        this.ob = new Ob(103,"Diastolic Blood Pressure", "mg/L", this.person, "150",LocalDate.of(2014, 4, 14));
         diasBloodMap.add(this.ob);
        this.ob = new Ob(103,"Diastolic Blood Pressure", "mg/L", this.person, "90",LocalDate.of(2014, 5, 14));
         diasBloodMap.add(this.ob);
        this.ob = new Ob(103,"Diastolic Blood Pressure", "mg/L", this.person, "80",LocalDate.of(2014, 6, 14));
        diasBloodMap.add(this.ob);
        
        this.ob = new Ob(103,"Systolic Blood Pressure", "mg/L", this.person, "100",LocalDate.of(2014, 2, 14));
         sysBloodMap.add(this.ob);
        this.ob = new Ob(103,"Systolic Blood Pressure", "mg/L", this.person, "110",LocalDate.of(2014, 3, 14));
         sysBloodMap.add(this.ob);
        this.ob = new Ob(103,"Systolic Blood Pressure", "mg/L", this.person, "150",LocalDate.of(2014, 4, 14));
        sysBloodMap.add(this.ob);
        this.ob = new Ob(103,"Systolic Blood Pressure", "mg/L", this.person, "90", LocalDate.of(2014, 5, 14));
         sysBloodMap.add(this.ob);
        this.ob = new Ob(103,"Systolic Blood Pressure", "mg/L", this.person, "80",LocalDate.of(2014, 6, 14));
        sysBloodMap.add(this.ob);
    }
 
    public ArrayList <Ob> reGetObs(){
        this.lOb.clear();
        this.ob = new Ob(201,"Cho", "mg/L", this.person, "201Cho");
        this.lOb.add(this.ob);
        this.ob = new Ob(202,"Cho", "mg/L", this.person, "202Cho");
        this.lOb.add(this.ob);
        this.ob = new Ob(203,"Cho", "mg/L", this.person, "203Cho");
        this.lOb.add(this.ob);
        return this.lOb;
               
    }
}
