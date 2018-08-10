/**
 * project: glcmtx
 * author:  kostrovik
 * date:    09/08/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
module startup {
    requires java.logging;
    requires javafx.controls;

    requires kernel;

    exports ru.glance.matrix.startup.common;

    uses com.github.kostrovik.kernel.interfaces.views.ViewEventListenerInterface;
    uses com.github.kostrovik.kernel.interfaces.controls.ControlBuilderFacadeInterface;
    uses com.github.kostrovik.kernel.interfaces.ApplicationLoggerInterface;

    provides com.github.kostrovik.kernel.interfaces.ModuleConfiguratorInterface with ru.glance.matrix.startup.common.Configurator;
}