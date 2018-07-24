package kernel.builders;

import helper.common.ApplicationLogger;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import provider.interfaces.ContentViewInterface;
import provider.interfaces.MenuBuilderInterface;

import java.util.EventObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    21/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
final public class SceneFactory {
    private static Logger logger = ApplicationLogger.getLogger(SceneFactory.class.getName());

    /**
     * Рефлективно внедряетмя при создании приложения. Когда в kernel.common.AppCore при вызове start() появляется
     * объект основного Stage приложения.
     */
    private static Stage mainWindow;

    private static volatile SceneFactory factory;
    private static Map<String, ContentViewInterface> storage;
    private static ModulesConfigBuilder config;

    private SceneFactory() {
        storage = new ConcurrentHashMap<>();
        config = ModulesConfigBuilder.getInstance();
    }

    public static SceneFactory getInstance() {
        if (factory == null) {
            synchronized (SceneFactory.class) {
                if (factory == null) {
                    factory = new SceneFactory();
                }
            }
        }
        return factory;
    }

    public void initScene(String moduleName, String viewName, EventObject event) {
        Scene scene = getSceneTemplate();
        Pane content = (Pane) scene.lookup("#scene-content");

        ContentViewInterface contentView;
        if (storage.containsKey(moduleName + "_" + viewName)) {
            contentView = storage.get(moduleName + "_" + viewName);
            contentView.initView(event);
        } else {
            contentView = createView(moduleName, viewName, event, content);
        }


        if (contentView != null && contentView.getView() != null) {
            content.getChildren().setAll(contentView.getView());
        } else {
            content.getChildren().setAll(errorCreateScene(content));
        }

        mainWindow.setScene(scene);
    }

    private ContentViewInterface createView(String moduleName, String viewName, EventObject event, Pane content) {
        return null;
    }

    private Scene getSceneTemplate() {
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox);

        Pane content = new Pane();
        content.setId("scene-content");
        vbox.getChildren().addAll(getSceneMenu(), content);

        setBackground(content);

        content.prefWidthProperty().bind(vbox.widthProperty());
        content.prefHeightProperty().bind(vbox.heightProperty());

        try {
            scene.getStylesheets().add(Class.forName(this.getClass().getName()).getResource("/styles/themes/admin-theme.css").toExternalForm());
        } catch (ClassNotFoundException error) {
            logger.log(Level.WARNING, "Ошибка загрузки стилей.", error);
        }

        return scene;
    }

    private MenuBar getSceneMenu() {
        MenuBar menuBar = new MenuBar();
        menuBar.setPadding(new Insets(0, 0, 0, 0));

        for (String module : config.moduleKeys()) {
            MenuBuilderInterface menu = config.getConfigForModule(module).getMenuBuilder();
            List<MenuItem> menuItems = menu.getMenuList();

            Menu addDataMenu = new Menu(menu.getModuleMenuName());
            addDataMenu.getItems().addAll(menuItems);

            menuBar.getMenus().add(addDataMenu);
        }

        return menuBar;
    }

    private Region errorCreateScene(Pane parent) {
        ScrollPane view = new ScrollPane();
        setBackground(view);

        view.prefWidthProperty().bind(parent.widthProperty());
        view.prefHeightProperty().bind(parent.heightProperty());

        Text value = new Text();
        value.setFill(Color.ORANGE);
        value.setText("Не возможно создать страницу.");
        value.setFont(Font.font(18));

        StackPane textHolder = new StackPane(value);
        ScrollBar scrollBar = new ScrollBar();

        textHolder.prefWidthProperty().bind(parent.widthProperty().subtract(scrollBar.getWidth()));
        textHolder.prefHeightProperty().bind(parent.heightProperty().subtract(scrollBar.getWidth()));

        view.setContent(textHolder);

        view.viewportBoundsProperty().addListener((arg0, arg1, arg2) -> {
            Node content = view.getContent();
            view.setFitToWidth(content.prefWidth(-1) < arg2.getWidth());
            view.setFitToHeight(content.prefHeight(-1) < arg2.getHeight());
        });

        return view;
    }

    private void setBackground(Region container) {
        container.setBackground(Background.EMPTY);
    }
}