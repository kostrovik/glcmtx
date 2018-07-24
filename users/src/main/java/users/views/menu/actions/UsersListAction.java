package users.views.menu.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    23/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class UsersListAction implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        System.out.println("test");

        System.exit(0);
    }
}