package ru.glance.matrix.users.views.menu;

import com.github.kostrovik.kernel.common.ConfigParser;
import com.github.kostrovik.kernel.interfaces.views.MenuBuilderInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import ru.glance.matrix.users.common.Configurator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    23/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class MenuBuilder implements MenuBuilderInterface {
    private static Logger logger = Configurator.getConfig().getLogger(MenuBuilder.class.getName());
    private ConfigParser parser;

    public MenuBuilder() {
        this.parser = new ConfigParser(getPath());
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
        return "Пользователи";
    }

    private EventHandler<ActionEvent> prepareAction(String actionClassName) {
        EventHandler<ActionEvent> action = null;
        Class<?> actionClass;
        try {
            actionClass = Class.forName(actionClassName);
            Constructor<?> constructor = actionClass.getDeclaredConstructor();
            action = (EventHandler<ActionEvent>) constructor.newInstance();
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

    private URI getPath() {
        URI applicationConfigPath = null;
        try {
            URL moduleResource = Class.forName(MenuBuilder.class.getName()).getResource("/ru/glance/matrix/users/configurations/menu_config.yaml");
            applicationConfigPath = moduleResource.toURI();
        } catch (URISyntaxException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Ошибка получения настроек.", e);
        }

        return applicationConfigPath;
    }
}
