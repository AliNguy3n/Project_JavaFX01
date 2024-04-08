
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
    public static Connection makeConnection(String user, String password, String database){
      String dbUrl ="jdbc:sqlserver://LAPTOP-NAL17MNF:1433;databaseName="+database+";user="+user+";password="+password+";encrypt=true;trustServerCertificate=true";
        try{
            cnn = DriverManager.getConnection(dbUrl);
           
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        return cnn;
    }
}
