<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>Create trip</title>

    <link rel="stylesheet" href="${contextPath}/css/create-trip.css">
</head>
<body>
    <div class="container">
        <div class="form-wrapper">
            <h2>Создать новую поездку</h2>

            <my:error-message errorMessage="${errorMessage}" removeFromSession="true"/>

            <form action="create-trip" method="post" class="trip-form">
                <div class="form-section">
                    <h3>Основная информация</h3>

                    <div class="input-row">
                        <div class="input-group">
                            <label for="departurePoint">Откуда *</label>
                            <input type="text" id="departurePoint" name="departurePoint"
                                   placeholder="Город отправления" required>
                        </div>

                        <div class="input-group">
                            <label for="destination">Куда *</label>
                            <input type="text" id="destination" name="destination"
                                   placeholder="Город назначения" required>
                        </div>
                    </div>

                    <div class="input-row">
                        <div class="input-group">
                            <label for="startAt">Дата начала *</label>
                            <input type="datetime-local" id="startAt" name="startAt" required>
                        </div>

                        <div class="input-group">
                            <label for="endAt">Дата окончания *</label>
                            <input type="datetime-local" id="endAt" name="endAt" required>
                        </div>
                    </div>

                    <div class="input-row">
                        <div class="input-group">
                            <label for="budget">Бюджет на человека (₽)</label>
                            <input type="number" id="budget" name="budget"
                                   placeholder="Пример: 15000" min="0">
                        </div>

                        <div class="input-group">
                            <label for="maxFellows">Максимум участников *</label>
                            <input type="number" id="maxFellows" name="maxFellows"
                                   placeholder="Включая вас" min="2" max="50" required>
                        </div>
                    </div>
                </div>

                <div class="form-section">
                    <h3>Теги поездки</h3>
                    <p class="section-description">Выберите теги, которые описывают вашу поездку</p>
                    <div class="tags-container">
                        <div class="tags-grid">
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="бюджетно">
                                <span class="tag-label">💰 Бюджетно</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="комфорт">
                                <span class="tag-label">⭐ Комфорт</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="экстрим">
                                <span class="tag-label">🏔️ Экстрим</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="семейный">
                                <span class="tag-label">👨‍👩‍👧‍👦 Семейный</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="романтика">
                                <span class="tag-label">💖 Романтика</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="гастрономия">
                                <span class="tag-label">🍝 Гастрономия</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="фотосессия">
                                <span class="tag-label">📸 Фотосессия</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="ночная_жизнь">
                                <span class="tag-label">🌃 Ночная жизнь</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="природа">
                                <span class="tag-label">🌲 Природа</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="история">
                                <span class="tag-label">🏛️ История</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="спорт">
                                <span class="tag-label">⚽ Спорт</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="йога">
                                <span class="tag-label">🧘 Йога</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="пляж">
                                <span class="tag-label">🏖️ Пляж</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="горы">
                                <span class="tag-label">⛰️ Горы</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="город">
                                <span class="tag-label">🏙️ Город</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="деревня">
                                <span class="tag-label">🌾 Деревня</span>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="form-section">
                    <h3>Описание</h3>
                    <div class="input-group">
                        <label for="description">Расскажите о поездке *</label>
                        <textarea id="description" name="description"
                                  placeholder="Опишите маршрут, цели поездки, ожидания от попутчиков..."
                                  rows="5" required></textarea>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="create-btn">Создать поездку</button>
                    <a href="${contextPath}/trips-feed" class="cancel-btn">Отмена</a>
                </div>
            </form>
        </div>
    </div>

    <script src="${contextPath}/js/create-trip.js"></script>
</body>
</html>
