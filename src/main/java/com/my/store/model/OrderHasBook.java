package com.my.store.model;

public class OrderHasBook {
    private int id;

    private int orderID;

    private int bookID;

    private int amount;

    public OrderHasBook(int id, int orderID, int bookID, int amount) {
        this.id = id;
        this.orderID = orderID;
        this.bookID = bookID;
        this.amount = amount;
    }

    public OrderHasBook(int orderID, int bookID, int amount) {
        this.orderID = orderID;
        this.bookID = bookID;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderHasBook{" +
                "id=" + id +
                ", orderID=" + orderID +
                ", bookID=" + bookID +
                ", amount=" + amount +
                '}';
    }
}
