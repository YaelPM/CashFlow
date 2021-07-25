package Controllers;

import Models.Category;
import Models.Message;
import Services.CashFlowServices;

import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CategoriesMenuContoller implements Initializable {

    @FXML
    private ComboBox<String> clasificacionCombo;

    @FXML
    private TableView<Category> CategoriesTable;

    @FXML
    private TableColumn<Category, String> clasificacionCol;

    @FXML
    private TableColumn<Category, String> categoriaCol;

    @FXML
    private TableColumn<Category, String> subcategoriaCol;

    @FXML
    private TextField categoriaInput;

    @FXML
    private TextField subcategoriaInput;

    @FXML
    private Button addButton;


    @FXML
    private Text CurrentUserName;

    @FXML
    private Text CurrentUserRole;

    private ObservableList<Category> categorias;

    private CashFlowServices svc = new CashFlowServices();

    private final Message alert = new Message();
    @FXML
    private ImageView onBack;
    @FXML
    private Region onBacking;
    @FXML
    private Button editButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.CategoriesTable.setEditable(true);
        this.clasificacionCol.setCellFactory(ChoiceBoxTableCell.forTableColumn(CashFlowServices.Clasificacion1, CashFlowServices.Clasificacion2));
        this.categoriaCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.subcategoriaCol.setCellFactory(TextFieldTableCell.forTableColumn());

        this.CurrentUserName.setText(Main.getCurrentUser().getNombres());
        this.CurrentUserRole.setText(Main.getCurrentUser().getRol());
        initCombox();
        fillTable();
    }

    @FXML
    private void onAdd(ActionEvent event) {

        String clasif = this.clasificacionCombo.getValue();
        String categoria = this.categoriaInput.getText();
        String subcat = this.subcategoriaInput.getText();
        boolean isValid = validateFields(categoria, subcat);
        if (isValid) {
            Category newCat = this.svc.validateCategory(categoria, subcat);
            if (newCat.getiD() == 0) {
                newCat.setClasificacion(clasif);
                newCat.setCategoria(categoria);
                newCat.setClasificacion(clasif);
                this.svc.createCategory(newCat);
                fillTable();
                clearFields();
            } else if (newCat.getiD() != 0) {
                alert.Category();
                clearFields();
            }

        } else {
            alert.ivalidFieldsAlert();
        }
    }

    @FXML
    private void onEdit(ActionEvent event) {
        Category item = this.CategoriesTable.getSelectionModel().getSelectedItem();
    }

    public void initCombox() {
        ObservableList<String> clasificaciones = this.svc.getCategoriesClasification();
        this.clasificacionCombo.setItems(clasificaciones);
        this.clasificacionCombo.setValue("Entrada");
    }

    public void fillTable() {
        this.categorias = this.svc.getCategories();
        this.CategoriesTable.setItems(categorias);
        this.clasificacionCol.setCellValueFactory(new PropertyValueFactory("clasificacion"));
        this.categoriaCol.setCellValueFactory(new PropertyValueFactory("categoria"));
        this.subcategoriaCol.setCellValueFactory(new PropertyValueFactory("sub_categoria"));
    }

    public boolean validateFields(String cat, String subcat) {
        return !(cat.equals("") || cat.equals(" ") || subcat.equals("") || subcat.equals(" "));
    }

    public void clearFields() {
        this.clasificacionCombo.setValue("Entrada");
        this.categoriaInput.clear();
        this.subcategoriaInput.clear();
    }


    @FXML
    private void backToMenuReg(MouseEvent event) {
        RouterMenu();
    }

    @FXML
    private void backToMenu(MouseEvent event) {
        RouterMenu();
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
            Stage myStage = (Stage) this.addButton.getScene().getWindow();
            myStage.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void editClasif(TableColumn.CellEditEvent<Category, String> clasifStringCellEditEvent) {

        Category item = this.CategoriesTable.getSelectionModel().getSelectedItem();
        item.setClasificacion(clasifStringCellEditEvent.getNewValue());
        this.svc.patchClasif(item);
        fillTable();
    }

    @FXML
    private void editCat(TableColumn.CellEditEvent<Category, String> categoryStringCellEditEvent) {

        Category item = this.CategoriesTable.getSelectionModel().getSelectedItem();
        item.setCategoria(categoryStringCellEditEvent.getNewValue());
        this.svc.patchCat(item);
        fillTable();

    }

    @FXML
    private void editSubCat(TableColumn.CellEditEvent<Category, String> subcatStringCellEditEvent) {
        Category item = this.CategoriesTable.getSelectionModel().getSelectedItem();
        item.setSub_categoria(subcatStringCellEditEvent.getNewValue());
        this.svc.patchSubCat(item);
        fillTable();
    }

}
