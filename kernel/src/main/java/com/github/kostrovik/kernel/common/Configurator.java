package com.github.kostrovik.kernel.common;

import com.github.kostrovik.kernel.dictionaries.ViewTypeDictionary;
import com.github.kostrovik.kernel.views.ColorThemesListView;
import com.github.kostrovik.kernel.views.ServerListView;
import com.github.kostrovik.kernel.views.menu.MenuBuilder;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.glance.matrix.helper.common.ApplicationLogger;
import ru.glance.matrix.provider.interfaces.ModuleConfiguratorInterface;
import ru.glance.matrix.provider.interfaces.views.ContentViewInterface;
import ru.glance.matrix.provider.interfaces.views.MenuBuilderInterface;
import ru.glance.matrix.provider.interfaces.views.ViewEventListenerInterface;

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

        views.put(ViewTypeDictionary.COLOR_THEME_LIST.name(), new ColorThemesListView(content, stage));
        views.put(ViewTypeDictionary.DATA_BASE_SERVER_LIST.name(), new ServerListView(content, stage));

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
