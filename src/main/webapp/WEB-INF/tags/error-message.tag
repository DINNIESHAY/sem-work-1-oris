<%@ tag description="Error Message Component" pageEncoding="UTF-8" %>
<%@ attribute name="errorMessage" required="false" type="java.lang.String" %>
<%@ attribute name="removeFromSession" required="false" type="java.lang.Boolean" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty errorMessage}">
    <div class="error-message">${errorMessage}</div>
    <c:if test="${removeFromSession}">
        <% session.removeAttribute("errorMessage"); %>
    </c:if>
</c:if>