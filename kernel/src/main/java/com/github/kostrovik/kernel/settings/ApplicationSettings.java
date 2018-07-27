package com.github.kostrovik.kernel.settings;

import com.github.kostrovik.kernel.builders.SceneFactory;
import com.github.kostrovik.kernel.dictionaries.ColorThemeDictionary;
import com.github.kostrovik.kernel.models.ServerConnectionAddress;
import ru.glance.matrix.helper.common.ApplicationLogger;
import ru.glance.matrix.helper.common.ConfigParser;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    26/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class ApplicationSettings {
    private static Logger logger = ApplicationLogger.getLogger(ApplicationSettings.class.getName());
    private static volatile ApplicationSettings settings;

    private Path applicationConfigPath;
    private ConfigParser parser;
    private ServerConnectionAddress defaultHost;

    private ApplicationSettings() {
        setPath();
        this.parser = new ConfigParser(readSettings());
    }

    public static ApplicationSettings getInstance() {
        if (settings == null) {
            synchronized (SceneFactory.class) {
                if (settings == null) {
                    settings = new ApplicationSettings();
                }
            }
        }
        return settings;
    }

    public List<ServerConnectionAddress> getHosts() {
        Map<String, Object> config = parser.getConfig();
        String defaultAddress = (String) config.getOrDefault("defaultHost", "");
        List<ServerConnectionAddress> addreses = new ArrayList<>();

        List<String> hosts = Arrays.asList(((String) config.getOrDefault("hosts", "")).split(","));

        for (String host : hosts) {
            ServerConnectionAddress address = createAddress(host);

            if (!defaultAddress.isEmpty() && address.getUrl().equals(defaultAddress)) {
                address.setDefault(true);
            }

            addreses.add(address);
        }

        return addreses;
    }

    public ServerConnectionAddress getDefaultHost() {
        if (defaultHost == null) {
            Optional<ServerConnectionAddress> host = getHosts().stream().filter(ServerConnectionAddress::isDefault).findFirst();
            host.ifPresent(serverConnectionAddress -> defaultHost = serverConnectionAddress);
        }

        if (defaultHost == null) {
            logger.log(Level.SEVERE, "Не найдено настроек для подключения к серверу.");
        }

        return defaultHost;
    }

    public void saveHostsList(List<ServerConnectionAddress> hosts) {
        Properties properties = readSettings();
        properties.setProperty("hosts", String.join(",", hosts.stream().map(host -> {
            if (host.isDefault()) {
                properties.setProperty("defaultHost", host.getUrl());
                defaultHost = host;
            }
            return String.format("%s@%s", host.getUrl(), host.getLastUsage());
        }).collect(Collectors.toList())));


        writeSettings(properties);
        parser = new ConfigParser(readSettings());
    }

    public void updateHostLastUsage() {
        Properties properties = readSettings();
        properties.setProperty("hosts", String.join(",", getHosts().stream().map(host -> {
            if (host.isDefault()) {
                host.setLastUsage(LocalDateTime.now());
                defaultHost = host;
            }
            return String.format("%s@%s", host.getUrl(), host.getLastUsage());
        }).collect(Collectors.toList())));

        writeSettings(properties);
        parser = new ConfigParser(readSettings());
    }

    public String getDetaultTheme() {
        Object colorTheme = parser.getConfigProperty("colorTheme");
        return colorTheme != null ? (String) colorTheme : ColorThemeDictionary.LIGHT.getThemeName();
    }

    public void saveDefaultColorTheme(String theme) {
        Properties properties = readSettings();
        properties.setProperty("colorTheme", theme);
        writeSettings(properties);
        parser = new ConfigParser(readSettings());
    }

    private ServerConnectionAddress createAddress(String host) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String[] hostParams = host.split("@");
        ServerConnectionAddress address = new ServerConnectionAddress(hostParams[0]);

        if (hostParams.length > 1 && !hostParams[1].equals("null")) {
            address.setLastUsage(LocalDateTime.parse(hostParams[1], formatter));
        }

        return address;
    }

    private void writeSettings(Properties properties) {
        try (FileOutputStream fileOut = new FileOutputStream(applicationConfigPath.toString())) {
            properties.store(fileOut, null);
        } catch (FileNotFoundException error) {
            logger.log(Level.SEVERE, String.format("Не найден файл конфигурации приложения: %s", applicationConfigPath), error);
        } catch (IOException error) {
            logger.log(Level.WARNING, String.format("Не возможно сохранить настройки: %s", applicationConfigPath), error);
        }
    }

    private void setPath() {
        try {
            URI applicationDirectory = ApplicationSettings.class.getProtectionDomain().getCodeSource().getLocation().toURI();

            if (Paths.get(applicationDirectory).getParent().toString().equals("/")) {
                applicationDirectory = URI.create(System.getProperty("java.home"));
            }

            applicationConfigPath = Paths.get(applicationDirectory.getPath() + "/settings", "application.properties");

            if (Files.notExists(applicationConfigPath.getParent())) {
                Files.createDirectory(applicationConfigPath.getParent());
                Files.createFile(applicationConfigPath);
            }
            if (Files.notExists(applicationConfigPath)) {
                Files.createFile(applicationConfigPath);
            }
        } catch (URISyntaxException e) {
            logger.log(Level.SEVERE, "Нет доступа к директории с настройками.", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Нет возможно создать директорию с настройками.", e);
        }
    }

    private Properties readSettings() {
        Properties result = new Properties();

        if (applicationConfigPath != null) {
            try (InputStream inputStream = new FileInputStream(applicationConfigPath.toString())) {
                result.load(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            } catch (NullPointerException error) {
                logger.log(Level.SEVERE, String.format("Не существует файл конфигурации приложения: %s", applicationConfigPath), error);
            } catch (FileNotFoundException error) {
                logger.log(Level.SEVERE, String.format("Не найден файл конфигурации приложения: %s", applicationConfigPath), error);
            } catch (IOException error) {
                logger.log(Level.SEVERE, String.format("Не возможно загрузить настройки: %s", applicationConfigPath), error);
            }
        }

        return result;
    }
}
