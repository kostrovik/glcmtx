package users.services;

import users.models.User;
import users.models.UserAccount;
import users.models.UserName;
import users.models.UserRole;

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
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            UserName name = new UserName("test", "last test");
            UserAccount account = new UserAccount("mainl@mainl.com", "test", "test");

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
