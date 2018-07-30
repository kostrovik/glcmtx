package ru.glance.matrix.provider.interfaces;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.glance.matrix.provider.interfaces.controls.ControlBuilderFacadeInterface;
import ru.glance.matrix.provider.interfaces.views.ContentViewInterface;
import ru.glance.matrix.provider.interfaces.views.MenuBuilderInterface;
import ru.glance.matrix.provider.interfaces.views.ViewEventListenerInterface;

import java.util.Map;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    24/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public interface ModuleConfiguratorInterface {
    MenuBuilderInterface getMenuBuilder();

    Map<String, ContentViewInterface> getViewEvents(Pane content, Stage stage);

    ViewEventListenerInterface getEventListener();

    ControlBuilderFacadeInterface getControlBuilder();
}
