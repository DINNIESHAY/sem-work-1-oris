<%@ tag description="My Trip Card Component" pageEncoding="UTF-8" %>
<%@ attribute name="tripWithLikes" required="true" type="java.lang.Object" %>
<%@ attribute name="contextPath" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="trip-card">
    <div class="trip-header">
        <h3 class="trip-route">
            <span class="departure">${tripWithLikes.trip.departurePoint}</span>
            <span class="arrow">→</span>
            <span class="destination">${tripWithLikes.trip.destination}</span>
        </h3>
        <div class="trip-actions">
            <!-- Кнопка просмотра заявок -->
            <a href="${contextPath}/applications-for-trip?tripId=${tripWithLikes.trip.id}" class="action-btn applications-btn" title="Просмотреть заявки">
                <i class="fas fa-users"></i>
            </a>

            <a href="${contextPath}/edit-trip?tripId=${tripWithLikes.trip.id}" class="action-btn edit-btn" title="Редактировать">✏️</a>
            <form action="${contextPath}/delete-trip" method="post" class="delete-form">
                <input type="hidden" name="tripId" value="${tripWithLikes.trip.id}">
                <button type="submit" class="action-btn delete-btn" title="Удалить"
                        onclick="return confirm('Вы уверены, что хотите удалить эту поездку?')">🗑️
                </button>
            </form>
        </div>
    </div>

    <div class="trip-dates">
        <span class="date-range">
            <fmt:formatDate value="${tripWithLikes.trip.startAt}" pattern="dd.MM.yyyy"/> -
            <fmt:formatDate value="${tripWithLikes.trip.endAt}" pattern="dd.MM.yyyy"/>
        </span>
    </div>

    <div class="trip-details">
        <div class="detail-item">
            <span class="detail-label">Бюджет:</span>
            <span class="detail-value">
                <fmt:formatNumber value="${tripWithLikes.trip.budget}" type="currency" currencyCode="RUB"/>
            </span>
        </div>
        <div class="detail-item">
            <span class="detail-label">Участники:</span>
            <span class="detail-value">${tripWithLikes.trip.maxFellows}</span>
        </div>
    </div>

    <div class="trip-tags-container">
        <c:forEach var="tag" items="${tripWithLikes.trip.tags}">
            <div class="trip-tag">#${tag}</div>
        </c:forEach>
    </div>

    <span class="trip-status ${tripWithLikes.trip.status}">
        <c:choose>
            <c:when test="${tripWithLikes.trip.status == 'PLANNING'}">Планируется</c:when>
            <c:when test="${tripWithLikes.trip.status == 'ACTIVE'}">Активен</c:when>
            <c:when test="${tripWithLikes.trip.status == 'COMPLETED'}">Завершен</c:when>
            <c:otherwise>${tripWithLikes.trip.status}</c:otherwise>
        </c:choose>
    </span>
    <span class="trip-likes-count" ${tripWithLikes.likesCount}>
        ❤ Понравилось:
        <c:choose>
            <c:when test="${tripWithLikes.likesCount == 1}">${tripWithLikes.likesCount} человеку </c:when>
            <c:otherwise>${tripWithLikes.likesCount} людям</c:otherwise>
        </c:choose>
    </span>
</div>