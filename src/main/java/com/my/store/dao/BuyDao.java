package com.my.store.dao;

import com.my.store.model.Book;
import com.my.store.model.Buy;
import com.my.store.model.BuyBook;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class BuyDao {
    private final DataSource dataSource;

    public BuyDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createOrder(Buy buy) {
        final String QUERY = "INSERT INTO buy (description, customer_id, step_id, order_price) " +
                "VALUE(?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = dataSource.getConnection();

            pstmt = con.prepareStatement(QUERY);

            // insert order info into main table buy
            pstmt.setString(1, buy.getDescription());
            pstmt.setInt(2, buy.getCustomerID());
            pstmt.setInt(3, buy.getStepID());
            pstmt.setFloat(4, buy.getOrderPrice());

            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public Buy getOrder(Buy buy) {
        final String QUERY = "SELECT * FROM buy WHERE description=? AND customer_id=? AND step_id=? AND order_price=?;";

        Connection con = null;
        PreparedStatement pstmt = null;

        Buy res = buy;

        try {
            con = dataSource.getConnection();

            pstmt = con.prepareStatement(QUERY);

            // insert order info into main table buy
            pstmt.setString(1, buy.getDescription());
            pstmt.setInt(2, buy.getCustomerID());
            pstmt.setInt(3, buy.getStepID());
            pstmt.setFloat(4, buy.getOrderPrice());

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                int id = rs.getInt("buy_id");
                String desc = rs.getString("description");
                int customerId = rs.getInt("customer_id");
                int stepId = rs.getInt("step_id");
                float price = rs.getFloat("order_price");

                res = new Buy(id, desc, customerId, stepId, price);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void addBooksInOrder(Map<BuyBook, Book> buyBook) {
        final String QUERY = "INSERT INTO buy_book (buy_book_buy_id, buy_book_book_id, amount) " +
                "VALUE(?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = dataSource.getConnection();

            pstmt = con.prepareStatement(QUERY);

            // insert order info into side table buy_book
            for (BuyBook it : buyBook.keySet()) {
                pstmt = con.prepareStatement(QUERY);

                pstmt.setInt(1, it.getBuyID());
                pstmt.setInt(2, it.getBookID());
                pstmt.setInt(3, it.getAmount());

                pstmt.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    public Map<Buy, BuyBook> getOrderInfo() {
        System.out.println("BuyDao.getOrder");

        Map<Buy, BuyBook> map = new HashMap<>();

        try(
                Connection con = dataSource.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM buy INNER JOIN buy_book ON buy.buy_id = buy_book.buy_book_buy_id;");
                ) {

            while(rs.next()) {
                int buyId = rs.getInt("buy_id");
                String orderDescription = rs.getString("description");
                int customerId = rs.getInt("customer_id");
                int stepId = rs.getInt("step_id");
                float orderPrice = rs.getFloat("order_price");

                int buyBookId = rs.getInt("buy_book_id");
                int buyBookBuyId = rs.getInt("buy_book_buy_id");
                int buyBookBookId = rs.getInt("buy_book_book_id");
                int amount = rs.getInt("amount");

                Buy tempBuy = new Buy(buyId, orderDescription, customerId, stepId, orderPrice);
                BuyBook tempBuyBook = new BuyBook(buyBookId, buyBookBuyId, buyBookBookId, amount);

                map.put(tempBuy, tempBuyBook);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
}
