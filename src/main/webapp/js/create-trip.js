document.addEventListener('DOMContentLoaded', function() {
    initializeDateValidation();
    initializeTagsCounter();
});

function initializeDateValidation() {
    /* Установка минимальной даты на сегодня
    * new Date() - создает объект Date с текущей датой и временем
    * .toISOString() - преобразует дату в строку в формате ISO 8601
    * Пример: "2024-01-15T14:30:45.123Z"
    * .slice(0, 16) - обрезает строку, оставляя только первые 16 символов
    */
    const today = new Date().toISOString().slice(0, 16);

    const startAtInput = document.getElementById('startAt');
    const endAtInput = document.getElementById('endAt');

    if (startAtInput) {
        startAtInput.min = today;
    }

    if (endAtInput) {
        endAtInput.min = today;
    }

    // Валидация дат
    if (startAtInput) {
        startAtInput.addEventListener('change', function() {
            if (endAtInput) {
                endAtInput.min = this.value;

                // Если конечная дата стала раньше начальной, сбросить её
                if (endAtInput.value && endAtInput.value < this.value) {
                    endAtInput.value = '';
                }
            }
        });
    }
}

function initializeTagsCounter() {
    // Подсчет выбранных тегов
    const tagCheckboxes = document.querySelectorAll('input[name="tags"]');

    tagCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', updateSelectedTagsCount);
    });

    // Инициализация счетчика при загрузке
    updateSelectedTagsCount();
}

function updateSelectedTagsCount() {
    const selectedCount = document.querySelectorAll('input[name="tags"]:checked').length;
    const counter = document.getElementById('selectedTagsCount') || createTagsCounter();
    counter.textContent = `Выбрано тегов: ${selectedCount}`;

    if (selectedCount > 0) {
        counter.style.background = '#f2e08b';
        counter.style.color = '#b54a2a';
        counter.style.fontWeight = '400';
    } else {
        counter.style.background = '#f8f9fa';
        counter.style.color = '#636e72';
        counter.style.fontWeight = 'normal';
    }
}

function createTagsCounter() {
    const counter = document.createElement('div');
    counter.id = 'selectedTagsCount';
    counter.className = 'tags-counter';
    counter.textContent = 'Выбрано тегов: 0';
    document.querySelector('.tags-container').appendChild(counter);
    return counter;
}

function validateTripForm() {
    const startAt = document.getElementById('startAt');
    const endAt = document.getElementById('endAt');
    const maxFellows = document.getElementById('maxFellows');

    let isValid = true;

    // Проверка дат
    if (startAt.value && endAt.value) {
        const startDate = new Date(startAt.value);
        const endDate = new Date(endAt.value);

        if (endDate <= startDate) {
            alert('Дата окончания должна быть позже даты начала');
            endAt.focus();
            isValid = false;
        }
    }

    // Проверка количества участников
    if (maxFellows.value < 2) {
        alert('Минимальное количество участников: 2');
        maxFellows.focus();
        isValid = false;
    }

    return isValid;
}

// Добавление обработчика на форму
document.addEventListener('DOMContentLoaded', function() {
    const tripForm = document.querySelector('.trip-form');
    if (tripForm) {
        tripForm.addEventListener('submit', function(e) {
            if (!validateTripForm()) {
                e.preventDefault();
            }
        });
    }
});