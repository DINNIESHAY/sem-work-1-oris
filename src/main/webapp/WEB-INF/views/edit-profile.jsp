<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>Edit profile</title>

    <link rel="stylesheet" href="${contextPath}/css/edit-profile.css">
</head>

<body>
    <div class="container">
        <div class="form-wrapper">
            <h2>Редактировать профиль</h2>

            <my:error-message errorMessage="${errorMessage}" removeFromSession="true"/>

            <form action="edit-profile" method="post" enctype="multipart/form-data">
                <div class="input-row">
                    <input type="text" name="firstName" placeholder="Имя" required value="${userInfo.firstName}">
                    <input type="text" name="lastName" placeholder="Фамилия" required value="${userInfo.lastName}">
                </div>

                <div class="input-row">
                    <input type="date" name="dateOfBirth" placeholder="Дата рождения" value="${userInfo.dateOfBirth}">
                    <div class="file-upload-wrapper">
                        <input type="file" name="avatar" id="avatar" accept="image/*" style="display: none;">
                        <button type="button" class="file-upload-btn" onclick="triggerFileInput()">Изменить аватар</button>
                        <span id="fileName" class="file-name">Файл не выбран</span>
                    </div>
                </div>

                <div class="current-avatar">
                    <c:if test="${not empty userInfo.avatarUrl}">
                        <p>Текущий аватар:</p>
                        <img src="${contextPath}/uploads/${userInfo.avatarUrl}" alt="Текущий аватар" class="avatar-preview">
                    </c:if>
                </div>

                <div class="input-full">
                    <input type="text" name="email" placeholder="Email" value="${userInfo.email}">
                </div>

                <div class="input-full">
                    <input type="text" name="phoneNumber" placeholder="Номер телефона (необязательно)" value="${userInfo.phoneNumber}">
                </div>

                <div class="input-full">
                    <input type="text" name="country" placeholder="Страна проживания" value="${userInfo.country}">
                </div>

                <div class="input-full">
                    <input type="text" name="city" placeholder="Город проживания" value="${userInfo.city}">
                </div>

                <div class="input-full">
                    <textarea name="bio" placeholder="Расскажите о себе, своих интересах и предпочтениях в путешествиях..." rows="4">${userInfo.bio}</textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="save-profile-btn">Сохранить изменения</button>
                    <a href="profile" class="cancel-btn">Отмена</a>
                </div>
            </form>
        </div>
    </div>

    <script src="${contextPath}/js/create-profile.js"></script>
</body>
</html>