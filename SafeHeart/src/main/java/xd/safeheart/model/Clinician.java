package xd.safeheart.model;

/* Created by: Loi Chai Lam
 * Modified Date: 25-4-2019
 */

/* Clinician class is the parent class of Practitioner */
public class Clinician extends UserRole {

	// Constructor
	public Clinician(int id) {
		super(id);
	}
		
	//Constructor
	public Clinician(int id, String familyName, String givenName, int age, String gender ) {
		super(id, familyName, givenName, age, gender);
	}
}
