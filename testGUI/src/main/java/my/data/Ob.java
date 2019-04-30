/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.data;

public class Ob {
	/* Observation class records the Patient's Cholesterol level */
	private int id;
	private String type;
	private String unit;
	private Person patient; // the observation of Patient
	private String value;
	
	//Constructor
	public Ob(int id){
		this.id = id;
	}
        
        public Ob(int id, String type, String unit, Person patient, String value){
		this.id = id;
                this.patient = patient;
                this.unit = unit;
                this.value = value;
                this.type = type;
	}
	
	
	/* The getter method for private variable: id, type, unit, patient, value */
	public int getID() {
		return this.id;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getUnit() {
		return this.unit;
	}
	
	public Person getPatient() {
		return this.patient;
	}
	
	public String getValue() {
		return this.value;
	}
	
	
	/* The setter method for private variable: type, unit, patient, value */
	public void setType(String type) {
		this.type = type;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setPatient(Person patient) {
		this.patient = patient;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}

