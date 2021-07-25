/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Message;
import Models.User;
import Services.CashFlowServices;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import Cashflow.Main;
import DAO.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController implements Initializable {

	 @FXML
	    private TextField nameInput;

	    @FXML
	    private Button loginButton;

	    @FXML
	    private Button registerButton;

	    @FXML
	    private TextField rolInput;

	    @FXML
	    private PasswordField passwordInput;

    Message message = new Message();
    
    UserDAO daoRegister= new UserDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void onCreateacc(ActionEvent event) {

        String name = nameInput.getText();
        String rol = rolInput.getText();
        String password = passwordInput.getText();
        boolean isValid = validateFields(name, rol, password);
        if (isValid) {
        	System.out.println(name+rol+password);
        	User user= new User(name, rol, password);
            daoRegister.insert(user);
            try {
                redirectToMenu();
            } catch (Exception e) {
                message.RegisterAlert();
            }
        } else {
            message.ivalidFieldsAlert();
        }
        clearForm();

    }

    @FXML
    private void onLogIn(ActionEvent event) {

        try {
            Main.secondStage.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void redirectToMenu(){
        Main.secondStage.close();
    }

    public boolean validateFields(String names, String rol, String pass) {
        return !(names.equals("") || names.equals(" ") || rol.equals("") || rol.equals(" ")|| pass.equals("") || pass.equals(" ") || pass.contains(" "));
    }
    public void clearForm() {
        nameInput.clear();
        rolInput.clear();
        passwordInput.clear();
    }

}
