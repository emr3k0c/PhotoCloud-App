package users;


import java.awt.image.BufferedImage;
import java.io.IOException;

import filters.*;
import image.ImageMatrix;


/**
 * FreeUser class is a subClass of User.
 * FreeUser inherits all properties and methods from User. 
 * FreeUser can only use Blurring and Sharpening filters.
 */
public class FreeUser extends User {
	
	

	public FreeUser(String nickname, String password, String realname, String surname, String email) throws IOException {
		super(nickname, password, realname, surname, email);
		
	}
	
	public FreeUser() throws IOException {
	}
	
	
	
	
}
