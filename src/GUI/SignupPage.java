package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import users.*;


/**
 * This class represents the Sign-up page of the application.
 * It extends JFrame and implements the ActionListener interface.
 */
public class SignupPage extends JFrame implements ActionListener {
	public static Map<String, User> userMap = new HashMap<String, User>();;
	public static Set<String> emailSet = new HashSet<String>();;
	Container container = getContentPane();
    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JLabel realnameLabel = new JLabel("REALNAME");
    JLabel surnameLabel = new JLabel("SURNAME");
    JLabel ageLabel = new JLabel("AGE");
    JLabel mailLabel = new JLabel("EMAIL ADRESS");
    JLabel userTypeLabel = new JLabel("USER TYPE");
    JComboBox<String> userTypeComboBox = new JComboBox<>();
    JTextField userTextField = new JTextField();
    JTextField nameTextField = new JTextField();
    JTextField surnameTextField = new JTextField();
    JTextField ageTextField = new JTextField();
    JTextField mailTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginLink = new JButton("LOGIN");
    JButton signupButton = new JButton("SIGNUP");
    JCheckBox showPassword = new JCheckBox("Show Password");
    
    
    /**
     * Constructor for SignupPage. Sets up the layout manager, component location and size, populates user types,
     * adds components to container and binds action events.
     */
    SignupPage() {

    	setLayoutManager();
        setLocationAndSize();
        populateUserTypes();
        addComponentsToContainer();
        addActionEvent();
        

    }
    
    /**
     * This method sets the layout manager for the container to null.
     */
    public void setLayoutManager() {
        container.setLayout(null);
    }

    
    /**
     * This method sets the location and size for each UI component.
     */
    public void setLocationAndSize() {
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        realnameLabel.setBounds(50, 290, 100, 30);
        surnameLabel.setBounds(50, 360, 100, 30);
        ageLabel.setBounds(50, 430, 100, 30);
        mailLabel.setBounds(50, 500, 100, 30);
        userTypeLabel.setBounds(50, 570, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        nameTextField.setBounds(150, 290, 150, 30);
        surnameTextField.setBounds(150, 360, 150, 30);
        ageTextField.setBounds(150, 430, 150, 30);
        mailTextField.setBounds(150, 500, 150, 30);
        userTypeComboBox.setBounds(150, 570, 150, 30 );
        signupButton.setBounds(200, 670, 100, 30);
        loginLink.setBounds(50, 670, 100, 30);
        


    }
    
    /**
     * This method populates the user type combo box with different user types.
     */
    private void populateUserTypes() {
        userTypeComboBox.addItem("FreeUser");
        userTypeComboBox.addItem("HobbyistUser");
        userTypeComboBox.addItem("ProfessionalUser");
        userTypeComboBox.addItem("Administrator");
       
    }

    
    /**
     * This method adds all the UI components to the container.
     */
    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(realnameLabel);
        container.add(surnameLabel);
        container.add(ageLabel);
        container.add(mailLabel);
        container.add(userTypeLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(nameTextField);
        container.add(surnameTextField);
        container.add(ageTextField);
        container.add(mailTextField);
        container.add(userTypeComboBox);
        container.add(signupButton);
        container.add(loginLink);
   
       
        
    }
    
    
    /**
     * This method binds action listeners to the necessary UI components.
     */
    public void addActionEvent() {
        signupButton.addActionListener(this);
        
        showPassword.addActionListener(this);
        
        loginLink.addActionListener(this);
        
     
    }
    
    
    
    /**
     * This method is triggered when an action occurs.
     *
     * @param e the event to be processed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            String userText;
            String pwdText;
            String nameText;
            String surnameText;
            String ageText;
            String mailText;
            String userType;
            userText = userTextField.getText();
            pwdText = passwordField.getText();
            nameText = nameTextField.getText();
            surnameText = surnameTextField.getText();
            ageText = ageTextField.getText();
            mailText = mailTextField.getText();
            userType = (String) userTypeComboBox.getSelectedItem();
            if (!(userText.length() < 6)) {
            	if (!SignupPage.userMap.containsKey(userText)) {
            		if (pwdText.length() >= 8) {
            			if (!nameText.isEmpty()) {
            				if (!surnameText.isEmpty()) {
            					if (!ageText.isEmpty() && Integer.parseInt(ageText) > 15) {
            						if (mailText.contains("@") && mailText.contains("mail.com")) {
            							if (!SignupPage.emailSet.contains(mailText)){
            								if (userType.equals("FreeUser")) {
            									try {
													User user = new FreeUser(userText, pwdText, nameText, surnameText, mailText);
													userMap.put(userText, user);
	            									emailSet.add(mailText);
	            									JOptionPane.showMessageDialog(this, "You have successfully signed up. Use your username and password to log in.");
	            									BaseLogger.Info.log("A new FreeUser has signed up with the username: " + userText + ".");
												} catch (IOException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
													BaseLogger.Error.log(e1.getMessage());

												}
            									
            									
            									
            								}
            								else if ( userType.equals("HobbyistUser")) {
            									try {
													User user = new HobbyistUser(userText, pwdText, nameText, surnameText, mailText);
													userMap.put(userText, user);
	            									emailSet.add(mailText);
	            									JOptionPane.showMessageDialog(this, "You have successfully signed up. Use your username and password to log in.");
	            									BaseLogger.Info.log("A new HobbyistUser has signed up with the username: " + userText + ".");

												} catch (IOException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
													BaseLogger.Error.log(e1.getMessage());
												}
            									
            									
            								}
            								else if (userType.equals("ProfessionalUser")) {
            									try {
													User user = new ProfessionalUser(userText, pwdText, nameText, surnameText, mailText);
													userMap.put(userText, user);
	            									emailSet.add(mailText);
	            									JOptionPane.showMessageDialog(this, "You have successfully signed up. Use your username and password to log in.");
	            									BaseLogger.Info.log("A new ProfessionalUser has signed up with the username: " + userText + ".");

												} catch (IOException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
													BaseLogger.Error.log(e1.getMessage());
												}
            									
            								}
            								else if (userType.equals("Administrator")) {
            									try {
													User user = new Administrator(userText, pwdText, nameText, surnameText, mailText);
													userMap.put(userText, user);
	            									emailSet.add(mailText);
	            									JOptionPane.showMessageDialog(this, "You have successfully signed up. Use your username and password to log in.");
	            									BaseLogger.Info.log("A new Administrator has signed up with the username: " + userText + ".");
												} catch (IOException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
													BaseLogger.Error.log(e1.getMessage());
												}
            									
            								}
            								else {
            									JOptionPane.showMessageDialog(this, "Please choose a user tier.");
            								}
            								
            								
            							}
            							else {
            								JOptionPane.showMessageDialog(this, "This e-mail is alreay in use.");
            							}
            							
            						}
            						else {
            							JOptionPane.showMessageDialog(this, "You must enter a valid e-mail adress!");
            						}
            						
            					}
            					else {
            						JOptionPane.showMessageDialog(this, "User should enter an age older than 15!");
            					}
            					
            				}
            				else {
            					JOptionPane.showMessageDialog(this, "You must enter a surname!");
            				}
                		
            			}
            		
            	
            			else {
            				JOptionPane.showMessageDialog(this, "You must enter a name!");
            			}
                	
            		}
            		else {
            			JOptionPane.showMessageDialog(this, "The password must be at least 8 characters long");
            		}
            	} 
            	else {
            		JOptionPane.showMessageDialog(this, "UsernameExist!");
            	}
            }
            else {
            	JOptionPane.showMessageDialog(this, "The username must be at least 6 characters long");
            	
            }

        }
        
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
             } 
            else {
                passwordField.setEchoChar('*');
            }
        }
        
        if (e.getSource() == loginLink) {
        
        	LoginPage frame = new LoginPage();
            frame.setTitle("Login Form");
            frame.setVisible(true);
            frame.setBounds(10, 10, 370, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            this.dispose();
        	
        }
    }

}
