package XD.SafeHeart.DS;
/* Created by: Loi Chai Lam
 * Modified Date: 25-4-2019
 */


public class Encounter {
	/* Encounter class records the appointment for Patient and Clinician */

	private int id;
	private Patient patient;
	private Clinician clinician;
	
	// Constructor
	public Encounter() {
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
