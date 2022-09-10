package com.my.store.controller;

import com.my.store.dao.BookStockDao;
import com.my.store.dao.CustomerDao;
import com.my.store.model.Book;
import com.my.store.model.Customer;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet("/personal-cabinet")
public class PersonalCabinet extends HttpServlet {
    private CustomerDao customerDao;
    private BookStockDao bookStockDao;

    @Resource(name="store")
    private DataSource dataSource;


    @Override
    public void init() {
        // create our db util ... and pass in the connection pool / datasource
        try {
            customerDao = new CustomerDao(dataSource);
            bookStockDao = new BookStockDao(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer = (Customer) req.getAttribute("CUSTOMER_CABINET");

        // get books from db util
        List<Book> bookList = bookStockDao.listBooks();

        // set attribute
        req.setAttribute("BOOK_LIST", bookList);

        // forward request response to cabinet.jsp
        RequestDispatcher dispatcher = req.getRequestDispatcher("/cabinet.jsp");
        dispatcher.forward(req, resp);
    }





}
