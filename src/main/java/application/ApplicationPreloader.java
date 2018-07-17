package application;

import application.interfaces.EventListenerInterface;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    17/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class ApplicationPreloader extends Preloader {
    private Stage stage;
    private EventListenerInterface consumer;
    private String login;
    private String password;
    private TextField userLogin;
    private PasswordField userPassword;
    private Button button;
    private Label notifications;

    public ApplicationPreloader(EventListenerInterface consumer) {
        this.consumer = consumer;
    }

    private Scene createLoginScene() {
        VBox loginForm = new VBox(10);
        loginForm.setPadding(new Insets(10, 10, 10, 10));

        userLogin = new TextField();
        userLogin.setPromptText("login");
        loginForm.getChildren().add(userLogin);
        userLogin.addEventFilter(KeyEvent.KEY_RELEASED, event -> notifications.setText(null));

        userPassword = new PasswordField();
        userPassword.setPromptText("password");
        loginForm.getChildren().add(userPassword);
        userPassword.addEventFilter(KeyEvent.KEY_RELEASED, event -> notifications.setText(null));

        button = new Button("Войти");
        button.setOnAction(t -> {
            login = userLogin.getText();
            password = userPassword.getText();
            disableForm(true);
            checkForm();
        });
        loginForm.getChildren().add(button);

        notifications = new Label(null);
        loginForm.getChildren().add(notifications);

        return new Scene(loginForm, 600, 400);
    }

    private void disableForm(boolean isDisabled) {
        if (isDisabled) {
            userLogin.setEditable(false);
            userPassword.setEditable(false);
        } else {
            userLogin.setEditable(true);
            userPassword.setEditable(true);
        }
        button.setDisable(isDisabled);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setScene(createLoginScene());
        stage.show();
    }

    private void checkForm() {
        if (login.trim().isEmpty()) {
            notifications.setText("Необходимо заполнить логин.");
        }

        if (stage.isShowing() && consumer != null) {
            Map<String, String> userAuthForm = new HashMap<>();
            userAuthForm.put("login", login);
            userAuthForm.put("password", password);
            consumer.handle(new EventObject(userAuthForm));
        }
    }

    @Override
    public boolean handleErrorNotification(ErrorNotification errorNotification) {
        notifications.setText(errorNotification.getDetails());
        disableForm(false);
        return true;
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification state) {
        if (state.getType() == StateChangeNotification.Type.BEFORE_START) {
            Platform.runLater(() -> stage.hide());
        }
    }
}
