package ru.glance.matrix.graphics.common.icons;

import javafx.scene.text.Font;
import ru.glance.matrix.graphics.config.ModuleSettings;
import ru.glance.provider.interfaces.controls.IconInterface;

/**
 * Словарь иконок.
 * Использует иконочный шрифт font-awesome (https://fontawesome.com).
 * <p>
 * project: glcmtx
 * author:  kostrovik
 * date:    20/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public enum SolidIcons implements IconInterface {
    CARET_DOWN("\uf0d7"),
    SERVER("\uf233"),
    DATA_BASE("\uf1c0"),
    PALETTE("\uf53f");

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
