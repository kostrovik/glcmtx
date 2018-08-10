package ru.glance.matrix.users.services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import ru.glance.matrix.users.models.User;
import ru.glance.matrix.users.models.UserAccount;
import ru.glance.matrix.users.models.UserName;
import ru.glance.matrix.users.models.UserRole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    24/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class UserService {
    public List<User> getUsersList() {

        ConnectionService connection = ConnectionService.getConnection();

        String res = connection.sendGet("/");



        JsonFactory jsonFactory= new JsonFactory();
        String json = "{\"test\":\"value test\"}";

        try (JsonParser jsonParser = jsonFactory.createParser(json)) {
            JsonToken jsonToken = jsonParser.nextToken();

            String cachedFieldName = null;

            while (jsonParser.hasCurrentToken()) {
                if (jsonToken == JsonToken.FIELD_NAME) {
                    cachedFieldName = jsonParser.getCurrentName();
                }

                if (jsonToken == JsonToken.VALUE_STRING && cachedFieldName != null) {
//                    dealProperties.put(cachedFieldName, jsonParser.getText());
                }

                if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
//                    dealProperties.put(cachedFieldName, jsonParser.getValueAsInt());
                }
                if (jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
//                    dealProperties.put(cachedFieldName, jsonParser.getBigIntegerValue());
                }
                if (jsonToken == JsonToken.VALUE_FALSE || jsonToken == JsonToken.VALUE_TRUE) {
//                    dealProperties.put(cachedFieldName, jsonParser.getValueAsBoolean());
                }

                System.out.println(jsonToken);
                System.out.println(jsonParser.getText());

                jsonToken = jsonParser.nextToken();
            }
        } catch (IOException e) {
        }



        List<User> users = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            UserName name = new UserName("test", "last test" + i);
            UserAccount account = new UserAccount("mainl@mainl.com" + i, "test", "test");

            List<UserRole> roles = new ArrayList<>();
            roles.add(new UserRole("admin", "Администратор"));
            roles.add(new UserRole("user", "Пользователь"));

            User user = new User(name, account);
            user.setRoles(roles);
            user.setComment("Комментарий многострочный. Комментарий многострочный. Комментарий многострочный. Комментарий многострочный. Комментарий многострочный. Комментарий многострочный. Комментарий многострочный. Комментарий многострочный. Комментарий многострочный. Комментарий многострочный. Комментарий многострочный. Комментарий многострочный. Комментарий многострочный. ");
            user.setPhysicalPerson(true);

            users.add(user);
        }

        return users;
    }
}
