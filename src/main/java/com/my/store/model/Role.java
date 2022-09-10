package com.my.store.model;

public class Role {
    private int id;

    private String nameRole;

    public Role(int id, String nameRole) {
        this.id = id;
        this.nameRole = nameRole;
    }

    public Role(String nameRole) {
        this.nameRole = nameRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", nameRole='" + nameRole + '\'' +
                '}';
    }
}
