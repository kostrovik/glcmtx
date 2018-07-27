package ru.glance.matrix.helper.common;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Служебный класс для парсинга различных объектов Properties и создания из них вложенного дерева на основе Map.
 *
 * project: glcmtx
 * author:  kostrovik
 * date:    23/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class ConfigParser {
    private static Logger logger = ApplicationLogger.getLogger(ConfigParser.class.getName());
    private Properties properties;
    private Map<String, Object> config;

    public ConfigParser(Properties properties) {
        this.properties = properties;
        config = new ConcurrentHashMap<>();
        prepareConfig();
    }

    private void prepareConfig() {
        logger.info("Подготовка конфигурации");

        for (Object key : properties.keySet()) {
            prepareProperty((String) key);
        }
    }

    /**
     * Раскладывает ключ по символу "точка". Создает Массив ключей для вложенного дерева настроек.
     *
     * @param key the key
     * @return the list
     */
    private List<String> parseKey(String key) {
        return Arrays.asList(key.split("\\."));
    }

    /**
     * Создает вложенное дерево для ключа из настроек.
     * Если ключа еще не создано в дереве то создает ветку на полную глубину и кладет значение в самый низ.
     * property: [
     * sub_property: [
     * sub_level_property: value
     * ]
     * ];
     * Если ключ до определенной вложенности уже существует то добавит новое значение в список.
     * property: [
     * sub_property: [
     * sub_level_property: value,
     * sub_level_property_1: value_1
     * ]
     * ];
     *
     * @param key the key
     */
    private void prepareProperty(String key) {
        String value = properties.getProperty(key);
        List<String> parsedKey = parseKey(key);

        if (!config.containsKey(parsedKey.get(0))) {
            config.put(parsedKey.get(0), createValueMap(parsedKey, value));
        } else {
            findAndSetKey(parsedKey, value);
        }
    }

    /**
     * Ищет ключ и добавляет в дерево новое значение на нужной глубине.
     *
     * @param keys  the keys
     * @param value the value
     */
    private void findAndSetKey(List<String> keys, String value) {
        List<String> mapKeys = new ArrayList<>(keys);
        Object configValue = config.get(mapKeys.get(0));
        mapKeys.remove(0);

        while (mapKeys.size() > 0) {
            if (configValue instanceof Map) {
                if (((Map) configValue).containsKey(mapKeys.get(0))) {
                    configValue = ((Map) configValue).get(mapKeys.get(0));
                    mapKeys.remove(0);
                } else {
                    ((Map) configValue).put(mapKeys.get(0), createValueMap(mapKeys, value));
                    mapKeys.clear();
                }
            }
        }

    }

    /**
     * Создает вложенное дерево настроек вида
     * property: [
     * sub_property: [
     * sub_level_property: value
     * ]
     * ]
     * до того уровня насколько сильно вложен ключ
     *
     * @param keys  вложенный ключ
     * @param value значение которое будет записано на самом нижнем уровне
     * @return the object
     */
    private Object createValueMap(List<String> keys, String value) {
        if (keys.size() > 1) {
            List<String> mapKeys = new ArrayList<>(keys);
            String lastKey = mapKeys.get(mapKeys.size() - 1);

            Map<String, Object> cachedValue = new ConcurrentHashMap<>();
            cachedValue.put(lastKey, value);
            mapKeys.remove(keys.size() - 1);
            for (int i = mapKeys.size() - 1; i > 0; i--) {
                Map<String, Object> newValue = new ConcurrentHashMap<>();
                newValue.put(mapKeys.get(i), cachedValue);
                cachedValue = newValue;
            }

            return cachedValue;
        }

        return value;
    }

    /**
     * Gets config.
     *
     * @return the config
     */
    public Map<String, Object> getConfig() {
        return config;
    }

    /**
     * Ищет в конфигурации указанный параметр.
     * Особенность в том что возвращается первый найденый элемент. Стоит учитывать что если свойство с
     * одинаковым названием есть на разной глубине то вернется верхнее найденное.
     *
     * @param property атрибут для поиска
     * @return the config property
     */
    public Object getConfigProperty(String property) {
        return findProperty(property, config);
    }

    /**
     * Рекурсивно ищет вложенный атрибут.
     *
     * @param property   the property
     * @param properties the properties
     * @return the object
     */
    private Object findProperty(String property, Map properties) {
        if (properties.keySet().contains(property)) {
            return properties.get(property);
        }

        for (Object value : properties.values()) {
            if (value instanceof Map) {
                return findProperty(property, (Map) value);
            }
        }

        logger.log(Level.WARNING, String.format("Не найден ключ конфигурации: %s", property));

        return null;
    }
}