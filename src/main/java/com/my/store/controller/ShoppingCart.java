package com.my.store.controller;

import com.my.store.dao.BookStockDao;
import com.my.store.dao.BuyDao;
import com.my.store.dao.CustomerDao;
import com.my.store.model.Book;
import com.my.store.model.Buy;
import com.my.store.model.BuyBook;
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
import java.util.*;

@WebServlet("/shopping-cart")
public class ShoppingCart extends HttpServlet {
    BookStockDao bookStockDao;
    BuyDao buyDao;
    CustomerDao customerDao;

    @Resource(name="store")
    private DataSource dataSource;

    @Override
    public void init() {
        // create our db util ... and pass in the connection pool / datasource
        try {
            bookStockDao = new BookStockDao(dataSource);
            buyDao = new BuyDao(dataSource);
            customerDao = new CustomerDao(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("shopping-cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");

        switch (command) {
            case "SUBMIT_ORDER": {
                submitOrder(req, resp);
                break;
            }
            case "REMOVE_ITEM": {
                removeItem(req, resp);
                break;
            }
            case "INCREMENT": {
                increment(req, resp);
                break;
            }
            case "DECREMENT": {
                decrement(req, resp);
                break;
            }
            case "ADD_TO_CART": {
                addBookIntoCart(req, resp);
                break;
            }
        }
    }

    private void addBookIntoCart(HttpServletRequest req, HttpServletResponse resp)throws IOException {

        Map<BuyBook, Book> books = new HashMap<>();

        // get book id from request attribute
        int id = Integer.parseInt(req.getParameter("BOOK_ID"));
        BuyBook tempBuyBook = new BuyBook(id, 1);
        Book tempBook = bookStockDao.getBook(String.valueOf(id));

        HttpSession session = req.getSession();
        Map<BuyBook, Book> listBooksInCart = (Map<BuyBook, Book>) req.getSession().getAttribute("CART_LIST");

        if(listBooksInCart == null) {
            books.put(tempBuyBook, tempBook);
            session.setAttribute("CART_LIST", books);
            session.setAttribute("ORDER_PRICE", tempBook.getPrice());
        } else {
            float orderPrice = (float) session.getAttribute("ORDER_PRICE");
            session.removeAttribute("ORDER_PRICE");

            books = listBooksInCart;
            boolean exist = false;

            for (BuyBook it : listBooksInCart.keySet()) {
                if(it.getBookID() == id) {
                    exist = true;
                    it.setAmount(it.getAmount() + 1);
                    session.setAttribute("ORDER_PRICE", orderPrice + tempBook.getPrice());
                }
            }
            if(!exist) {
                books.put(tempBuyBook, tempBook);
                session.setAttribute("ORDER_PRICE", orderPrice + tempBook.getPrice());
            }
        }
        if((int) req.getSession().getAttribute("ROLE") == 4) {
            resp.sendRedirect("starting-page");
        } else {
            resp.sendRedirect("personal-cabinet");
        }
    }

    private void submitOrder(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        HttpSession session = req.getSession();

        String tempDescription = req.getParameter("description");
        int tempCustomerId;
        int tempStep = 1;
        float tempPrice = (float) session.getAttribute("ORDER_PRICE");
        if((session.getAttribute("CUSTOMER")) != null) {
            tempCustomerId = ((Customer) (session.getAttribute("CUSTOMER"))).getId();

            Buy order = new Buy(tempDescription, tempCustomerId, tempStep, tempPrice);

            buyDao.createOrder(order);

            int orderID = buyDao.getOrder(order).getId();

            Map<BuyBook, Book> orderItems = (Map<BuyBook, Book>) session.getAttribute("CART_LIST");

            for (BuyBook it : orderItems.keySet()) {
                it.setBuyID(orderID);
                bookStockDao.updateAmountBook(it.getBookID(), it.getAmount());
            }
            buyDao.addBooksInOrder(orderItems);
        }

        session.removeAttribute("CART_LIST");
        session.removeAttribute("ORDER_PRICE");

        if((int)session.getAttribute("ROLE") == 4) {
            resp.sendRedirect("starting-page");
        } else {
            resp.sendRedirect("personal-cabinet");
        }
    }

    private void removeItem(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        HttpSession session = req.getSession();

        String item = req.getParameter("item");

        Map<BuyBook, Book> cartList = (HashMap<BuyBook, Book>) session.getAttribute("CART_LIST");

        Iterator<Map.Entry<BuyBook, Book>> iterator = cartList.entrySet().iterator();

        session.removeAttribute("CART_LIST");
        while(iterator.hasNext()) {
            Map.Entry<BuyBook, Book> temp = iterator.next();

            if(temp.getValue().toString().equals(item)) {
                // get price per one book and then get price the whole order
                float tempPrice = temp.getKey().getAmount() * temp.getValue().getPrice();
                float price = (float)(session.getAttribute("ORDER_PRICE"));

                // reset order price attribute in session
                session.removeAttribute("ORDER_PRICE");
                session.setAttribute("ORDER_PRICE", price - tempPrice);

                // remove book from order list
                iterator.remove();
            }
        }

        session.setAttribute("CART_LIST", cartList);
        resp.sendRedirect("shopping-cart");
    }

    private void increment(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        HttpSession session = req.getSession();

        String item = req.getParameter("item");

        Map<BuyBook, Book> cartList = (Map<BuyBook, Book>) session.getAttribute("CART_LIST");
        Iterator<Map.Entry<BuyBook, Book>> iterator = cartList.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry<BuyBook, Book> temp = iterator.next();

            if(temp.getKey().toString().equals(item)) {
                // get amount of appropriate book and increment its amount
                int amount = temp.getKey().getAmount();
                temp.getKey().setAmount(amount + 1);

                // get price per one book and then get price the whole order
                float tempPrice = temp.getValue().getPrice();
                float price = (float)(session.getAttribute("ORDER_PRICE"));

                // reset order price attribute in session
                session.removeAttribute("ORDER_PRICE");
                session.setAttribute("ORDER_PRICE", price + tempPrice);
            }
        }
        resp.sendRedirect("shopping-cart");
    }

    private void decrement(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        HttpSession session = req.getSession();

        String item = req.getParameter("item");

        Map<BuyBook, Book> cartList = (Map<BuyBook, Book>) session.getAttribute("CART_LIST");
        Iterator<Map.Entry<BuyBook, Book>> iterator = cartList.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry<BuyBook, Book> temp = iterator.next();

            if(temp.getKey().toString().equals(item)) {
                // get amount of appropriate book and increment its amount
                int amount = temp.getKey().getAmount();
                if(amount == 0) {
                    iterator.remove();
                } else {
                    temp.getKey().setAmount(amount - 1);

                    // get price per one book and then get price the whole order
                    float tempPrice = temp.getValue().getPrice();
                    float price = (float) (session.getAttribute("ORDER_PRICE"));

                    // reset order price attribute in session
                    session.removeAttribute("ORDER_PRICE");
                    session.setAttribute("ORDER_PRICE", price - tempPrice);
                }
            }
        }
        resp.sendRedirect("shopping-cart");
    }
}
