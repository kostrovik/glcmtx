/**
 * project: glcmtx
 * author:  kostrovik
 * date:    18/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
module kernel {
    requires javafx.graphics;
    requires javafx.controls;
    requires java.logging;

    requires helper;
    requires graphics;
    requires provider;

    exports com.github.kostrovik.kernel.common;

    opens com.github.kostrovik.kernel.models;

    uses ru.glance.matrix.provider.interfaces.ModuleConfiguratorInterface;
    uses ru.glance.matrix.provider.interfaces.views.ViewEventListenerInterface;
    uses ru.glance.matrix.provider.interfaces.controls.ControlBuilderFacadeInterface;

    provides ru.glance.matrix.provider.interfaces.ModuleConfiguratorInterface with com.github.kostrovik.kernel.common.Configurator;
    provides ru.glance.matrix.provider.interfaces.ServerConnectionInterface with com.github.kostrovik.kernel.common.ServerConnector;
    provides ru.glance.matrix.provider.interfaces.ApplicationSettingsInterface with com.github.kostrovik.kernel.settings.ApplicationSettings;
    provides ru.glance.matrix.provider.interfaces.views.ViewEventListenerInterface with com.github.kostrovik.kernel.builders.SceneFactory;
}