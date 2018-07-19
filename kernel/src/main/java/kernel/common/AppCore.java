package kernel.common;

import helper.common.ApplicationLogger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    18/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class AppCore extends Application {
    private static Logger logger = ApplicationLogger.getLogger(AppCore.class.getName());
    private static Preloader preloader;
    private static Stage mainStage;

    private static void initScene() {
        Platform.runLater(() -> mainStage.show());
    }

    public static void main(String[] args) {
        preloader = new ApplicationPreloader(event -> {
            Object source = event.getSource();
            if (source instanceof Map) {
                if (!((Map) source).get("login").equals("test")) {
                    preloader.handleErrorNotification(new Preloader.ErrorNotification(String.format("class: %s, method: %s", AppCore.class.getName(), "main()"), "Не верные данные пользователя.", new Exception()));
                } else {
                    preloader.handleStateChangeNotification(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));
                    initScene();
                }
            } else {
                preloader.handleStateChangeNotification(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));
                initScene();
            }
        });
        Platform.runLater(() -> {
            try {
                Stage primaryStage = new Stage();
                preloader.start(primaryStage);
            } catch (Exception error) {
                logger.log(Level.SEVERE, "Ошибка запуска preloader.", error);
            }
        });

        launch(args);
    }

    public void start(Stage mainWindow) {
        logger.info("Запуск приложения.");

        mainStage = mainWindow;
        setStageSize(mainWindow);

        // Завершение приложения при закрытии окна.
        mainWindow.setOnHidden(event -> System.exit(0));
    }

    /**
     * Устанавливает размеры главного окна на всю ширину и высоту экрана
     *
     * @param stage Объект главного окна приложения
     */
    private void setStageSize(Stage stage) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
    }
}