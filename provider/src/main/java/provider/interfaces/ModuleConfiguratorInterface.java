package provider.interfaces;

import javafx.scene.layout.Pane;

import java.util.Map;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    24/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public interface ModuleConfiguratorInterface {
    MenuBuilderInterface getMenuBuilder(EventListenerInterface listener);

    Map<String, ContentViewInterface> getViewEvents(Pane content);
}
