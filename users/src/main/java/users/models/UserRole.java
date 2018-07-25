package users.models;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    24/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class UserRole {
    private String roleName;
    private String roleTitle;
    private String roleDescription;

    public UserRole(String roleName, String roleTitle) {
        this.roleName = roleName;
        this.roleTitle = roleTitle;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public String getRoleDescription() {
        return roleDescription;
    }
}
