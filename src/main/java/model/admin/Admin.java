package model.admin;

import java.util.Date;

public class Admin {
    private int id;
    private String username;
    private String password;
    private int roles;

    public Admin() {
    }

    public Admin(String username, String password, int roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Admin(int id, String username, String password, int roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getRoles() {
        return roles;
    }

    public void setRoles(int roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
