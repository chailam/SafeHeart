package xd.safeheart.model;
/* Created by: Loi Chai Lam
 * Modified Date: 25-4-2019
 */

/* Encounter class records the appointment for Patient and Clinician */
public class Encounter {

	private int id;
	private Patient patient;
	private Clinician clinician;
	
	// Constructor
	public Encounter(int id) {
            this.id = id;
	}
        
        public Encounter(int id, Patient patient, Clinician clinician) {
            this.id = id;
            this.patient = patient;
            this.clinician = clinician;
	}
	
	
	/* The getter method for private variable: id, patient, clinician */
	public int getId() {
		return this.id;
	}
	
	public Patient getPatient() {
		return this.patient;
	}
	
	public Clinician getClinician() {
		return this.clinician; 
	}
	
	
	/* The setter method for private variable: patient, clinician */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public void setClinician(Clinician clinician) {
		this.clinician = clinician;
	}
}
