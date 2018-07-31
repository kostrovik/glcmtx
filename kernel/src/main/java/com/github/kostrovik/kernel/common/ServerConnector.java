package com.github.kostrovik.kernel.common;

import ru.glance.helper.common.ApplicationLogger;
import com.github.kostrovik.kernel.interfaces.ServerConnectionInterface;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    25/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class ServerConnector implements ServerConnectionInterface {
    private static Logger logger = ApplicationLogger.getLogger(ServerConnector.class.getName());
    private String serverUrl;

    @Override
    public String sendGet(String apiUrl) {
        StringBuilder response = new StringBuilder();
        StringBuilder responseHeaders = new StringBuilder();

        try {
            URL serverApiUrl = new URL(serverUrl + apiUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) serverApiUrl.openConnection();
            httpConnection.setRequestMethod("GET");

            responseHeaders.append(String.format("Запрос: %s\n", serverApiUrl.toExternalForm()));
            responseHeaders.append(String.format("Meтoд: %s\n", httpConnection.getRequestMethod()));
            responseHeaders.append(String.format("Koд ответа: %s\n", httpConnection.getResponseCode()));
            responseHeaders.append(String.format("Oтвeт: %s\n", httpConnection.getResponseMessage()));

            InputStreamReader input = new InputStreamReader(httpConnection.getInputStream(), Charset.forName("UTF-8"));

            int character;
            while (((character = input.read()) != -1)) {
                response.append((char) character);
            }
            input.close();

        } catch (IOException e) {
            logger.log(Level.WARNING, String.format("Ошибка выполнения запроса\n%s", responseHeaders), e);
        }

        return response.toString();
    }

    @Override
    public String sendPost(String apiUrl, String json) {
        StringBuilder response = new StringBuilder();
        StringBuilder responseHeaders = new StringBuilder();

        try {
            URL serverApiUrl = new URL(serverUrl + apiUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) serverApiUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setRequestMethod("POST");

            OutputStream output = httpConnection.getOutputStream();
            output.write(json.getBytes(Charset.forName("UTF-8")));
            output.close();

            responseHeaders.append(String.format("Запрос: %s\n", serverApiUrl.toExternalForm()));
            responseHeaders.append(String.format("Meтoд: %s\n", httpConnection.getRequestMethod()));
            responseHeaders.append(String.format("Koд ответа: %s\n", httpConnection.getResponseCode()));
            responseHeaders.append(String.format("Oтвeт: %s\n", httpConnection.getResponseMessage()));

            InputStreamReader input = new InputStreamReader(httpConnection.getInputStream(), Charset.forName("UTF-8"));

            int character;
            while (((character = input.read()) != -1)) {
                response.append((char) character);
            }
            input.close();

        } catch (IOException e) {
            logger.log(Level.WARNING, String.format("Ошибка выполнения запроса\n%s", responseHeaders), e);
        }

        return response.toString();
    }
}
