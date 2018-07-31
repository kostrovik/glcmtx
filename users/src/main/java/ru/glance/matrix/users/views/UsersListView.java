package ru.glance.matrix.users.views;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import com.github.kostrovik.kernel.graphics.common.ControlBuilderFacade;
import ru.glance.helper.common.ApplicationLogger;
import com.github.kostrovik.kernel.interfaces.views.ContentViewInterface;
import com.github.kostrovik.kernel.interfaces.views.LayoutType;
import com.github.kostrovik.kernel.interfaces.views.ViewEventInterface;
import com.github.kostrovik.kernel.interfaces.views.ViewEventListenerInterface;
import ru.glance.matrix.users.common.Configurator;
import ru.glance.matrix.users.dictionaries.ViewTypeDictionary;
import ru.glance.matrix.users.models.User;
import ru.glance.matrix.users.models.UserRole;

import java.util.Collection;
import java.util.EventObject;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    24/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class UsersListView implements ContentViewInterface {
    private static Logger logger = ApplicationLogger.getLogger(UsersListView.class.getName());
    private Pane parent;
    private ObservableList<User> data;
    private ControlBuilderFacade facade;

    public UsersListView(Pane parent) {
        this.parent = parent;
        this.data = FXCollections.observableArrayList();
        this.facade = new ControlBuilderFacade();
    }

    @Override
    public void initView(EventObject event) {
        data.addAll((Collection<? extends User>) event.getSource());
    }

    @Override
    public Region getView() {
        VBox view = new VBox();

        view.prefWidthProperty().bind(parent.widthProperty());
        view.prefHeightProperty().bind(parent.heightProperty());

        TableView table = createTable();

        view.getChildren().setAll(getFilter(), table);

        return view;
    }

    private HBox getFilter() {
        HBox filterView = new HBox(10);
        filterView.setPadding(new Insets(10, 10, 10, 10));

        filterView.getChildren().add(facade.createTextField("Имя"));

        return filterView;
    }

    private TableView createTable() {
        TableView<User> table = new TableView<>();
        table.setEditable(false);

        TableColumn<User, String> id = facade.createTableStringColumn("Идентификатор", "id");

        TableColumn<User, String> name = facade.createTableStringColumn("ФИО", "fullName");

        TableColumn<User, String> roles = facade.createTableColumn("Роли");

        roles.setCellValueFactory(param -> {
            User value = param.getValue();
            TableColumn column = param.getTableColumn();

            String data;
            if (value.getRoles().isEmpty()) {
                data = "";
            } else {
                data = String.join("\n", value.getRoles().stream().map(UserRole::getRoleTitle).collect(Collectors.toList()));
            }

            Text text = new Text(data);
            text.applyCss();
            if (column.getWidth() < text.getBoundsInLocal().getWidth() + 10) {
                column.setMinWidth(text.getBoundsInLocal().getWidth() + 10);
            }

            return new ReadOnlyObjectWrapper<>(data);
        });

        TableColumn<User, String> email = facade.createTableStringColumn("E-mail", "email");

        TableColumn<User, Boolean> isPhysicalPerson = facade.createTableBooleanColumn("Физ. лицо", "physicalPerson");

        TableColumn<User, String> comment = facade.createTableMultilineColumn("Комментарий", "comment");

        table.setItems(data);
        table.getColumns().addAll(id, name, roles, email, isPhysicalPerson, comment);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        table.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                showUserEditor(table.getSelectionModel().getSelectedItem());

            }
        });

        return table;
    }

    private void showUserEditor(User user) {
        Configurator configurator = Configurator.getConfig();
        ViewEventListenerInterface listener = configurator.getEventListener();
        listener.handle(new ViewEventInterface() {
            @Override
            public String getModuleName() {
                return UsersListView.class.getModule().getName();
            }

            @Override
            public String getViewName() {
                return ViewTypeDictionary.USER_VIEW.name();
            }

            @Override
            public Object getEventData() {
                return user;
            }

            @Override
            public LayoutType getLayoutType() {
                return LayoutType.POPUP;
            }
        });
    }
}
