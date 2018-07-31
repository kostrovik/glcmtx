package com.github.kostrovik.kernel.interfaces;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.github.kostrovik.kernel.interfaces.controls.ControlBuilderFacadeInterface;
import com.github.kostrovik.kernel.interfaces.views.ContentViewInterface;
import com.github.kostrovik.kernel.interfaces.views.MenuBuilderInterface;
import com.github.kostrovik.kernel.interfaces.views.ViewEventListenerInterface;

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
