package xd.safeheart.model;
import java.util.HashSet;

/* Created by: Loi Chai Lam
 * Modified Date: 25-4-2019
 */

/* User class contains a set of User Role. 
 * So that each User can have many roles */
public class User {
	
	private HashSet <UserRole> roles = new HashSet<>(); 

	// Constructor
	public User() {
	}
	
	public void addRoles(UserRole u) {
		this.roles.add(u);
	}
	
	public void removeRoles(UserRole u) {
		this.roles.remove(u);
	}
	
	public boolean searhRoles(UserRole u) {
		if (roles.contains(u)) {
			return true;
		}
		else {
			return false;
		}
	}

}
