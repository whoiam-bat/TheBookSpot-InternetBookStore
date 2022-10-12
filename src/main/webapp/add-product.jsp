<%--
  Created by IntelliJ IDEA.
  User: Oleksii_Drabchak
  Date: 10/10/2022
  Time: 5:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new book</title>
</head>
<body>
<div id="container">
    <h3>Add new book</h3>

    <form action="${pageContext.request.contextPath}/features" method="post">

        <input type="hidden" name="command" value="ADD_PRODUCT"/>

        <table>
            <tbody>
            <tr>
                <td><label>Title:</label></td>
                <td><input type="text" name="title"/></td>
            </tr>
            <tr>
                <td><label>Author:</label></td>
                <td><input type="text" name="author"/></td>
            </tr>
            <tr>
                <td><label>Genre:</label></td>
                <td><input type="text" name="genre"/></td>
            </tr>
            <tr>
                <td><label>Price:</label></td>
                <td><input type="number" name="price" step="0.01"/></td>
            </tr>
            <tr>
                <td><label>Amount:</label></td>
                <td><input type="number" name="amount"/></td>
            </tr>
            <tr>
                <td><label>Description:</label></td>
                <td><textarea name="description" rows="5" cols="50" required></textarea>
                </td>
            </tr>
            <tr>
                <td><label>Image:</label></td>
                <td><input type="file" name="srcImg"/></td>
            </tr>

            <tr>
                <td><label></label></td>
                <td><input type="submit" value="Save" class="save"/></td>
            </tr>
            </tbody>
        </table>
    </form>

    <div style="clear: both;"></div>
    <a href="${pageContext.request.contextPath}/features">Back to list</a>
</div>
</body>
</html>
