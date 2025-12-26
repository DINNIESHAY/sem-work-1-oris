<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>Registration</title>

    <link rel="stylesheet" href="${contextPath}/css/register.css">
</head>

<body>
    <div class="container">
        <div class="left-panel"></div>
        <div class="right-panel">
            <h2>Здравствуй!<br>Добро пожаловать в мир путешествий :)</h2>

            <my:error-message errorMessage="${errorMessage}" removeFromSession="true"/>

            <form action="register" method="post">
                <div class="input-row">
                    <input type="text" name="username" placeholder="Имя пользователя" required>
                    <input type="email" name="email" placeholder="Email" required>
                </div>
                <div class="input-row">
                    <input type="password" name="password" placeholder="Придумайте пароль" required>
                    <input type="password" name="repeatPassword" placeholder="Повторите пароль" required>
                </div>
                <button type="submit" class="sign-up-btn">Зарегистрироваться</button>

                <div class="auth-link">
                    Уже путешествуете с нами? <a href="${contextPath}/login">Вернуться в путь!</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>

