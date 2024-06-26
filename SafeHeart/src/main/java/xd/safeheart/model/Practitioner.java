package xd.safeheart.model;
/* Created by: Loi Chai Lam
 * Modified Date: 25-4-2019
 */

/* Practitioner class is the sub-class of Clinician. 
 * It records the details of Practitioner */
public class Practitioner extends Clinician {

	//Constructor
	public Practitioner(int id) {
		super(id);
	}
		
	//Constructor
	public Practitioner(int id, String familyName, String givenName, int age, String gender ) {
		super(id, familyName, givenName, age, gender);
	}
}
