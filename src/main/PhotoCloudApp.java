package main;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

import GUI.BaseLogger;
import GUI.LoginPage;
import GUI.PostPage.Post;
import GUI.SignupPage;
import image.ImageSecretary;
import users.*;


/**
 * The main entry point of the PhotoCloud application.
 * It creates an instance of the PhotoCloudApp class and calls the workingLogic method.
 *
 *@param args the command line arguments. 
 */
public class PhotoCloudApp {
	/************** Pledge of Honor ******************************************
	I hereby certify that I have completed this programming project on my own 
	without any help from anyone else. The effort in the project thus belongs 
	completely to me. I did not search for a solution, or I did not consult any 
	program written by others or did not copy any program from other sources. I 
	read and followed the guidelines provided in the project description.
	READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
	SIGNATURE: <Emre Koc, 76659> 
	*************************************************************************/
	public static void main(String[] args) {
		PhotoCloudApp application = new PhotoCloudApp();
		application.workingLogic();
        
        
	}
	
	
	/**
     * This method contains the main logic of the application.
     * It logs the start and end time of the application, 
     * reads user data from a text file, and sets up the application window.
     * It also sets a shutdown hook to save user data to the text file when the application exits.
     */
	public void workingLogic() {
		BaseLogger.Info.log("Application started at: " + LocalDateTime.now());
		readFromtxt();
		LoginPage frame = new LoginPage();
        frame.setTitle("Login");
        frame.setVisible(true);
        frame.setBounds(10, 10, 370, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                writeTotxt();
            }
        });
        BaseLogger.Info.log("Application ended at: " + LocalDateTime.now());

		
	}
	
	/**
     * This method reads user data, posts, and comments from a text file and stores them in memory.
     * It throws an exception if the text file is not found or if an error occurs during reading.
     */
	public void readFromtxt() {
		try (Scanner scanner = new Scanner(Paths.get("data.txt"))) {
		    
		 	User readingUser = null;
		    Post readingPost = null;

		    while (scanner.hasNextLine()) {
		        String currentLine = scanner.nextLine();
		        String[] datasToRecord = currentLine.split(", ");
		        if (datasToRecord.length == 7) {
		        	User user = null;
		        	if (datasToRecord[6].equals("FreeUser")) {
		        		user = new FreeUser();
		        	}
		        	else if (datasToRecord[6].equals("HobbyistUser")) {
		        		user = new HobbyistUser();
		        	}
		        	else if(datasToRecord[6].equals("ProfessionalUser")) {
		        		user = new ProfessionalUser();
		        	}
		        	else if(datasToRecord[6].equals("Administrator")) {
		        		user = new Administrator();
		        	}
		        	
	                user.setNickname(datasToRecord[0]);
	                user.setPassword(datasToRecord[1]);
	                user.setRealName(datasToRecord[2]);
	                user.setSurname(datasToRecord[3]);
	                user.setEmail(datasToRecord[4]);
	                user.setImageNameAndExtension(datasToRecord[5]);
	                String[] parts = datasToRecord[5].split("\\.");
	                String imageName = parts[0];
	                String extension = parts[1];
	                user.setProfilePicture(ImageSecretary.readResourceImage(imageName, extension));
	                SignupPage.userMap.put(user.getNickname(), user);
	                SignupPage.emailSet.add(user.getEmail());
	                readingUser = user;
		        	
		        }
		        else if (datasToRecord.length == 4) {
		        	Post post = new Post();
	                post.setUser(readingUser);
		        	post.setLikes(Integer.parseInt(datasToRecord[0]));
	                post.setDislikes(Integer.parseInt(datasToRecord[1]));
	                post.setImageNameAndExtension(datasToRecord[2]);
	                post.setVisible(Boolean.parseBoolean(datasToRecord[3]));
	              
	                String[] parts = datasToRecord[2].split("\\.");
	                String imageName = parts[0];
	                String extension = parts[1];
	                post.setImage(ImageSecretary.readResourceImage(imageName, extension));
	                readingUser.posts.add(post);
	                readingPost = post;
		        }
		        else if (datasToRecord.length == 2) {
		        	String commentOwnerNickname = datasToRecord[0];
	                String comment = datasToRecord[1];
	                User commentOwner = SignupPage.userMap.get(commentOwnerNickname);
	                if(commentOwner != null) {
	                    readingPost.comments.put(commentOwner, comment);
	                   
	                }
		        }
		        
		    }
		}
		catch (IOException e) {
		    e.printStackTrace();
		    BaseLogger.Error.log(e.getMessage());
		}
	 
		
	}
	
	
	/**
     * This method saves the user data, posts, and comments currently in memory to a text file.
     * It throws an exception if the text file cannot be opened for writing or if an error occurs during writing.
     */
	public void writeTotxt() {
		try (Formatter output = new Formatter("data.txt")) {
			 for (Map.Entry<String, User> users : SignupPage.userMap.entrySet()) {
				 String userType = null;
				 if (users.getValue() instanceof FreeUser) {
					 userType = "FreeUser";
				 }
				 else if ( users.getValue() instanceof HobbyistUser) {
					 userType = "HobbyistUser";
				 }
				 else if (users.getValue() instanceof ProfessionalUser) {
					 if (users.getValue() instanceof Administrator) {
						 userType = "Administrator";
					 }
					 else {
					 userType = "ProfessionalUser";
					 }
				 }
				 else if (users.getValue() instanceof Administrator) {
					 
				 }
				 output.format("%s, %s, %s, %s, %s, %s, %s%n", users.getValue().getNickname(), users.getValue().getPassword(), users.getValue().getRealName(), users.getValue().getSurname(), users.getValue().getEmail(), users.getValue().getImageNameAndExtension(), userType);

				 for (Post post : users.getValue().posts) {
					 output.format("%d, %d, %s, %b%n", post.getLikes(), post.getDislikes(), post.getImageNameAndExtension(), post.isVisible());
					 for (Map.Entry<User, String> comments : post.comments.entrySet()) {
						 output.format("%s, %s%n", comments.getKey().getNickname(), comments.getValue());
					 }

				 }
			 }
		 }
		 catch (SecurityException | FileNotFoundException | FormatterClosedException e) {
			 e.printStackTrace();
			 BaseLogger.Error.log(e.getMessage());
			 
		 }
		
	}
}
