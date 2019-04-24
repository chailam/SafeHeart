/* Created by: Loi Chai Lam
 * Modified Date: 25-4-2019
 */


public class Observation {
	/* Observation class records the Patient's Cholesterol level */
	private int id;
	private String type;
	private String unit;
	private Patient patient; // the observation of Patient
	private String value;
	
	//Constructor
	public Observation(int id){
		this.id = id;
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
	
}
