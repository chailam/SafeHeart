package xd.safeheart.ds;
/* Created by: Loi Chai Lam
 * Modified Date: 25-4-2019
 */

public class Patient extends UserRole {
	/* Patient class is the sub-class of UserRole. 
	 * It records the details of Patient */

	//Constructor
		public Patient(int id) {
			super(id);
		}
			
		//Constructor
		public Patient(int id, String familyName, String givenName, int age, String gender ) {
			super(id, familyName, givenName, age, gender);
		}
}
