package ru.glance.matrix.users.common;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.glance.matrix.helper.common.ApplicationLogger;
import ru.glance.matrix.provider.interfaces.ModuleConfiguratorInterface;
import ru.glance.matrix.provider.interfaces.views.ContentViewInterface;
import ru.glance.matrix.provider.interfaces.views.MenuBuilderInterface;
import ru.glance.matrix.provider.interfaces.views.ViewEventListenerInterface;
import ru.glance.matrix.users.dictionaries.ViewTypeDictionary;
import ru.glance.matrix.users.views.UserEditorView;
import ru.glance.matrix.users.views.UsersListView;
import ru.glance.matrix.users.views.menu.MenuBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    23/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class Configurator implements ModuleConfiguratorInterface {
    private static Logger logger = ApplicationLogger.getLogger(Configurator.class.getName());

    @Override
    public MenuBuilderInterface getMenuBuilder() {
        return new MenuBuilder();
    }

    @Override
    public Map<String, ContentViewInterface> getViewEvents(Pane content, Stage stage) {
        Map<String, ContentViewInterface> views = new HashMap<>();

        views.put(ViewTypeDictionary.USERS_LIST.name(), new UsersListView(content));
        views.put(ViewTypeDictionary.USER_VIEW.name(), new UserEditorView(content, stage));

        return views;
    }

    @Override
    public ViewEventListenerInterface getEventListener() {
        ServiceLoader<ViewEventListenerInterface> serviceLoader = ServiceLoader.load(ModuleLayer.boot(), ViewEventListenerInterface.class);

        Optional<ViewEventListenerInterface> applicationSettings = serviceLoader.findFirst();

        if (applicationSettings.isPresent()) {
            return applicationSettings.get();
        }
        logger.log(Level.SEVERE, String.format("Не найден контейнер view приложения. Модуль: %s", this.getClass().getModule().getName()));

        return null;
    }
}
