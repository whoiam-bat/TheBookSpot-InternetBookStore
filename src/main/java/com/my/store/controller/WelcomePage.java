package com.my.store.controller;

import com.my.store.WriteXMLUtil;
import com.my.store.dao.BookStockDao;
import com.my.store.model.Book;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@WebServlet("/starting-page")
public class WelcomePage extends HttpServlet {
    private BookStockDao bookStockDao;
    private WriteXMLUtil xmlUtil;

    @Resource(name="store")
    private DataSource dataSource;

    @Override
    public void init() {
        // create our db util ... and pass in the connection pool / datasource
        try {
            bookStockDao = new BookStockDao(dataSource);
            xmlUtil = WriteXMLUtil.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if("SIGN_OUT".equals(request.getParameter("action"))) {
            session.removeAttribute("CART_LIST");
        }

        session.removeAttribute("ROLE");
        session.removeAttribute("CUSTOMER");

        session.setAttribute("ROLE", 4);

        // get books from db util
        List<Book> bookList = bookStockDao.listBooks();

        // Write xml file with book titles
        xmlUtil.writeXML(bookList);

        // add books to the request
        request.setAttribute("BOOK_LIST", bookList);

        // send to JSP page (view)
        RequestDispatcher dispatcher = request.getRequestDispatcher("welcome-page.jsp");
        dispatcher.forward(request, response);
    }
}
