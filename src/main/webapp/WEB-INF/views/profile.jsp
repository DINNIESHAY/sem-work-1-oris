<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>Your profile</title>

    <link rel="stylesheet" href="${contextPath}/css/profile.css">
</head>

<body>
    <a href="${contextPath}/trips-feed" class="go-back-btn">
        <i class="fas fa-arrow-left"></i> На главную
    </a>

    <div class="container">
        <div class="profile-wrapper">
            <div class="profile-header">
                <h2>Профиль</h2>
            </div>

            <div class="profile-content">
                <div class="avatar-section">
                    <div class="avatar-container">
                        <div class="avatar-preview">
                            <c:choose>
                                <c:when test="${not empty userInfo.avatarUrl}">
                                    <img src="${contextPath}/uploads/${userInfo.avatarUrl}" alt="Аватар">
                                </c:when>
                                <c:otherwise>
                                    <img src="${contextPath}/css/images/avatar-placeholder.jpg" alt="Аватар по умолчанию">
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <span class="username-section">${user.username}</span>
                </div>

                <div class="profile-info">
                    <div class="info-section">
                        <h3>Основная информация</h3>
                        <div class="info-grid">
                            <div class="info-item">
                                <span class="info-label">Имя:</span>
                                <span class="info-value">
                                        <c:choose>
                                            <c:when test="${not empty userInfo.firstName}">
                                                ${userInfo.firstName}
                                            </c:when>
                                            <c:otherwise>
                                                Не указано
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Фамилия:</span>
                                <span class="info-value">
                                        <c:choose>
                                            <c:when test="${not empty userInfo.lastName}">
                                                ${userInfo.lastName}
                                            </c:when>
                                            <c:otherwise>
                                                Не указано
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Email:</span>
                                <span class="info-value">${userInfo.email}</span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Телефон:</span>
                                <span class="info-value">
                                        <c:choose>
                                            <c:when test="${not empty userInfo.phoneNumber}">
                                                ${userInfo.phoneNumber}
                                            </c:when>
                                            <c:otherwise>
                                                Не указан
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                            </div>
                        </div>
                    </div>

                    <div class="info-section">
                        <h3>Дополнительная информация</h3>
                        <div class="info-grid">
                            <div class="info-item">
                                <span class="info-label">Дата рождения:</span>
                                <span class="info-value">
                                        <c:choose>
                                            <c:when test="${not empty userInfo.dateOfBirth}">
                                                ${userInfo.dateOfBirth}
                                            </c:when>
                                            <c:otherwise>
                                                Не указана
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Страна:</span>
                                <span class="info-value">
                                        <c:choose>
                                            <c:when test="${not empty userInfo.country}">
                                                ${userInfo.country}
                                            </c:when>
                                            <c:otherwise>
                                                Не указана
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Город:</span>
                                <span class="info-value">
                                        <c:choose>
                                            <c:when test="${not empty userInfo.city}">
                                                ${userInfo.city}
                                            </c:when>
                                            <c:otherwise>
                                                Не указан
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                            </div>
                        </div>
                    </div>

                    <c:if test="${not empty userInfo.bio}">
                        <div class="info-section">
                            <h3>О себе</h3>
                            <div class="bio-content">
                                    ${userInfo.bio}
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>

            <div class="profile-actions">
                <a href="${contextPath}/edit-profile" class="action-link">Редактировать профиль</a>
                <a href="${contextPath}/my-trips" class="action-link">Мои поездки</a>
                <a href="${contextPath}/my-favorites" class="action-link">Избранное</a>
                <a href="${contextPath}/logout" class="action-link logout-link">Выйти</a>
            </div>
        </div>
    </div>
</body>
</html>