package com.github.kostrovik.kernel.common;

import javafx.scene.layout.Pane;
import provider.interfaces.ContentViewInterface;
import provider.interfaces.EventListenerInterface;
import provider.interfaces.MenuBuilderInterface;
import provider.interfaces.ModuleConfiguratorInterface;
import com.github.kostrovik.kernel.views.menu.MenuBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    23/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class Configurator implements ModuleConfiguratorInterface {
    @Override
    public MenuBuilderInterface getMenuBuilder(EventListenerInterface listener) {
        return new MenuBuilder(listener);
    }

    @Override
    public Map<String, ContentViewInterface> getViewEvents(Pane content) {
        return new HashMap<>();
    }
}