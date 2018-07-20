package common;

import builders.ButtonBuilder;
import common.icons.SolidIcons;
import javafx.scene.control.Button;

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
        return builder.createButton(icon,buttonTitle);
    }
}
