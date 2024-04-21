package home;

import java.time.LocalDateTime;

/**
* @author Duc Linh
*/
public class Task {
	private String id;
	private String title;
	private String content;
	private String status;
	private String menber;
	private LocalDateTime start;
	private LocalDateTime finish;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
}
