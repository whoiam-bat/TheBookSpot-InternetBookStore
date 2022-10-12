package com.my.store.controller;

import com.my.store.WriteXMLUtil;
import com.my.store.dao.BookStockDao;
import com.my.store.model.Book;
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
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

@WebServlet("/personal-cabinet")
public class PersonalCabinet extends HttpServlet {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        // change default role of user
        session.removeAttribute("ROLE");
        session.removeAttribute("USER_LIST");

        int role = ((Customer) session.getAttribute("CUSTOMER")).getRoleID();
        session.setAttribute("ROLE", role);


        // create booklist
        List<Book> bookList = bookStockDao.listBooks();

        // Write xml file with book titles
        xmlUtil.writeXML(bookList);

        req.setAttribute("BOOK_LIST", bookList);

        req.getRequestDispatcher("cabinet.jsp").forward(req, resp);
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("PersonalCabinet.doPost");
    }
}
