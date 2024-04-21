package home;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
* @author Duc Linh
*/

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import login.DBConnect;
import login.LoginController;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.AgendaView;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.DateControl.EntryEditParameter;
import com.calendarfx.view.WeekDayHeaderView;
import com.calendarfx.view.WeekTimeScaleView;
import com.calendarfx.view.WeekView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class HomeForMemberController implements Initializable {

    @FXML
    private AgendaView agendaView;

    @FXML
    private Button btnApply;

    @FXML
    private Button btnSearch;

    @FXML
    private CheckComboBox<String> checkDisplayMode;

    @FXML
    private ChoiceBox<String> choiceDate;

    @FXML
    private ChoiceBox<String> choiceStatus;

    @FXML
    private DatePicker datePicker;

    @FXML
    private GridPane gridDoingTime;

    @FXML
    private GridPane gridPaneTaskDoing;

    @FXML
    private FontIcon iKonSearch;

    @FXML
    private Label lbTaskDoing;

    @FXML
    private Label lbTaskFailded;

    @FXML
    private Label lbTaskPlan;

    @FXML
    private BorderPane paneMemberCalendar;

    @FXML
    private AnchorPane paneMemberBasic;

    @FXML
    private BorderPane paneMemberMain;

    @FXML
    private AnchorPane paneMemberTop;

    @FXML
    private SplitPane paneSplitTask;

    @FXML
    private AnchorPane paneTaskDoing;

    @FXML
    private AnchorPane paneTaskFailed;

    @FXML
    private AnchorPane paneTaskPlan;

    @FXML
    private ScrollPane scrollTaskDoing;

    @FXML
    private ScrollPane scrollTaskFailed;

    @FXML
    private ScrollPane scrollTaskPanel;

    @FXML
    private TextField txtSearch;

    @FXML
    private VBox vBoxTaskFailed;

    @FXML
    private VBox vBoxTaskPlan;

    @FXML
    private WeekDayHeaderView weekDayHeaderView;

    @FXML
    private WeekTimeScaleView weekTimeScaleView;

    @FXML
    private WeekView weekView;
    
    @FXML
    private ScrollPane scrollMemberCalendar;
    
    @FXML
    private BorderPane paneDetailTask;
    
    @FXML
    private Button btnBack;
    
    @FXML
    private FontIcon iKonBack;
    
    @FXML
    private Button btnApplyTask;
    
    @FXML
    private FontIcon iKonApplyTask;
	@FXML
	void handleTaskSearch(ActionEvent event) {

	}
	
    @FXML
    void handleActionPaneTask(ActionEvent event) {
    	if(event.getSource() ==btnBack) {
    		paneDetailTask.setVisible(false);
    		getChoiceSelected(null);
    		btnApply.setDisable(false);
    	}
    }
    Pane item = null;
	ObservableList<Task> taskFailed = FXCollections.observableArrayList();
	ObservableList<Task> taskDoing = FXCollections.observableArrayList();
	ObservableList<Task> taskPlan = FXCollections.observableArrayList();
	ObservableList<String> displayMode = FXCollections.observableArrayList();

	Map<String, Integer> mapDate = new HashMap<String, Integer>();
	String[] date = { "Week", "5 Days", "3 Days", "2 Days", "Today" };
	String[] status = { "Basic", "Calendar", "Gantt chart" };
	int selectedDate=2;
	String selectedStatus;
	Homeboot homeboot;

	ObservableList<String> selectedModeDisplay;
	
	Calendar calendar = new Calendar("Test");
	CalendarSource calendarSource = new CalendarSource("source");
	Connection cnn;
	PreparedStatement st;
	ResultSet rs;
	
	LocalDateTime currentDateTime = LocalDateTime.now();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadTaskBar();
		try {
			loadTaskData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getChoiceSelected(null);
		calendar.setReadOnly(true);
		calendarSource.getCalendars().addAll(calendar);
		paneDetailTask.setVisible(false);
		weekView.getCalendarSources().setAll(calendarSource);
		
		agendaView.getCalendarSources().setAll(calendarSource);
		// choiceDate.setOnAction(this::getChoiceSelected);
		weekView.setEntryDetailsPopOverContentCallback(entry -> {
            handleEntrySelection(entry);
            return null; 
        });
		
		
	}
	
	private void handleEntrySelection(DateControl.EntryDetailsPopOverContentParameter entryDetails) {
	    Entry<?> entry = entryDetails.getEntry();
	    displayTaskDetail(entry);
	}
	
	private <T> void displayTaskDetail(T entry) {
		if(entry != null) {
			paneDetailTask.setVisible(true);
			btnApply.setDisable(true);
		    paneMemberCalendar.setVisible(false);
		    paneMemberBasic.setVisible(false);
			System.out.println("Da goi entry");
		}
	}
	
	public HomeForMemberController(){
	
	}
	private void SetConnection() {
		cnn= DBConnect.makeConnection(LoginController.obSt.getValue("serverName"), 
				LoginController.obSt.getValue("port"), LoginController.obSt.getValue("databaseName"),
				LoginController.obSt.getValue("usernameServer"), LoginController.obSt.getValue("passwordServer"));
		
	}
	// Ham load tất cả các nội dung cho choicebox
	private void loadTaskBar() {
		
		mapDate.put("Week", 7);
		mapDate.put("5 Days", 5);
		mapDate.put("3 Days", 3);
		mapDate.put("2 Days", 2);
		mapDate.put("Today", 1);
		
		displayMode.add("Doing");
		displayMode.add("Delay");
		displayMode.add("Plan");
		
		choiceDate.getItems().addAll(date);
		choiceStatus.getItems().addAll(status);
		checkDisplayMode.getItems().addAll(displayMode);
		checkDisplayMode.getProperties().values();
		
		HomeReaderWriter hrw = new HomeReaderWriter();
		homeboot = hrw.homeReader();
		selectedDate=mapDate.get(homeboot.getSelectedDate());
		choiceDate.setValue(homeboot.getSelectedDate());
		choiceStatus.setValue(homeboot.getSelectedStatus());
		
		if(homeboot.isModeDoing()) {
			checkDisplayMode.getCheckModel().check(0);
		}
		if(homeboot.isModeDelay()) {
			checkDisplayMode.getCheckModel().check(1);
		}
		if(homeboot.isModePlan()) {
			checkDisplayMode.getCheckModel().check(2);
		}	
	}
	

	// Ham xử lí hành động khi choicebox được select
	@FXML
	private void getChoiceSelected(ActionEvent event) {
		selectedModeDisplay= checkDisplayMode.getCheckModel().getCheckedItems();
		selectedDate = mapDate.get(choiceDate.getValue());
		selectedStatus=choiceStatus.getValue();
		boolean doing=false;
		boolean delay=false;
		boolean plan = false;
		for( Object ob: selectedModeDisplay) {
			if(ob.toString().equals("Doing")) {
				doing = true;
			}else if(ob.toString().equals("Delay")){
				delay =true;
			}else if(ob.toString().equals("Plan")) {
				plan = true;
			}
		}
		if(choiceStatus.getValue().equals("Basic")) {
			paneMemberCalendar.setVisible(false);
			paneMemberBasic.setVisible(true);
			paneMemberMain.setRight(null);
			displayModeBasic(doing,delay,plan);
			
		}
		if(choiceStatus.getValue().equals("Calendar")) {
			paneMemberCalendar.setVisible(true);
			paneMemberBasic.setVisible(false);
			paneMemberMain.setRight(agendaView);
			displayModeCalendar(selectedDate);
		}
		
		homeboot = new Homeboot(choiceDate.getValue(), selectedStatus, doing, delay, plan);
		HomeReaderWriter hrw = new HomeReaderWriter();
		hrw.homeWriter(homeboot);
	}

	private void loadGridDoing() {
		// Clear all
		gridPaneTaskDoing.getChildren().clear();
		gridPaneTaskDoing.getRowConstraints().clear();
		gridPaneTaskDoing.getColumnConstraints().clear();

		gridDoingTime.getChildren().clear();
		gridDoingTime.getRowConstraints().clear();
		gridDoingTime.getColumnConstraints().clear();

		RowConstraints row = new RowConstraints();
		row.setPrefHeight(30);
		gridDoingTime.getRowConstraints().add(row);
		String style = "-fx-background-color: transparent; -fx-background-radius:5px";
		for (int i = 0; i < selectedDate; i++) {
			ColumnConstraints col = new ColumnConstraints();
			Label lb = new Label();
			VBox vb = new VBox();
			vb.setStyle(style);
			vb.setFillWidth(true);
			vb.setAlignment(Pos.CENTER);
			lb.setText(String.format("Day %d", i + 1));
			vb.getChildren().add(lb);
			gridDoingTime.add(vb, i, 0);

			col.setMaxWidth(Region.USE_COMPUTED_SIZE);
			col.setPrefWidth(200);
			col.setHgrow(Priority.ALWAYS);
			gridPaneTaskDoing.getColumnConstraints().add(col);
			gridDoingTime.getColumnConstraints().add(col);
			loadTaskElement(taskDoing, gridPaneTaskDoing, i, 0);
		}
		gridPaneTaskDoing.setHgap(3);
		gridPaneTaskDoing.setVgap(3);

	}
	private void displayModeBasic(boolean doing, boolean delay, boolean plan) {
		// on-off TaskFailed
		if(paneSplitTask.getItems().contains(paneTaskFailed)) {
			if(!delay) {
				paneSplitTask.getItems().remove(paneTaskFailed);
			}else {
				vBoxTaskFailed.getChildren().clear();
				loadTaskElement(taskFailed, vBoxTaskFailed, 0, 0);
			}
		}
		else {
			if(delay) {
				vBoxTaskFailed.getChildren().clear();
				loadTaskElement(taskFailed, vBoxTaskFailed, 0, 0);
				paneSplitTask.getItems().add(paneTaskFailed);
			}
		}
		// on-off TaskDoing
		if(paneSplitTask.getItems().contains(paneTaskDoing)) {
			if(!doing) {
				paneSplitTask.getItems().remove(paneTaskDoing);
			}else {
				loadGridDoing();
			}
		}
		else {
			if(doing) {
				loadGridDoing();
				paneSplitTask.getItems().add(paneTaskDoing);
			}
		}
		// on-off TaskPlan
		if(paneSplitTask.getItems().contains(paneTaskPlan)) {
			if(!plan) {
				paneSplitTask.getItems().remove(paneTaskPlan);
			}else {
				vBoxTaskPlan.getChildren().clear();
				loadTaskElement(taskPlan, vBoxTaskPlan, 0, 0);
			}
		}
		else {
			if(plan) {
				vBoxTaskPlan.getChildren().clear();
				loadTaskElement(taskPlan, vBoxTaskPlan, 0, 0);
				paneSplitTask.getItems().add(paneTaskPlan);
			}
		}
	}

	// Hàm tạo ra các taskElement
	private <T extends Pane> void loadTaskElement(ObservableList<Task> task, T pane, int col, int row) {
		for (Task t : task) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/TaskElement.fxml"));
			try {
				item = loader.load();
				item.setOnMouseClicked(new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(MouseEvent event) {
		                // Gọi phương thức khi click chuột vào 
		            	if(event.getClickCount()==2) {
		            		displayTaskDetail(task);
		            	}
		            }
		        });
			} catch (IOException e) {
				e.printStackTrace();
			}
			TaskElementController eControl = loader.getController();
			eControl.setTask(t);
			if (pane instanceof GridPane) {
				if (col == -1) {
					// Nothing
				} else if(t.getStart().isBefore(currentDateTime.plusDays(col)) && 						t.getFinish().isAfter(currentDateTime.plusDays(col))) {
					((GridPane) pane).add(item, col, row);
					row++;
				}
			} else {
				pane.getChildren().add(item);
			}
		}
	}
	// Hàm gọi render cho mode Calendar
	private void displayModeCalendar(int numberOfday) {

		
		
		// Gán giá trị số ngày cần hiển thị
		weekView.setNumberOfDays(numberOfday);
		weekView.setAdjustToFirstDayOfWeek(false);
		weekView.setEnableCurrentTimeMarker(true);
		weekDayHeaderView.setNumberOfDays(numberOfday);
		weekDayHeaderView.setAdjustToFirstDayOfWeek(false);
		
		DateControl dateControl= new DateControl() {};
		dateControl.setEntryEditPolicy(param -> false);
		
		// Định nghĩa chính sách chỉnh sửa
		Callback<EntryEditParameter, Boolean> editPolicy = param -> {
		    return false;
		};

		// Thiết lập chính sách chỉnh sửa cho WeekView
		weekView.setEntryEditPolicy(editPolicy);
	}
	
	// Hàm gán giá trị cho các Element từ CSDL
	private void loadTaskData() throws SQLException {
		SetConnection();
		String query = "select * from ExecuteTasks inner join tasks on ExecuteTasks.ID = tasks.ID  where Assegnee=?";
		st = cnn.prepareStatement(query);
		st.setString(1, LoginController.uslg.getUsername());
		rs = st.executeQuery();
		while(rs.next()) {
			String ID = rs.getString("ID");
			String Title = rs.getString("Title");
			String Assegnee = rs.getString("Assegnee");
			String TaskStatus = rs.getString("TaskStatus");
			String Report = rs.getString("Report");
			LocalDate StartDate = rs.getDate("StartDate").toLocalDate();
			LocalTime StartTime = rs.getTime("StartTime").toLocalTime();
			LocalDate EndDate = rs.getDate("EndDate").toLocalDate();
			LocalTime EndTime = rs.getTime("EndTime").toLocalTime();
			String Assigner = rs.getString("Assigner");
			String UserObject = rs.getString("UserObject");
			String Content = rs.getString("Content");
			int Fullday = rs.getInt("Fullday");
			String Calendar = rs.getString("Calendar");
			makeTask(ID,Title, Assegnee, TaskStatus,Report,StartDate, StartTime, EndDate, 
					EndTime, Assigner, UserObject, Content);
			makeEntry(ID,Title, Assegnee, TaskStatus,Report,StartDate, StartTime, EndDate, 
					EndTime, Assigner, UserObject, Content);
		}
		
		
		cnn.close();
	}
	//Ham Remake task 
	private void makeTask(String ID, String Title, String Assegnee, String TaskStatus, String Report,
			LocalDate StartDate, LocalTime StartTime, LocalDate EndDate, LocalTime EndTime, String Assigner, String 			UserObject, String Content) {
		Task task = new Task();
		task.setId(ID);
		task.setTitle(Title);
		task.setContent(Content);
		
		LocalDateTime taskStartDateTime = LocalDateTime.of(StartDate, StartTime);
		LocalDateTime taskEndDateTime = LocalDateTime.of(EndDate, EndTime);
		System.out.println("End:"+taskEndDateTime.toString());
		System.out.println("Current:"+currentDateTime.toString());
	    task.setStart(taskStartDateTime);
		task.setFinish(taskEndDateTime);		
		
		if(taskEndDateTime.isBefore(currentDateTime)) {
			task.setStatus("Delay");	
			taskFailed.add(task);
		}else if(taskEndDateTime.isAfter(currentDateTime)) {
			task.setStatus("Doing");
			taskDoing.add(task);	
		}else if(taskStartDateTime.isAfter(currentDateTime)) {
			task.setStatus("Plan");
			taskPlan.add(task);
		}
	}
	private void makeEntry(String ID, String Title, String Assegnee, String TaskStatus, String Report,
			LocalDate StartDate, LocalTime StartTime, LocalDate EndDate, LocalTime EndTime, String Assigner, String 			UserObject, String Content) {
		LocalDateTime taskStartDateTime = LocalDateTime.of(StartDate, StartTime);
		LocalDateTime taskEndDateTime = LocalDateTime.of(EndDate, EndTime);
		Entry<String> entry = new Entry<>(Title);
		entry.setId(ID);
		entry.setUserObject("A");
		entry.setLocation(Content);
		entry.setInterval(taskStartDateTime,taskEndDateTime);
		
        calendar.addEntry(entry);
	}

}
