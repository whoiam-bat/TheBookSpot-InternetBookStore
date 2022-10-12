<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ftmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>The Book Spot</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/welcome-page.css">
</head>
<body>
<header class="header">
    <div class="header1">
        <a href="#" class="logo"><i class="fas fa-book"></i> the book spot</a>
        <form class="search-form" autocomplete="off">
            <input type="search" id="search-field" name="myBook" placeholder="Search..."/>
        </form>

        <div class="personal">
            <c:if test="${not empty sessionScope.CART_LIST}">
                <p class="amount-items">${fn:length(sessionScope.CART_LIST)}</p>
            </c:if>
            <a href="${pageContext.request.contextPath}/shopping-cart.jsp" class="fas fa-shopping-cart"
               title="Shopping cart">
            </a>
            <a href="${pageContext.request.contextPath}/sign-in" class="fas fa-user" title="Sign in"></a>
            <a href="${pageContext.request.contextPath}/sign-up" class="fas fa-user-plus" title="Sign up"></a>
        </div>
    </div>

    <div class="header2">
        <nav class="navbar">
            <a href="#home">home</a>
            <a href="#featured">featured</a>
            <a href="#arrivals">arrivals</a>
            <a href="#reviews">reviews</a>
            <a href="#blogs">blogs</a>
        </nav>
    </div>
</header>

<section class="home" id="home">
    <div class="row">
        <c:forEach var="it" items="${requestScope.BOOK_LIST}">
            <div class="item" id=${it.id}>
                <div class="product-media-ref">
                    <a href="#" title="${it.title}">
                        <img data-src="${it.imagePATH}" class="product-media" alt="${it.title}"
                             src="${it.imagePATH}">
                    </a>
                </div>
                <div class="product-name-author">
                    <a href="#" class="product-name" title="${it.title}">${it.title}</a>
                    <div class="product-author">${it.author}</div>
                </div>
                <div class="product-price">
                    <div class="price">$<ftmt:formatNumber type="number" maxFractionDigits="2"
                                                           value="${it.price}"/></div>
                    <form action="shopping-cart" class="buy-form" method="post">
                        <input type="hidden" name="BOOK_ID" value="${it.id}">
                        <input type="hidden" name="command" value="ADD_TO_CART">
                        <button type="submit" class="btn">Add to cart</button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
</section>

<script type="text/javascript" src="javascript/search.js"></script>
</body>
</html>