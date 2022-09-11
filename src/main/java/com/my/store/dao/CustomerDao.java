package com.my.store.dao;

import com.my.store.JdbcConfig;
import com.my.store.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    private final Connection connection;

    public CustomerDao() {
        connection = JdbcConfig.getInstance().getConnection();
    }

    public void addCustomer(Customer customer) {
        final String ownerName = "Oleksii Drabchak";
        final String ownerEmail = "drabchak.aleksey@gmail.com";

        try (
                PreparedStatement pstmt = connection.prepareStatement(
                        "INSERT INTO customer (id, fullname, email, password, role_id) VALUE (?, ?, ?, ? ,?)");) {

            pstmt.setInt(1, customer.getId());
            pstmt.setString(2, customer.getFullName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPassword());
            if (customer.getFullName().equals(ownerName) && customer.getEmail().equals(ownerEmail)) {
                pstmt.setInt(5, 1);
            } else {
                pstmt.setInt(5, 3);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(Customer customer) {
        try (
                PreparedStatement pstmt = connection.prepareStatement(
                        "DELETE FROM customer WHERE fullname LIKE ?;")) {

            pstmt.setString(1, customer.getFullName());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> customerList() {
        List<Customer> customerList = new ArrayList<>();

        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM customer;")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int roleID = rs.getInt("role_id");

                Customer temp = new Customer(id, fullName, email, password, roleID);

                customerList.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }

    public void updateCustomer(Customer customer) {
        try (
                PreparedStatement pstmt = connection.prepareStatement(
                        "UPDATE customer SET fullname=?, email=?, password=?, role_id=?" +
                                " WHERE id=?;");
        ) {
            pstmt.setString(1, customer.getFullName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPassword());
            pstmt.setInt(4, customer.getRoleID());
            pstmt.setInt(5, customer.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean emailAndPasswordExists(String email, String password) {
        try (
                PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM customer WHERE email=? AND password=?;")
        ) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
