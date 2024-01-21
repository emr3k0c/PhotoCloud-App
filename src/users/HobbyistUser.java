package users;

import java.awt.image.BufferedImage;
import java.io.IOException;

import filters.*;
import image.ImageMatrix;


/**
 * HobbyistUser class is a subClass of User.
 * HobbyistUser inherits all properties and methods from User. 
 * HobbyistUser, additional to FreeUser, can use Brightness and Contrast filter.
 */
public class HobbyistUser extends User {
	
	

	public HobbyistUser(String nickname, String password, String realname, String surname, String email) throws IOException {
		super(nickname, password, realname, surname, email);
		
	}
	public HobbyistUser() throws IOException {
		
		
	}

}
