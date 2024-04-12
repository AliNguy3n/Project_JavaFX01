package accounts;
/**
* @author Duc Linh
*/
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import dashboard.DashboardController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;
import login.DBConnect;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class AccountsController implements Initializable {
	protected Connection cnn;
    protected String user ="sa";
    protected String password="1234";
    protected String database= "MyApps";
    protected String tablename ="user";
    PreparedStatement st;
    ResultSet rs;
    ItemAcc ite = null;
    protected ObservableList<ItemAcc> table = null;
	@FXML
	private BorderPane panelAccountsMain;
	@FXML
	private GridPane grInfo;
	@FXML
	private HBox hBoxChart;
	@FXML
	private LineChart<String, Integer> lnChart;
	@FXML
	private TableView<ItemAcc> tbAccounts;
	@FXML
	private TableColumn<ItemAcc, Integer> colNo;
	@FXML
	private TableColumn<ItemAcc, String> colUsername;
	@FXML
	private TableColumn<ItemAcc, String> colPassword;
	@FXML
	private TableColumn<ItemAcc, Integer> colPermission;
	@FXML
	private TableColumn<ItemAcc, String> colStatus;
	@FXML
	private TableColumn<ItemAcc, String> colDesc;
	@FXML
	private TableColumn<ItemAcc, String> colHandle;
	@FXML
    private Label lbPermission;
    @FXML
    private Label lbStatus;
    @FXML
    private Label lbUser;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnAddUser;
    @FXML
    private Button btnRefresh;
    @FXML
    private FontIcon imageAddUser;
    @FXML
    private FontIcon imageRefresh;
    @FXML
    private FontIcon iKonClear;
    @FXML
    private FontIcon iKonSearch;
    @FXML
    private TextField txtSearch;
    
    @FXML
    void handleBtnAction(ActionEvent event) throws SQLException {
    	if(event.getSource() == btnAddUser) {
    		addOrMod(DashboardController.uslg,DashboardController.uslg.permission,false);
    		
    	}else if(event.getSource() ==btnSearch) {
    		String keyword = txtSearch.getText();
    		ObservableList<ItemAcc> result = listSearch(keyword);
    		if(result==null) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Error Message!");
    			alert.setContentText("Not found username!");
    			alert.show();
    		}else {
    			tbAccounts.setItems(result);
    		}
    		
    	}else if(event.getSource() ==btnClear) {
    		txtSearch.clear();
    		loadTableAcc();
    	}else if(event.getSource() ==btnRefresh) {
    		loadTableAcc();
    	}
    	
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lbUser.setText(DashboardController.uslg.getUsername());
		lbPermission.setText("Lever "+DashboardController.uslg.getPermission());
		lbStatus.setText(DashboardController.uslg.getStatus());
		cnn = DBConnect.makeConnection(user, password, database);
		try {
			long startTime = System.currentTimeMillis();
			loadTableAcc();
			loadlnChart();
			long endTime = System.currentTimeMillis();
			System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void loadTableAcc() throws SQLException {
		
		
		if(DashboardController.uslg.getPermission()==1) {			
			table = selectUser(1);	
			System.out.println("Load complete");
		}else if(DashboardController.uslg.getPermission()==2) {
			table = selectUser(2);
		}else if(DashboardController.uslg.getPermission()==3) {
			
		}else {
			System.out.println("Permission not valid");
		}
		colNo.setCellValueFactory( new PropertyValueFactory<ItemAcc, Integer>("no"));
		colUsername.setCellValueFactory(new PropertyValueFactory<ItemAcc, String>("username"));
		
		colPermission.setCellValueFactory(new PropertyValueFactory<ItemAcc, Integer>("permission"));
		colStatus.setCellValueFactory(new PropertyValueFactory<ItemAcc, String>("status"));
		colDesc.setCellValueFactory(new PropertyValueFactory<ItemAcc, String>("desc"));
		
		colPassword.setCellValueFactory(data -> data.getValue().passwordProperty());
		colPassword.setCellFactory(column -> new PasswordFieldCell());
		
		Callback<TableColumn<ItemAcc, String>, TableCell<ItemAcc, String>> cellFactory = (TableColumn<ItemAcc,String> param)
		->{
			return new TableCell<ItemAcc, String>(){
				
				@Override
				public void updateItem(String item,boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setGraphic(null);
						setText(null);
						System.out.println(item);
					}
					else {
						FontIcon edit = new FontIcon();
						FontIcon delete = new FontIcon();
						edit.setStyle("-fx-icon-color:#42A5F5;-fx-icon-code:fltfal-document-edit-24;-fx-icon-size:20;"
								+ "-fx-cursor: hand ;");
						delete.setStyle("-fx-icon-color:#F4511E;-fx-icon-code:fltfal-delete-forever-24;-fx-icon-size:20;"
								+ "-fx-cursor: hand ;");
						HBox managebtn = new HBox(edit,delete);
                        managebtn.setStyle("-fx-alignment:center");
                        
                        //Khi nhan nut Update
                        edit.setOnMouseClicked((MouseEvent ev)->{
                        	ite = tbAccounts.getSelectionModel().getSelectedItem();
                        	try {
								addOrMod(ite,DashboardController.uslg.permission,true);
							} catch (SQLException e) {
								e.printStackTrace();
							}
                        });
                        //Khi nhan nut Delete
                        delete.setOnMouseClicked((MouseEvent ev)->{
                        	ite = tbAccounts.getSelectionModel().getSelectedItem();
                        	try {
								deleteAcc(ite.getUsername());
							} catch (SQLException e) {
								
								e.printStackTrace();
							}
                        });
                        setGraphic(managebtn);

                        setText(null);
					}
				}
			};
		};
		
		colHandle.setCellFactory(cellFactory);
		tbAccounts.setItems(table);
			
		
	}
	
	//select dữ liệu của all user từ DBS
	private ObservableList<ItemAcc> selectUser(int permission) throws SQLException {
		
		ObservableList<ItemAcc> tableUser = FXCollections.observableArrayList();
		String query = "select * from ["+tablename+"] where permission >= ?;";
		st = cnn.prepareStatement(query);
		st.setInt(1, permission);
		rs = st.executeQuery();
		int i=1;
		while(rs.next()) {
			ItemAcc ac = new ItemAcc(rs.getString(1),rs.getString(2), rs.getInt(3),
					rs.getString(4),rs.getString(5),i);
			i++;
			tableUser.add(ac);
		}
		
		return tableUser;
		
	}
	// Method Search 
	private ObservableList<ItemAcc> listSearch(String keyword) {
		ObservableList<ItemAcc> listSearch=FXCollections.observableArrayList();
		for (ItemAcc item : table) {
			if(item.username.toLowerCase().contains(keyword.toLowerCase())) {
				listSearch.add(item);
			}
		}
		return listSearch;
	}
	// Method xoa mot Account
	private void deleteAcc(String acc) throws SQLException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete Account!");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to Delete: "+acc);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(
		        getClass().getResource("/CSS/Accounts.css").toExternalForm());
		dialogPane.getStyleClass().add("dialogAlert");
		
		ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.isPresent() && result.get().equals(buttonTypeYes)) {
			String query ="delete from [user] where username = ?";
			st = cnn.prepareStatement(query);
			st.setString(1, acc);
			int count;
			count = st.executeUpdate();
			if(count ==1) {
				Alert inf = new Alert(Alert.AlertType.INFORMATION);
				inf.setTitle("Deleted");
				inf.setContentText("User has been deleted!");
				inf.show();
			}else {
				Alert inf = new Alert(Alert.AlertType.ERROR);
				inf.setTitle("Error");
				inf.setContentText("Has error, can not delete !");
				inf.show();
			}
		}
		
	}
	

	
	//Method AddNew member hoac Modify Account
		protected void addOrMod(ItemAcc acc,int permission ,boolean option) throws SQLException {
			Stage newState = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/accounts/AddOrMod.fxml"));
			
			Pane root=null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			AddOrModController ADC = loader.getController();
			ADC.setData(acc, permission, option);
			Scene newScene =  new Scene(root);
			
			
			newState.setScene(newScene);
			newState.show();
			
			
			
		}
	
	// Method load line chart
	private void loadlnChart() {
		
		XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
		XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
		series1.setName("Color0");
		
        // Thêm dữ liệu vào series
        series1.getData().add(new XYChart.Data<>("1", 7));
        series1.getData().add(new XYChart.Data<>("2", 14));
        series1.getData().add(new XYChart.Data<>("3", 15));
        series1.getData().add(new XYChart.Data<>("4", 15));
        series1.getData().add(new XYChart.Data<>("5", 34));
        series1.getData().add(new XYChart.Data<>("6", 16));
        series1.getData().add(new XYChart.Data<>("7", 22));
        
        series2.getData().add(new XYChart.Data<>("1", 12));
        series2.getData().add(new XYChart.Data<>("2", 20));
        series2.getData().add(new XYChart.Data<>("3", 23));
        series2.getData().add(new XYChart.Data<>("4", 30));
        series2.getData().add(new XYChart.Data<>("5", 31));
        series2.getData().add(new XYChart.Data<>("6", 27));
        series2.getData().add(new XYChart.Data<>("7", 30));
        
        for (XYChart.Data<String, Integer> data : series1.getData()) {
            Circle circle = new Circle(0); // Tạo một hình tròn với bán kính 5
            data.setNode(circle); // Thiết lập hình dạng của nút cho mỗi điểm dữ liệu

        }
        for (XYChart.Data<String, Integer> data : series2.getData()) {
            Circle circle = new Circle(0); // Tạo một hình tròn với bán kính 5
            data.setNode(circle); 
            //data.getNode().setStyle("-fx-stroke-line-cap: round;");
        }
        lnChart.getData().add(series1);
        lnChart.getData().add(series2);
        
	}
	
	
}
