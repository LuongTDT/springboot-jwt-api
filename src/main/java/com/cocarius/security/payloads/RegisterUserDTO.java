package com.cocarius.security.payloads;

/**
 * @author LuongTDT
 */
public class RegisterUserDTO extends ParentDTO {
    private String username;
    private String password;
    private String email;
    private String[] roles;

    public RegisterUserDTO(String username, String password, String email, String[] roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public RegisterUserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
