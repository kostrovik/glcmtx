package ru.glance.matrix.users.models;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    24/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class UserAccount {
    private String email;
    private String login;
    private String password;
    private Token token;

    public UserAccount(String email, String login, String password) {
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }
}
