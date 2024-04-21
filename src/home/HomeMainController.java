package home;

/**
* @author Duc Linh
*/
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import login.LoginController;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import dashboard.DashboardController;

public class HomeMainController implements Initializable {

    @FXML
    private FontIcon iKonClock;

    @FXML
    private FontIcon iKonHomeTask;

    @FXML
    private Label lbDate;

    @FXML
    private Label lbHomeMain;

    @FXML
    private BorderPane paneHomeMain;

    @FXML
    private AnchorPane topPaneHome;
    
    //Khai bao cac bien instance:
    Pane center = null;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lbDate.setText(LocalDate.now().toString());
		switch(LoginController.uslg.getPermission()) {
		case 1 -> loadHomepage("/home/HomeForMember.fxml");
		case 2 -> loadHomepage("/home/HomeForMember.fxml");
		case 3 -> loadHomepage("/home/HomeForMember.fxml");
		}
	}
	void loadHomepage(String st) {
		try {
			center = FXMLLoader.load(getClass().getResource(st));
		} catch (IOException e) {
			e.printStackTrace();
		}
		paneHomeMain.setCenter(center);
	}
}

