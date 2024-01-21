package users;

import java.awt.image.BufferedImage;
import java.io.IOException;

import filters.*;
import image.ImageMatrix;


/**
 * ProfessionalUser class is a subClass of User.
 * ProfessionalUser inherits all properties and methods from User. 
 * Professional tier is the most comprehensive tier, which, in addition to Hobbyist, can use GrayScale and Edge detection filters.
 */
public class ProfessionalUser extends User {
	
	

	public ProfessionalUser(String nickname, String password, String realname, String surname, String email) throws IOException {
		super(nickname, password, realname, surname, email);
		
	}
	
	public ProfessionalUser() throws IOException {
		
	}

}
