package common.icons;

import config.ModuleSettings;
import javafx.scene.text.Font;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    20/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public enum SolidIcons {
    CARET_DOWN("\uf0d7");

    private final String character;
    private final Font font;
    private final ModuleSettings settings;

    private SolidIcons(String character) {
        this.character = character;
        this.settings = new ModuleSettings();
        this.font = prepareFont(settings);
    }

    public String getSymbol() {
        return character;
    }

    public Font getFont() {
        return font;
    }

    private Font prepareFont(ModuleSettings settings) {
        return Font.loadFont(settings.getFontPath(), settings.getDefaultIconsFontSize());
    }
}
