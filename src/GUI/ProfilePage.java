package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;

import GUI.PostPage.Post;
import filters.*;
import image.ImageMatrix;
import image.ImageSecretary;
import users.*;


/**
 * This class represents a user's profile page in the application.
 * It extends the JFrame class and implements the ActionListener interface to respond to the user's actions.
 */
public class ProfilePage extends JFrame implements ActionListener {
	private static final int BUTTON_WIDTH = 60;
    private static final int BUTTON_HEIGHT = 30;
    private static final int IMAGE_WIDTH = 200;
    private static final int IMAGE_HEIGHT = 300;
	JPanel panel = new JPanel();
    JScrollPane scrollPane;
	JButton homeButton = new JButton("HOME");
    JButton editButton= new JButton("Edit Profile");
    Map<Post, JButton> postDeleteButtons = new HashMap<Post, JButton>();
    Map<Post, JButton> filterButtons = new HashMap<Post, JButton>();
    Map<Post, JButton> visibilityButtons = new HashMap<Post, JButton>();
    Map<Post,JButton> postImageButtons = new HashMap<>();


    User user;
    User viewer;
    boolean selfProfile;
    
    
    /**
     * Constructor for ProfilePage that initializes the User and Viewer and sets the initial layout.
     * It also defines action listeners for the home button and edit button.
     *
     * @param user   the user whose profile is being viewed
     * @param viewer the user who is currently viewing the profile
     */
    public ProfilePage(User user, User viewer) {
    this.user = user;
    this.viewer = viewer;
    panel.add(homeButton);
    homeButton.addActionListener(this);
    homeButton.setBounds(10, 10, 80, BUTTON_HEIGHT);
    this.selfProfile = user.equals(viewer) || (viewer instanceof Administrator);
    if (selfProfile) {
    	panel.add(editButton);
    	editButton.addActionListener(this);
    	editButton.setBounds(290, 70, 120, BUTTON_HEIGHT);
    }
    displayUserInfo();
	displayPosts();
	addActionEvent();
	panel.setLayout(null);
    this.add(panel); 
    
    
    
    this.setSize(500, 700);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);


    }
    

    /**
     * This method displays the information of the user.
     * It shows the username, real name, surname, and profile picture of the user.
     * If the viewer is viewing their own profile or they are an administrator, it also shows their password and email.
     */
    public void displayUserInfo() {
    	JLabel userLabel = new JLabel("Username: " + user.getNickname());
    	panel.add(userLabel);
    	userLabel.setBounds(30, 110, 150, BUTTON_HEIGHT);
        JLabel nameSurname = new JLabel(user.getRealName() + " " + user.getSurname());
        panel.add(nameSurname);
        nameSurname.setBounds(30, 150, 150, BUTTON_HEIGHT);
        JLabel profilePicture = new JLabel(new ImageIcon(user.getProfilePicture().getBufferedImage().getScaledInstance(50, 50 , Image.SCALE_DEFAULT)));
        panel.add(profilePicture);
        profilePicture.setBounds(30, 70, BUTTON_WIDTH, BUTTON_HEIGHT);
        if (selfProfile) {
        	JLabel password = new JLabel("Password: " + user.getPassword());
        	panel.add(password);
        	password.setBounds(30, 230, 210, BUTTON_HEIGHT);
        	JLabel email = new JLabel(user.getEmail());
        	panel.add(email);
        	email.setBounds(30, 190, 150, BUTTON_HEIGHT);
        }
    }
    
    
    /**
     * This method is used to set action listeners for home and edit buttons.
     */
    public void addActionEvent() {
        homeButton.addActionListener(this);
        
        editButton.addActionListener(this);
        
        
    }
    
   
    /**
     * This method displays the posts of the user.
     * It shows all posts that are visible and if the viewer is viewing their own profile or they are an administrator, it also shows invisible posts.
     */
    public void displayPosts() {
		int YCoordinate = 270;
		int postXIndex = 1;
			for(Post post : user.posts) {
				if (post.isVisible()) {
				
				JButton image = new JButton(new ImageIcon(post.getImage().getBufferedImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT)));
				image.setBorderPainted(true);
				image.setContentAreaFilled(true);
				panel.add(image);
				Border gridBorder = BorderFactory.createLineBorder(Color.BLACK);
				image.setBorder(gridBorder);
				image.addActionListener(this);
				postImageButtons.put(post, image);
				if (postXIndex % 3 == 1) {
					image.setBounds(50, YCoordinate , 130, 130);
					postXIndex += 1;
					
				}
				else if (postXIndex % 3 == 2) {
					image.setBounds(180,YCoordinate, 130, 130);
					postXIndex += 1;

				}
				else {
					image.setBounds(310, YCoordinate, 130, 130);
					YCoordinate += 130;
					postXIndex += 1;

				}
				
				
				}
				else {
					if (selfProfile) {
						JButton image = new JButton(new ImageIcon(post.getImage().getBufferedImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT)));
						image.setBorderPainted(true);
						image.setContentAreaFilled(true);
						panel.add(image);
						Border gridBorder = BorderFactory.createLineBorder(Color.BLACK);
						image.setBorder(gridBorder);
						image.addActionListener(this);
						postImageButtons.put(post, image);
						if (postXIndex % 3 == 1) {
							image.setBounds(50, YCoordinate , 130, 130);
							postXIndex += 1;

							
						}
						else if (postXIndex % 3 == 2) {
							image.setBounds(180,YCoordinate, 130, 130);
							postXIndex += 1;

							
						}
						else {
							image.setBounds(310, YCoordinate, 130, 130);
							YCoordinate += 130;
							postXIndex += 1;

							
						}
						
					}
					
				}
				
				
			}
		
	}
    
    /**
     * This method overrides the actionPerformed method from the ActionListener interface.
     * It defines what should happen when the home button, edit button, and image button are clicked.
     * It performs image filtering, image deleting operations as well.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource().equals(homeButton)) {
    		DiscoveryPage homeFrame = new DiscoveryPage(viewer);
            homeFrame.setTitle("Home Page");
            homeFrame.setVisible(true);
            homeFrame.setBounds(10, 10, 550, 800);
            homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            homeFrame.setResizable(false);
            this.dispose();
    	}
    	
    	if (e.getSource().equals(editButton)) {
    		JFrame editFrame = new JFrame("Edit Profile");
    		JLabel userLabel = new JLabel("USERNAME");
    	    JLabel passwordLabel = new JLabel("PASSWORD");
    	    JLabel realnameLabel = new JLabel("REALNAME");
    	    JLabel surnameLabel = new JLabel("SURNAME");
    	    JLabel mailLabel = new JLabel("EMAIL ADRESS");
    	    JTextField userTextField = new JTextField();
    	    JTextField nameTextField = new JTextField();
    	    JTextField surnameTextField = new JTextField();
    	    JTextField mailTextField = new JTextField();
    	    JTextField passwordField = new JTextField();
    	    JComboBox<String> choosePP = new JComboBox<String>(ImageSecretary.getRawImageNames().toArray(new String[0]));
    	    JButton apply = new JButton("Apply Changes");
			JButton changeProfilePhoto = new JButton("Set Profile Photo");
			apply.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent a) {
	            	if (a.getSource().equals(apply)) {
	            		String userText;
	                    String pwdText;
	                    String nameText;
	                    String surnameText;
	                    String mailText;
	                    if (!userTextField.getText().isBlank()) {
	                    userText = userTextField.getText();
	                    }
	                    else {
	                    	userText = user.getNickname();
	                    }
	                    if (!passwordField.getText().isBlank()) {
	                    	pwdText = passwordField.getText();
	                    }
	                    else {
	                    	pwdText = user.getPassword();
	                    }
	                    if (!nameTextField.getText().isBlank()) {
	                    	nameText = nameTextField.getText();
	                    }
	                    else {
	                    	nameText = user.getRealName();
	                    }
	                    if (!surnameTextField.getText().isBlank()) {
	                    	surnameText = surnameTextField.getText();
	                    }
	                    else {
	                    	surnameText = user.getSurname();
	                    }
	                    if (!mailTextField.getText().isBlank()) {
		                    mailText = mailTextField.getText();

	                    }
	                    else {
	                    	mailText = user.getEmail();
	                    }
	                    
	                    if (!(userText.length() < 6)) {
	                    	if (!SignupPage.userMap.containsKey(userText) || userText.equals(user.getNickname())) {
	                    		if (pwdText.length() >= 8) {
	                    			if (mailText.contains("@") && mailText.contains("mail.com")) {
	                    				if (!SignupPage.emailSet.contains(mailText) || mailText.equals(user.getEmail())){
	                    					SignupPage.userMap.remove(user.getNickname());
	                    					SignupPage.emailSet.remove(user.getEmail());
	                    					user.setNickname(userText);
	                    					user.setPassword(pwdText);
	                    					user.setRealName(nameText);
	                    					user.setSurname(surnameText);
	                    					user.setEmail(mailText);
	                    					SignupPage.userMap.put(userText, user);
	                    					SignupPage.emailSet.add(mailText);
	                    					ProfilePage profileFrame = new ProfilePage(user, viewer);
	            		        			profileFrame.setVisible(true);
	            		        			profileFrame.setBounds(10, 10, 550, 800);
	            		        			profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            		        			profileFrame.setResizable(false);
	        	            				JOptionPane.showMessageDialog(editFrame, "Your changes are applied. You can close this window");
	        	            				BaseLogger.Info.log("User: " + user + "has updated their information.");
	        	            				editFrame.dispose();
	        								
	                    				}
	                    				else {
	                    					JOptionPane.showMessageDialog(editFrame, "This e-mail is alreay in use.");
	                    				}
	                    							
	                    			}
	                    			else {
	                    				JOptionPane.showMessageDialog(editFrame, "You must enter a valid e-mail adress!");
	                    			}
	                    						
	                    		 }
	                    		
	                    		else {
	                    			JOptionPane.showMessageDialog(editFrame, "The password must be at least 8 characters long");
	                    		}
	                    	} 
	                    	else {
	                    		JOptionPane.showMessageDialog(editFrame, "UsernameExist!");
	                    	}
	                    }
	            	else {
	            		JOptionPane.showMessageDialog(editFrame, "The username must be at least 6 characters long");
	                    	
	                }

	           }
	            		
	            		
	            	
	            
	            	
	            }
			});
			changeProfilePhoto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					if (a.getSource().equals(changeProfilePhoto)) {
	            		String imageNameWithExtension = (String) choosePP.getSelectedItem();
		            	String[] parts = imageNameWithExtension.split("\\.");
		                String imageName = parts[0];
		                String extension = parts[1];
		                try {
		                    ImageMatrix image = ImageSecretary.readResourceImage(imageName, extension);
		                    user.setProfilePicture(image);
		                    user.setImageNameAndExtension(imageNameWithExtension);
		                    ProfilePage profileFrame = new ProfilePage(user, viewer);
		        			profileFrame.setVisible(true);
		        			profileFrame.setBounds(10, 10, 550, 800);
		        			profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        			profileFrame.setResizable(false);
		                    JOptionPane.showMessageDialog(editFrame, "New profile photo is set. You can close this window");
		                    BaseLogger.Info.log("User: " + user.getNickname() + " has changed their profile photo.");
		                    editFrame.dispose();
	            			
		        			
	            			
	            			

		                } catch (IOException ioException) {
		                    ioException.printStackTrace();
		                    BaseLogger.Error.log(ioException.getMessage());
		                }
	            	}
					
				}
				
			});
    		editFrame.setLayout(null);
    		editFrame.add(userLabel);
    		editFrame.add(passwordLabel);
    		editFrame.add(realnameLabel);
    		editFrame.add(surnameLabel);
    		editFrame.add(mailLabel);
    		editFrame.add(userTextField);
    		editFrame.add(nameTextField);
    		editFrame.add(surnameTextField);
    		editFrame.add(mailTextField);
    		editFrame.add(passwordField);
    		editFrame.add(changeProfilePhoto);
    		editFrame.add(apply);
    		editFrame.add(choosePP);
    		userLabel.setBounds(50, 150, 100, 30);
            passwordLabel.setBounds(50, 220, 100, 30);
            realnameLabel.setBounds(50, 290, 100, 30);
            surnameLabel.setBounds(50, 360, 100, 30);
            mailLabel.setBounds(50, 430, 100, 30);
            userTextField.setBounds(150, 150, 150, 30);
            passwordField.setBounds(150, 220, 150, 30);
            nameTextField.setBounds(150, 290, 150, 30);
            surnameTextField.setBounds(150, 360, 150, 30);            
            mailTextField.setBounds(150, 430, 150, 30);  
            choosePP.setBounds(450, 80, 100, 50);
            changeProfilePhoto.setBounds(450, 150, 120, 40);
            apply.setBounds(200, 500, 120, 40);
            editFrame.setBounds(10, 10, 600, 610);
    		editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			editFrame.setVisible(true);
			
			
            
           
    	}
    	for (Map.Entry<Post, JButton> postImageButton :postImageButtons.entrySet()) {
    		if (e.getSource().equals(postImageButton.getValue())) {
    			if (!selfProfile) {
    				PostPage postPage = new PostPage(postImageButton.getKey(), this.viewer);
   				 	postPage.setVisible(true);
   				 	postPage.setBounds(10, 10, 400, 800);
   				 	postPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   				 	postPage.setResizable(false);
   				 	this.dispose();
    				
    			}
    			else {
    				
    				JFrame editPost = new JFrame();
    				JLabel username = new JLabel(user.getNickname());
					JLabel profilePicture = new JLabel(new ImageIcon(user.getProfilePicture().getBufferedImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_DEFAULT)));
					JLabel image = new JLabel(new ImageIcon(postImageButton.getKey().getImage().getBufferedImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_DEFAULT)));
					JButton backToProfile = new JButton("<-- Back To Profile");
					JButton deletePost = new JButton("Delete");
		    		JButton applyFilter = new JButton("Apply Filter");
		    		JButton makeVisible = new JButton("Make Visible");
		    		

		    		
		    		
		    		backToProfile.addActionListener(new ActionListener() {
		    			public void actionPerformed(ActionEvent a) {
		    				ProfilePage profileFrame = new ProfilePage(user, viewer);
		        			profileFrame.setVisible(true);
		        			profileFrame.setBounds(10, 10, 550, 800);
		        			profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        			profileFrame.setResizable(false);
		                    editPost.dispose();
		    			}
		    		});
		    		
		    		deletePost.addActionListener(new ActionListener() {
		    			public void actionPerformed(ActionEvent a) {
		    				user.posts.remove(postImageButton.getKey());
		        			JOptionPane.showMessageDialog(editPost, "This post is deleted. You are being redirected back to your profile page.");
		        			BaseLogger.Info.log("User: " + user + "has deleted " + postImageButton.getKey().getImageNameAndExtension() + "image from their sharings.");
		        			ProfilePage profileFrame = new ProfilePage(user, viewer);
		        			profileFrame.setVisible(true);
		        			profileFrame.setBounds(10, 10, 550, 800);
		        			profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        			profileFrame.setResizable(false);
		                    editPost.dispose();
		    			}
		    		});
		    		
		    		applyFilter.addActionListener(new ActionListener() {
		    			public void actionPerformed(ActionEvent a) {
		    				Post toFilterPost = postImageButton.getKey(); 
		        			JFrame filterFrame = new JFrame("Filter the Image");
		        			JComboBox<String> filterBox = new JComboBox<String>();
		        			if (user instanceof ProfessionalUser) {
		        				filterBox.addItem("Blur");
		        				filterBox.addItem("Sharpen");
		        				filterBox.addItem("GrayScale");
		        				filterBox.addItem("EdgeDetection");
		        				filterBox.addItem("Brightness");
		        				filterBox.addItem("Contrast");
		        			}
		        			else if (user instanceof HobbyistUser) {
		        				filterBox.addItem("Blur");
		        				filterBox.addItem("Sharpen");
		        				filterBox.addItem("Brightness");
		        				filterBox.addItem("Contrast");
		        			}
		        			else if (user instanceof FreeUser) {
		        				filterBox.addItem("Blur");
		        				filterBox.addItem("Sharpen");
		        				
		        			}
		        			
		        			JSlider filteringAmount = new JSlider(0, 100, 0); 
		        			filteringAmount.setMajorTickSpacing(25); 
		        			filteringAmount.setMinorTickSpacing(10); 
		        			filteringAmount.setPaintTicks(true); 
		        			filteringAmount.setPaintLabels(true);
		        	        

		        			
		        			JButton applyFilter = new JButton("Apply and Share");
		        			applyFilter.addActionListener(new ActionListener() {
		        	            public void actionPerformed(ActionEvent a) {
		        	            	if (a.getSource().equals(applyFilter)) {
		        	            	String filterToApply = (String) filterBox.getSelectedItem();
		        	            	int filterAmount = filteringAmount.getValue();
		        	            		if (filterToApply.equals("Blur")) {
		        	            			long startTime = System.currentTimeMillis();
		        	            			ImageMatrix filteredImage = Blurring.blurringFilter(toFilterPost.getImage(), filterAmount);
		        	            			long endTime = System.currentTimeMillis();
		        	            			BaseLogger.Info.log("Blur filter is applied to " + postImageButton.getKey().getImageNameAndExtension() + " file, took: " + (endTime-startTime) + "ms.");
		        	            			String imageName = JOptionPane.showInputDialog("Please enter a name for saving the filtered image to the images folder.");
		        	            			ImageSecretary.writeFilteredImageToResources(filteredImage, imageName);
		        	            			Post filteredPost = new Post(user, filteredImage);
		        	            			filteredPost.setImageNameAndExtension(imageName+".png");
		        	            			user.posts.add(filteredPost);
		        	            			
		        	            			JOptionPane.showMessageDialog(filterFrame, "Filter applied and post shared privately. Go to profile page to see.");
		        	            			BaseLogger.Info.log("The image: " + imageName + ".png has written to the images folder.");
		        	            			filterFrame.dispose();
		        	            			
		        	            		}
		        	            		else if (filterToApply.equals("Sharpen")) {
		        	            			long startTime = System.currentTimeMillis();
		        	            			ImageMatrix filteredImage = Sharpening.sharpeningFilter(toFilterPost.getImage(), filterAmount);
		        	            			long endTime = System.currentTimeMillis();
		        	            			String imageName = JOptionPane.showInputDialog("Please enter a name for saving the filtered image to the images folder.");
		        	            			BaseLogger.Info.log("Sharpen filter is applied to " + postImageButton.getKey().getImageNameAndExtension() + " file, took: " + (endTime-startTime) + "ms.");
		        	            			ImageSecretary.writeFilteredImageToResources(filteredImage, imageName);
		        	            			Post filteredPost = new Post(user, filteredImage);
		        	            			filteredPost.setImageNameAndExtension(imageName+".png");
		        	            			user.posts.add(filteredPost);
		        	            			JOptionPane.showMessageDialog(filterFrame, "Filter applied and post shared privately. Go to profile page to.");
		        	            			BaseLogger.Info.log("The image: " + imageName + ".png has written to the images folder.");
		        	            			filterFrame.dispose();
		        	            		}
		        	            		else if (filterToApply.equals("GrayScale")) {
		        	            			long startTime = System.currentTimeMillis();
		        	            			ImageMatrix filteredImage = GrayScale.grayScalingFilter(toFilterPost.getImage(), filterAmount);
		        	            			long endTime = System.currentTimeMillis();
		        	            			BaseLogger.Info.log("GrayScale filter is applied to " + postImageButton.getKey().getImageNameAndExtension() + " file, took: " + (endTime-startTime) + "ms.");
		        	            			String imageName = JOptionPane.showInputDialog("Please enter a name for saving the filtered image to the images folder.");
		        	            			ImageSecretary.writeFilteredImageToResources(filteredImage, imageName);
		        	            			Post filteredPost = new Post(user, filteredImage);
		        	            			filteredPost.setImageNameAndExtension(imageName+".png");
		        	            			user.posts.add(filteredPost);
		        	            			JOptionPane.showMessageDialog(filterFrame, "Filter applied and post shared privately. Go to discovery page to see.");
		        	            			BaseLogger.Info.log("The image: " + imageName + ".png has written to the images folder.");
		        	            			filterFrame.dispose();
		        	            		}
		        	            		else if (filterToApply.equals("EdgeDetection")) {
		        	            			long startTime = System.currentTimeMillis();
		        	            			ImageMatrix preprocessedImage = EdgeDetection.Preprocessing(toFilterPost.getImage());
		        	            			ImageMatrix filteredImage = EdgeDetection.EdgeDetectionFilter(preprocessedImage, filterAmount);
		        	            			long endTime = System.currentTimeMillis();
		        	            			BaseLogger.Info.log("EdgeDetection filter is applied to " + postImageButton.getKey().getImageNameAndExtension() + " file, took: " + (endTime-startTime) + "ms.");
		        	            			String imageName = JOptionPane.showInputDialog("Please enter a name for saving the filtered image to the images folder.");
		        	            			ImageSecretary.writeFilteredImageToResources(filteredImage, imageName);
		        	            			Post filteredPost = new Post(user, filteredImage);
		        	            			filteredPost.setImageNameAndExtension(imageName+".png");
		        	            			user.posts.add(filteredPost);
		        	            			JOptionPane.showMessageDialog(filterFrame, "Filter applied and post shared privately. Go to discovery page to see.");
		        	            			BaseLogger.Info.log("The image: " + imageName + ".png has written to the images folder.");
		        	            			filterFrame.dispose();
		        	            		}
		        	            		else if (filterToApply.equals("Brightness")) {
		        	            			long startTime = System.currentTimeMillis();
		        	            			ImageMatrix filteredImage = Brightness.BrightnessFilter(toFilterPost.getImage(), filterAmount);
		        	            			long endTime = System.currentTimeMillis();
		        	            			BaseLogger.Info.log("Brightness filter is applied to " + postImageButton.getKey().getImageNameAndExtension() + " file, took: " + (endTime-startTime) + "ms.");
		        	            			String imageName = JOptionPane.showInputDialog("Please enter a name for saving the filtered image to the images folder.");
		        	            			ImageSecretary.writeFilteredImageToResources(filteredImage, imageName);
		        	            			Post filteredPost = new Post(user, filteredImage);
		        	            			filteredPost.setImageNameAndExtension(imageName+".png");
		        	            			user.posts.add(filteredPost);
		        	            			JOptionPane.showMessageDialog(filterFrame, "Filter applied and post shared privately. Go to discovery page to see.");
		        	            			BaseLogger.Info.log("The image: " + imageName + ".png has written to the images folder.");
		        	            			filterFrame.dispose();
		        	            		}
		        	            		else if (filterToApply.equals("Contrast")) {
		        	            			long startTime = System.currentTimeMillis();
		        	            			ImageMatrix filteredImage = Contrast.ContrastFilter(toFilterPost.getImage(), filterAmount);
		        	            			long endTime = System.currentTimeMillis();
		        	            			BaseLogger.Info.log("Contrast filter is applied to " + postImageButton.getKey().getImageNameAndExtension() + " file, took: " + (endTime-startTime) + "ms.");
		        	            			String imageName = JOptionPane.showInputDialog("Please enter a name for saving the filtered image to the images folder.");
		        	            			ImageSecretary.writeFilteredImageToResources(filteredImage, imageName);
		        	            			Post filteredPost = new Post(user, filteredImage);
		        	            			filteredPost.setImageNameAndExtension(imageName+".png"); 
		        	            			user.posts.add(filteredPost);
		        	            			JOptionPane.showMessageDialog(filterFrame, "Filter applied and post shared privately. Go to discovery page to see.");
		        	            			BaseLogger.Info.log("The image: " + imageName + ".png has written to the images folder.");
		        	            			filterFrame.dispose();
		        	            		}
		        	            	
		        	            	
		        	            	}
		        	            }
		        	         });
		        			filterFrame.setLayout(new FlowLayout());
		        			filterFrame.add(filterBox);
		        			filterFrame.add(filteringAmount);
		        			filterFrame.add(applyFilter);
		        			filterFrame.pack();
		        			filterFrame.setLocationRelativeTo(null);
		        			filterFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        			filterFrame.setVisible(true);
		    			}
		    		});
		    		
		    		makeVisible.addActionListener(new ActionListener() {
		    			public void actionPerformed(ActionEvent a) {
		    				postImageButton.getKey().setVisible(true);
		    				JOptionPane.showMessageDialog(editPost, "Post is now visible to other users.");
		    				BaseLogger.Info.log("User: " + user.getNickname() + " has made " + postImageButton.getKey().getImageNameAndExtension() + " image visible.");
		    			}
		    		});
		    		
	       
	    			
		    		editPost.setLayout(null);
		    		editPost.add(username);
		    		editPost.add(profilePicture);
					editPost.add(image);
					editPost.add(deletePost);
					editPost.add(applyFilter);
					editPost.add(makeVisible);
		    		username.setBounds(120, 80, BUTTON_WIDTH, BUTTON_HEIGHT);
					profilePicture.setBounds(10, 80, BUTTON_WIDTH, BUTTON_HEIGHT);
					image.setBounds(20, 140, IMAGE_WIDTH, IMAGE_HEIGHT);
		    		deletePost.setBounds(170, 450, 80, BUTTON_HEIGHT);
		    		applyFilter.setBounds(20, 450, 150, BUTTON_HEIGHT);
	        		makeVisible.setBounds(260, 450, 110, BUTTON_HEIGHT);
	        		editPost.add(backToProfile);
	        		backToProfile.setBounds(10, 10, 150, 40);
	        		editPost.setVisible(true);
   				 	editPost.setBounds(10, 10, 400, 800);
   				 	editPost.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   				 	editPost.setResizable(false);
   				 	this.dispose();
    				}
    				
    				
    			}
    			
    		}
    	
    }
    
    

}
