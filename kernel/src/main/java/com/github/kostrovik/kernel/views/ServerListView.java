package com.github.kostrovik.kernel.views;

import com.github.kostrovik.kernel.models.ServerConnectionAddress;
import com.github.kostrovik.kernel.settings.ApplicationSettings;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import ru.glance.matrix.graphics.common.ControlBuilderFacade;
import ru.glance.matrix.graphics.controls.field.LabeledTextField;
import ru.glance.helper.common.ApplicationLogger;
import ru.glance.provider.interfaces.ServerConnectionAddressInterface;
import ru.glance.provider.interfaces.views.PopupWindowInterface;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.EventObject;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    26/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class ServerListView implements PopupWindowInterface {
    private static Logger logger = ApplicationLogger.getLogger(ServerListView.class.getName());

    private ObservableList<ServerConnectionAddressInterface> data;
    private ControlBuilderFacade facade;
    private TableView<ServerConnectionAddressInterface> table;
    private Stage stage;
    private ApplicationSettings settings;
    private Pane parent;
    private VBox view;

    public ServerListView(Pane parent, Stage stage) {
        this.data = FXCollections.observableArrayList();
        this.facade = new ControlBuilderFacade();
        this.table = new TableView<>();
        this.stage = stage;
        this.settings = ApplicationSettings.getInstance();
        this.parent = parent;

        this.view = createView();
    }

    @Override
    public void initView(EventObject event) {
        data.setAll((Collection<? extends ServerConnectionAddress>) event.getSource());
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Region getView() {
        return view;
    }

    private VBox createView() {
        VBox view = new VBox(10);
        view.setPadding(new Insets(10, 10, 10, 10));

        view.prefWidthProperty().bind(parent.widthProperty());
        view.prefHeightProperty().bind(parent.heightProperty());

        TableView table = createTable();
        table.prefHeightProperty().bind(view.heightProperty());

        view.getChildren().setAll(viewTitle(), table, addForm(), viewButtons());

        return view;
    }

    private Region viewTitle() {
        Text title = new Text("Выбор сервера приложения.");
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
            settings.saveHostsList(data);
            data.setAll(settings.getHosts());
            stage.close();
        });

        cancelButton.setOnAction(event -> stage.close());

        HBox buttonView = new HBox(10);
        buttonView.setPadding(new Insets(10, 10, 10, 10));
        buttonView.getChildren().addAll(saveButton, cancelButton);
        buttonView.setAlignment(Pos.CENTER_RIGHT);

        return buttonView;
    }

    private Region addForm() {
        Button addButton = facade.createButton("Добавить");
        addButton.setCancelButton(true);

        LabeledTextField newHost = facade.createTextField("URL адрес");
        newHost.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                addButton.setCancelButton(newValue.trim().isEmpty());
            }
        });
        newHost.setMinWidth(200);

        addButton.setOnAction(event -> {
            if (!addButton.isCancelButton()) {
                ServerConnectionAddress newAddress = new ServerConnectionAddress(newHost.getText());
                data.add(newAddress);
            }
        });

        HBox formView = new HBox(10);
        formView.setPadding(new Insets(10, 10, 10, 10));
        formView.getChildren().addAll(newHost, addButton);
        formView.setAlignment(Pos.CENTER_LEFT);

        newHost.prefWidthProperty().bind(formView.widthProperty().divide(2));

        return formView;
    }

    private TableView createTable() {
        table.setEditable(true);
        table.setSelectionModel(null);

        TableColumn<ServerConnectionAddressInterface, String> url = facade.createTableStringColumn("URL сервера", "url");
        TableColumn<ServerConnectionAddressInterface, LocalDateTime> lastUsage = facade.createTableLocalDateTimeColumn("Последнее соединение", "lastUsage", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        TableColumn<ServerConnectionAddressInterface, Boolean> isDefault = facade.createTableBooleanColumn("Установлен по умолчанию", "default");

        isDefault.setCellValueFactory(param -> {
            ServerConnectionAddressInterface value = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(value.isDefault());
            booleanProp.addListener((observable, oldValue, newValue) -> {
                clearAllSelectioins();
                value.setDefault(newValue);
            });
            return booleanProp;
        });

        TableColumn<ServerConnectionAddressInterface, ServerConnectionAddressInterface> action = new TableColumn<>("Действие");
        Text actionColumnName = new Text(action.getText());
        actionColumnName.applyCss();
        action.setMinWidth(actionColumnName.getBoundsInLocal().getWidth() + 10);
        action.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ServerConnectionAddressInterface, ServerConnectionAddressInterface> call(TableColumn<ServerConnectionAddressInterface, ServerConnectionAddressInterface> param) {
                TableCell<ServerConnectionAddressInterface, ServerConnectionAddressInterface> cell = new TableCell<>() {
                    Button actionButton = facade.createButton("Удалить");

                    @Override
                    public void updateItem(ServerConnectionAddressInterface item, boolean empty) {
                        super.updateItem(item, empty);
                        if (isEmpty()) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            actionButton.setOnAction(event -> removeItem(table.getItems().get(getIndex())));
                            setGraphic(actionButton);
                            Platform.runLater(() -> {
                                if (action.getMinWidth() < actionButton.getBoundsInLocal().getWidth() + 10) {
                                    action.setMinWidth(actionButton.getBoundsInLocal().getWidth() + 10);
                                }
                            });
                            setText(null);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER_RIGHT);
                return cell;
            }
        });


        table.setItems(data);

        table.getColumns().addAll(url, lastUsage, isDefault, action);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

    private void removeItem(ServerConnectionAddressInterface item) {
        data.remove(item);
    }

    private void clearAllSelectioins() {
        data.setAll(data.stream().peek(item -> item.setDefault(false)).collect(Collectors.toList()));
    }
}
