package com.my.store.controller;

import com.my.store.dao.BookStockDao;
import com.my.store.dao.BuyDao;
import com.my.store.model.Book;
import com.my.store.model.Customer;

import javax.annotation.Resource;
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
    private BookStockDao bookStockDao;

    @Resource(name="store")
    private DataSource dataSource;


    @Override
    public void init() {
        // create our db util ... and pass in the connection pool / datasource
        try {
            bookStockDao = new BookStockDao(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // change default role of user
        req.getSession().removeAttribute("ROLE");
        System.out.println(req.getSession().getAttribute("CUSTOMER"));
        int role = ((Customer) req.getSession().getAttribute("CUSTOMER")).getRoleID();
        req.getSession().setAttribute("ROLE", role);


        // create booklist
        List<Book> bookList = bookStockDao.listBooks();

        req.setAttribute("BOOK_LIST", bookList);

        req.getRequestDispatcher("cabinet.jsp").forward(req, resp);
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("PersonalCabinet.doPost");
    }
}
