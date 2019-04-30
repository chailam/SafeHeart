/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.model;

/**
 *
 * @author caila
 */
public class Person {
	/* UserRole class is the parent class of Clinician and Patient. 
	 * It defines as a Role so that the Clinician also can be a Patient. */

	private int id;
	private String familyName;
	private String givenName;
	private int age;
	private String gender;
	
	//Constructor
	public Person(int id) {
		this.id = id;
	}
	
	//Constructor
	public Person(int id, String familyName, String givenName, int age, String gender ) {
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
        
        /////So that it will show in UI
        @Override
        public String toString(){
            return this.familyName + " " + this.givenName + " " + this.age + " " + this.gender;
        }
}
