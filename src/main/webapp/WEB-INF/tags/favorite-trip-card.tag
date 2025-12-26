<%@ tag description="Favorite Trip Component" pageEncoding="UTF-8" %>
<%@ attribute name="tripWithOwner" required="true" type="java.lang.Object" %>
<%@ attribute name="contextPath" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form action="${contextPath}/trip-info" method="get" class="trip-card">
    <input type="hidden" name="id" value="${tripWithOwner.trip.id}">

    <div class="trip-header">
        <h3 class="trip-route">
            <span class="departure">${tripWithOwner.trip.departurePoint}</span>
            <span class="arrow">→</span>
            <span class="destination">${tripWithOwner.trip.destination}</span>
        </h3>

        <div class="header-actions">
            <span class="trip-status ${tripWithOwner.trip.status}">
                <c:choose>
                    <c:when test="${tripWithOwner.trip.status == 'PLANNING'}">Планируется</c:when>
                    <c:when test="${tripWithOwner.trip.status == 'ACTIVE'}">Активен</c:when>
                    <c:when test="${tripWithOwner.trip.status == 'COMPLETED'}">Завершен</c:when>
                    <c:otherwise>${tripWithOwner.trip.status}</c:otherwise>
                </c:choose>
            </span>

            <c:choose>
                <c:when test="${tripWithOwner.favorite}">
                    <a href="${contextPath}/remove-from-favorites?tripId=${tripWithOwner.trip.id}&returnUrl=my-favorites"
                       class="favorite-btn favorite-active" title="Удалить из избранного">
                        <i class="fas fa-heart"></i>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="${contextPath}/add-to-favorites?tripId=${tripWithOwner.trip.id}&returnUrl=my-favorites"
                       class="favorite-btn" title="Добавить в избранное">
                        <i class="far fa-heart"></i>
                    </a>
                </c:otherwise>
            </c:choose>

            <!-- кнопка заявки -->
            <a href="${contextPath}/trip-application?id=${tripWithOwner.trip.id}"
               class="application-btn" title="Отправить заявку">
                <i class="fas fa-paper-plane"></i>
            </a>
        </div>
    </div>

    <div class="trip-dates">
        <span class="date-range">
            <fmt:formatDate value="${tripWithOwner.trip.startAt}" pattern="dd.MM.yyyy"/> -
            <fmt:formatDate value="${tripWithOwner.trip.endAt}" pattern="dd.MM.yyyy"/>
        </span>
    </div>

    <div class="trip-details">
        <div class="detail-item">
            <span class="detail-label">Бюджет:</span>
            <span class="detail-value">
                <fmt:formatNumber value="${tripWithOwner.trip.budget}" type="currency" currencyCode="RUB"/>
            </span>
        </div>
        <div class="detail-item">
            <span class="detail-label">Участники:</span>
            <span class="detail-value">${tripWithOwner.trip.maxFellows}</span>
        </div>
        <div class="detail-item">
            <span class="detail-label">Организатор:</span>
            <span class="detail-value">${tripWithOwner.ownerUsername}</span>
        </div>
    </div>

    <div class="trip-tags-container">
        <c:forEach var="tag" items="${tripWithOwner.trip.tags}">
            <div class="trip-tag">#${tag}</div>
        </c:forEach>
    </div>

    <button type="submit" class="hidden-submit"></button>
</form>