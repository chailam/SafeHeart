package xd.safeheart.ds;

/* Created by: Loi Chai Lam
 * Modified Date: 25-4-2019
 */


public class Clinician extends UserRole {
	/* Clinician class is the parent class of Practitioner */

	// Constructor
	public Clinician(int id) {
		super(id);
	}
		
	//Constructor
	public Clinician(int id, String familyName, String givenName, int age, String gender ) {
		super(id, familyName, givenName, age, gender);
	}
}
