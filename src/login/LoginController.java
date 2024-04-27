package login;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import accounts.ItemAcc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import settings.ObSetting;
import settings.SettingsIO;

public class LoginController implements Initializable{
	public static ItemAcc uslg;
	public static ObSetting obSt;
	Connection cnn;
	protected String serverName;
	protected String user;
	protected String password;
	protected String database;
	protected String port;
    PreparedStatement st;
    ResultSet rs;
    @FXML
    private AnchorPane mainlogin;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnExit;
    @FXML
    private TextField txtName;
    @FXML
    private AnchorPane panelLeft;
    @FXML
    private AnchorPane panelRight;
    @FXML
    private AnchorPane panelBlur;
    @FXML
    private CheckBox checkboxRememberMe;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtName.requestFocus();
        txtName.setText("admin");
        txtPass.setText("123");
    }    

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if(event.getSource() == btnSignIn){
            login();
        }
      
        if(event.getSource()== btnExit){
            System.exit(0);
        }
    }
    public LoginController() {
    	try {
    		obSt = new ObSetting();
        	SettingsIO sti = new SettingsIO();
        	obSt = sti.SettingsReader();
    		if(obSt!=null) {
    			user = obSt.getValue("usernameServer");
        		password =obSt.getValue("passwordServer");
        		database = obSt.getValue("databaseName");
        		serverName = obSt.getValue("serverName");
        		port = obSt.getValue("port");
    		}else {
    			loadDashboard();
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        cnn = DBConnect.makeConnection(serverName, port, database ,user, password);
    }
    private void login(){
        String name = txtName.getText().trim();
        String pass = txtPass.getText().trim();
        String query = "select * from [user] where username=? and password=?";
        try{
            st = cnn.prepareStatement(query);
            st.setString(1, name);
            st.setString(2, pass);
            rs = st.executeQuery();
            if(name.isBlank()){
                new Alert(Alert.AlertType.ERROR,"Username cannot left blank!").showAndWait();
            }else if(pass.isBlank()){
                new Alert(Alert.AlertType.ERROR,"Password cannot left blank!").showAndWait();

            }else{
                if(!rs.next()){
                    new Alert(Alert.AlertType.ERROR,"Incorrect Username or Password!").showAndWait();
                }else{
                	uslg = new ItemAcc(rs.getString(1), rs.getString(2), rs.getInt(3),
                    		rs.getString(4),rs.getString(5),0); 
                	
                    loadDashboard();
                    cnn.close();
                }
            }
        }catch(Exception e){  
        }
    }
    
    private void loadDashboard() throws IOException{
        btnSignIn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/dashboard/Dashboard.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Dashboard!");
        Image icon64 = new Image(getClass().getResourceAsStream("/assets/logo64_64.png"));
		stage.getIcons().add(icon64);
        stage.setScene(new Scene(root));
        stage.show();
    }
	
}
