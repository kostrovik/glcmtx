package ru.glance.matrix.users.common;

import com.github.kostrovik.kernel.interfaces.ApplicationLoggerInterface;
import com.github.kostrovik.kernel.interfaces.ModuleConfiguratorInterface;
import com.github.kostrovik.kernel.interfaces.controls.ControlBuilderFacadeInterface;
import com.github.kostrovik.kernel.interfaces.views.ContentViewInterface;
import com.github.kostrovik.kernel.interfaces.views.MenuBuilderInterface;
import com.github.kostrovik.kernel.interfaces.views.ViewEventListenerInterface;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
final public class Configurator implements ModuleConfiguratorInterface {
    private static Logger logger;
    private static volatile Configurator configurator;
    private static Map<String, ContentViewInterface> views;

    private Configurator() {
        views = new HashMap<>();
        logger = getLogger(Configurator.class.getName());
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
    public Map<String, ContentViewInterface> getModuleViews(Pane content, Stage stage) {
        if (views.isEmpty()) {
            synchronized (Configurator.class) {
                if (views.isEmpty()) {
                    views.put(ViewTypeDictionary.USERS_LIST.name(), new UsersListView(content));
                    views.put(ViewTypeDictionary.USER_VIEW.name(), new UserEditorView(content, stage));
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

    @Override
    public Logger getLogger(String className) {
        Optional<ApplicationLoggerInterface> applicationLogger = getFirstLoadedImplementation(ApplicationLoggerInterface.class);

        return applicationLogger.map(applicationLoggerInterface -> applicationLoggerInterface.getLogger(className)).orElse(Logger.getLogger(className));
    }
}
