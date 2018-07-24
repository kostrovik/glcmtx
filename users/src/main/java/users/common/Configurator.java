package users.common;

import provider.interfaces.MenuBuilderInterface;
import provider.interfaces.ModuleConfiguratorInterface;
import users.views.menu.MenuBuilder;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    23/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class Configurator implements ModuleConfiguratorInterface {
    @Override
    public MenuBuilderInterface getMenuBuilder() {
        return new MenuBuilder();
    }
}
