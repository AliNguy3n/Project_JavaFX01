
package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Duc Linh
 */
public class DBConnect {
    static Connection cnn;

    public static Connection makeConnection(String serverName, String port, String database, String user, String password){
      String dbUrl ="jdbc:sqlserver://"+serverName+":"+port+";databaseName="+database+";user="+user+";password="+password+";encrypt=true;trustServerCertificate=true";
        try{
            cnn = DriverManager.getConnection(dbUrl);
           
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        return cnn;
    }

}
