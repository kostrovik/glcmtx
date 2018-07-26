package ru.glance.matrix.graphics.builders;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    24/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class TableColumnBuilder<T, R> {

    public <T, R> TableColumn<T, R> createColumn(String columnName) {
        TableColumn<T, R> column = new TableColumn<>(columnName);

        Text columnNameText = new Text(columnName);
        columnNameText.applyCss();
        column.setMinWidth(columnNameText.getBoundsInLocal().getWidth() + 10);

        return column;
    }

    public TableColumn<T, R> createStringValueColumn(String columnName, String property) {
        TableColumn<T, R> column = createColumn(columnName);
        column.setCellValueFactory(new CellPropertyValueFactory<>(property));

        column.setCellFactory(new Callback<>() {
            @Override
            public TableCell<T, R> call(TableColumn<T, R> param) {
                return new TableCell<>() {
                    @Override
                    public void updateItem(R item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            if (item != null) {
                                setText(item.toString());

                                Text text = new Text(getText());
                                text.applyCss();

                                if (column.getMinWidth() < text.getBoundsInLocal().getWidth() + 10) {
                                    column.setMinWidth(text.getBoundsInLocal().getWidth() + 10);
                                }
                            } else {
                                setText(null);
                            }
                        }
                    }
                };
            }
        });

        return column;
    }

    public TableColumn<T, R> createMultilineStringValueColumn(String columnName, String property) {
        TableColumn<T, R> column = createColumn(columnName);
        column.setCellValueFactory(new CellPropertyValueFactory<>(property, true));
        column.setCellFactory(new Callback<>() {
            @Override
            public TableCell<T, R> call(TableColumn<T, R> param) {
                return new TableCell<>() {
                    private Text cellText;

                    @Override
                    public void cancelEdit() {
                        super.cancelEdit();
                        setGraphic(cellText);
                    }

                    @Override
                    public void updateItem(R item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty() && !isEditing()) {
                            cellText = createText(item.toString());
                            setGraphic(cellText);
                        }
                    }

                    private Text createText(String value) {
                        Text text = new Text(value);
                        text.getStyleClass().add("text");
                        text.wrappingWidthProperty().bind(getTableColumn().widthProperty());
                        return text;
                    }
                };
            }
        });

        return column;
    }

    public TableColumn<T, Boolean> createBooleanValueColumn(String columnName, String property) {
        TableColumn<T, Boolean> column = createColumn(columnName);
        column.setCellValueFactory(new CellPropertyValueFactory<>(property));

        column.setCellFactory(param -> {
            CheckBoxTableCell<T, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        return column;
    }

    public TableColumn<T, LocalDateTime> createLocalDateTimeValueColumn(String columnName, String property, DateTimeFormatter formatter) {
        TableColumn<T, LocalDateTime> column = createColumn(columnName);
        column.setCellValueFactory(new CellPropertyValueFactory<>(property));

        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else {
                    if (item != null) {
                        setText(item.format(formatter));
                    } else {
                        setText(null);
                    }
                }
            }
        });

        return column;
    }
}
