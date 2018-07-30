package ru.glance.matrix.provider.interfaces.views;

import ru.glance.matrix.provider.interfaces.views.ViewEventInterface;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    24/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public interface ViewEventListenerInterface {
    void handle(ViewEventInterface event);
}
