package ru.glance.matrix.graphics.builders;

import ru.glance.matrix.graphics.helper.PropertyReference;
import ru.glance.matrix.helper.common.ApplicationLogger;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    25/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class CellPropertyValueFactory<S, T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
    private static Logger logger = ApplicationLogger.getLogger(CellPropertyValueFactory.class.getName());

    private final String property;
    private boolean isMultiline;

    private Class<?> columnClass;
    private String previousProperty;
    private PropertyReference<T> propertyRef;

    public CellPropertyValueFactory(String property) {
        this.property = property;
        this.isMultiline = false;
    }

    public CellPropertyValueFactory(String property, boolean isMultiline) {
        this.property = property;
        this.isMultiline = isMultiline;
    }

    public void setIsMultiline(boolean isMultiline) {
        this.isMultiline = isMultiline;
    }

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
        return getCellDataReflectively(param, param.getValue());
    }

    public final String getProperty() {
        return property;
    }

    private ObservableValue<T> getCellDataReflectively(TableColumn.CellDataFeatures<S, T> param, S rowData) {
        if (getProperty() == null || getProperty().isEmpty() || rowData == null) return null;

        try {
            // we attempt to cache the property reference here, as otherwise
            // performance suffers when working in large data models. For
            // a bit of reference, refer to RT-13937.
            if (columnClass == null || previousProperty == null ||
                    !columnClass.equals(rowData.getClass()) ||
                    !previousProperty.equals(getProperty())) {

                // create a new PropertyReference
                this.columnClass = rowData.getClass();
                this.previousProperty = getProperty();
                this.propertyRef = new PropertyReference<T>(rowData.getClass(), getProperty());
            }

            if (propertyRef != null) {

                TableColumn column = param.getTableColumn();

                if (propertyRef.hasProperty()) {
                    return propertyRef.getProperty(rowData);
                } else {
                    T value = propertyRef.get(rowData);

                    Text text = new Text(value.toString());
                    text.applyCss();
                    if (!isMultiline) {
                        if (column.getWidth() < text.getBoundsInLocal().getWidth() + 10) {
                            column.setMinWidth(text.getBoundsInLocal().getWidth() + 10);
                        }
                    }

                    if (value instanceof Boolean) {
                        ReadOnlyBooleanWrapper booleanProp = new ReadOnlyBooleanWrapper((Boolean) value);
                        return (ObservableValue<T>) booleanProp;
                    }

                    return new ReadOnlyObjectWrapper<T>(value);
                }
            }
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, String.format("Не возможно получить свойство %s, в CellPropertyValueFactory: %s из переданного объекта %s", getProperty(), this, rowData.getClass()), e);
            propertyRef = null;
        }

        return null;
    }
}
