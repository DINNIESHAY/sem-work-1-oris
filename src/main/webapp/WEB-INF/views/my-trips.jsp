<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>Your trips feed</title>
    <link rel="stylesheet" href="${contextPath}/css/my-trips.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <a href="${contextPath}/profile" class="go-back-btn">← Назад</a>
    <div class="container">
        <div class="trips-feed">
            <c:choose>
                <c:when test="${empty trips}">
                    <div class="no-trips">
                        <h3>Пока нет активных поездок</h3>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="tripWithLikes" items="${trips}">
                        <my:my-trip-card tripWithLikes="${tripWithLikes}" contextPath="${contextPath}"/>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
        <a href="${contextPath}/profile" class="go-back-btn">← Назад</a>
    </div>

    <script>
        function confirmDelete(event) {
            if (!confirm('Are you sure you want to delete this post?')) {
                event.preventDefault();
                return false;
            }
            return true;
        }
    </script>
</body>
</html>