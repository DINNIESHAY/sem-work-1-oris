<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>Trips feed</title>

    <link rel="stylesheet" href="${contextPath}/css/trips-feed.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

    <my:error-message errorMessage="${errorMessage}" removeFromSession="true"/>
    <my:user-auth user="${user}" contextPath="${contextPath}"/>

    <div class="container">
        <div class="trip-banner">
            <div class="banner-content">
                <h2>ХОЧЕШЬ ОРГАНИЗОВАТЬ СВОЮ ПОЕЗДКУ?</h2>
                <a href="${contextPath}/create-trip" class="create-trip-btn">СОЗДАТЬ!</a>
            </div>
        </div>

        <div class="trips-feed">
            <c:choose>
                <c:when test="${empty trips}">
                    <div class="no-trips">
                        <h3>Пока нет активных поездок</h3>
                        <p>Будь первым, кто создаст поездку!</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="tripWithOwner" items="${trips}">
                        <my:trip-card tripWithOwner="${tripWithOwner}" user="${user}" contextPath="${contextPath}"/>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>