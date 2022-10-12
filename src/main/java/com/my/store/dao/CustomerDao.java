package com.my.store.dao;

import com.my.store.model.Book;
import com.my.store.model.Customer;
import com.my.store.model.Role;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    private final DataSource dataSource;

    public CustomerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void addCustomer(Customer customer) {
        final String ownerName = "Oleksii Drabchak";
        final String ownerEmail = "drabchak.aleksey@gmail.com";

        try (
                Connection con = dataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(
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
                Connection con = dataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(
                        "DELETE FROM customer WHERE customer.email LIKE ?;")) {

            pstmt.setString(1, customer.getEmail());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> customerList(int role) {
        List<Customer> customerList = new ArrayList<>();

        try (
                Connection con = dataSource.getConnection();
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM customer\n" +
                        "    JOIN role ON customer.role_id = role.id\n" +
                        "    WHERE role_id>=? ORDER BY customer.role_id;")) {
            stmt.setInt(1, role);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int roleID = rs.getInt("role_id");
                String roleName = rs.getString("name_role");

                Customer temp = new Customer(id, fullName,email, password, roleID, roleName);

                customerList.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }

    public void updateCustomer(Customer customer) {
        try(
                Connection con = dataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(
                        "UPDATE customer SET role_id=?" +
                " WHERE id=?;");
                ) {

            pstmt.setInt(1, customer.getRoleID());
            pstmt.setInt(2, customer.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer searchCustomer(String email, String password) {
        Customer res = null;
        try(
                Connection con = dataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement("SELECT * FROM customer\n" +
                        "    JOIN role ON customer.role_id = role.id\n" +
                        "    WHERE email=? AND password=?;");
        ) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                res = new Customer(rs.getInt("id"),
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("role_id"),
                        rs.getString("name_role"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
    public List<Customer> searchCustomer(String email) {
        List<Customer> res = new ArrayList<>();
        try(
                Connection con = dataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement("SELECT * " +
                        "FROM customer\n" +
                        "    JOIN role ON customer.role_id = role.id\n" +
                        "    WHERE email=? ORDER BY customer.id;");
        ) {
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                res.add(new Customer(rs.getInt("id"),
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("role_id"),
                        rs.getString("name_role")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
