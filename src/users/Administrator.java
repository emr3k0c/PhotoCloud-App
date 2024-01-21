package users;

import java.io.IOException;

/**
 * Administrator class is a subClass of ProfessionalUser.
 * Administrator inherits all properties and methods from ProfessionalUser. 
 * Also administrator user has specific privileges such as deleting any post and modifying any user's information.
 */
public class Administrator extends ProfessionalUser {
	
	public Administrator(String nickname, String password, String realname, String surname, String email) throws IOException {
		super(nickname, password, realname, surname, email);
	}
	
	public Administrator() throws IOException {
		
	}
}
