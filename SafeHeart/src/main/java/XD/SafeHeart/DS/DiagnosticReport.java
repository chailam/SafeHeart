package XD.SafeHeart.DS;
import java.util.ArrayList;

/* Created by: Loi Chai Lam
 * Modified Date: 25-4-2019
 */


public class DiagnosticReport {
	/* DiagnosticReport class records the Patient's Observation class */

	private Encounter refEncounter; 
	private int id;
	private ArrayList <Observation> listObs = new ArrayList<>(); 
	
	//Constructor
	public DiagnosticReport(int id) {
		this.id = id;
	}
	
	/* The getter method for private variable: id, refEncounter, Observation with that Id */
	public int getId() {
		return this.id;
	}
	
	public Encounter getEncounter() {
		return this.refEncounter;
	}
	
	public Observation getObsWithId(int id) {
		// Loop through the Observation list: listObs, and return the Observation that match the id
		//else, return null
		for (Observation obs : listObs) {
			if (obs.getID() == id) {
				return obs;
			}
		}
		return null;
	}
	
	
	/* The setter method for private variable: refEncounter; and add the Observation into listObs */
	public void setEncounter(Encounter encounter) {
		this.refEncounter = encounter;
	}
	
	// Add the Observation into list
	public void addObs(Observation obs) {
		this.listObs.add(obs);
	}
}
