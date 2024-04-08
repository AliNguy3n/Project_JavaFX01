package accounts;
/**
* @author Duc Linh
*/
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class ItemAcc {
	String username;
	private StringProperty password = new SimpleStringProperty();
	int permission;
	String status, desc;
	int no;
	public ItemAcc(String username, String password, int permission, String status, String desc, int no) {
		super();
		this.username = username;
		this.password.set(password);
		this.permission = permission;
		this.status = status;
		this.desc = desc;
		this.no = no;
	}
	public StringProperty passwordProperty() {
        return password;
    }
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		 return password.get();
	}
	public int getPermission() {
		return permission;
	}
	public String getStatus() {
		return status;
	}
	public String getDesc() {
		return desc;
	}
	public int getNo() {
		return no;
	}
}
