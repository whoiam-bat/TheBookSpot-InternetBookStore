package com.my.store.controller;

import com.my.store.dao.BookStockDao;
import com.my.store.model.Book;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/product-page")
public class ProductPage extends HttpServlet {
    BookStockDao bookStockDao;

    @Resource(name="store")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        bookStockDao = new BookStockDao(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("productId");

        Book book = bookStockDao.searchBookById(id);

        req.setAttribute("PRODUCT", book);

        req.getRequestDispatcher("product-page.jsp").forward(req, resp);
    }
}
