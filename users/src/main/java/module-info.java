/**
 * project: glcmtx
 * author:  kostrovik
 * date:    22/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
module users {
    requires java.logging;
    requires javafx.controls;

    requires helper;
    requires provider;
    requires graphics;

    exports users.common;
    exports users.views;
    exports users.views.menu;
    exports users.views.menu.actions;

    opens users.models;

    provides ru.glance.matrix.provider.interfaces.ModuleConfiguratorInterface with users.common.Configurator;
}