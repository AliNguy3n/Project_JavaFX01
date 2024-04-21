package settings;

/**
* @author Duc Linh
*/
import java.util.HashMap;
import java.util.Map;

public class ObSetting {
	//1 General
//	language;
//	pageStartup;
//	guiTheme;
//	notification;
//	saveDataToLocal;
	
	//2 Appearance
	
	//3 Connect
	
	//4 Database
//	databaseType;
//	serverName;
//	usernameServer;
//	passwordServer;
//	databaseName;
//  port;
	public Map<String, String> settings = new HashMap<String, String>();
	
	public void setValue(String key,String value) {
		if(value==null || value.isEmpty() || value.isBlank() ) {
			value = null;
		}
		settings.put(key, value);
	}
	public String getValue(String key) {
		return settings.get(key);
	}
	
}
