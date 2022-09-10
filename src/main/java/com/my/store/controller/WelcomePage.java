package com.my.store.controller;

import com.my.store.dao.BookStockDao;
import com.my.store.dao.CustomerDao;
import com.my.store.model.Book;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet("/starting-page")
public class WelcomePage extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // get books from db util
        List<Book> bookList = bookStockDao.listBooks();

        // add books to the request
        request.setAttribute("BOOK_LIST", bookList);

        // send to JSP page (view)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/welcome-page.jsp");
        dispatcher.forward(request, response);
    }

}
