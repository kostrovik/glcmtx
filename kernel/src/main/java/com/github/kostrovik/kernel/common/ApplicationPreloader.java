package com.github.kostrovik.kernel.common;

import ru.glance.matrix.graphics.common.ControlBuilderFacade;
import ru.glance.matrix.graphics.controls.notification.Notification;
import ru.glance.matrix.graphics.controls.notification.NotificationType;
import helper.common.ApplicationLogger;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.github.kostrovik.kernel.interfaces.EventListenerInterface;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    18/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class ApplicationPreloader extends Preloader {
    private static Logger logger = ApplicationLogger.getLogger(ApplicationPreloader.class.getName());

    private Stage stage;
    private EventListenerInterface consumer;
    private TextField userLogin;
    private PasswordField userPassword;
    private Button enterButton;
    private Notification formNotification;

    public ApplicationPreloader(EventListenerInterface consumer) {
        this.consumer = consumer;
    }

    private Scene createLoginScene() {
        ControlBuilderFacade facade = new ControlBuilderFacade();

        AnchorPane pane = new AnchorPane();

        VBox loginForm = new VBox(10);
        loginForm.setPadding(new Insets(10, 10, 10, 10));
        loginForm.setPrefWidth(300);

        userLogin = new TextField();
        userLogin.setPromptText("login");
        loginForm.getChildren().add(userLogin);
        userLogin.addEventFilter(KeyEvent.KEY_RELEASED, event -> formNotification.setIsVisible(false));

        userPassword = new PasswordField();
        userPassword.setPromptText("password");
        loginForm.getChildren().add(userPassword);
        userPassword.addEventFilter(KeyEvent.KEY_RELEASED, event -> formNotification.setIsVisible(false));

        enterButton = facade.createButton("Войти");
        enterButton.setOnAction(t -> {
            disableForm(true);
            checkForm();
        });

        Button exitButton = facade.createButton("Отмена");
        exitButton.setOnAction(t -> System.exit(0));

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(enterButton, exitButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        loginForm.getChildren().add(buttons);

        formNotification = facade.createFormNotification();

        loginForm.getChildren().add(formNotification);

        loginForm.addEventHandler(KeyEvent.KEY_RELEASED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                enterButton.fire();
                ev.consume();
            }
        });

        pane.getChildren().addAll(loginForm);
        AnchorPane.setRightAnchor(loginForm, 20.0);
        AnchorPane.setTopAnchor(loginForm, 20.0);

        Scene preloader = new Scene(pane, 600, 400);

        try {
            preloader.getStylesheets().add(Class.forName(this.getClass().getName()).getResource("/styles/preloader.css").toExternalForm());
            preloader.getStylesheets().add(Class.forName(this.getClass().getName()).getResource("/styles/themes/admin-theme.css").toExternalForm());
        } catch (ClassNotFoundException error) {
            logger.log(Level.WARNING, "Ошибка загрузки изображения для preloader.", error);
        }

        return preloader;
    }

    private void disableForm(boolean isDisabled) {
        if (isDisabled) {
            userLogin.setEditable(false);
            userPassword.setEditable(false);
        } else {
            userLogin.setEditable(true);
            userPassword.setEditable(true);
        }
        enterButton.setDisable(isDisabled);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setScene(createLoginScene());

        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }

    private void checkForm() {
        if (userLogin.getText().trim().isEmpty()) {
            String location = String.format("class: %s, method: %s", this.getClass().getName(), "checkForm()");
            handleErrorNotification(new Preloader.ErrorNotification(location, "Необходимо заполнить логин.", new Exception()));
        } else if (consumer != null) {
            Map<String, String> userAuthForm = new HashMap<>();
            userAuthForm.put("login", userLogin.getText());
            userAuthForm.put("password", userPassword.getText());
            consumer.handle(new EventObject(userAuthForm));
        }
    }

    @Override
    public boolean handleErrorNotification(Preloader.ErrorNotification errorNotification) {
        formNotification.setMessage(errorNotification.getDetails());
        formNotification.setType(NotificationType.ERROR);

        disableForm(false);
        return true;
    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification state) {
        if (state.getType() == Preloader.StateChangeNotification.Type.BEFORE_START) {
            Platform.runLater(() -> stage.hide());
        }
    }
}
