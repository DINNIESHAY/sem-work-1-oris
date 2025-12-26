<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>Create Profile</title>

    <link rel="stylesheet" href="${contextPath}/css/create-profile.css">
</head>

<body>
    <div class="container">
        <div class="form-wrapper">
            <h2>Расскажите о себе</h2>

            <my:error-message errorMessage="${errorMessage}" removeFromSession="true"/>

            <form action="create-profile" method="post" enctype="multipart/form-data">
                <div class="input-row">
                    <input type="text" name="firstName" placeholder="Имя" required value="${param.firstName}">
                    <input type="text" name="lastName" placeholder="Фамилия" required value="${param.lastName}">
                </div>

                <div class="input-row">
                    <input type="date" name="dateOfBirth" placeholder="Дата рождения" value="${param.dateOfBirth}">
                    <div class="file-upload-wrapper">
                        <input type="file" name="avatar" id="avatar" accept="image/*" style="display: none;">
                        <button type="button" class="file-upload-btn" onclick="triggerFileInput()">Выбрать аватар</button>
                        <span id="fileName" class="file-name">Файл не выбран</span>
                    </div>
                </div>

                <div class="input-full">
                    <input type="text" name="email" placeholder="Email" value="${param.email}">
                </div>

                <div class="input-full">
                    <input type="text" name="phoneNumber" placeholder="Номер телефона (необязательно)" value="${param.phoneNumber}">
                </div>

                <div class="input-full">
                    <input type="text" name="country" placeholder="Страна проживания" value="${param.country}">
                </div>

                <div class="input-full">
                    <input type="text" name="city" placeholder="Город проживания" value="${param.city}">
                </div>

                <div class="input-full">
                    <textarea name="bio" placeholder="Расскажите о себе, своих интересах и предпочтениях в путешествиях..." rows="4">${param.bio}</textarea>
                </div>

                <button type="submit" class="create-profile-btn">Создать профиль</button>
            </form>
        </div>
    </div>

    <script src="${contextPath}/js/create-profile.js"></script>
</body>
</html>