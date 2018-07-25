package users.models;

import java.util.List;
import java.util.UUID;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    24/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class User {
    private UUID id;

    private UserName userName;

    private UserAccount account;

    private List<UserRole> roles;
    private boolean isPhysicalPerson;

    private String shop;

    private String comment;

    public User(UserName userName, UserAccount account) {
        this.userName = userName;
        this.account = account;
    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return userName.getFullName();
    }

    public String getEmail() {
        return account.getEmail();
    }

    public String getComment() {
        return comment;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public boolean isPhysicalPerson() {
        return isPhysicalPerson;
    }

    public void setPhysicalPerson(boolean physicalPerson) {
        isPhysicalPerson = physicalPerson;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
