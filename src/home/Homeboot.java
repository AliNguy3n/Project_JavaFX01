package home;

/**
* @author Duc Linh
*/
public class Homeboot {
	private String selectedDate;
	private String selectedStatus;
	private boolean modeDoing;
	private boolean modeDelay;
	private boolean modePlan;
	
	public Homeboot(String selectedDate, String selectedStatus, boolean modeDoing, boolean modeDelay, boolean modePlan) {
		super();
		this.selectedDate = selectedDate;
		this.selectedStatus = selectedStatus;
		this.modeDoing = modeDoing;
		this.modeDelay = modeDelay;
		this.modePlan = modePlan;
	}
	
	public Homeboot() {
		super();
	}

	public String getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}
	public String getSelectedStatus() {
		return selectedStatus;
	}
	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}
	public boolean isModeDoing() {
		return modeDoing;
	}
	public void setModeDoing(boolean modeDoing) {
		this.modeDoing = modeDoing;
	}
	public boolean isModeDelay() {
		return modeDelay;
	}
	public void setModeDelay(boolean modeDelay) {
		this.modeDelay = modeDelay;
	}
	public boolean isModePlan() {
		return modePlan;
	}
	public void setModePlan(boolean modePlan) {
		this.modePlan = modePlan;
	}
	
	
	
}
