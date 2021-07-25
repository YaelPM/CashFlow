/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.User;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainMenuContoller implements Initializable {

    @FXML
    private Button categoryButton;

    @FXML
    private Button cashflowButton;

    @FXML
    private Button moneyindexButton;

    @FXML
    private ImageView categoriesImg;

    @FXML
    private ImageView cashflowImg;

    @FXML
    private ImageView moneyindexImg;

    @FXML
    private Text CurrentUserName;

    @FXML
    private Text CurrentUserRole;

    User currentuser = new User();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void onCategories(ActionEvent event) {
        redirectToCategories();
    }

    @FXML
    private void onCategoriesimg(MouseEvent event) {
        redirectToCategories();
    }

    @FXML
    private void onCashFlow(ActionEvent event) {
        redirectToCashFlow();
    }

    @FXML
    private void onMoneyIndex(ActionEvent event) {
        redirectToMoneyIndex();
    }

    @FXML
    private void onCashFlowImg(MouseEvent event) {
        redirectToCashFlow();
    }

    @FXML
    private void onMoneyIndexImg(MouseEvent event) {
        redirectToMoneyIndex();
    }

    public void redirectToCategories() {
        try {
            Main.newStage("CategoriesMenu", "Categorias");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void redirectToCashFlow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/CashFlowMenu.fxml"));
            Parent root = loader.load();
            CashFlowMenuController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setMaximized(true);
            stage.setTitle("CashFlow App");
            stage.show();
            //stage.setOnCloseRequest(e -> controller.closeWindow());
            Stage myStage = (Stage) this.categoryButton.getScene().getWindow();
            myStage.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void redirectToMoneyIndex() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MenuIndicadores.fxml"));
            Parent root = loader.load();
            MenuIndicadoresController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setMaximized(true);
            stage.setTitle("CashFlow App");
            stage.show();
            //stage.setOnCloseRequest(e -> controller.closeWindow());
            Stage myStage = (Stage) this.categoryButton.getScene().getWindow();
            myStage.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
