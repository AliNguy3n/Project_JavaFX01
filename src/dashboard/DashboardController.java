package dashboard;


/**
* @author Duc Linh
*/
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import login.LoginController;
import settings.ObSetting;
import settings.SettingsIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import org.kordamp.ikonli.javafx.FontIcon;

import accounts.ItemAcc;
import javafx.scene.control.Menu;


public class DashboardController  implements Initializable  {
	
	@FXML
    private AnchorPane anchorMain;
	
	@FXML
    private FontIcon imageList;
    @FXML
    private Button btnAccounts;
    
    @FXML
    private Button btnAnalyze;

    @FXML
    private Button btnApps;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnManager;

    @FXML
    private Button btnSettings;

    @FXML
    private FontIcon imageAccounts;

    @FXML
    private FontIcon imageAnalyze;

    @FXML
    private FontIcon imageApps;

    @FXML
    private FontIcon imageHome;

    @FXML
    private FontIcon imageManager;

    @FXML
    private FontIcon imageSettings;

    @FXML
    private MenuItem menuAbout;

    @FXML
    private MenuBar menuBarMain;

    @FXML
    private MenuItem menuCheckUpdate;

    @FXML
    private MenuItem menuClose;

    @FXML
    private MenuItem menuCopy;

    @FXML
    private MenuItem menuCut;

    @FXML
    private MenuItem menuDelelte;

    @FXML
    private MenuItem menuDuplicate;

    @FXML
    private MenuItem menuHelp;

    @FXML
    private MenuItem menuImport1;

    @FXML
    private MenuItem menuImport2;

    @FXML
    private MenuItem menuModify1;

    @FXML
    private MenuItem menuModify2;

    @FXML
    private MenuItem menuNew;

    @FXML
    private MenuItem menuOpen;

    @FXML
    private MenuItem menuOption;

    @FXML
    private MenuItem menuPaste;

    @FXML
    private MenuItem menuPrivary;

    @FXML
    private MenuItem menuPrivew1;

    @FXML
    private MenuItem menuRedo;

    @FXML
    private MenuItem menuRef;

    @FXML
    private MenuItem menuSave;

    @FXML
    private MenuItem menuSaveAs;

    @FXML
    private MenuItem menuTips;

    @FXML
    private MenuItem menuTool1;

    @FXML
    private MenuItem menuTool2;

    @FXML
    private MenuItem menuTool3;

    @FXML
    private MenuItem menuUndo;

    @FXML
    private MenuItem menuWin1;

    @FXML
    private MenuItem menuWin2;

    @FXML
    private AnchorPane panelNavLeft;

    @FXML
    private AnchorPane panelNavLeftSub;

    @FXML
    private AnchorPane panelTop;
    @FXML
    private BorderPane panelCenter;
    @FXML
    private Menu menuGroupEdit;

    @FXML
    private Menu menuGroupFile;

    @FXML
    private Menu menuGroupHelp;

    @FXML
    private Menu menuGroupModify;

    @FXML
    private Menu menuGroupTools;

    @FXML
    private Menu menuGroupView;

    @FXML
    private Menu menuGroupWin;
    @FXML
    private ImageView ImageAvatar;
    @FXML
    private Label lbUsername;
    @FXML
    private Label lbVersion;
    @FXML
    void handleMenuAction(ActionEvent event) {
    	if(event.getSource() == menuAbout) {
    		loadMenuBar("/menufile/file_About.fxml");
    	}
    }
    // Khai bao cac bien instance
//	public static ItemAcc uslg =  LoginController.uslg;
	public static ItemAcc uslg =  new ItemAcc("admin","123",1,"Active","Lever 1",0);
    boolean listState=true;
    Pane center =null;
    SettingsIO stio = new SettingsIO();
    public static ObSetting obst ;
    
    @FXML
    
    void handleNavAction(MouseEvent event) {
    	if(event.getSource() ==imageList) {
    		hanldeList(!listState);
    		controllPanelCenter(!listState);
    		listState=!listState;
    		System.out.println(listState);
    	}
    	if(event.getSource() ==btnHome || event.getSource()== imageHome) {
    		loadPage("/home/HomeMain.fxml");
    	}
    	if(event.getSource() ==btnManager || event.getSource()== imageManager) {
    		System.out.println("Handle Manager");
    	}
    	if(event.getSource() ==btnAnalyze || event.getSource()== imageAnalyze) {
    		System.out.println("Handle Alyz");
    	}
    	if(event.getSource() ==btnAccounts || event.getSource()== imageAccounts) {
    		loadPage("/accounts/Accounts.fxml");
    	}
    	if(event.getSource() ==btnApps || event.getSource()== imageApps) {
    		System.out.println("Handle apps");
    	}
    	if(event.getSource() ==btnSettings || event.getSource()== imageSettings) {
    		loadPage("/settings/SettingsMain.fxml");
    	}
    	
    }
    
	@Override 
	public void initialize(URL url,ResourceBundle rb) {
		try {
			obst = stio.SettingsReader();
		} catch (Exception e) {
			e.printStackTrace();
		}
		switch(obst.getValue("pageStartup")) {
			case "Settings" -> loadPage("/settings/SettingsMain.fxml");
			case "Home" -> loadPage("/home/HomeMain.fxml");
			case "Accounts" -> loadPage("/accounts/Accounts.fxml"); 
			
			default -> loadPage("/home/HomeMain.fxml");
		}
		;
		lbUsername.setText(uslg.getUsername());
		Image avatar = new Image(getClass().getResourceAsStream("/assets/avatarplaceholder128_128.png"));
		ImageAvatar.setImage(avatar);
	
		
	}
	
	// Bat tat Navigation Sub
	void hanldeList(boolean listState) {
		panelNavLeftSub.setVisible(listState);
	}
	
	//Set chieu rong cho Center Panel
	void controllPanelCenter(boolean listState) {
		if(listState) {
			AnchorPane.setLeftAnchor(panelCenter, 190.0);
		}else {
			AnchorPane.setLeftAnchor(panelCenter, 40.0);
		}
	}
	//Set trang thai cho Button paneNavleft
	
	//Functional cho Load page
	private void loadPage(String namePage) {
		
		try {
			center = FXMLLoader.load(getClass().getResource(namePage));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		panelCenter.setCenter(center);
		
	}
	private void loadMenuBar(String namePage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(namePage));
		Stage stage = new Stage();
		Pane  root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image icon64 = new Image(getClass().getResourceAsStream("/assets/logo64_64.png"));
        stage.setTitle("Alil Software");
        stage.getIcons().add(icon64);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	
}
