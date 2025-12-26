<%@ tag description="Application Card Component" pageEncoding="UTF-8" %>
<%@ attribute name="application" required="true" type="java.lang.Object" %>
<%@ attribute name="index" required="true" type="java.lang.Integer" %>
<%@ attribute name="contextPath" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="application-card">
    <div class="application-header">
        <span class="application-number">Заявка #${index}</span>
        <a href="${contextPath}/user-profile?userId=${application.userId}" class="application-id" title="Открыть профиль">
            <i class="fas fa-user user-icon"></i> Профиль пользователя
        </a>
    </div>

    <div class="application-contacts">
        <h4>Контакты:</h4>
        <p>${application.contacts}</p>
    </div>

    <c:if test="${not empty application.message}">
        <div class="application-message">
            <h4>Комментарий:</h4>
            <p>${application.message}</p>
        </div>
    </c:if>

    <div class="application-actions">
        <button class="copy-contacts-btn" onclick="copyToClipboard('${application.contacts}')"
                title="Скопировать контакты">
            <i class="fas fa-copy"></i> Копировать контакты
        </button>
    </div>
</div>