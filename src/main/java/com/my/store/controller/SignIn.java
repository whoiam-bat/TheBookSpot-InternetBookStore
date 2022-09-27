package com.my.store.controller;

import com.my.store.dao.CustomerDao;
import com.my.store.model.Customer;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.InputMismatchException;

@WebServlet("/sign-in")
public class SignIn extends HttpServlet {
    private static final String SIGN_IN_PAGE = "sign-in-form.jsp";
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
        req.getRequestDispatcher(SIGN_IN_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        try {
            // read customer info from form data
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            // create a new customer object
            Customer customer = new Customer(email, password);

            // if user already exists in database
            isUserNotExists(customer);

            // get customer from db
            customer = customerDao.searchCustomer(email, password);

            // send to main page for customer
            session.setAttribute("CUSTOMER", customer);
            resp.sendRedirect("personal-cabinet");

        } catch (InputMismatchException e) {
            System.out.println("Error: " + e.getMessage());
            session.setAttribute("ERROR", "User with such email doesn't exist");
            resp.sendRedirect(SIGN_IN_PAGE);
        }
    }

    private void isUserNotExists(Customer customer) throws InputMismatchException {
        if (customerDao.searchCustomer(customer.getEmail(), customer.getPassword()) == null)
            throw new InputMismatchException();
    }
}
