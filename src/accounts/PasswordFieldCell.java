package accounts;
/**
* @author Duc Linh
*/
import javafx.scene.control.TableCell;

public class PasswordFieldCell extends TableCell<ItemAcc, String>{
   
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
        } else {
            setText(maskPassword(item));
        }
    }

    private String maskPassword(String password) {
        return "\u2022".repeat(password.length());
    }
}
