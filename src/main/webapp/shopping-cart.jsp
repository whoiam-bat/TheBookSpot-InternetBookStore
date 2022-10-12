<%--
  Created by IntelliJ IDEA.
  User: Oleksii_Drabchak
  Date: 9/18/2022
  Time: 9:39 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="ftmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css">

    <title>Cart</title>
</head>
<body>
    <c:if test="${fn:length(sessionScope.CART_LIST) == 0}">
        <h2 class="error-message">Cart is empty</h2>
    </c:if>
    <c:if test="${fn:length(sessionScope.CART_LIST) != 0}">
        <table class="table table-dark table-striped">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Price per item</th>
                    <th>Amount</th>
                    <th>Price</th>
                    <th></th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="it" items="${sessionScope.CART_LIST}">
                    <tr class="item">
                        <td class="title">${it.value.title}</td>
                        <td class="author">${it.value.author}</td>
                        <td class="price-per-item">
                            $<ftmt:formatNumber type="number" maxFractionDigits="2" value="${it.value.price}"/>
                        </td>
                        <td class="amount">
                            <c:url var="increment" value="shopping-cart">
                                <c:param name="command" value="INCREMENT" />
                                <c:param name="item" value="${it.key}" />
                            </c:url>
                            <c:url var="decrement" value="shopping-cart">
                                <c:param name="command" value="DECREMENT" />
                                <c:param name="item" value="${it.key}" />
                            </c:url>

                            <div style="display: inline-flex">
                                <form action="${increment}" method="post">
                                    <button type="submit">+</button>
                                </form>
                                <input type="text" name="amount" value="${it.key.amount}" style="width: 30px;">
                                <form action="${decrement}" method="post">
                                    <button type="submit">-</button>
                                </form>
                            </div>
                        </td>
                        <td class="price">
                            $<ftmt:formatNumber type="number" maxFractionDigits="2" value="${it.value.price * it.key.amount}"/>
                        </td>
                        <td class="remove">
                            <form action="${pageContext.request.contextPath}/shopping-cart" method="post">
                                <input type="hidden" name="command" value="REMOVE_ITEM">
                                <input type="hidden" name="item" value="${it.value}">
                                <input type="submit" value="Remove from cart" class="button">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>

            <tfoot>
                <tr class="order-bottom">
                    <td></td>
                    <td></td>
                    <td>Order amount:</td>
                    <td>${fn:length(sessionScope.CART_LIST)}</td>
                    <td>Price:</td>
                    <td>$<ftmt:formatNumber type="number" maxFractionDigits="2" value="${sessionScope.ORDER_PRICE}"/></td>
                </tr>
            </tfoot>
        </table>
        <form action="confirm-order.jsp">
            <input type="submit" value="Checkout" class="button">
        </form>
    </c:if>

    <c:if test="${sessionScope.ROLE == 4}">
        <a href="${pageContext.request.contextPath}/starting-page">Go to home page!</a>
    </c:if>
    <c:if test="${sessionScope.ROLE != 4}">
        <a href="${pageContext.request.contextPath}/personal-cabinet">Go to home page!</a>
    </c:if>
</body>
</html>
