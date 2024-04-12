package home;

/**
* @author Duc Linh
*/
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

public class TaskElementController implements Initializable{

    @FXML
    private FontIcon iKonCompete;

    @FXML
    private FontIcon iKonContent;

    @FXML
    private FontIcon iKonDelay;

    @FXML
    private FontIcon iKonDoing;

    @FXML
    private FontIcon iKonTime;

    @FXML
    private FontIcon iKonToGo;

    @FXML
    private Label lbTaskContent;

    @FXML
    private Label lbTaskStartEnd;

    @FXML
    private Label lbTaskTitle;

    @FXML
    private GridPane taskElement;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	String styleDelay =" ";
	String styleDoing ="-fx-background-color:#ECFEFF;";
	String styleCompele ="";
    public void setTask(Task task) {
    	setColor(task.getStatus());
    	lbTaskTitle.setText(task.getTitle());
    	lbTaskContent.setText(task.getContent());
    	lbTaskStartEnd.setText(task.getStart().toString() +" - "+ task.getFinish().toString());
    	
    	iKonDoing.setVisible(task.getStatus().equals("Doing"));
    	iKonCompete.setVisible(task.getStatus().equals("Complete"));
    	iKonDelay.setVisible(task.getStatus().equals("Delay"));
    	
    }
    private void setColor(String st) {
    	switch(st) {
    	case "Doing" -> taskElement.setStyle(styleDoing);
    	case "Complete" ->taskElement.setStyle(styleCompele);
    	case "Delay" -> taskElement.setStyle(styleDelay);
    	}
    }
}

