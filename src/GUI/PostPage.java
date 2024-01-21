package GUI;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import GUI.PostPage.Post;
import image.ImageMatrix;
import users.Administrator;
import users.User;


/**
 * PostPage is a JFrame that displays the detailed view of a single post.
 * It includes options to like/dislike the post, add a new comment, and navigate to home.
 */
public class PostPage extends JFrame implements ActionListener { 
	private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 30;
    private static final int IMAGE_WIDTH = 200;
    private static final int IMAGE_HEIGHT = 300;
    int commentYPosition;
	Post post;
	Container container = getContentPane();
	JButton homeButton = new JButton("Home");
	JButton likeButton = new JButton("Like");
	JButton dislikeButton = new JButton("Dislike");
	JButton addCommentButton = new JButton("Add new comment");
	JLabel commentSection = new JLabel("Comments");
	JButton username;
	JButton profilePicture;
	Map<JLabel, JButton> deleteCommentButtons = new HashMap<JLabel, JButton>();
	User viewer;
	
	
	/**
     * Constructor method for the PostPage.
     * @param post the Post object that needs to be displayed.
     * @param viewer the User object for the currently logged-in user.
     */
	public PostPage(Post post, User viewer) {
        this.post = post;
        this.viewer = viewer;
       
        setLayout(null);
      
        container.add(homeButton);
        homeButton.addActionListener(this);
        
        
        container.add(likeButton);
        container.add(dislikeButton);
        container.add(addCommentButton);
        container.add(commentSection);
        likeButton.addActionListener(this);
        dislikeButton.addActionListener(this);
        addCommentButton.addActionListener(this);
        homeButton.setBounds(10, 10, BUTTON_WIDTH, BUTTON_HEIGHT);
		likeButton.setBounds(30, 330, 70, 30);
		dislikeButton.setBounds(120, 330, 70, 30);;
		addCommentButton.setBounds(210, 330, 150, 30);
		commentSection.setBounds(30, 370, 70, 30);;	
        displayPost();
       
    }
	
	
	
	
	/**
     * This method retrieves the details of the post and adds them to the container of the PostPage.
     * It also adds the comments on the post to the container.
     */
	public void displayPost() {
	    username = new JButton(post.user.getNickname()); 
	    username.setBorderPainted(false);
		username.setContentAreaFilled(false);
	    container.add(username);
	    username.addActionListener(this);
	    username.setBounds(60, 50,120, BUTTON_HEIGHT);
		profilePicture = new JButton(new ImageIcon(SignupPage.userMap.get(post.user.getNickname()).getProfilePicture().getBufferedImage().getScaledInstance(BUTTON_WIDTH, IMAGE_HEIGHT, Image.SCALE_DEFAULT)));
		profilePicture.setBorderPainted(false);
		profilePicture.setContentAreaFilled(false);
		container.add(profilePicture);
		profilePicture.addActionListener(this);
		profilePicture.setBounds(10, 50, 50, 50);
	    JLabel image = new JLabel( new ImageIcon(post.getImage().getBufferedImage().getScaledInstance(150, 170, Image.SCALE_DEFAULT)));
	    container.add(image);
	    image.setBounds(100, 120, 150, 170);
	    JLabel likes = new JLabel(Integer.toString(post.getLikes()));
	    container.add(likes);
	    likes.setBounds(60, 300, 30, 30);
	    JLabel dislikes = new JLabel(Integer.toString(post.getDislikes()));
	    container.add(dislikes);
	    dislikes.setBounds(150, 300, 30, 30);
	    commentYPosition = 420;
	    for (Map.Entry<User, String> set :post.comments.entrySet()) {
	    	JLabel comment = new JLabel(set.getKey().getNickname() + ": " + set.getValue());
	    	container.add(comment);
	    	comment.setBounds(30, commentYPosition, 300, 40);
	    	
	    	if (post.user.equals(viewer) || (viewer instanceof Administrator)) {
	    		JButton deleteComment = new JButton("X");
	    		container.add(deleteComment);
	    		deleteComment.addActionListener(this);
	    		deleteComment.setBounds(340, commentYPosition+10, 20, 20);
	    		deleteCommentButtons.put(comment, deleteComment);
	    		
	    	}
	    	commentYPosition += 50;
	    	
	    }

	}
	
	/**
	 * Post class represents a post object in the application. Each post is associated with a User who posts it,
	 * an image attached to the post, likes and dislikes count, visibility status, and the comments made by other users.
	 * Each post also stores the image's name and extension.
	 */
	public static class Post {
        private User user;
        private ImageMatrix image;
        public Map<User, String> comments;
        private int likes;
        private int dislikes;
        private boolean isVisible;
        private String imageNameAndExtension;

        public void setUser(User user) {
			this.user = user;
		}


		public void setImage(ImageMatrix image) {
			this.image = image;
		}


		public void setLikes(int likes) {
			this.likes = likes;
		}


		public void setDislikes(int dislikes) {
			this.dislikes = dislikes;
		}

		
		/**
	     * Constructor that initializes the Post object with a user who made the post and an ImageMatrix object representing the image attached to the post.
	     * Also initializes likes and dislikes count to 0, sets up an empty comments map, and sets visibility status to false.
	     * @param user The user who is creating the post
	     * @param image The image associated with the post
	     */
		public Post(User user, ImageMatrix image) {
            this.user = user;
            this.image = image;
            this.likes = 0;
            this.dislikes = 0;
            this.comments = new HashMap<User,String>();
            this.isVisible = false;
        }
		public Post() {
            this.likes = 0;
            this.dislikes = 0;
            this.comments = new HashMap<User,String>();
            this.isVisible = false;
			
		}
        

        public User getUser() {
            return user;
        }
        
        public int getLikes() {
			return likes;
		}

		public void liked() {
			this.likes += 1;
		}
		
		/**
	     * This method returns the visibility status of the post.
	     * @return true if the post is visible, false otherwise.
	     */
		public boolean isVisible() {
			return isVisible;
		}

		/**
	     * This method sets the visibility status of the post.
	     * @param visibility The desired visibility status
	     */
		public void setVisible(Boolean visibility) {
			this.isVisible = visibility;
		}


		public String getImageNameAndExtension() {
			return imageNameAndExtension;
		}


		public void setImageNameAndExtension(String imageNameAndExtension) {
			this.imageNameAndExtension = imageNameAndExtension;
		}


		public int getDislikes() {
			return dislikes;
		}

		public void disliked() {
			this.dislikes += 1;
		}
		
		public ImageMatrix getImage() {
        	return image;
        }
		

      
    }
	
	/**
     * This method is called when an action is performed on PostPage.
     * It handles various interactions like button clicks.
     * @param e the ActionEvent object representing the event that has occurred.
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
		
		if (e.getSource().equals(username) || e.getSource().equals(profilePicture)) {
			User profileOwner = this.post.getUser();
			ProfilePage profileFrame = new ProfilePage(profileOwner, this.viewer);
			profileFrame.setVisible(true);
			profileFrame.setBounds(10, 10, 550, 800);
			profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			profileFrame.setResizable(false);
            this.dispose();
			
		}
		if (e.getSource().equals(likeButton)) {
			post.liked();
			PostPage postPage = new PostPage(this.post, this.viewer);
			postPage.setVisible(true);
	        postPage.setBounds(10, 10, 400, 800);
	        postPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        postPage.setResizable(false);
	        BaseLogger.Info.log("User: " +viewer + "liked the " + this.post.getUser() + "'s " + this.post.getImageNameAndExtension()+ " image.");
	        this.dispose();
			
		}
		if (e.getSource().equals(dislikeButton)) {
			post.disliked();
			PostPage postPage = new PostPage(this.post, this.viewer);
			postPage.setVisible(true);
	        postPage.setBounds(10, 10, 400, 800);
	        postPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        postPage.setResizable(false);
	        BaseLogger.Info.log("User: " +viewer + "disliked the " + this.post.getUser() + "'s " + this.post.getImageNameAndExtension()+ " image.");

	        this.dispose();
		}
		
		if (e.getSource().equals(addCommentButton)) {
			String comment = JOptionPane.showInputDialog("Enter your comment");
			post.comments.put(viewer, comment);
			PostPage postPage = new PostPage(this.post, this.viewer);
			postPage.setVisible(true);
	        postPage.setBounds(10, 10, 400, 800);
	        postPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        postPage.setResizable(false);
	        BaseLogger.Info.log("User: " +viewer + "commented the " + this.post.getUser() + "'s " + this.post.getImageNameAndExtension()+ " image: " + comment + ".");
	        this.dispose();
		}
		
		for (Map.Entry<JLabel, JButton> deleteCommentButton :deleteCommentButtons.entrySet()) {
    		if (e.getSource().equals(deleteCommentButton.getValue())) {
    			container.remove(deleteCommentButton.getKey());
    			String[] parts = deleteCommentButton.getKey().getText().split("\\:");
    			post.comments.remove(SignupPage.userMap.get(parts[0]));
    			container.remove(deleteCommentButton.getValue());
    			container.remove(deleteCommentButton.getKey());
    			container.revalidate();
    			container.repaint();
    	        BaseLogger.Info.log("User: " +viewer + "deleted the " + parts[0] + "'s comment: '" + parts[1] + "' from" +  this.post.getImageNameAndExtension()+ " image.");

    			
    		}
		}
    	
    }
}
