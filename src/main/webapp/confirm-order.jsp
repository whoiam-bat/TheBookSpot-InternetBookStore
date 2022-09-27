<%--
  Created by IntelliJ IDEA.
  User: Oleksii_Drabchak
  Date: 9/20/2022
  Time: 4:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
          integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT"
          crossorigin="anonymous">
    <title>Title</title>
</head>
<body>

    <c:if test="${sessionScope.ROLE == 4}">
        <c:url var="signIn" value="sign-up"/>
        <c:url var="signUp" value="sign-in"/>

        <a href="${signIn}" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Create an account</a>
        <a href="${signUp}" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Sign in</a>

    </c:if>
    <c:if test="${sessionScope.ROLE != 4}">
        <form action="shopping-cart" method="post">
            <input type="hidden" name="command" value="SUBMIT_ORDER">
            <input type="email" name="email" value="${sessionScope.CUSTOMER.email}" id="email" autocomplete="off"
                   placeholder="Enter your email">
            <input type="text" name="description" id="description" autocomplete="off" placeholder="Enter shipping address">
            <input type="submit" name="Submit" value="Submit order">
        </form>
    </c:if>

</body>
</html>
