package ru.glance.matrix.startup.views.menu;

import com.github.kostrovik.kernel.interfaces.views.MenuBuilderInterface;
import javafx.scene.control.MenuItem;
import ru.glance.matrix.startup.common.Configurator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    23/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class MenuBuilder implements MenuBuilderInterface {
    private static Logger logger = Configurator.getConfig().getLogger(MenuBuilder.class.getName());

    @Override
    public List<MenuItem> getMenuList() {
        return new ArrayList<>();
    }

    @Override
    public String getModuleMenuName() {
        return "Startup";
    }
}
