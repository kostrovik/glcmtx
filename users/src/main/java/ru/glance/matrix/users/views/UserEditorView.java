package ru.glance.matrix.users.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.glance.matrix.graphics.common.ControlBuilderFacade;
import ru.glance.matrix.helper.common.ApplicationLogger;
import ru.glance.matrix.provider.interfaces.views.PopupWindowInterface;
import ru.glance.matrix.users.models.User;

import java.util.EventObject;
import java.util.logging.Logger;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    27/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class UserEditorView implements PopupWindowInterface {
    private static Logger logger = ApplicationLogger.getLogger(UserEditorView.class.getName());

    private ControlBuilderFacade facade;
    private Stage stage;
    private Pane parent;
    private User user;

    public UserEditorView(Pane parent, Stage stage) {
        this.facade = new ControlBuilderFacade();
        this.stage = stage;
        this.parent = parent;
    }

    @Override
    public void initView(EventObject event) {
        user = (User) event.getSource();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Region getView() {
        VBox view = new VBox(10);
        view.setPadding(new Insets(10, 10, 10, 10));

        view.prefWidthProperty().bind(parent.widthProperty());
        view.prefHeightProperty().bind(parent.heightProperty());

        view.getChildren().setAll(viewTitle(), viewButtons());

        return view;
    }

    private Region viewTitle() {
        Text title = new Text("Редактирование данных пользователя.");
        title.getStyleClass().add("view-title");

        HBox titleView = new HBox(10);
        titleView.setPadding(new Insets(10, 10, 10, 10));
        titleView.getChildren().addAll(title);

        return titleView;
    }

    private Region viewButtons() {
        Button saveButton = facade.createButton("Сохранить");
        Button cancelButton = facade.createButton("Отмена");

        saveButton.setOnAction(event -> {
//            settings.saveHostsList(data);
//            data.setAll(settings.getHosts());
            stage.close();
        });

        cancelButton.setOnAction(event -> stage.close());

        HBox buttonView = new HBox(10);
        buttonView.setPadding(new Insets(10, 10, 10, 10));
        buttonView.getChildren().addAll(saveButton, cancelButton);
        buttonView.setAlignment(Pos.CENTER_RIGHT);

        return buttonView;
    }
}
