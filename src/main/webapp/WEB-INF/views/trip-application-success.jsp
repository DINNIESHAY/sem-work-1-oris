<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>Application is sent</title>

    <link rel="stylesheet" href="${contextPath}/css/trip-application.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="container">
        <div class="application-form">
            <div class="success-message">
                <i class="fas fa-check-circle success-icon"></i>
                <h1>Заявка отправлена!</h1>
                <p>${successMessage}</p>
                <p>Организатор свяжется с вами в ближайшее время.</p>
                <div class="success-actions">
                    <a href="${contextPath}/trips-feed" class="back-btn">Вернуться к ленте поездок</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
