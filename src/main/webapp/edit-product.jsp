<%--
  Created by IntelliJ IDEA.
  User: Oleksii_Drabchak
  Date: 10/10/2022
  Time: 4:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<div id="container">
    <h3>Edit book</h3>

    <form action="${pageContext.request.contextPath}/features" method="post">

        <input type="hidden" name="command" value="EDIT_PRODUCT"/>
        <input type="hidden" name="book" value="${requestScope.BOOK}"/>

        <table>
            <tbody>
            <tr>
                <td><label>Title:</label></td>
                <td><input type="text" name="title" value="${requestScope.BOOK.title}" readonly/></td>
            </tr>
            <tr>
                <td><label>Author:</label></td>
                <td><input type="text" name="author" value="${requestScope.BOOK.author}" readonly/></td>
            </tr>
            <tr>
                <td><label>Genre:</label></td>
                <td><input type="text" name="genre" value="${requestScope.BOOK.genre}" readonly/></td>
            </tr>
            <tr>
                <td><label>Price:</label></td>
                <td><input type="number" name="price" value="${requestScope.BOOK.price}" step="0.01"/></td>
            </tr>
            <tr>
                <td><label>Amount:</label></td>
                <td><input type="number" name="amount" value="${requestScope.BOOK.amount}"/></td>
            </tr>
            <tr>
                <td><label>Description:</label></td>
                <td><textarea name="w3review" rows="5" cols="50" readonly>${requestScope.BOOK.description}</textarea>
                </td>
            </tr>
            <tr>
                <td><label></label></td>
                <td><input type="submit" value="Save" class="save"/></td>
            </tr>
            </tbody>
        </table>
    </form>

    <div style="clear: both;"></div>
    <a href="features">Back to list</a>
</div>
</body>
</html>
