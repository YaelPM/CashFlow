/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Services.ReportService;

import java.net.URL;
import java.util.ResourceBundle;

import Cashflow.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MenuIndicadoresController implements Initializable {
    @FXML
    private Button registerButton;
    @FXML
    private Button reportButton;
    @FXML
    private ImageView registerIMG;
    @FXML
    private ImageView ReportIMG;
    @FXML
    private Text CurrentUserName;
    @FXML
    private Text CurrentUserRole;
    @FXML
    private Region onBacking;
    @FXML
    private ImageView onBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.CurrentUserName.setText(Main.getCurrentUser().getNombres());
        this.CurrentUserRole.setText(Main.getCurrentUser().getRol()); 
        validateSuperUser();
    }    

    @FXML
    private void onRegister(ActionEvent event) {
        RouterRegister();
    }

    @FXML
    private void onReport(ActionEvent event) {
        RouterReport();
    }

    @FXML
    private void onCategoriesimg(MouseEvent event) {
        //Register
        RouterRegister();
    }

    @FXML
    private void onMoneyIndexImg(MouseEvent event) {
        //Report
        RouterReport();
    }

    @FXML
    private void backToMenuReg(MouseEvent event) {
        RouterMenu();
    }

    @FXML
    private void backToMenu(MouseEvent event) {
        RouterMenu();
    }

    
    public void validateSuperUser(){
        if (this.CurrentUserRole.getText().equals("Director de Finanzas")) {
            this.reportButton.setVisible(false);
            this.reportButton.setManaged(false);
            this.ReportIMG.setVisible(false);
            this.ReportIMG.setManaged(false);
            
            double X = this.registerButton.getLayoutX();
            double XI = this.registerIMG.getLayoutX();
            this.registerButton.setLayoutX(X + 350);
            this.registerIMG.setLayoutX(XI + 350);
        }
    }
    
    
    public void RouterMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainMenu.fxml"));
            Parent root = loader.load();
            MainMenuContoller controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setMaximized(true);
            stage.setTitle("CashFlow App");
            stage.show();
            //stage.setOnCloseRequest(e -> controller.closeWindow());
            Stage myStage = (Stage) this.reportButton.getScene().getWindow();
            myStage.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void RouterRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/RegistroIndicadores.fxml"));
            Parent root = loader.load();
            RegistroIndicadoresController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setMaximized(true);
            stage.setTitle("CashFlow App");
            stage.show();
            //stage.setOnCloseRequest(e -> controller.closeWindow());
            Stage myStage = (Stage) this.reportButton.getScene().getWindow();
            myStage.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void RouterReport() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ReportMenu.fxml"));
            Parent root = loader.load();
            ReportMenuController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setMaximized(true);
            stage.setTitle("CashFlow App");
            stage.show();
            //stage.setOnCloseRequest(e -> controller.closeWindow());
            Stage myStage = (Stage) this.reportButton.getScene().getWindow();
            myStage.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
