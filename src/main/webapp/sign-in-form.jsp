<%--
  Created by IntelliJ IDEA.
  User: Oleksii_Drabchak
  Date: 9/7/2022
  Time: 9:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign up page</title>

    <link rel="stylesheet" href="css/authentication.css">

</head>

<c:if test="${sessionScope.ERROR != null}">
    <c:set var="errMessage" value="${sessionScope.ERROR}" scope="page"/>
</c:if>

<%
    session.removeAttribute("ERROR");
%>

<body>
<c:if test="${errMessage != null}">
    <div class="alert alert-danger alert-dismissible" role="alert">
        <strong>Warning!</strong> ${errMessage}
        <a href="${pageContext.request.contextPath}/sign-in" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    </div>
</c:if>
<div class="container">
    <form action="sign-in" method="post" class="form" id="signin">
        <h1>Sign In</h1>

        <div class="form-field">
            <label for="email">Email:</label>
            <input type="email" name="email" id="email" autocomplete="off" placeholder="Enter your email">
            <small></small>
        </div>

        <div class="form-field">
            <label for="password">Password:</label>
            <input type="password" name="password" id="password" autocomplete="off" placeholder="Enter password">
            <small></small>
        </div>

        <div class="form-field">
            <label for="bttn">&nbsp;</label>
            <button type="submit" class="btn btn-warning" id="bttn">Sign-in</button>
        </div>
    </form>
</div>
</body>
</html>