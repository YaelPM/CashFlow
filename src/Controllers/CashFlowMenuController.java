/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.CashFlow;
import Models.Message;
import Services.CashFlowServices;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Cashflow.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CashFlowMenuController implements Initializable {
  @FXML
    private ComboBox<String> categoriesCombo;
    @FXML
    private TableView<CashFlow> MovementsTable;
    @FXML
    private TableColumn<CashFlow, String> dateCol;
    @FXML
    private TableColumn<CashFlow, String> descCol;
    @FXML
    private TableColumn<CashFlow, String> catCol;
    @FXML
    private TextField descriptionInput;
    @FXML
    private Button saveButton;
    @FXML
    private Text CurrentUserName;
    @FXML
    private Text CurrentUserRole;
    @FXML
    private Region onBacking;
    @FXML
    private ImageView onBack;
    @FXML
    private CheckBox checkEntrada;
    @FXML
    private CheckBox checkSalida;
    @FXML
    private TextField payInput;

    private ObservableList<CashFlow> flujos;
    
    CashFlowServices svc = new CashFlowServices();
    
    private final Message alert = new Message();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.CurrentUserName.setText(Main.getCurrentUser().getNombres());
        this.CurrentUserRole.setText(Main.getCurrentUser().getRol()); 
        fillTable();
    }

    @FXML
    private void onSave(ActionEvent event) {

        String monto = this.payInput.getText();
        LocalDate date = LocalDate.now();
        String fecha = date.toString();
        String descripcion = this.descriptionInput.getText();
        String category = this.categoriesCombo.getValue();
        boolean isValid = validateForm(monto, descripcion, category);
        if (isValid) {
            CashFlow flujo = new CashFlow(fecha, descripcion, Integer.parseInt(monto), category);
            this.svc.createCashFlow(flujo);  
            fillTable();
            clearFields();
        }else{
            this.alert.ivalidFieldsAlert();
            clearFields();
        }
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
    private String cbEntrada(ActionEvent event) {
        if (checkEntrada.isSelected()) {
            checkSalida.setSelected(false);
            ObservableList<String> entradas = this.svc.getCategoriesCatalog("Entrada");
            this.categoriesCombo.setItems(entradas);
            this.categoriesCombo.setValue(entradas.get(1));
            return "Entrada";
        }
        return null;
    }

    @FXML
    private String cbSalida(ActionEvent event) {
        if (checkSalida.isSelected()) {
            checkEntrada.setSelected(false);
            ObservableList<String> salidas = this.svc.getCategoriesCatalog("Salida");
            this.categoriesCombo.setItems(salidas);
            this.categoriesCombo.setValue(salidas.get(1));
            return "Salida";
        }
        return null;
    }

    public void fillTable(){
        this.flujos = this.svc.getFlows();
        this.MovementsTable.setItems(flujos);
        this.dateCol.setCellValueFactory(new PropertyValueFactory("fecha"));
        this.descCol.setCellValueFactory(new PropertyValueFactory("descripcion"));
        this.catCol.setCellValueFactory(new PropertyValueFactory("categoria"));
    }
    
    public boolean validateForm(String monto, String descripcion, String categoria) {
        return !(monto.equals("") || monto.contains(" ") || monto.equals(" ") || descripcion.equals("") || descripcion.equals(" "));
    }

    public void clearFields(){
        this.payInput.clear();
        this.descriptionInput.clear();
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
            Stage myStage = (Stage) this.saveButton.getScene().getWindow();
            myStage.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
