package Models;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Message{

    
    
    public void loginAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Usuario o Contraseña Incorrectos", ButtonType.OK);
        alert.getDialogPane().setMinHeight(80);
        alert.showAndWait();
    }
    
    public void ivalidFieldsAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR, "Llena los Campos Correctamente", ButtonType.OK);
        alert.getDialogPane().setMinHeight(80);
        alert.showAndWait();
    }
    
    public void RegisterAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Llena Correctamente los Campos", ButtonType.OK);
        alert.getDialogPane().setMinHeight(80);
        alert.showAndWait();
    }
    
    public void emailAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Este correo ya pertenece a una cuenta", ButtonType.OK);
        alert.getDialogPane().setMinHeight(80);
        alert.showAndWait();
    }
    
    public void Category() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Esta Categoria Ya Existe!", ButtonType.OK);
        alert.getDialogPane().setMinHeight(80);
        alert.showAndWait();
    }
}