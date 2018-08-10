/**
 * project: glcmtx
 * author:  kostrovik
 * date:    19/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
module helper {
    requires java.logging;
    requires kernel;

    exports ru.glance.helper.common;

    provides com.github.kostrovik.kernel.interfaces.ApplicationLoggerInterface with ru.glance.helper.common.ApplicationLogger;
}