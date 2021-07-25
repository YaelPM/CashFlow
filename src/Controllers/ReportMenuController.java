/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Services.ReportService;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javax.management.timer.Timer;

import Cashflow.Main;


public class ReportMenuController implements Initializable {

    @FXML
    private Text CurrentUserName;
    @FXML
    private Text CurrentUserRole;
    @FXML
    private Region onBacking;
    @FXML
    private ImageView onBack;
    @FXML
    private ComboBox<String> comboMes;
    @FXML
    private Button onGenerateReport;

    ReportService rs = new ReportService();

    ObservableList<String> meses = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCombox();
        this.comboMes.setValue("Noviembre");
        this.comboMes.setItems(meses);
        this.CurrentUserName.setText(Main.getCurrentUser().getNombres());
        this.CurrentUserRole.setText(Main.getCurrentUser().getRol());
    }

    @FXML
    private void backToMenuReg(MouseEvent event) {
        RouterMenu();
    }

    @FXML
    private void backToMenu(MouseEvent event) {
        RouterMenu();
    }

    @FXML
    private void onGenerate(ActionEvent event) {
        rs.generateReport(this.comboMes.getValue());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "El reporte ha sido generado!", ButtonType.OK);
        alert.getDialogPane().setMinHeight(80);
        alert.showAndWait();
    }
    
    public void initCombox(){
        this.meses.add("Noviembre");
        this.meses.add("Diciembre");
        this.meses.add("Enero");
        this.meses.add("Febrero");
        this.meses.add("Marzo");
        this.meses.add("Abril");
        this.meses.add("Mayo");
        this.meses.add("Junio");
        this.meses.add("Julio");
        this.meses.add("Agosto");
        this.meses.add("Septiembre");
        this.meses.add("Octubre");
    }

    public void RouterMenu() {
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
            Stage myStage = (Stage) this.comboMes.getScene().getWindow();
            myStage.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
