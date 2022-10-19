package com.my.store;

import com.my.store.model.Book;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class WriteXMLUtil {
    private static WriteXMLUtil instance;
    private final static String xmlPath =
            "D:/My-Folder/Programming/Java/EPAM/Final-Project/InternetStore/store/src/main/webapp/xml/books-out.xml";

    private WriteXMLUtil() {}

    public static WriteXMLUtil newInstance() {
        if(instance == null) instance = new WriteXMLUtil();
        return instance;
    }

    public void writeXML(List<Book> bookList) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement("bookList");

            root.setAttribute("xmlns", "http://www.nure.ua");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xsi:schemaLocation", "http://www.nure.ua " +
                    "file:///D:/My-Folder/Programming/Java/EPAM/Final-Project/InternetStore/store/src/main/webapp/xml/books.xsd");

            document.getDocumentURI();
            document.appendChild(root);

            for (Book book : bookList) {
                root.appendChild(getBook(document, book.getTitle(), book.getId()));
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

            DOMSource source = new DOMSource(document);

            StreamResult file = new StreamResult(new File(xmlPath));
            transformer.transform(source, file);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private Element getBook(Document document, String title, int id) {
        Element book = document.createElement("book");

        book.appendChild(getBookNodeById(document, "id", id));
        book.appendChild(getBookNodeByTitle(document, "title", title));

        return book;
    }
    private Node getBookNodeByTitle(Document document, String name, String value) {
        Element node = document.createElement(name);
        node.appendChild(document.createTextNode(value));
        return node;
    }
    private Node getBookNodeById(Document document, String name, int value) {
        Element node = document.createElement(name);
        node.appendChild(document.createTextNode(String.valueOf(value)));
        return node;
    }
}
