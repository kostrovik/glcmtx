package graphics.controls.field;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    24/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class LabeledTextField extends Control {
    private final ObjectProperty<String> label;

    public LabeledTextField(String label) {
        this.label = new SimpleObjectProperty<>();
        setLabel(label);
    }

    // свойсто название поля
    public ObjectProperty<String> labelProperty() {
        return label;
    }

    public String getLabel() {
        return label.get();
    }

    public void setLabel(String labelValue) {
        label.set(labelValue);
    }
    // -- свойсто название поля --

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LabeledTextFieldSkin(this);
    }
}
