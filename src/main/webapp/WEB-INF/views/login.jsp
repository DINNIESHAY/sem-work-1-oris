<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>Log in</title>

    <link rel="stylesheet" href="${contextPath}/css/login.css">
</head>

<body>
    <div class="container">

        <div class="left-panel">
            <h2>С возвращением, странник!</h2>

            <my:error-message errorMessage="${errorMessage}" removeFromSession="true"/>

            <form action="login" method="post">
                <div class="input-column">
                    <input type="email" name="email" placeholder="Email" required>
                    <input type="password" name="password" placeholder="Пароль" required>
                </div>
                <button type="submit" class="sign-in-btn">Войти</button>

                <div class="auth-link">
                    Еще не с нами? <a href="${contextPath}/register">Начать свою travel-историю</a>
                </div>
            </form>
        </div>

        <div class="right-panel"></div>
    </div>
</body>
</html>