<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>User profile</title>

    <link rel="stylesheet" href="${contextPath}/css/user-profile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>

<body>
    <a href="javascript:history.back()" class="go-back-btn">
        <i class="fas fa-arrow-left"></i> Назад
    </a>

    <my:user-auth user="${user}" contextPath="${contextPath}"/>

    <div class="container">
        <div class="profile-wrapper">
            <div class="profile-header">
                <h2><i class="fas fa-user-circle"></i> Профиль пользователя</h2>
            </div>

            <my:error-message errorMessage="${errorMessage}" removeFromSession="true"/>

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

                    <span class="username-section">
                        <i class="fas fa-at"></i> ${user.username}
                    </span>
                </div>

                <div class="profile-info">
                    <div class="info-section">
                        <h3><i class="fas fa-info-circle"></i> Основная информация</h3>
                        <div class="info-grid">
                            <div class="info-item">
                                <span class="info-label">
                                    <i class="fas fa-signature"></i> Имя:
                                </span>
                                <span class="info-value">
                                    <c:choose>
                                        <c:when test="${not empty userInfo.firstName}">
                                            <i class="fas fa-user"></i> ${userInfo.firstName}
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-question-circle"></i> Не указано
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">
                                    <i class="fas fa-signature"></i> Фамилия:
                                </span>
                                <span class="info-value">
                                    <c:choose>
                                        <c:when test="${not empty userInfo.lastName}">
                                            <i class="fas fa-user"></i> ${userInfo.lastName}
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-question-circle"></i> Не указано
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">
                                    <i class="fas fa-envelope"></i> Email:
                                </span>
                                <span class="info-value">
                                    <i class="fas fa-at"></i> ${user.email}
                                </span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">
                                    <i class="fas fa-phone"></i> Телефон:
                                </span>
                                <span class="info-value">
                                    <c:choose>
                                        <c:when test="${not empty userInfo.phoneNumber}">
                                            <i class="fas fa-mobile-alt"></i> ${userInfo.phoneNumber}
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-question-circle"></i> Не указан
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                        </div>
                    </div>

                    <div class="info-section">
                        <h3><i class="fas fa-address-card"></i> Дополнительная информация</h3>
                        <div class="info-grid">
                            <div class="info-item">
                                <span class="info-label">
                                    <i class="fas fa-birthday-cake"></i> Дата рождения:
                                </span>
                                <span class="info-value">
                                    <c:choose>
                                        <c:when test="${not empty userInfo.dateOfBirth}">
                                            <i class="fas fa-calendar-alt"></i> ${userInfo.dateOfBirth}
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-question-circle"></i> Не указана
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">
                                    <i class="fas fa-globe"></i> Страна:
                                </span>
                                <span class="info-value">
                                    <c:choose>
                                        <c:when test="${not empty userInfo.country}">
                                            <i class="fas fa-flag"></i> ${userInfo.country}
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-question-circle"></i> Не указана
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">
                                    <i class="fas fa-city"></i> Город:
                                </span>
                                <span class="info-value">
                                    <c:choose>
                                        <c:when test="${not empty userInfo.city}">
                                            <i class="fas fa-building"></i> ${userInfo.city}
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-question-circle"></i> Не указан
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                        </div>
                    </div>

                    <c:if test="${not empty userInfo.bio}">
                        <div class="info-section">
                            <h3><i class="fas fa-comment-dots"></i> О себе</h3>
                            <div class="bio-content">
                                <i class="fas fa-quote-left"></i>
                                    ${userInfo.bio}
                                <i class="fas fa-quote-right"></i>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</body>
</html>