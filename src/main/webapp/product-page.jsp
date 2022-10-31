<%--
  Created by IntelliJ IDEA.
  User: Oleksii_Drabchak
  Date: 10/17/2022
  Time: 8:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ftmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>${requestScope.PRODUCT.title}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css">

    <link rel="stylesheet" href="css/product-page.css">
    <script type="text/javascript" src="javascript/script.js"></script>
</head>
<body>
<header class="header">
    <div class="header1">
        <a href="${sessionScope.ROLE == 4 ? 'starting-page' : 'personal-cabinet'}" class="logo">
            <i class="fas fa-book"></i> the book spot
        </a>
        <form class="search-form" autocomplete="off">
            <input type="search" id="search-field" name="myBook" placeholder="Search..."/>
        </form>

        <div class="personal">
            <a href="${pageContext.request.contextPath}/shopping-cart" title="Shopping cart">
                <i class="fas fa-shopping-cart cart" value="${fn:length(sessionScope.CART_LIST)}"></i>
            </a>
            <c:if test="${sessionScope.ROLE == 4}">
                <a href="${pageContext.request.contextPath}/sign-in" class="fas fa-user" title="Sign in"></a>
                <a href="${pageContext.request.contextPath}/sign-up" class="fas fa-user-plus" title="Sign up"></a>
            </c:if>
            <c:if test="${sessionScope.ROLE != 4}">
                <a href="#" class="fas fa-user-circle" title="${sessionScope.CUSTOMER.fullName}" id="account"></a>
                <a href="starting-page" class="fas fa-sign-out-alt" title="Sign out"></a>
            </c:if>
        </div>
    </div>

    <div class="header2">
        <nav class="navbar">
            <a href="${sessionScope.ROLE == 4 ? 'starting-page' : 'personal-cabinet'}#${requestScope.PRODUCT.id}">Home</a>
            <a href=${sessionScope.ROLE == 1 || sessionScope.ROLE == 2 ? "features" : "#featured"}>
                ${sessionScope.ROLE == 1 || sessionScope.ROLE == 2 ? "features" : "featured"}
            </a>
            <a href="#arrivals">arrivals</a>
            <a href="#reviews">reviews</a>
            <a href="#blogs">blogs</a>
        </nav>
    </div>
</header>

<div class="container">
    <div class="left-column">
        <img data-image="book-img" src="${requestScope.PRODUCT.imagePATH}" class="img" alt="${requestScope.PRODUCT.title}">
    </div>
    <div class="right-column">

        <div class="product-description">
            <span>Book</span>
            <h1 class="title">${requestScope.PRODUCT.title}</h1>
            <h2 class="author">${requestScope.PRODUCT.author}</h2>
            <p class="descr">${requestScope.PRODUCT.description}</p>
        </div>

        <div class="product-price">
            <span class="price">$${requestScope.PRODUCT.price}</span>
            <form action="shopping-cart" class="buy-form" method="post">
                <input type="hidden" name="BOOK_ID" value="${requestScope.PRODUCT.id}">
                <input type="hidden" name="command" value="ADD_TO_CART">
                <button type="submit" class="btn" ${requestScope.PRODUCT.amount == 0 ? 'disabled' : ''}>
                    ${requestScope.PRODUCT.amount == 0 ? 'Out of stock' : 'Add to cart'}
                </button>
            </form>
        </div>
    </div>
</div>

<footer class="footer">

</footer>
<script type="text/javascript" src="javascript/search.js"></script>
</body>
</html>
