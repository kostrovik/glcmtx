package ru.glance.matrix.provider.interfaces;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    24/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public interface ModuleEventInterface {
    String getModuleName();

    String getEventType();

    Object getEventData();
}
