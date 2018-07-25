package graphics.builders;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.text.Text;
import javafx.util.Callback;

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
}
