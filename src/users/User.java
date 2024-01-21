package users;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import GUI.PostPage;
import GUI.*;
import image.ImageMatrix;
import image.ImageSecretary;

/**
 * Abstract User class serves as a template for all user types.
 * It holds general user data such as name, email, and profile picture, 
 * and provides basic functionality for interacting with the user's data.
 */

public abstract class User {
	protected String nickname;
	protected String password;
	protected String realName;
	protected String surname;
	protected String email;
	protected ImageMatrix profilePicture;
	protected String imageNameAndExtension;
	public List<PostPage.Post> posts;
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User() throws IOException {
		this.profilePicture = ImageSecretary.readResourceImage("defaultPP", "png");  
		this.posts = new ArrayList<PostPage.Post>();
		this.imageNameAndExtension = "defaultPP.png";
		
		
		
	}
	public User(String nickname, String password, String realname, String surname, String email) throws IOException {
		this.nickname = nickname;
		this.password = password;
		this.realName = realname;
		this.surname = surname;
		this.email = email;
		this.profilePicture = ImageSecretary.readResourceImage("defaultPP", "png");  
		this.posts = new ArrayList<PostPage.Post>();
		this.imageNameAndExtension = "defaultPP.png";
		
		
		
	}

	public String getImageNameAndExtension() {
		return imageNameAndExtension;
	}

	public void setImageNameAndExtension(String imageNameAndExtension) {
		this.imageNameAndExtension = imageNameAndExtension;
	}

	public ImageMatrix getProfilePicture() {
		return profilePicture;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public String getRealName() {
		return realName;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public void setProfilePicture(ImageMatrix profilePicture) {
		this.profilePicture = profilePicture;
	}
	
	
	
	
	
	

}
