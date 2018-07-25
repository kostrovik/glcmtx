package ru.glance.matrix.users.views.menu.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ru.glance.matrix.provider.interfaces.EventListenerInterface;
import ru.glance.matrix.provider.interfaces.ModuleEventInterface;
import ru.glance.matrix.users.dictionaries.ViewTypeDictionary;
import ru.glance.matrix.users.models.User;
import ru.glance.matrix.users.services.UserService;

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