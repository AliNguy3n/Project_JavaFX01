module FX_MyProject {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires org.kordamp.ikonli.javafx;
	requires javafx.base;
	requires org.controlsfx.controls;
	requires com.calendarfx.view;
	requires MaterialFX;

	
	opens application to javafx.graphics, javafx.fxml;
	opens login to javafx.fxml;
	opens dashboard to javafx.fxml;
	opens home to javafx.fxml;
	opens accounts to javafx.fxml,javafx.base;
	opens settings to javafx.fxml;
	
}
