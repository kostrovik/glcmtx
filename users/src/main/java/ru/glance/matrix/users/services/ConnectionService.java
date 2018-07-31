package ru.glance.matrix.users.services;

import ru.glance.helper.common.ApplicationLogger;
import ru.glance.provider.interfaces.ServerConnectionInterface;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    25/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class ConnectionService {
    private static Logger logger = ApplicationLogger.getLogger(ConnectionService.class.getName());
    private static ServerConnectionInterface connection;
    private static volatile ConnectionService service;

    private ConnectionService() {
        connection = createConnection();
    }

    public static ConnectionService getConnection() {
        if (service == null) {
            synchronized (ConnectionService.class) {
                if (service == null) {
                    service = new ConnectionService();
                }
            }
        }

        return service;
    }

    public String sendGet(String api) {
        return connection.sendGet(api);
    }

    public String sendPost(String api, String json) {
        return connection.sendPost(api, json);
    }

    private ServerConnectionInterface createConnection() {
        ServiceLoader<ServerConnectionInterface> serviceLoader = ServiceLoader.load(ModuleLayer.boot(), ServerConnectionInterface.class);

        Optional<ServerConnectionInterface> serverConnector = serviceLoader.findFirst();

        if (serverConnector.isPresent()) {
            connection = serverConnector.get();
        } else {
            logger.log(Level.WARNING, String.format("Не доступно подключение к серверу. Модуль: %s", this.getClass().getModule().getName()));
        }

        return connection;
    }
}