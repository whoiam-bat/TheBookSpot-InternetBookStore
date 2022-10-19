<%--
  Created by IntelliJ IDEA.
  User: Oleksii_Drabchak
  Date: 10/2/2022
  Time: 4:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ftmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Features</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css">

    <link rel="stylesheet" href="css/features.css">
    <script type="text/javascript" src="javascript/script.js"></script>
</head>
<body>
<header class="header">
    <div class="header1">
        <p class="welcome">${sessionScope.CUSTOMER.fullName} status: ${sessionScope.CUSTOMER.roleName}</p>

        <div class="personal">
            <a href="#" class="fas fa-user-circle" title="${sessionScope.CUSTOMER.fullName}" id="account"></a>
            <a href="${pageContext.request.contextPath}/starting-page" class="fas fa-sign-out-alt" title="Sign out"></a>
        </div>
    </div>

    <div class="header2">
        <nav class="navbar">
            <a href="${pageContext.request.contextPath}/personal-cabinet">Home</a>
            <a href="#users">Users</a>
            <a href="#products">Products</a>
        </nav>
    </div>
</header>
<section class="users-list" id="users">
    <div class="search-div">
        <p>Users</p>
        <form action="features" class="search-form" autocomplete="off">
            <input type="hidden" name="command" value="SEARCH_USER">
            <input type="search" id="search-field" name="login" placeholder="Search..."/>
        </form>
    </div>
    <div class="users-table">
        <table class="table table-dark table-striped">
            <thead>
            <tr>
                <th>Id</th>
                <th>Full name</th>
                <th>Email</th>
                <th>Status</th>
                <c:if test="${sessionScope.ROLE == 1}">
                    <th></th>
                    <th></th>
                </c:if>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="it" items="${sessionScope.USER_LIST}">
                <tr id="${it.id}">
                    <td>${it.id}</td>
                    <td>${it.fullName}</td>
                    <td>${it.email}</td>
                    <td>${it.roleName}</td>

                    <c:if test="${sessionScope.ROLE == 1}">
                        <c:if test="${it.roleID == 1}">
                            <td></td>
                            <td></td>
                            <td></td>
                        </c:if>
                        <c:if test="${it.roleID != 1}">
                            <td>
                                <form action="${pageContext.request.contextPath}/features" method="post">
                                    <input type="hidden" name="command" value="MAKE_ADMIN">
                                    <input type="hidden" name="user" value="${it}">
                                    <input type="submit" value="Make admin"
                                           onclick="alert('Are you sure, you want to appoint admin role to this user?')">
                                </form>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/features" method="post">
                                    <input type="hidden" name="command" value="REMOVE_ADMIN">
                                    <input type="hidden" name="user" value="${it}">
                                    <input type="submit" value="Remove admin" ${it.roleID == 3 ? 'disabled' : ''}
                                           onclick="alert('Are you sure, you want to delete this admin?')">
                                </form>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/features" method="post">
                                    <input type="hidden" name="command" value="REMOVE_USER">
                                    <input type="hidden" name="user" value="${it}">
                                    <input type="submit" value="Delete"
                                           onclick="alert('Are you sure, you want to delete this user?')">
                                </form>
                            </td>
                        </c:if>
                    </c:if>
                    <c:if test="${sessionScope.ROLE == 2}">
                        <c:if test="${it.roleID == 2}">
                            <td></td>
                        </c:if>
                        <c:if test="${it.roleID != 2}">
                            <td>
                                <form action="${pageContext.request.contextPath}/features" method="post">
                                    <input type="hidden" name="command" value="REMOVE_USER">
                                    <input type="hidden" name="user" value="${it}">
                                    <input type="submit" value="Delete"
                                           onsubmit="alert('Are you sure, you want to delete this user?')">
                                </form>
                            </td>
                        </c:if>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</section>

<section class="products-list" id="products">
    <div class="search-div">
        <p>Products</p>
        <form action="features" class="search-form" autocomplete="off">
            <input type="hidden" name="command" value="SEARCH_PRODUCT">
            <input type="search" id="search-product" name="login" placeholder="Search..."/>
        </form>
    </div>
    <div class="products-table">
        <table class="table table-dark table-striped">
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Author</th>
                <th>Price</th>
                <th>Amount</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="it" items="${sessionScope.BOOK_LIST}">
                <tr>
                    <td>${it.id}</td>
                    <td>${it.title}</td>
                    <td>${it.author}</td>
                    <td>
                        $<ftmt:formatNumber type="number" maxFractionDigits="2" value="${it.price}"/>
                    </td>
                    <td>${it.amount}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/features">
                            <input type="hidden" name="book" value="${it.id}">
                            <input type="hidden" name="command" value="LOAD_PRODUCT">
                            <button type="submit">Edit</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/features">
                            <input type="hidden" name="command" value="NEW_PRODUCT"/>
                            <button type="submit">Add new product</button>
                        </form>
                    </td>
                    <td></td><td></td><td></td><td></td>
                </tr>
            </tfoot>
        </table>
    </div>
</section>
</body>
</html>
