package com.my.store.model;

public class BuyBook {
    private int id;

    private int buyID;

    private int bookID;

    private int amount;

    public BuyBook(int id, int buyID, int bookID, int amount) {
        this.id = id;
        this.buyID = buyID;
        this.bookID = bookID;
        this.amount = amount;
    }

    public BuyBook(int buyID, int bookID, int amount) {
        this.buyID = buyID;
        this.bookID = bookID;
        this.amount = amount;
    }

    public BuyBook(int bookID, int amount) {
        this.bookID = bookID;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuyID() {
        return buyID;
    }

    public void setBuyID(int orderID) {
        this.buyID = orderID;
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
                ", buyID=" + buyID +
                ", bookID=" + bookID +
                ", amount=" + amount +
                '}';
    }
}
