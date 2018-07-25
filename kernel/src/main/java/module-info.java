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
    requires users;

    exports com.github.kostrovik.kernel.common;

    uses provider.interfaces.ModuleConfiguratorInterface;
    provides provider.interfaces.ModuleConfiguratorInterface with com.github.kostrovik.kernel.common.Configurator;
}