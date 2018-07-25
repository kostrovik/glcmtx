/**
 * project: glcmtx
 * author:  kostrovik
 * date:    20/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
module graphics {
    requires javafx.graphics;
    requires javafx.controls;
    requires java.logging;
    requires helper;

    exports graphics.common;
    exports graphics.common.icons;
    exports graphics.controls.notification;
    exports graphics.controls.field;
}