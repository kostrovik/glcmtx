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

    exports ru.glance.matrix.graphics.common;
    exports ru.glance.matrix.graphics.common.icons;
    exports ru.glance.matrix.graphics.controls.notification;
    exports ru.glance.matrix.graphics.controls.field;
}