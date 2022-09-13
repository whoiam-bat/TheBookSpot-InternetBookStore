<%--
  Created by IntelliJ IDEA.
  User: Oleksii_Drabchak
  Date: 9/7/2022
  Time: 9:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign in</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
</head>
<body>

<c:if test="${sessionScope.ERROR != null}">
    <c:set var="errMessage" value="${sessionScope.ERROR}" scope="page"/>
</c:if>

<%
    session.removeAttribute("ERROR");
%>


<form action="sign-in" method="post">
    <table>
        <c:if test="${errMessage != null}">
            <div class="alert alert-danger alert-dismissible" role="alert">
                <strong>Warning!</strong> ${errMessage}
                <a href="${pageContext.request.contextPath}/sign-up">Sign up</a>
            </div>
        </c:if>

        <tbody>
        <tr>
            <td><label for="email">Email:</label></td>
            <td><input type="email" name="email" id="email"></td>
        </tr>

        <tr>
            <td><label for="password">Password:</label></td>
            <td><input type="password" name="password" id="password"></td>
        </tr>

        <tr>
            <td><label></label></td>
            <td><input type="submit" value="Sign in" class="signIn"/></td>
        </tr>
        </tbody>
    </table>
</form>

</body>
</html>
