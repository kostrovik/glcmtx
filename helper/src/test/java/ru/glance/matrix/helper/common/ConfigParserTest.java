package ru.glance.matrix.helper.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    23/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class ConfigParserTest {
        private Properties properties;

        @BeforeEach
        void initProperties() {
            properties = new Properties();
            properties.setProperty("prop_single", "single_value");
            properties.setProperty("property.module.view.builder_main", "configurator.core.views.MainBuilder");
            properties.setProperty("property.module.menu.builder", "configurator.core.views.menu.MainBuilder");
        }

        @Test
        void countSettingsTest() {
            ConfigParser parser = new ConfigParser(properties);
            Map config = parser.getConfig();

            assertEquals(2, config.keySet().size());
        }

        @Test
        void configValueTest() {
            ConfigParser parser = new ConfigParser(properties);
            Map config = parser.getConfig();

            assertEquals("single_value", config.get("prop_single"));
            assertEquals("configurator.core.views.MainBuilder", ((Map) ((Map) ((Map) config.get("property")).get("module")).get("view")).get("builder_main"));
            assertEquals("configurator.core.views.menu.MainBuilder", ((Map) ((Map) ((Map) config.get("property")).get("module")).get("menu")).get("builder"));
        }

        @Test
        void configGetPropertyTest() {
            ConfigParser parser = new ConfigParser(properties);

            assertEquals("single_value", parser.getConfigProperty("prop_single"));
            assertEquals("configurator.core.views.MainBuilder", parser.getConfigProperty("builder_main"));
        }

        @Test
        void configGetPropertyFirstTest() {
            ConfigParser parser = new ConfigParser(properties);
            properties.setProperty("property.module.prop_single", "deep_property");

            assertNotEquals("deep_property", parser.getConfigProperty("prop_single"));
        }

        @Test
        void configGetPropertyNullTest() {
            ConfigParser parser = new ConfigParser(properties);

            assertNull(parser.getConfigProperty("undefined property"));
        }
}