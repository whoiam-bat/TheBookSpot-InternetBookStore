package com.my.store.model;

public class Customer {
    private int id;

    private String fullName;

    private String email;

    private String password;
    private int roleID;
    private String roleName;

    public Customer(int id, String fullName, String email, String password, int roleID, String roleName) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.roleID = roleID;
        this.roleName = roleName;
    }

    public Customer(int id, String fullName, String email, String password, int roleID) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.roleID = roleID;
    }

    public Customer(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public Customer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roleID=" + roleID +
                ", roleName=" + roleName +
                '}';
    }
}
