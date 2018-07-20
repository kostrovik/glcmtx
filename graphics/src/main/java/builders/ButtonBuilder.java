package builders;

import common.icons.SolidIcons;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    20/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class ButtonBuilder {
    public Button createButton(String buttonLabel) {
        Button button = new Button();
        setLabel(button, buttonLabel);
        return button;
    }

    public Button createButton(SolidIcons buttonIcon) {
        Button button = new Button();
        setIcon(button, buttonIcon);
        return button;
    }

    public Button createButton(SolidIcons buttonIcon, String buttonLabel) {
        Button button = new Button();
        setIcon(button, buttonIcon);
        setLabel(button, buttonLabel);
        return button;
    }

    private void setLabel(Button button, String buttonLabel) {
        button.setText(buttonLabel);
    }

    private void setIcon(Button button, SolidIcons buttonIcon) {
        Text icon = new Text(buttonIcon.getSymbol());
        icon.setFont(buttonIcon.getFont());
        button.setGraphic(icon);
    }
}