package com.my.store.controller;

import com.my.store.dao.CustomerDao;
import com.my.store.model.Customer;

import java.io.*;
import java.util.InputMismatchException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;

@WebServlet("/sign-up")
public class SignUp extends HttpServlet {
    private static final String SIGN_UP_PAGE = "sign-up-form.jsp";
   private CustomerDao customerDao;

    @Resource(name="store")
    private DataSource dataSource;

    @Override
    public void init() {
        // create our db util ... and pass in the connection pool / datasource
        try {
            customerDao = new CustomerDao(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(SIGN_UP_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        try {
            // read customer info from form data
            String fullname = req.getParameter("fullname");
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            // create a new customer object
            Customer customer = new Customer(fullname, email, password);

            // if user already exists in database
            isUserAlreadyExists(customer);

            // add the customer to the database
            customerDao.addCustomer(customer);

            // get customer from db
            customer = customerDao.searchCustomer(email, password);

            // send to main page for customer
            session.setAttribute("CUSTOMER", customer);
            resp.sendRedirect("personal-cabinet");

        } catch (InputMismatchException e) {
            System.out.println("Error: " + e.getMessage());
            session.setAttribute("ERROR", "User with such email already exists");
            resp.sendRedirect(SIGN_UP_PAGE);
        }
    }

    private void isUserAlreadyExists(Customer customer) throws InputMismatchException {
        if (customerDao.searchCustomer(customer.getEmail(), customer.getPassword()) != null)
            throw new InputMismatchException();
    }
}