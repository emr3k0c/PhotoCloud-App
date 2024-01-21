package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import users.User;
import GUI.BaseLogger;


/**
 * LoginPage class represents the Login Page of the GUI application.
 * It extends JFrame and implements ActionListener for handling GUI actions.
 */
public class LoginPage extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton signupLink = new JButton("SIGNUP");
    JCheckBox showPassword = new JCheckBox("Show Password");


    public LoginPage() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }
    
    /**
     * This method sets the layout manager for the container.
     * It sets it to the null. 
     * So the location of all GUI components will be arranged by developer.
     */
    
    public void setLayoutManager() {
        container.setLayout(null);
    }

    /**
     * This method sets the location and size for the GUI components.
     */
    public void setLocationAndSize() {
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        loginButton.setBounds(200, 300, 100, 30);
        signupLink.setBounds(50, 300, 100, 30);
        
    }
    
    
    /**
     * This method adds the GUI components to the container.
     */
    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(signupLink);
        
    }

    /**
     * This method attaches action listeners to the GUI components.
     */
    public void addActionEvent() {
        loginButton.addActionListener(this);
        
        showPassword.addActionListener(this);
        
        signupLink.addActionListener(this);
    }


    
    /**
     * This method is called when an action occurs.
     * It handles all the actions performed on the GUI components.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = passwordField.getText();
            if (SignupPage.userMap.containsKey(userText) && SignupPage.userMap.get(userText).getPassword().equals(pwdText)) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                BaseLogger.Info.log("User " + userText + " logged in successfully");
                DiscoveryPage discoveryFrame = new DiscoveryPage(SignupPage.userMap.get(userText));
                discoveryFrame.setTitle("PhotoCloud");
                discoveryFrame.setVisible(true);
                discoveryFrame.setBounds(10, 10, 550, 800);
                discoveryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                discoveryFrame.setResizable(false);
                
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
				BaseLogger.Info.log("Invalid login attempt");

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
        
        if (e.getSource() == signupLink) {
        	SignupPage signupFrame = new SignupPage();
            signupFrame.setTitle("Signup Form");
            signupFrame.setVisible(true);
            signupFrame.setBounds(10, 10, 400, 800);
            signupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            signupFrame.setResizable(false);
            this.dispose();
        	
        }
    }

}


