<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<html>
<head>
    <title>Edit trip</title>

    <link rel="stylesheet" href="${contextPath}/css/create-trip.css">
</head>
<body>
    <div class="container">
        <div class="form-wrapper">
            <h2>Редактировать поездку</h2>

            <my:error-message errorMessage="${errorMessage}" removeFromSession="true"/>

            <form action="edit-trip" method="post" class="trip-form">
                <input type="hidden" name="tripId" value="${trip.id}">

                <div class="form-section">
                    <h3>Основная информация</h3>

                    <div class="input-row">
                        <div class="input-group">
                            <label for="departurePoint">Откуда</label>
                            <input type="text" id="departurePoint" name="departurePoint"
                                   value="${trip.departurePoint}" readonly
                                   class="readonly-field">
                        </div>

                        <div class="input-group">
                            <label for="destination">Куда</label>
                            <input type="text" id="destination" name="destination"
                                   value="${trip.destination}" readonly
                                   class="readonly-field">
                        </div>
                    </div>

                    <div class="input-row">
                        <div class="input-group">
                            <label for="startAt">Дата начала *</label>
                            <input type="datetime-local" id="startAt" name="startAt"
                                   value="<fmt:formatDate value="${trip.startAt}" pattern="yyyy-MM-dd'T'HH:mm"/>" required>
                        </div>

                        <div class="input-group">
                            <label for="endAt">Дата окончания *</label>
                            <input type="datetime-local" id="endAt" name="endAt"
                                   value="<fmt:formatDate value="${trip.endAt}" pattern="yyyy-MM-dd'T'HH:mm"/>" required>
                        </div>
                    </div>

                    <div class="input-row">
                        <div class="input-group">
                            <label for="budget">Бюджет на человека (₽)</label>
                            <input type="number" id="budget" name="budget"
                                   value="${trip.budget}"
                                   placeholder="Пример: 15000" min="0">
                        </div>

                        <div class="input-group">
                            <label for="status">Статус поездки *</label>
                            <select id="status" name="status" required>
                                <option value="PLANNING" ${trip.status == 'PLANNING' ? 'selected' : ''}>Планируется</option>
                                <option value="ACTIVE" ${trip.status == 'ACTIVE' ? 'selected' : ''}>Активна</option>
                                <option value="COMPLETED" ${trip.status == 'COMPLETED' ? 'selected' : ''}>Завершена</option>
                            </select>
                        </div>

                        <div class="input-group">
                            <label for="maxFellows">Максимум участников *</label>
                            <input type="number" id="maxFellows" name="maxFellows"
                                   value="${trip.maxFellows}"
                                   placeholder="Включая вас" min="2" max="50" required>
                        </div>
                    </div>
                </div>

                <div class="form-section">
                    <h3>Теги поездки</h3>
                    <p class="section-description">Выберите теги, которые описывают вашу поездку</p>
                    <div class="tags-container">
                        <div class="tags-grid">
                            <c:set var="tagsList" value="${trip.tags}"/>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="бюджетно"
                                ${tagsList.contains('бюджетно') ? 'checked' : ''}>
                                <span class="tag-label">💰 Бюджетно</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="комфорт"
                                ${tagsList.contains('комфорт') ? 'checked' : ''}>
                                <span class="tag-label">⭐ Комфорт</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="экстрим"
                                ${tagsList.contains('экстрим') ? 'checked' : ''}>
                                <span class="tag-label">🏔️ Экстрим</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="семейный"
                                ${tagsList.contains('семейный') ? 'checked' : ''}>
                                <span class="tag-label">👨‍👩‍👧‍👦 Семейный</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="романтика"
                                ${tagsList.contains('романтика') ? 'checked' : ''}>
                                <span class="tag-label">💖 Романтика</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="гастрономия"
                                ${tagsList.contains('гастрономия') ? 'checked' : ''}>
                                <span class="tag-label">🍝 Гастрономия</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="фотосессия"
                                ${tagsList.contains('фотосессия') ? 'checked' : ''}>
                                <span class="tag-label">📸 Фотосессия</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="ночная_жизнь"
                                ${tagsList.contains('ночная_жизнь') ? 'checked' : ''}>
                                <span class="tag-label">🌃 Ночная жизнь</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="природа"
                                ${tagsList.contains('природа') ? 'checked' : ''}>
                                <span class="tag-label">🌲 Природа</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="история"
                                ${tagsList.contains('история') ? 'checked' : ''}>
                                <span class="tag-label">🏛️ История</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="спорт"
                                ${tagsList.contains('спорт') ? 'checked' : ''}>
                                <span class="tag-label">⚽ Спорт</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="йога"
                                ${tagsList.contains('йога') ? 'checked' : ''}>
                                <span class="tag-label">🧘 Йога</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="пляж"
                                ${tagsList.contains('пляж') ? 'checked' : ''}>
                                <span class="tag-label">🏖️ Пляж</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="горы"
                                ${tagsList.contains('горы') ? 'checked' : ''}>
                                <span class="tag-label">⛰️ Горы</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="город"
                                ${tagsList.contains('город') ? 'checked' : ''}>
                                <span class="tag-label">🏙️ Город</span>
                            </label>
                            <label class="tag-checkbox">
                                <input type="checkbox" name="tags" value="деревня"
                                ${tagsList.contains('деревня') ? 'checked' : ''}>
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
                                  rows="5" required>${trip.description}</textarea>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="create-btn">Сохранить изменения</button>
                    <a href="${contextPath}/my-trips" class="cancel-btn">Отмена</a>
                </div>
            </form>
        </div>
    </div>

    <script src="${contextPath}/js/create-trip.js"></script>
</body>
</html>