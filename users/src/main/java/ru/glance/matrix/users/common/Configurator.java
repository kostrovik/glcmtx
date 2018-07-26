package ru.glance.matrix.users.common;

import javafx.scene.layout.Pane;
import ru.glance.matrix.provider.interfaces.views.ContentViewInterface;
import ru.glance.matrix.provider.interfaces.EventListenerInterface;
import ru.glance.matrix.provider.interfaces.views.MenuBuilderInterface;
import ru.glance.matrix.provider.interfaces.ModuleConfiguratorInterface;
import ru.glance.matrix.users.dictionaries.ViewTypeDictionary;
import ru.glance.matrix.users.views.UsersListView;
import ru.glance.matrix.users.views.menu.MenuBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    23/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class Configurator implements ModuleConfiguratorInterface {
    @Override
    public MenuBuilderInterface getMenuBuilder(EventListenerInterface listener) {
        return new MenuBuilder(listener);
    }

    @Override
    public Map<String, ContentViewInterface> getViewEvents(Pane content) {
        Map<String, ContentViewInterface> views = new HashMap<>();

        views.put(ViewTypeDictionary.USERS_LIST.name(), new UsersListView(content));

        return views;
    }
}
