package kernel.common;

import common.ControlBuilderFacade;
import helper.common.ApplicationLogger;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kernel.interfaces.EventListenerInterface;

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
    private String login;
    private String password;
    private TextField userLogin;
    private PasswordField userPassword;
    private Button enterButton;
    private Label notifications;

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
        userLogin.addEventFilter(KeyEvent.KEY_RELEASED, event -> notifications.setText(null));

        userPassword = new PasswordField();
        userPassword.setPromptText("password");
        loginForm.getChildren().add(userPassword);
        userPassword.addEventFilter(KeyEvent.KEY_RELEASED, event -> notifications.setText(null));

        enterButton = facade.createButton("Войти");
        enterButton.setOnAction(t -> {
            login = userLogin.getText();
            password = userPassword.getText();
            disableForm(true);
            checkForm();
        });

        Button exitButton = facade.createButton("Отмена");
        exitButton.setOnAction(t -> System.exit(0));

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(enterButton, exitButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        loginForm.getChildren().add(buttons);

        notifications = new Label(null);
        loginForm.getChildren().add(notifications);

        pane.setId("preloader");
        pane.getChildren().addAll(loginForm);
        AnchorPane.setRightAnchor(loginForm, 20.0);
        AnchorPane.setTopAnchor(loginForm, 20.0);

        Scene preloader = new Scene(pane, 600, 400);


        try {
            preloader.getStylesheets().add(Class.forName(this.getClass().getName()).getResource("/styles/preloader.css").toExternalForm());
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
    public boolean handleErrorNotification(Preloader.ErrorNotification errorNotification) {
        notifications.setText(errorNotification.getDetails());
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
