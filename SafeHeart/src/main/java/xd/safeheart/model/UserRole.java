package xd.safeheart.model;
/* Created by: Loi Chai Lam
 * Modified Date: 25-4-2019
 */

/* UserRole class is the parent class of Clinician and Patient. 
 * It defines as a Role so that the Clinician also can be a Patient. */
public class UserRole {

	private int id;
	private String familyName;
	private String givenName;
	private int age;
	private String gender;
	
	//Constructor
	public UserRole(int id) {
		this.id = id;
	}
	
	//Constructor
	public UserRole(int id, String familyName, String givenName, int age, String gender ) {
		this.id = id;
		this.familyName = familyName;
		this.givenName = givenName;
		this.age = age;
		this.gender = gender;	
	}
	
	/* The getter method for private variable: id, familyName, givenName,age, gender  */
	public int getId() {
		return this.id;
	}
	
	public String getFamilyName() {
		return this.familyName;
	}
	
	public String getGivenName() {
		return this.givenName;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	/* The setter method for private variable: familyName, givenName,age, gender*/
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
        
        @Override
        public String toString(){
            return this.id + "   " + this.familyName + " " + this.givenName + " " + this.age;
        }

}
