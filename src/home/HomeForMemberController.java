package home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
* @author Duc Linh
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class HomeForMemberController implements Initializable{

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
	    private AnchorPane paneMemberCenter;

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
    Pane item = null;
    
    @FXML
    void handleTaskSearch(ActionEvent event) {

    }
    ObservableList<Task> taskFailed = FXCollections.observableArrayList();
    ObservableList<Task> taskDoing = FXCollections.observableArrayList();
    ObservableList<Task> taskPlan = FXCollections.observableArrayList();
    ObservableList<String> displayMode = FXCollections.observableArrayList();

    Map<String, Integer> mapDate = new HashMap<String, Integer>(); 
    String[] date = {"Week", "5 Days","3 Days","2 Days", "Today"};
    String[] status= {"A","B"};
    int selectedDate=3;
    String selectedStatus;
    ObservableList<String> selectedModeDisplay;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		loadTaskBar();
		loadTaskData();
		
		loadTaskElement(taskFailed, vBoxTaskFailed,0,0);
		loadTaskElement(taskPlan, vBoxTaskPlan,0,0);
		loadGridDoing();
		//choiceDate.setOnAction(this::getChoiceSelected);	
		
	}
	
	//Ham load tất cả các nội dung cho choicebox
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
		
	}
	//Ham xử lí hành động khi một choicebox được select
	@FXML
	private void getChoiceSelected(ActionEvent event) {
		selectedModeDisplay= checkDisplayMode.getCheckModel().getCheckedItems();
		selectedDate = mapDate.get(choiceDate.getValue());
		
		loadGridDoing();	
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
		// on-off TaskFailed
		if(paneSplitTask.getItems().contains(paneTaskFailed)) {
			if(!delay) {
				paneSplitTask.getItems().remove(paneTaskFailed);
			}
		}
		else {
			if(delay) {
				paneSplitTask.getItems().add(paneTaskFailed);
			}
		}
		// on-off TaskDoing
		if(paneSplitTask.getItems().contains(paneTaskDoing)) {
			if(!doing) {
				paneSplitTask.getItems().remove(paneTaskDoing);
			}
		}
		else {
			if(doing) {
				paneSplitTask.getItems().add(paneTaskDoing);
			}
		}
		// on-off TaskPlan
		if(paneSplitTask.getItems().contains(paneTaskPlan)) {
			if(!plan) {
				paneSplitTask.getItems().remove(paneTaskPlan);
			}
		}
		else {
			if(plan) {
				paneSplitTask.getItems().add(paneTaskPlan);
			}
		}
		
	}
	
	private void loadGridDoing() {
		//Clear all
		gridPaneTaskDoing.getChildren().clear();
		gridPaneTaskDoing.getRowConstraints().clear();
		gridPaneTaskDoing.getColumnConstraints().clear();
		
		gridDoingTime.getChildren().clear();
		gridDoingTime.getRowConstraints().clear();
		gridDoingTime.getColumnConstraints().clear();
		
		RowConstraints row= new RowConstraints();
		row.setPrefHeight(30);
		gridDoingTime.getRowConstraints().add(row);
		String style ="-fx-background-color: transparent; -fx-background-radius:5px";
		for(int i=0; i<selectedDate; i++) {
			ColumnConstraints col = new ColumnConstraints();
			Label lb = new Label();
			VBox vb = new VBox();
			vb.setStyle(style);
			vb.setFillWidth(true);
			vb.setAlignment(Pos.CENTER);
			lb.setText(String.format("Day %d", i+1));
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
	
	// Hàm tạo ra các taskElement
	private <T extends Pane> void  loadTaskElement(ObservableList<Task> task,T pane, int col, int row  ) {
		for(Task t: task) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/TaskElement.fxml"));
			try {
				item = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			TaskElementController eControl = loader.getController();
			eControl.setTask(t);
			if(pane instanceof GridPane) {
				if(col==-1) {
					
				}else {
					((GridPane) pane).add(item,col,row);					
					row++;	
				}
			}else {
				pane.getChildren().add(item);				
			}
		}
	}
			
	//Hàm gán giá trị cho các Element từ CSDL
	void loadTaskData() {
		
		Task task = new Task();
		task.setId(0);
		task.setTitle("Task 01 Your mission");
		task.setContent("Washing disks");
		task.setStatus("Doing");
		task.setStart(LocalDateTime.of(2024,4,8,15,30));
		task.setFinish(LocalDateTime.of(2024,4,8,17,30));
		taskDoing.add(task);
		
		task = new Task();
		task.setId(1);
		task.setTitle("Task 02 Your mission");
		task.setContent("Do my homie");
		task.setStatus("Doing");
		task.setStart(LocalDateTime.of(2024,4,8,15,30));
		task.setFinish(LocalDateTime.of(2024,4,8,17,30));
		taskDoing.add(task);
		
		task = new Task();
		task.setId(2);
		task.setTitle("Task 03 Your mission");
		task.setContent("Do my homie");
		task.setStatus("Doing");
		task.setStart(LocalDateTime.of(2024,4,8,15,30));
		task.setFinish(LocalDateTime.of(2024,4,8,17,30));
		taskDoing.add(task);
		
		task = new Task();
		task.setId(3);
		task.setTitle("Task 04 Your mission");
		task.setContent("Do my homie");
		task.setStatus("Doing");
		task.setStart(LocalDateTime.of(2024,4,8,15,30));
		task.setFinish(LocalDateTime.of(2024,4,8,17,30));
		taskDoing.add(task);
		
		task = new Task();
		task.setId(4);
		task.setTitle("Task 05 Your mission");
		task.setContent("Do my homie");
		task.setStatus("Doing");
		task.setStart(LocalDateTime.of(2024,4,8,15,30));
		task.setFinish(LocalDateTime.of(2024,4,8,17,30));
		taskDoing.add(task);
		
		
		task = new Task();
		task.setId(7);
		task.setTitle("Task 08 Your mission");
		task.setContent("Do my homie");
		task.setStatus("Delay");
		task.setStart(LocalDateTime.of(2024,4,8,15,30));
		task.setFinish(LocalDateTime.of(2024,4,8,17,30));
		taskFailed.add(task);
		
		task = new Task();
		task.setId(8);
		task.setTitle("Task 09 Your mission");
		task.setContent("Do my homie");
		task.setStatus("Complete");
		task.setStart(LocalDateTime.of(2024,4,8,15,30));
		task.setFinish(LocalDateTime.of(2024,4,8,17,30));
		taskPlan.add(task);
		
		task = new Task();
		task.setId(9);
		task.setTitle("Task10 Your mission");
		task.setContent("Do my homie");
		task.setStatus("Delay");
		task.setStart(LocalDateTime.of(2024,4,8,15,30));
		task.setFinish(LocalDateTime.of(2024,4,8,17,30));
		taskFailed.add(task);
		
		task = new Task();
		task.setId(10);
		task.setTitle("Task 11 Your mission");
		task.setContent("Do my homie");
		task.setStatus("Delay");
		task.setStart(LocalDateTime.of(2024,4,8,15,30));
		task.setFinish(LocalDateTime.of(2024,4,8,17,30));
		taskFailed.add(task);
		
		task = new Task();
		task.setId(11);
		task.setTitle("Task 12 Your mission");
		task.setContent("Do my homie");
		task.setStatus("Delay");
		task.setStart(LocalDateTime.of(2024,4,8,15,30));
		task.setFinish(LocalDateTime.of(2024,4,8,17,30));
		taskFailed.add(task);
		
		task = new Task();
		task.setId(12);
		task.setTitle("Task 13 Your mission");
		task.setContent("Do my homie");
		task.setStatus("Delay");
		task.setStart(LocalDateTime.of(2024,4,8,15,30));
		task.setFinish(LocalDateTime.of(2024,4,8,17,30));
		taskFailed.add(task);
		
	}
}

