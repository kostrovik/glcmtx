package com.github.kostrovik.kernel.common;

import javafx.scene.layout.Pane;
import ru.glance.matrix.provider.interfaces.views.ContentViewInterface;
import ru.glance.matrix.provider.interfaces.EventListenerInterface;
import ru.glance.matrix.provider.interfaces.views.MenuBuilderInterface;
import ru.glance.matrix.provider.interfaces.ModuleConfiguratorInterface;
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
