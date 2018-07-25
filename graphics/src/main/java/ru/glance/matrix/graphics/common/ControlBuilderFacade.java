package ru.glance.matrix.graphics.common;

import ru.glance.matrix.graphics.builders.ButtonBuilder;
import ru.glance.matrix.graphics.builders.TableColumnBuilder;
import ru.glance.matrix.graphics.common.icons.SolidIcons;
import ru.glance.matrix.graphics.controls.field.LabeledTextField;
import ru.glance.matrix.graphics.controls.notification.Notification;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    20/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class ControlBuilderFacade {
    public Button createButton(String buttonTitle) {
        ButtonBuilder builder = new ButtonBuilder();
        return builder.createButton(buttonTitle);
    }

    public Button createButton(String buttonTitle, SolidIcons icon) {
        ButtonBuilder builder = new ButtonBuilder();
        return builder.createButton(icon, buttonTitle);
    }

    public Notification createFormNotification() {
        return new Notification();
    }

    public LabeledTextField createTextField(String labelValue) {
        return new LabeledTextField(labelValue);
    }

    public <E, V> TableColumn<E, V> createTableColumn(String columnName) {
        TableColumnBuilder<E, V> builder = new TableColumnBuilder<>();
        return builder.createColumn(columnName);
    }

    public <E, V> TableColumn<E, V> createTableColumn(String columnName, String propertyName) {
        TableColumnBuilder<E, V> builder = new TableColumnBuilder<>();
        return builder.createStringValueColumn(columnName, propertyName);
    }

    public <E, V> TableColumn<E, V> createTableMultilineColumn(String columnName, String propertyName) {
        TableColumnBuilder<E, V> builder = new TableColumnBuilder<>();
        return builder.createMultilineStringValueColumn(columnName, propertyName);
    }

    public <E, V> TableColumn<E, Boolean> createTableBooleanColumn(String columnName, String propertyName) {
        TableColumnBuilder<E, Boolean> builder = new TableColumnBuilder<>();
        return builder.createBooleanValueColumn(columnName, propertyName);
    }
}
