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

@WebServlet("/sign-up")
public class SignUp extends HttpServlet {
    public static final String SIGN_UP_PAGE = "sign-up-form.jsp";
    private CustomerDao customerDao;

    @Resource(name="store")
    private DataSource dataSource;

    @Override
    public void init() {
        // create our db util ... and pass in the connection pool / datasource
        try {
            customerDao = new CustomerDao();
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
        try {
            // read customer info from form data
            String fullname = req.getParameter("fullname");
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            // create a new customer object
            Customer customer = new Customer(fullname, email, password);


            // if user already exists in database
            isUserAlreadyExists(customer);
            req.setAttribute("CUSTOMER_CABINET", customer);

            // add the customer to the database
            customerDao.addCustomer(customer);

            // send to main page for customer
            resp.sendRedirect("/personal-cabinet");
        } catch (InputMismatchException e) {
            System.out.println("Error: " + e.getMessage());
            HttpSession session = req.getSession();
            session.setAttribute("ERROR", "User with such email already exists");
            resp.sendRedirect(SIGN_UP_PAGE);
        }
    }

    private void isUserAlreadyExists(Customer customer) throws InputMismatchException {
        String email = customer.getEmail();
        String password = customer.getPassword();
        if (customerDao.emailAndPasswordExists(email, password))
            throw new InputMismatchException();
    }
}