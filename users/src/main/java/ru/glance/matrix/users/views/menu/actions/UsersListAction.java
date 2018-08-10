package ru.glance.matrix.users.views.menu.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import com.github.kostrovik.kernel.interfaces.views.LayoutType;
import com.github.kostrovik.kernel.interfaces.views.ViewEventInterface;
import com.github.kostrovik.kernel.interfaces.views.ViewEventListenerInterface;
import ru.glance.matrix.users.common.Configurator;
import ru.glance.matrix.users.dictionaries.ViewTypeDictionary;
import ru.glance.matrix.users.services.UserService;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    23/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class UsersListAction implements EventHandler<ActionEvent> {
    private UserService service;

    public UsersListAction() {
        service = new UserService();
    }

    @Override
    public void handle(ActionEvent event) {
        Configurator configurator = Configurator.getConfig();
        ViewEventListenerInterface listener = configurator.getEventListener();
        listener.handle(new ViewEventInterface() {
            @Override
            public String getModuleName() {
                return UsersListAction.class.getModule().getName();
            }

            @Override
            public String getViewName() {
                return ViewTypeDictionary.USERS_LIST.name();
            }

            @Override
            public Object getEventData() {
                return service.getUsersList();
            }

            @Override
            public LayoutType getLayoutType() {
                return LayoutType.DEFAULT;
            }
        });
    }
}