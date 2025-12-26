<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>Favorites</title>

    <link rel="stylesheet" href="${contextPath}/css/my-favorites.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <a href="${contextPath}/profile" class="go-back-btn">← Назад</a>
    <div class="container">
        <div class="trips-feed">
            <c:choose>
                <c:when test="${empty trips}">
                    <div class="no-trips">
                        <h3>Пока нет избранных поездок</h3>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="tripWithOwner" items="${trips}">
                        <my:favorite-trip-card tripWithOwner="${tripWithOwner}" contextPath="${contextPath}"/>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
        <a href="${contextPath}/profile" class="go-back-btn">← Назад</a>
    </div>
</body>
</html>