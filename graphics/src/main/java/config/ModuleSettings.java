package config;

import helper.common.ApplicationLogger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    20/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
final public class ModuleSettings {
    private static Logger logger = ApplicationLogger.getLogger(ModuleSettings.class.getName());

    private static Properties config;
    private final static String defaultConfigFilePath = "/control_icons.properties";

    public ModuleSettings() {
        if (config == null) {
            config = getDefaultConfig();
        }
    }

    private Properties getDefaultConfig() {
        Properties result = new Properties();

        try (InputStream inputStream = Class.forName(ModuleSettings.class.getName()).getResourceAsStream(defaultConfigFilePath)) {
            result.load(inputStream);

            result.setProperty("icons.font.path", preparePathForDefaultResource(result.getProperty("icons.font.path")));

        } catch (FileNotFoundException error) {
            logger.log(Level.SEVERE, "Не найден файл конфигурации по умолчанию", error);
        } catch (IOException error) {
            logger.log(Level.SEVERE, "Не возможно загрузить настройки умолчанию", error);
        } catch (ClassNotFoundException error) {
            logger.log(Level.SEVERE, "Не возможно найти класс", error);
        }

        return result;
    }

    private String preparePathForDefaultResource(String path) throws FileNotFoundException, ClassNotFoundException {
        URL resource = Class.forName(ModuleSettings.class.getName()).getResource(path);

        if (resource == null) {
            throw new FileNotFoundException("resources path: " + path);
        }

        return resource.toExternalForm();
    }

    public String getFontPath() {
        return config.getProperty("icons.font.path");
    }

    public double getDefaultIconsFontSize() {
        return Double.parseDouble(config.getProperty("icons.font.size"));
    }
}
