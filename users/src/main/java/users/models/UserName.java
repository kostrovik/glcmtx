package users.models;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    24/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class UserName {
    private String name;
    private String surname;
    private String secondName;

    public UserName(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFullName() {
        if (secondName == null) {
            return String.format("%s %s", surname, name);
        }
        return String.format("%s %s %s", surname, name, secondName);
    }
}
