package com.my.store.dao;

import com.my.store.model.Book;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class BookStockDao {
    private final DataSource dataSource;

    public BookStockDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Book> listBooks() {
        List<Book> bookList = new ArrayList<>();

        try(
                Connection con = dataSource.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM book WHERE amount > 0;");
                ) {

            while(rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                float price = rs.getFloat("price");
                int amount = rs.getInt("amount");
                String description = rs.getString("description");
                String imagePATH = rs.getString("image");

                Book temp = new Book(id, title, author, genre, price, amount, description, imagePATH);

                bookList.add(temp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    public Book getBook(String bookId) {
        Book res = null;
        try(
                Connection con = dataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement("SELECT * FROM book WHERE id=?;");
        ) {
            pstmt.setInt(1, Integer.parseInt(bookId));

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                res = new Book(rs.getInt("id"),
                        rs.getString("title"), rs.getString("author"),
                        rs.getString("genre"), rs.getFloat("price"),
                        rs.getInt("amount"), rs.getString("description"), rs.getString("image"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public List<Book> searchBook(String nameBook) {
        List<Book> res = new ArrayList<>();
        try(
                Connection con = dataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement("SELECT * FROM book WHERE title=?;");
        ) {
            pstmt.setString(1, nameBook);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                res.add(new Book(rs.getInt("id"),
                        rs.getString("title"), rs.getString("author"),
                        rs.getString("genre"), rs.getFloat("price"),
                        rs.getInt("amount"), rs.getString("description"), rs.getString("image")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public void addBook(Book book) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(
                        "INSERT INTO book (title, author, genre, price, amount, description, image) " +
                                "VALUE(?, ?, ?, ?, ?, ?, ?)");
                ) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getGenre());
            pstmt.setFloat(4, book.getPrice());
            pstmt.setInt(5, book.getAmount());
            pstmt.setString(6, book.getDescription());
            pstmt.setString(7, "templates/book_img/" + book.getImagePATH());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(String id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement("DELETE FROM book WHERE id=?;");) {

            pstmt.setInt(1, Integer.parseInt(id));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAmountBook(int id, int amount) {
        try(
                Connection con = dataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(
                        "UPDATE book SET amount=amount - ?" +
                                " WHERE id=?;");) {

            pstmt.setInt(1, amount);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book) {
        try(
                Connection con = dataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(
                        "UPDATE book SET price=?, amount=?" +
                                " WHERE id=?;");) {
            pstmt.setFloat(1, book.getPrice());
            pstmt.setInt(2, book.getAmount());
            pstmt.setInt(3, book.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
