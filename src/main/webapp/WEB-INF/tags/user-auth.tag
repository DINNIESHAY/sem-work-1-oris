<%@ tag description="User Authentication Component" pageEncoding="UTF-8" %>
<%@ attribute name="user" required="false" type="java.lang.Object" %>
<%@ attribute name="contextPath" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${not empty user}">
        <a href="${contextPath}/profile" class="user-info">
            <i class="fas fa-user user-icon"></i>${user.username}
        </a>
    </c:when>
    <c:otherwise>
        <a href="${contextPath}/register" class="register-btn">Зарегистрироваться / Войти</a>
    </c:otherwise>
</c:choose>