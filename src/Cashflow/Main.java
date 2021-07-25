package Cashflow;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
    
	private static Scene scene;
	private static Stage primaryStage;
	public static Stage secondStage;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage; 
		try {
			scene = new Scene(LoadFXML("Login")); 
			scene.getStylesheets().add(getClass().getResource("../Views/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("*Login*");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void setFXML(String fxml, String title) throws IOException{
		try {
			scene.setRoot(LoadFXML(fxml));
			primaryStage.sizeToScene();
			primaryStage.centerOnScreen();
			primaryStage.setTitle(title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private static Parent LoadFXML(String fxml) throws IOException{
		FXMLLoader fxmlLoader= new FXMLLoader(Main.class.getResource("../Views/"+ fxml+ ".fxml"));
		return fxmlLoader.load();
	}
	
	public static void newStage(String fxml, String title) {
		try {
			secondStage= new Stage();
			Scene scene= new Scene(LoadFXML(fxml));
			secondStage.setScene(scene);
			secondStage.setTitle(title);
			secondStage.initOwner(primaryStage);
			secondStage.initModality(Modality.WINDOW_MODAL);
			secondStage.centerOnScreen();
			secondStage.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
    
}
