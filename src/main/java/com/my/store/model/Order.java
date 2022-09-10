package com.my.store.model;

public class Order {
    private int id;

    private String description;

    private int customerID;

    private int stepID;


    public Order(int id, String description, int customerID, int stepID) {
        this.id = id;
        this.description = description;
        this.customerID = customerID;
        this.stepID = stepID;
    }

    public Order(String description, int customerID, int stepID) {
        this.description = description;
        this.customerID = customerID;
        this.stepID = stepID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getStepID() {
        return stepID;
    }

    public void setStepID(int stepID) {
        this.stepID = stepID;
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", customerID=" + customerID +
                ", stepID=" + stepID +
                '}';
    }
}
