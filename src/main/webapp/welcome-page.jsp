<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Document</title>
    <link rel="stylesheet" href="css/welcome-page.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>

<header class="header">
    <div class="header_catalog">
    </div>
    <div class="header_search">
        <form action="starting-page" method="get">
            <input type="text" name="title" placeholder="Search" class="search-field"/>
            <input type="submit" value title="Search" class="submit"/>
            <span><i class="fa-thin fa-magnifying-glass"></i></span>
        </form>
    </div>
    <div class="header_personal">
        <a href="${pageContext.request.contextPath}/sign-up" title="Sign up"><i class="fa fa-solid fa-user-plus"></i></a>
        <a href="${pageContext.request.contextPath}/sign-in" title="Sign in"><i class="fa fa-sign-in fa-lg"></i></a>
    </div>
</header>

    <div class="col-products">
        <div class="col-wrapper">
            <c:forEach var="it" items="${requestScope.BOOK_LIST}">
                <div class="item" style="width: 359px;">
                    <div class="product" id=${it.id}>
                        <a href="#" class="product_media_wrapper" title="${it.title}">
                            <img data-src="${it.imagePATH}" class="product_media" alt="${it.title}" src="${it.imagePATH}">
                        </a>
                        <a href="#" class="product_name" title="${it.title}">${it.title}</a>
                        <div class="product_author">${it.author}</div>
                        <div class="product_price">
                            <div class="product_price_current">${it.price}$</div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>