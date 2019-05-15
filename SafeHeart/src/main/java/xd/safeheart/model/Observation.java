package xd.safeheart.model;

import java.time.LocalDate;

/* Created by: Loi Chai Lam
 * Modified Date: 25-4-2019
 */

/* Observation class records the Patient's Cholesterol level */
public class Observation {
	
	private int id;
	private String type;
	private String unit;
	private Patient patient; // the observation of Patient
	private String value;
        private LocalDate date;
	
	//Constructor
	public Observation(int id){
		this.id = id;
	}
        
         public Observation(int id, String type, Patient patient, String value){
		this.id = id;
                this.patient = patient;
                this.value = value;
                this.type = type;
	}
        
        public Observation(int id, String type, String unit, Patient patient, String value){
		this.id = id;
                this.patient = patient;
                this.unit = unit;
                this.value = value;
                this.type = type;
	}
        
        public Observation(int id, String type, String unit, Patient patient, String value, LocalDate date){
		this.id = id;
                this.patient = patient;
                this.unit = unit;
                this.value = value;
                this.type = type;
                this.date = date;
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
	
	public Patient getPatient() {
		return this.patient;
	}
	
	public String getValue() {
		return this.value;
	}
	
        public LocalDate getDate() {
		return this.date;
	}
        public int getDay() {
		return this.date.getDayOfMonth();
	}
        public int getMonth() {
		return this.date.getMonthValue();
	}
        
        public int getYear() {
		return this.date.getYear();
	}
	
	/* The setter method for private variable: type, unit, patient, value */
	public void setType(String type) {
		this.type = type;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
         public void setDDate(LocalDate date) {
		this.date = date;
	}
}
