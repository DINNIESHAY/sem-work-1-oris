<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>Applications for your trip</title>

    <link rel="stylesheet" href="${contextPath}/css/applications-for-trip.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="container">
        <a href="${contextPath}/my-trips" class="go-back-btn">← Назад к моим поездкам</a>

        <div class="applications-header">
            <h1>Заявки на поездку</h1>
            <div class="trip-info">
                <h2>${trip.trip.departurePoint} → ${trip.trip.destination}</h2>
                <div class="trip-dates">
                    <fmt:formatDate value="${trip.trip.startAt}" pattern="dd.MM.yyyy"/> -
                    <fmt:formatDate value="${trip.trip.endAt}" pattern="dd.MM.yyyy"/>
                </div>
            </div>
        </div>

        <my:error-message errorMessage="${errorMessage}" removeFromSession="true"/>

        <div class="applications-container">
            <c:choose>
                <c:when test="${empty applications}">
                    <div class="no-applications">
                        <i class="fas fa-inbox"></i>
                        <h3>Пока нет заявок на эту поездку</h3>
                        <p>Заявки от других пользователей появятся здесь</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="applications-count">
                        Найдено заявок: ${applications.size()}
                    </div>

                    <c:forEach var="application" items="${applications}" varStatus="status">
                        <my:application-card
                                application="${application}"
                                index="${status.index + 1}"
                                contextPath="${contextPath}"/>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script>
        function copyToClipboard(text) {
            navigator.clipboard.writeText(text).then(function() {
                alert('Контакты скопированы в буфер обмена');
            }, function(err) {
                console.error('Ошибка копирования: ', err);
            });
        }
    </script>
</body>
</html>