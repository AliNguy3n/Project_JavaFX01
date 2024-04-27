package home;

import java.time.LocalDateTime;

/**
* @author Duc Linh
*/
public class Task {
	private String id;
	private String title;
	private String content;
	private String taskstatus;
	private String menber;
	private LocalDateTime start;
	private LocalDateTime finish;
	private String assegnee;
	private String assigner;
	private String calendar;
	private String fullday;
	private String report;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTaskstatus() {
		return taskstatus;
	}
	public void setTaskstatus(String taskstatus) {
		this.taskstatus = taskstatus;
	}
	public String getMenber() {
		return menber;
	}
	public void setMenber(String menber) {
		this.menber = menber;
	}
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public LocalDateTime getFinish() {
		return finish;
	}
	public void setFinish(LocalDateTime finish) {
		this.finish = finish;
	}
	public String getAssegnee() {
		return assegnee;
	}
	public void setAssegnee(String assegnee) {
		this.assegnee = assegnee;
	}
	public String getAssigner() {
		return assigner;
	}
	public void setAssigner(String assigner) {
		this.assigner = assigner;
	}
	public String getCalendar() {
		return calendar;
	}
	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}
	public String getFullday() {
		return fullday;
	}
	public void setFullday(String fullday) {
		this.fullday = fullday;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	
	
	
}
