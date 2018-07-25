package views.menu;

import helper.common.ApplicationLogger;
import helper.common.ConfigParser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import provider.interfaces.EventListenerInterface;
import provider.interfaces.MenuBuilderInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    23/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class MenuBuilder implements MenuBuilderInterface {
    private static Logger logger = ApplicationLogger.getLogger(MenuBuilder.class.getName());
    private final static String defaultConfigFilePath = "/configurations/menu_config.properties";
    private ConfigParser parser;
    private EventListenerInterface listener;

    public MenuBuilder(EventListenerInterface listener) {
        this.parser = new ConfigParser(loadConfig());
        this.listener = listener;
    }

    @Override
    public List<MenuItem> getMenuList() {
        Object menuList = parser.getConfigProperty("items");
        List<MenuItem> menuItems = new ArrayList<>();

        if (menuList != null && menuList instanceof Map) {
            for (Object menuKey : ((Map) menuList).keySet()) {
                Map menuObject = (Map) ((Map) menuList).get(menuKey);
                MenuItem item = new MenuItem((String) menuObject.get("title"));
                item.setOnAction(prepareAction((String) menuObject.get("action")));
                menuItems.add(item);
            }
        }

        return menuItems;
    }

    @Override
    public String getModuleMenuName() {
        return "Главное";
    }

    private EventHandler<ActionEvent> prepareAction(String actionClassName) {
        EventHandler<ActionEvent> action = null;
        Class<?> actionClass;
        try {
            actionClass = Class.forName(actionClassName);
            Constructor<?> constructor = actionClass.getDeclaredConstructor(EventListenerInterface.class);
            action = (EventHandler<ActionEvent>) constructor.newInstance(listener);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, String.format("Для пункта меню не найден класс action %s.", actionClassName), e);
        } catch (NoSuchMethodException e) {
            logger.log(Level.SEVERE, "Не задан конструктор для action с необходимымой сигнатурой getDeclaredConstructor().", e);
        } catch (IllegalAccessException e) {
            logger.log(Level.SEVERE, "Конструктор для action не доступен.", e);
        } catch (InstantiationException | InvocationTargetException e) {
            logger.log(Level.SEVERE, String.format("Не возможно создать объект action %s.", actionClassName), e);
        }

        return action;
    }

    private Properties loadConfig() {
        Properties config = new Properties();

        try (InputStream inputStream = Class.forName(this.getClass().getName()).getResourceAsStream(defaultConfigFilePath)) {
            config.load(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        } catch (ClassNotFoundException error) {
            logger.log(Level.SEVERE, "Не возможно найти класс", error);
        } catch (FileNotFoundException error) {
            logger.log(Level.SEVERE, String.format("Модуль: %s. Не найден файл конфигурации по умолчанию: %s.", this.getClass().getModule().getName(), defaultConfigFilePath), error);
        } catch (IOException error) {
            logger.log(Level.SEVERE, String.format("Модуль: %s. Не возможно загрузить настройки умолчанию: %s.", this.getClass().getModule().getName(), defaultConfigFilePath), error);
        }

        return config;
    }
}
