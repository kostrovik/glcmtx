package com.github.kostrovik.kernel.common;

import com.github.kostrovik.kernel.dictionaries.ViewTypeDictionary;
import com.github.kostrovik.kernel.views.ColorThemesListView;
import com.github.kostrovik.kernel.views.ServerListView;
import com.github.kostrovik.kernel.views.menu.MenuBuilder;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.glance.helper.common.ApplicationLogger;
import ru.glance.provider.interfaces.ModuleConfiguratorInterface;
import ru.glance.provider.interfaces.controls.ControlBuilderFacadeInterface;
import ru.glance.provider.interfaces.views.ContentViewInterface;
import ru.glance.provider.interfaces.views.MenuBuilderInterface;
import ru.glance.provider.interfaces.views.ViewEventListenerInterface;

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
final public class Configurator implements ModuleConfiguratorInterface {
    private static Logger logger = ApplicationLogger.getLogger(Configurator.class.getName());
    private static volatile Configurator configurator;
    private static Map<String, ContentViewInterface> views;

    private Configurator() {
        views = new HashMap<>();
    }

    public static Configurator provider() {
        return getConfig();
    }

    public static Configurator getConfig() {
        if (configurator == null) {
            synchronized (Configurator.class) {
                if (configurator == null) {
                    configurator = new Configurator();
                }
            }
        }
        return configurator;
    }

    @Override
    public MenuBuilderInterface getMenuBuilder() {
        return new MenuBuilder();
    }

    @Override
    public Map<String, ContentViewInterface> getViewEvents(Pane content, Stage stage) {
        if (views.isEmpty()) {
            synchronized (Configurator.class) {
                if (views.isEmpty()) {
                    views.put(ViewTypeDictionary.COLOR_THEME_LIST.name(), new ColorThemesListView(content, stage));
                    views.put(ViewTypeDictionary.DATA_BASE_SERVER_LIST.name(), new ServerListView(content, stage));
                }
            }
        }

        return views;
    }

    @Override
    public ViewEventListenerInterface getEventListener() {
        Optional<ViewEventListenerInterface> applicationSettings = getFirstLoadedImplementation(ViewEventListenerInterface.class);

        if (applicationSettings.isPresent()) {
            return applicationSettings.get();
        }
        logger.log(Level.SEVERE, String.format("Не найден контейнер view приложения. Модуль: %s", this.getClass().getModule().getName()));

        return null;
    }

    @Override
    public ControlBuilderFacadeInterface getControlBuilder() {
        Optional<ControlBuilderFacadeInterface> controlBuilderFacade = getFirstLoadedImplementation(ControlBuilderFacadeInterface.class);

        if (controlBuilderFacade.isPresent()) {
            return controlBuilderFacade.get();
        }
        logger.log(Level.SEVERE, String.format("Не найден фасад для построения элементов интерфейса. Модуль: %s", this.getClass().getModule().getName()));

        return null;
    }

    private <E> Optional<E> getFirstLoadedImplementation(Class<E> type) {
        return ServiceLoader.load(ModuleLayer.boot(), type).findFirst();
    }
}
