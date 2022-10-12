package com.my.store.controller;

import com.my.store.dao.BookStockDao;
import com.my.store.dao.CustomerDao;
import com.my.store.model.Book;
import com.my.store.model.Customer;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/features")
public class UserController extends HttpServlet {
    private CustomerDao customerDao;
    private BookStockDao bookStockDao;

    @Resource(name="store")
    private DataSource dataSource;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            customerDao = new CustomerDao(dataSource);
            bookStockDao = new BookStockDao(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        List<Customer> customerList = customerDao.customerList((int)session.getAttribute("ROLE"));
        List<Book> bookList = bookStockDao.listBooks();

        session.removeAttribute("USER_LIST");
        session.removeAttribute("BOOK_LIST");

        session.setAttribute("USER_LIST", customerList);
        session.setAttribute("BOOK_LIST", bookList);

        String command = req.getParameter("command");

        if ("SEARCH_USER".equals(command)) {
            String login = req.getParameter("login");
            if (!login.equals("")) {
                session.removeAttribute("USER_LIST");
                List<Customer> customer = customerDao.searchCustomer(login);
                session.setAttribute("USER_LIST", customer);
            }
        } else if ("SEARCH_PRODUCT".equals(command)) {
            String title = req.getParameter("book-title");
            if (!title.equals("")) {
                session.removeAttribute("BOOK_LIST");
                List<Book> book = bookStockDao.searchBook(title);
                session.setAttribute("BOOK_LIST", book);
            }
        } else if ("LOAD_PRODUCT".equals(command)) {
            String bookId = req.getParameter("book");

            Book temp = bookStockDao.getBook(bookId);

            req.setAttribute("BOOK", temp);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("edit-product.jsp");
            requestDispatcher.forward(req, resp);
        } else if("NEW_PRODUCT".equals(command)) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("add-product.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            req.getRequestDispatcher("user-features.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");

        switch(command) {
            case "MAKE_ADMIN": {
                appointAdmin(req, resp);
                break;
            }
            case "REMOVE_ADMIN": {
                removeAdmin(req, resp);
                break;
            } case "REMOVE_USER": {
                removeUser(req, resp);
                break;
            }
            case "EDIT_PRODUCT": {
                editProduct(req, resp);
                break;
            } case "ADD_PRODUCT": {
                addProduct(req, resp);
                break;
            }
        }

    }

    private void appointAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        HttpSession session = req.getSession();

        List<Customer> customers = (ArrayList<Customer>) session.getAttribute("USER_LIST");
        session.removeAttribute("USER_LIST");
        String customer = req.getParameter("user");


        for (Customer it : customers) {
            if(it.toString().equals(customer)) {
                it.setRoleID(2);
                it.setRoleName("admin");
                customerDao.updateCustomer(it);
                break;
            }
        }

        session.setAttribute("USER_LIST", customers);
        resp.sendRedirect("features");
    }
    private void removeAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        HttpSession session = req.getSession();

        List<Customer> customers = (ArrayList<Customer>) session.getAttribute("USER_LIST");
        session.removeAttribute("USER_LIST");
        String customer = req.getParameter("user");


        for (Customer it : customers) {
            if(it.toString().equals(customer)) {
                it.setRoleID(3);
                it.setRoleName("user");
                customerDao.updateCustomer(it);
                break;
            }
        }

        session.setAttribute("USER_LIST", customers);
        resp.sendRedirect("features");
    }
    private void removeUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        List<Customer> customers = (ArrayList<Customer>) session.getAttribute("USER_LIST");
        session.removeAttribute("USER_LIST");
        String customer = req.getParameter("user");

        for (int i = 0; i < customers.size(); i++) {
            if(customers.get(i).toString().equals(customer)) {
                customerDao.deleteCustomer(customers.get(i));
                customers.remove(i);
                break;
            }
        }

        session.setAttribute("USER_LIST", customers);
        resp.sendRedirect("features");
    }

    private void editProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        List<Book> books = (List<Book>) session.getAttribute("BOOK_LIST");
        session.removeAttribute("BOOK_LIST");

        String book = req.getParameter("book");
        float price = Float.parseFloat(req.getParameter("price"));
        int amount = Integer.parseInt(req.getParameter("amount"));

        for (Book it : books) {
            if(it.toString().equals(book)) {
                it.setPrice(price);
                it.setAmount(amount);
                bookStockDao.updateBook(it);
                break;
            }
        }

        session.setAttribute("BOOK_LIST", books);
        resp.sendRedirect("features");
    }
    private void addProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        List<Book> bookList = (ArrayList<Book>) session.getAttribute("BOOK_LIST");
        session.removeAttribute("BOOK_LIST");

        String title = req.getParameter("title");
        String author = req.getParameter("author");
        String genre = req.getParameter("genre");
        float price = Float.parseFloat(req.getParameter("price"));
        int amount = Integer.parseInt(req.getParameter("amount"));
        String description = req.getParameter("description");
        String srcImg = req.getParameter("srcImg");

        Book temp = new Book(title, author, genre, price, amount, description, srcImg);
        bookStockDao.addBook(temp);
        bookList = bookStockDao.listBooks();
        session.setAttribute("BOOK_LIST", bookList);
        resp.sendRedirect("features");
    }
}
