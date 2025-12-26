<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>Application for trip</title>

    <link rel="stylesheet" href="${contextPath}/css/trip-application.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="container">
        <div class="application-form">
            <h1>Заявка на участие в поездке</h1>

            <my:error-message errorMessage="${errorMessage}" removeFromSession="true"/>

            <div class="trip-info">
                <h2>${tripWithOwner.trip.departurePoint} → ${tripWithOwner.trip.destination}</h2>
                <div class="trip-dates">
                    <fmt:formatDate value="${tripWithOwner.trip.startAt}" pattern="dd.MM.yyyy"/> -
                    <fmt:formatDate value="${tripWithOwner.trip.endAt}" pattern="dd.MM.yyyy"/>
                </div>
                <div class="organizer">
                    Организатор: ${tripWithOwner.ownerUsername}
                </div>
            </div>

            <form action="${contextPath}/trip-application" method="post" class="application-form-content">
                <input type="hidden" name="tripId" value="${tripWithOwner.trip.id}">

                <div class="form-group">
                    <label for="contacts">Ваши контакты *</label>
                    <textarea id="contacts" name="contacts" rows="3"
                              placeholder="Укажите ваши контакты (телефон, email, telegram и т.д.)"
                              required>${contacts}</textarea>
                </div>

                <div class="form-group">
                    <label for="message">Комментарий или вопросы</label>
                    <textarea id="message" name="message" rows="5"
                              placeholder="Напишите ваш комментарий, вопросы к организатору или дополнительную информацию о себе">${message}</textarea>
                </div>

                <div class="form-actions">
                    <a href="${contextPath}/trips-feed" class="cancel-btn">Отмена</a>
                    <button type="submit" class="submit-btn">Отправить заявку</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>