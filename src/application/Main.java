package application;
	
/**
* @author Duc Linh
*/
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {			
//			Parent root = FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
			Parent root = FXMLLoader.load(getClass().getResource("/dashboard/Dashboard.fxml"));
	        Scene scene = new Scene(root);
	        //primaryStage.initStyle(StageStyle.UNDECORATED);
	        Image icon64 = new Image(getClass().getResourceAsStream("/assets/logo64_64.png"));
	        primaryStage.setTitle("Login!");
	        primaryStage.getIcons().add(icon64);
	       
	        primaryStage.setScene(scene);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
