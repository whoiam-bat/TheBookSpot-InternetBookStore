package com.my.store.model;

public class Buy {
    private int id;

    private String description;

    private int customerID;

    private int stepID;

    private float orderPrice;


    public Buy(int id, String description, int customerID, int stepID, float orderPrice) {
        this.id = id;
        this.description = description;
        this.customerID = customerID;
        this.stepID = stepID;
        this.orderPrice = orderPrice;
    }

    public Buy(String description, int customerID, int stepID,  float orderPrice) {
        this.description = description;
        this.customerID = customerID;
        this.stepID = stepID;
        this.orderPrice = orderPrice;
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

    public float getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }


    @Override
    public String toString() {
        return "Buy{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", customerID=" + customerID +
                ", stepID=" + stepID +
                ", orderPrice=" + orderPrice +
                '}';
    }
}
