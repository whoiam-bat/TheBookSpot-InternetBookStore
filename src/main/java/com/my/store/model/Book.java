package com.my.store.model;

public class Book {
    private int id;

    private String title;

    private String author;

    private String genre;

    private float price;

    private int amount;

    private String imagePATH;


    public Book(int id, String title, String author, String genre, float price, int amount, String imagePATH) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.amount = amount;
        this.imagePATH = imagePATH;
    }

    public Book(String title, String author, String genre, float price, int amount, String imagePATH) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.amount = amount;
        this.imagePATH = imagePATH;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public String getImagePATH() {
        return imagePATH;
    }

    public void setImagePATH(String imagePATH) {
        this.imagePATH = imagePATH;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", imagePATH='" + imagePATH + '\'' +
                '}';
    }
}
