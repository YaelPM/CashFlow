/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Message;
import Models.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Cashflow.Main;
import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class LoginController implements Initializable {

    private Label label;
    @FXML
    private TextField emailInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    Message message = new Message();

    ArrayList<User> users = new ArrayList<>();

    User currentuser = new User();
    UserDAO dao= new UserDAO();

    @FXML
    private Button closeBtn;

    @FXML
    void closeOnMouseClicked(MouseEvent event) {
    	System.exit(1);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void onLogin(ActionEvent event) {
        String email = emailInput.getText();
        String pass = passwordInput.getText();
        boolean isValid = validateFields(email, pass);
        if (isValid) {
            try {
            	if(validateUser(email)) {
            		Main.setFXML("MainMenu", "Menu");
            	}else {
            		message.ivalidFieldsAlert();
            	}
            } catch (Exception e) {
                message.loginAlert();
            }
        } else {
            message.ivalidFieldsAlert();
        }
    }

    @FXML
    private void onRegister(ActionEvent event) {
    	Main.newStage("Register" , "Register");
       
    }

    public boolean validateUser(String user) {
    	
    	if(dao.getUser(user)!=null) {
    		return true;
    	}else {
    		return false;
    	}
		
    }
    
    public boolean validateFields(String email, String pass) {
        return !(email.equals("") || email.equals(" ") || email.contains(" ") || pass.equals("") || pass.equals(" ") || pass.contains(" "));
    }
}
