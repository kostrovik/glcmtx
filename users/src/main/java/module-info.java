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

    exports ru.glance.matrix.users.common;
    exports ru.glance.matrix.users.views;
    exports ru.glance.matrix.users.views.menu;
    exports ru.glance.matrix.users.views.menu.actions;

    opens ru.glance.matrix.users.models;

    uses ru.glance.provider.interfaces.ServerConnectionInterface;
    uses ru.glance.provider.interfaces.ApplicationSettingsInterface;
    uses ru.glance.provider.interfaces.views.ViewEventListenerInterface;
    uses ru.glance.provider.interfaces.controls.ControlBuilderFacadeInterface;

    provides ru.glance.provider.interfaces.ModuleConfiguratorInterface with ru.glance.matrix.users.common.Configurator;
}