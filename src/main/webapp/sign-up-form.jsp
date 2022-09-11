<%--
  Created by IntelliJ IDEA.
  User: Oleksii_Drabchak
  Date: 9/6/2022
  Time: 4:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign up</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
</head>

<c:if test="${sessionScope.ERROR != null}">
  <c:set var="errMessage" value="${sessionScope.ERROR}" scope="page"/>
</c:if>

<%
  session.removeAttribute("ERROR");
%>

<body>
  <form action="sign-up" method="post">

    <c:if test="${errMessage != null}">
      <div class="alert alert-danger alert-dismissible" role="alert">
        <strong>Warning!</strong> ${errMessage}
        <a href="${pageContext.request.contextPath}/sign-up" class="close" data-dismiss="alert" aria-label="close">&times;</a>
      </div>
    </c:if>

    <table>
      <tbody>
      <tr>
        <td><label for="fullname">Full name:</label></td>
        <td><input type="text" name="fullname" id="fullname"></td>
      </tr>

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
        <td><input type="submit" value="Sign up" class="signUp"/></td>
        <td><input type="hidden" name="command" value="LIST"/></td>
      </tr>
      </tbody>
    </table>
  </form>

</body>
</html>
