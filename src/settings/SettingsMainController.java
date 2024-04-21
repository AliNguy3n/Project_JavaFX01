package settings;
/**
* @author Duc Linh
*/
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

public class SettingsMainController implements Initializable{

    @FXML
    private ToggleButton btnAppearance;

    @FXML
    private Button btnCancelSetting;

    @FXML
    private ToggleButton btnConnect;

    @FXML
    private Button btnConnectOk;

    @FXML
    private ToggleButton btnDatabase;

    @FXML
    private ToggleButton btnGeneral;

    @FXML
    private ToggleGroup btnGroupSetting;

    @FXML
    private FontIcon iKonOpenFolder;

    @FXML
    private Button btnSaveSetting;
    
    @FXML
    private Button btnLoadFile;
    @FXML
    private Button btnTestConnect;

    @FXML
    private CheckBox checkboxSavelocal;

    @FXML
    private CheckBox checkboxNotification;

    @FXML
    private ChoiceBox<String> choiceBoxPageStart;

    @FXML
    private ChoiceBox<String> choiceboxDataType;

    @FXML
    private ChoiceBox<String> choiceboxLanguage;

    @FXML
    private ChoiceBox<String> choiceboxTheme;

    @FXML
    private FontIcon iKonAppearance;

    @FXML
    private FontIcon iKonConnect;

    @FXML
    private FontIcon iKonDatabase;

    @FXML
    private FontIcon iKonGeneral;

    @FXML
    private FontIcon iKonSettings;

    @FXML
    private Label lbCheckConnect;

    @FXML
    private Label lbConnect;

    @FXML
    private Label lbDatabaseType;

    @FXML
    private Label lbDatabasename;

    @FXML
    private Label lbLanguage;

    @FXML
    private Label lbMessDatabase;

    @FXML
    private Label lbNotification;

    @FXML
    private Label lbPageStartup;

    @FXML
    private Label lbPassword;

    @FXML
    private Label lbSave;

    @FXML
    private Label lbServername;

    @FXML
    private Label lbSettings;

    @FXML
    private Label lbTheme;

    @FXML
    private Label lbUsername;

    @FXML
    private ScrollPane paneAppearance;

    @FXML
    private ScrollPane paneDatabase;

    @FXML
    private ScrollPane paneGeneral;
    @FXML
    private GridPane paneGridGen;
    @FXML
    private GridPane paneLeft;

    @FXML
    private BorderPane paneSettingMain;

    @FXML
    private HBox paneSettingTop;

    @FXML
    private ScrollPane panelConnect;

    @FXML
    private StackPane stackpaneCenter;

    @FXML
    private TextField txtDatabasename;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPath;

    @FXML
    private TextField txtServername;

    @FXML
    private TextField txtUsername;
    @FXML
    private Label lbPort;
    @FXML
    private TextField txtPort;
 // Set du lieu cho cac Choice box:
    //	1.Thang General:
    	String[] choiceLanguage = {"English","Vietnamese"};
    	String[] choiceGui = {"Light Theme","Dark Theme"};
    	String[] choicepage = {"Home","Manager","Analyze","Accounts","Apps","Settings"};
    //	2.Thang Database:
    	String[] choiceDatabaseType = {"SQL Server","My SQL","Not use"};
    	SettingsIO stio = new SettingsIO();
    	ObSetting obSettings = new ObSetting();
    	
    @FXML
    void handleActionSetting(ActionEvent event) throws Exception {
    	if(event.getSource() == btnGeneral) {
    		displayStack("General");
    	}else if(event.getSource() == btnAppearance) {
    		displayStack("Appearance");
    	}else if(event.getSource() ==btnConnect) {
    		displayStack("Connect");
    	}else if(event.getSource() ==btnDatabase) {
    		displayStack("Database");
    	}else if(event.getSource() ==btnConnectOk) {
    		
    	}else if(event.getSource() ==btnLoadFile) {
    		loadFile();
    	}else if(event.getSource() ==btnSaveSetting) {
    		saveSettings();
    	}else if(event.getSource() ==btnCancelSetting) {
    		cancelSaving();
    	}
    	
    }
    @FXML
    void handleActionChoiceBox(ActionEvent event) {
    	if(event.getSource() ==checkboxSavelocal) {
    		if(checkboxSavelocal.selectedProperty().getValue()) {
    			btnLoadFile.setDisable(false);;
    			txtPath.setDisable(false);
    		}else {
    			btnLoadFile.setDisable(true);;
    			txtPath.setDisable(true);
    		}
    	}
    }
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
    	try {
			obSettings = stio.SettingsReader();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	choiceboxLanguage.getItems().addAll(choiceLanguage);
    	choiceboxLanguage.setValue(obSettings.getValue("language"));
    	choiceboxTheme.getItems().addAll(choiceGui);
    	choiceboxTheme.setValue(obSettings.getValue("guiTheme"));
    	choiceBoxPageStart.getItems().addAll(choicepage);
    	choiceBoxPageStart.setValue(obSettings.getValue("pageStartup"));
    	checkboxNotification.setSelected(Boolean.parseBoolean(obSettings.getValue("notification")));
    	checkboxSavelocal.setSelected(Boolean.parseBoolean(obSettings.getValue("saveDataToLocal")));
    	txtPath.setText(obSettings.getValue("pathfolder"));
    	btnLoadFile.setDisable(!Boolean.parseBoolean(obSettings.getValue("saveDataToLocal")));;
		txtPath.setDisable(!Boolean.parseBoolean(obSettings.getValue("saveDataToLocal")));
		
		choiceboxDataType.getItems().addAll(choiceDatabaseType);
    	choiceboxDataType.setValue(obSettings.getValue("databaseType"));
    	txtPort.setText(obSettings.getValue("port"));
    	txtServername.setText(obSettings.getValue("serverName"));
    	txtUsername.setText(obSettings.getValue("usernameServer"));
    	txtPassword.setText(obSettings.getValue("passwordServer"));
    	txtDatabasename.setText(obSettings.getValue("databaseName"));
    	
    	displayStack("General");
	}

    
    
    	
    // Ham bat tat cac Stackpane
    void displayStack(String pane) {
    	paneGeneral.setVisible(pane.equals("General"));
		paneAppearance.setVisible(pane.equals("Appearance"));
		paneDatabase.setVisible(pane.equals("Database"));
		panelConnect.setVisible(pane.equals("Connect"));
    }
    void loadFile() {
    	System.out.println("Alo load file");
    	Stage fileStage = new Stage();
    	
    	DirectoryChooser directory = new DirectoryChooser();
    	directory.setTitle("Choice folder:");
    	File directoryPath = directory.showDialog(fileStage);
    	if( directoryPath !=null) {
    		String path = directoryPath.getAbsolutePath();
    		txtPath.setText(path);
    	}
    }
    
	void saveSettings() throws Exception {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Save your change ?");
		alert.setContentText("Would you like to confirm that all the above changes will be saved");
		ButtonType buttonTypeYes = new ButtonType("Yes");
		ButtonType buttonTypeNo = new ButtonType("No");

		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.isPresent() && result.get().equals(buttonTypeYes)) {
			ObSetting obSettings = new ObSetting();
			obSettings.setValue("language", choiceboxLanguage.getValue());
			obSettings.setValue("pageStartup", choiceBoxPageStart.getValue());
			obSettings.setValue("guiTheme", choiceboxTheme.getValue());
			obSettings.setValue("notification", checkboxNotification.selectedProperty().getValue().toString());
			obSettings.setValue("saveDataToLocal", checkboxSavelocal.selectedProperty().getValue().toString());
			obSettings.setValue("pathfolder", txtPath.getText());
			obSettings.setValue("databaseType", choiceboxDataType.getValue());
			obSettings.setValue("serverName", txtServername.getText());
			obSettings.setValue("usernameServer", txtUsername.getText());
			obSettings.setValue("passwordServer", txtPassword.getText());
			obSettings.setValue("databaseName", txtDatabasename.getText());
			obSettings.setValue("port", txtPort.getText());
			if(stio.SettingsWriter(obSettings)) {
				Alert alertIO = new Alert(AlertType.INFORMATION);
				alertIO.setTitle("Congratulation!");
				alertIO.setContentText("Save complete! Restart program to Apply new settings");
				alertIO.show();
			}else {
				Alert alertIO = new Alert(AlertType.ERROR);
				alertIO.setTitle("ERROR");
				alertIO.setContentText("Error occurred when saving settings!");
				alertIO.show();
			}
			
		}
		
	}
	void cancelSaving() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Not Save your change ?");
		alert.setContentText("Cancel your action!");
		ButtonType buttonTypeYes = new ButtonType("Yes");
		ButtonType buttonTypeNo = new ButtonType("No");

		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.isPresent() && result.get().equals(buttonTypeYes)) {
			initialize(null, null);
		}
	}
}