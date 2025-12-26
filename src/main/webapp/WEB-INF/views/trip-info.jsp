<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>Trip information</title>

    <link rel="stylesheet" href="${contextPath}/css/trip-info.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <a href="${contextPath}/trips-feed" class="go-back-btn">← На главную</a>

    <my:user-auth user="${user}" contextPath="${contextPath}"/>

    <div class="container">
        <div class="trip-details-wrapper">

            <div class="trip-header-section">
                <div class="route-container">
                    <h1 class="trip-route">
                        <span class="departure">${tripWithOwner.trip.departurePoint}</span>
                        <span class="arrow">→</span>
                        <span class="destination">${tripWithOwner.trip.destination}</span>
                    </h1>
                    <span class="trip-status ${tripWithOwner.trip.status}">
                            <c:choose>
                                <c:when test="${tripWithOwner.trip.status == 'PLANNING'}">Планируется</c:when>
                                <c:when test="${tripWithOwner.trip.status == 'ACTIVE'}">Активен</c:when>
                                <c:when test="${tripWithOwner.trip.status == 'COMPLETED'}">Завершен</c:when>
                                <c:otherwise>${tripWithOwner.trip.status}</c:otherwise>
                            </c:choose>
                        </span>
                </div>

                <div class="trip-dates">
                    <i class="fas fa-calendar-alt date-icon"></i>
                    <span class="date-range">
                            <fmt:formatDate value="${tripWithOwner.trip.startAt}" pattern="dd.MM.yyyy"/> -
                            <fmt:formatDate value="${tripWithOwner.trip.endAt}" pattern="dd.MM.yyyy"/>
                        </span>
                </div>
            </div>

            <div class="trip-info-section">
                <h2>Информация о поездке</h2>
                <div class="info-grid">
                    <div class="info-card">
                        <div class="info-icon">
                            <i class="fas fa-wallet"></i>
                        </div>
                        <div class="info-content">
                            <span class="info-label">Бюджет на человека</span>
                            <span class="info-value">
                                    <fmt:formatNumber value="${tripWithOwner.trip.budget}" type="currency" currencyCode="RUB"/>
                                </span>
                        </div>
                    </div>

                    <div class="info-card">
                        <div class="info-icon">
                            <i class="fas fa-users"></i>
                        </div>
                        <div class="info-content">
                            <span class="info-label">Максимум участников</span>
                            <span class="info-value">${tripWithOwner.trip.maxFellows}</span>
                        </div>
                    </div>

                    <div class="info-card">
                        <div class="info-icon">
                            <i class="fas fa-map-marker-alt"></i>
                        </div>
                        <div class="info-content">
                            <span class="info-label">Точка отправления</span>
                            <span class="info-value">${tripWithOwner.trip.departurePoint}</span>
                        </div>
                    </div>

                    <div class="info-card">
                        <div class="info-icon">
                            <i class="fas fa-flag-checkered"></i>
                        </div>
                        <div class="info-content">
                            <span class="info-label">Пункт назначения</span>
                            <span class="info-value">${tripWithOwner.trip.destination}</span>
                        </div>
                    </div>
                </div>
            </div>

            <c:if test="${not empty tripWithOwner.trip.tags}">
                <div class="tags-section">
                    <h2>Теги поездки</h2>
                    <div class="tags-container">
                        <c:forEach var="tag" items="${tripWithOwner.trip.tags}">
                            <div class="trip-tag">#${tag}</div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>

            <div class="description-section">
                <h2>Описание поездки</h2>
                <div class="description-content">
                    ${tripWithOwner.trip.description}
                </div>
            </div>

            <div class="owner-section">
                <h2>Организатор поездки</h2>
                <div class="owner-card">
                    <div class="owner-avatar">
                        <c:choose>
                            <c:when test="${not empty tripWithOwner.userInfo.avatarUrl}">
                                <img src="${contextPath}/uploads/${tripWithOwner.userInfo.avatarUrl}" alt="Аватар организатора">
                            </c:when>
                            <c:otherwise>
                                <img src="${contextPath}/css/images/avatar-placeholder.jpg" alt="Аватар по умолчанию">
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="owner-info">
                        <div class="owner-name">
                            <c:choose>
                                <c:when test="${not empty tripWithOwner.userInfo.firstName || not empty tripWithOwner.userInfo.lastName}">
                                    ${tripWithOwner.userInfo.firstName} ${tripWithOwner.userInfo.lastName}
                                </c:when>
                                <c:otherwise>
                                    Не указано
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="owner-username">@${tripWithOwner.ownerUsername}</div>

                        <div class="owner-details">
                            <c:if test="${not empty tripWithOwner.userInfo.country || not empty tripWithOwner.userInfo.city}">
                                <div class="owner-location">
                                    <i class="fas fa-map-marker-alt"></i>
                                    <c:if test="${not empty tripWithOwner.userInfo.country}">
                                        ${tripWithOwner.userInfo.country}
                                        <c:if test="${not empty tripWithOwner.userInfo.city}">, ${tripWithOwner.userInfo.city}</c:if>
                                    </c:if>
                                    <c:if test="${empty tripWithOwner.userInfo.country && not empty tripWithOwner.userInfo.city}">
                                        ${tripWithOwner.userInfo.city}
                                    </c:if>
                                </div>
                            </c:if>

                            <c:if test="${not empty tripWithOwner.userInfo.dateOfBirth}">
                                <div class="owner-contact">
                                    <i class="fas fa-birthday-cake"></i>
                                    Дата рождения: ${tripWithOwner.userInfo.dateOfBirth}
                                </div>
                            </c:if>

                            <c:if test="${not empty tripWithOwner.userInfo.email}">
                                <div class="owner-contact">
                                    <i class="fas fa-envelope"></i>
                                        ${tripWithOwner.userInfo.email}
                                </div>
                            </c:if>

                            <c:if test="${not empty tripWithOwner.userInfo.phoneNumber}">
                                <div class="owner-contact">
                                    <i class="fas fa-phone"></i>
                                        ${tripWithOwner.userInfo.phoneNumber}
                                </div>
                            </c:if>
                        </div>

                        <c:if test="${not empty tripWithOwner.userInfo.bio}">
                            <div class="owner-bio">
                                <h4>О себе:</h4>
                                <p>${tripWithOwner.userInfo.bio}</p>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
