package ru.glance.matrix.graphics.common;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.glance.matrix.graphics.builders.ButtonBuilder;
import ru.glance.matrix.graphics.builders.TableColumnBuilder;
import ru.glance.matrix.graphics.builders.TableFormBuilder;
import ru.glance.matrix.graphics.common.icons.SolidIcons;
import ru.glance.matrix.graphics.controls.field.LabeledTextField;
import ru.glance.matrix.graphics.controls.notification.Notification;
import ru.glance.provider.interfaces.controls.ControlBuilderFacadeInterface;
import ru.glance.provider.interfaces.controls.IconInterface;
import ru.glance.provider.interfaces.controls.IconPositionInterface;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс представляющий фасад к различным билдерам контролов.
 * <p>
 * project: glcmtx
 * author:  kostrovik
 * date:    20/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class ControlBuilderFacade implements ControlBuilderFacadeInterface {
    public Button createButton(String buttonTitle) {
        return createButton(buttonTitle, null);
    }

    public Button createButton(String buttonTitle, IconInterface icon) {
        ButtonBuilder builder = new ButtonBuilder();
        if (icon == null) {
            return builder.createButton(buttonTitle);
        }

        return builder.createButton((SolidIcons) icon, buttonTitle);
    }

    public Button createButton(String buttonTitle, IconInterface icon, IconPositionInterface iconPosition) {
        ButtonBuilder builder = new ButtonBuilder();
        Button button = builder.createButton((SolidIcons) icon, buttonTitle);
        builder.setIconPosition(button, (ButtonIconPosition) iconPosition);

        return button;
    }

    public Notification createFormNotification() {
        return new Notification();
    }

    public LabeledTextField createTextField(String labelValue) {
        return new LabeledTextField(labelValue);
    }

    public LabeledTextField createPasswordField(String labelValue) {
        return new LabeledTextField(labelValue, true);
    }

    public <E, V> TableColumn<E, V> createTableColumn(String columnName) {
        TableColumnBuilder<E, V> builder = new TableColumnBuilder<>();
        return builder.createColumn(columnName);
    }

    public <E> TableColumn<E, String> createTableStringColumn(String columnName, String propertyName) {
        TableColumnBuilder<E, String> builder = new TableColumnBuilder<>();
        return builder.createStringValueColumn(columnName, propertyName);
    }

    public <E> TableColumn<E, String> createTableMultilineColumn(String columnName, String propertyName) {
        TableColumnBuilder<E, String> builder = new TableColumnBuilder<>();
        return builder.createMultilineStringValueColumn(columnName, propertyName);
    }

    public <E> TableColumn<E, Boolean> createTableBooleanColumn(String columnName, String propertyName) {
        TableColumnBuilder<E, Boolean> builder = new TableColumnBuilder<>();
        return builder.createBooleanValueColumn(columnName, propertyName);
    }

    public <E> TableColumn<E, LocalDateTime> createTableLocalDateTimeColumn(String columnName, String propertyName, DateTimeFormatter formatter) {
        TableColumnBuilder<E, LocalDateTime> builder = new TableColumnBuilder<>();
        return builder.createLocalDateTimeValueColumn(columnName, propertyName, formatter);
    }

    @Override
    public GridPane createTableFormLayout() {
        TableFormBuilder builder = new TableFormBuilder();
        return builder.createLayout();
    }

    @Override
    public TextField addTextField(GridPane formLayout, String label) {
        TableFormBuilder builder = new TableFormBuilder();
        return builder.createFormTextField(formLayout, label, false);
    }

    @Override
    public TextField addPasswordField(GridPane formLayout, String label) {
        TableFormBuilder builder = new TableFormBuilder();
        return builder.createFormTextField(formLayout, label, true);
    }
}
