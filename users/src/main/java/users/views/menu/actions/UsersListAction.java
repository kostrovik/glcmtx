package users.views.menu.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import provider.interfaces.EventListenerInterface;
import provider.interfaces.ModuleEventInterface;
import users.dictionaries.ViewTypeDictionary;
import users.models.User;
import users.services.UserService;

import java.util.List;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    23/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class UsersListAction implements EventHandler<ActionEvent> {
    private EventListenerInterface listener;
    private UserService service;

    public UsersListAction(EventListenerInterface listener) {
        this.listener = listener;
        service = new UserService();
    }

    @Override
    public void handle(ActionEvent event) {
        ModuleEventInterface moduleEvent = new ModuleEventInterface() {
            @Override
            public String getModuleName() {
                return "users";
            }

            @Override
            public String getEventType() {
                return ViewTypeDictionary.USERS_LIST.name();
            }

            @Override
            public Object getEventData() {
                List<User> users = service.getUsersList();
                return users;
            }
        };
        listener.handle(moduleEvent);
    }
}