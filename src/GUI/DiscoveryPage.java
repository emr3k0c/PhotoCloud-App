package GUI;

import java.awt.*;
import java.awt.FlowLayout;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import GUI.PostPage.Post;
import image.ImageMatrix;
import image.ImageSecretary;
import users.User;


/**
 * DiscoveryPage is a JFrame that displays the posts shared by all users
 * in a grid layout. It also includes options to navigate to home, share a new post,
 * and visit the logged-in user's profile.
 */

public class DiscoveryPage extends JFrame implements ActionListener {
	private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 30;
    private static final int IMAGE_WIDTH = 200;
    private static final int IMAGE_HEIGHT = 300;
	User user;
    JPanel panel = new JPanel();
    Map<JButton, JButton> postUsernamePPButtons = new HashMap<>();
    Map<Post,JButton> postImageButtons = new HashMap<>();
    JScrollPane scrollPane;
    JButton homeButton = new JButton("HOME");
    JButton shareNewPost = new JButton("NEW POST");
    JButton usernameButton;
    JButton profilePicture;
   
	
    /**
     * Constructor method for the DiscoveryPage.
     * @param user the User object for the currently logged-in user.
     */
    public DiscoveryPage(User user) {
        
    	this.user = user;
        homeButton.addActionListener(this);
        shareNewPost.addActionListener(this);
        
        
        panel.add(homeButton);
        panel.add(shareNewPost);
        
        usernameButton = new JButton(user.getNickname()); 
    	profilePicture = new JButton(new ImageIcon(SignupPage.userMap.get(user.getNickname()).getProfilePicture().getBufferedImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));

        usernameButton.setBorderPainted(false);
    	usernameButton.setContentAreaFilled(false);
        panel.add(usernameButton);
        usernameButton.addActionListener(this);
        
        profilePicture.setBorderPainted(false);
    	profilePicture.setContentAreaFilled(false);
    	panel.add(profilePicture);
    	profilePicture.addActionListener(this);
    	
        displayPosts();
        
        panel.setLayout(null);
        homeButton.setBounds(10, 10, BUTTON_WIDTH, BUTTON_HEIGHT);
        shareNewPost.setBounds(290, 50 , BUTTON_WIDTH, BUTTON_HEIGHT);
        usernameButton.setBounds(100, 50, 170, BUTTON_HEIGHT);
        profilePicture.setBounds(10, 50, BUTTON_WIDTH, BUTTON_HEIGHT);

         
        
        
        
       this.add(panel);
        this.setSize(500, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


    }
    
   
	
    /**
     * This method retrieves all the posts from each user in the SignupPage.userMap
     * and adds them to the panel of the DiscoveryPage. Posts are displayed in a grid layout.
     */
	public void displayPosts() {
		int YCoordinate = 150;
		int postXIndex = 1;
		for (Entry<String, User> userMap : SignupPage.userMap.entrySet()) {
			for(Post post : userMap.getValue().posts) {
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
					postXIndex+=1;
					
				}
				else if (postXIndex % 3 == 2) {
					image.setBounds(180,YCoordinate, 130, 130);
					postXIndex+=1;
				}
				else {
					image.setBounds(310, YCoordinate, 130, 130);
					YCoordinate += 130;
					postXIndex+=1;
				}
				
				
				}
				
				
			}
		}
		

	}
	
	
	/**
     * This method is called when an action is performed on DiscoveryPage.
     * It handles various interactions like button clicks.
     * @param e the ActionEvent object representing the event that has occurred.
     */
	@Override
    public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(homeButton)) {
			panel.revalidate();
            panel.repaint();
		}
		
		if(e.getSource().equals(usernameButton) || e.getSource().equals(profilePicture)) {
			ProfilePage profileFrame = new ProfilePage(this.user, this.user);
			profileFrame.setVisible(true);
			profileFrame.setBounds(10, 10, 550, 800);
			profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			profileFrame.setResizable(false);
            this.dispose();
			
		}
		
		for (Map.Entry<JButton, JButton> toProfileButtons :postUsernamePPButtons.entrySet()) {
			if (e.getSource().equals(toProfileButtons.getKey()) || e.getSource().equals(toProfileButtons.getValue())) {
				User profileOwner = SignupPage.userMap.get(toProfileButtons.getKey().getText());
				ProfilePage profileFrame = new ProfilePage(profileOwner, this.user);
				profileFrame.setVisible(true);
				profileFrame.setBounds(10, 10, 550, 800);
				profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				profileFrame.setResizable(false);
	            this.dispose();
			}
		}
		
		for (Map.Entry<Post, JButton> imageButtons :postImageButtons.entrySet()) {
			if (e.getSource().equals(imageButtons.getValue())) {
				PostPage postPage = new PostPage(imageButtons.getKey(), this.user);
				 postPage.setVisible(true);
		         postPage.setBounds(10, 10, 400, 800);
		         postPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		         postPage.setResizable(false);
		         this.dispose();
			}
		}
		
		
		
		if (e.getSource().equals(shareNewPost)) {
			JFrame newPost = new JFrame("Share a Post");
			JComboBox<String> imagesToShare = new JComboBox<String>(ImageSecretary.getRawImageNames().toArray(new String[0]));
			JButton shareButton = new JButton("Share");
			shareButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent a) {
	            	if (a.getSource().equals(shareButton)) {
	            	String imageNameWithExtension = (String) imagesToShare.getSelectedItem();
	            	String[] parts = imageNameWithExtension.split("\\.");
	           
	                String imageName = parts[0];
	                String extension = parts[1];
	                try {
	                    ImageMatrix image = ImageSecretary.readResourceImage(imageName, extension);
	                    Post post = new Post(user, image);
	                    post.setImageNameAndExtension(imageNameWithExtension);
	                    user.posts.add(post);
	                    JOptionPane.showMessageDialog(newPost, "New post is shared privately.");
	                    BaseLogger.Info.log("User: " + user.getNickname() + " shared " + imageNameWithExtension  +" file as new photo.");
	                    newPost.dispose();

	                } catch (NullPointerException | IOException ex) {
	                    ex.printStackTrace();
	                    BaseLogger.Error.log(ex.getMessage());
	                }
	            }
	            }
				
	            

				
			});
			newPost.setLayout(new FlowLayout());
		    newPost.add(imagesToShare);
		    newPost.add(shareButton);
		    newPost.pack();
		    newPost.setLocationRelativeTo(null);
		    newPost.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    newPost.setVisible(true);

		}
		
	}
	

}
