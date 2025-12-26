<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>TravelFellows - Welcome</title>
    <link rel="stylesheet" href="${contextPath}/css/welcome.css">
</head>

<body>
    <div class="container">

        <div class="site-title">TravelFellows</div>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
            <%
                session.removeAttribute("errorMessage");
            %>
        </c:if>

        <div class="fullscreen-panel">
            <div class="overlay"></div>
            <div class="center-content">
                <div class="site-description">
                    <h1>Найди своих попутчиков</h1>
                    <p>Сервис для тех, кто верит, что путешествовать вместе - значит делиться впечатлениями,
                        экономить бюджет и находить друзей по всему миру.</p>
                </div>
                <form action="${contextPath}/trips-feed" method="get">
                    <button class="start-btn">начать путешествие с нами</button>
                </form>
            </div>
        </div>
    </div>

    <div class="bottom-bar">
        <span>Приключения</span>
        <span>&#10052;</span>
        <span>Любовь</span>
        <span>&#10052;</span>
        <span>Новые друзья</span>
        <span>&#10052;</span>
        <span>Вдохновение</span>
    </div>
</body>
</html>