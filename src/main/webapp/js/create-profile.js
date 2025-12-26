document.addEventListener('DOMContentLoaded', function() {
    initializeFileUpload();
    initializeFormValidation();
});

function initializeFileUpload() {
    const avatarInput = document.getElementById('avatar');
    if (!avatarInput) return;

    avatarInput.addEventListener('change', function(e) {
        const fileName = this.files[0] ? this.files[0].name : 'Файл не выбран';
        document.getElementById('fileName').textContent = fileName;

        if (this.files[0] && this.files[0].size > 5 * 1024 * 1024) {
            alert('Размер файла не должен превышать 5MB');
            this.value = '';
            document.getElementById('fileName').textContent = 'Файл не выбран';
            return;
        }

        previewImage(this.files[0]);
    });
}

function previewImage(file) {
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            let preview = document.getElementById('imagePreview');
            if (!preview) {
                preview = document.createElement('div');
                preview.id = 'imagePreview';
                preview.style.marginTop = '10px';
                preview.style.textAlign = 'center';
                document.querySelector('.file-upload-wrapper').appendChild(preview);
            }

            preview.innerHTML = '<img src="' + e.target.result + '" style="max-width: 100px; max-height: 100px; border-radius: 5px;">';
        }
        reader.readAsDataURL(file);
    } else {
        const preview = document.getElementById('imagePreview');
        if (preview) {
            preview.remove();
        }
    }
}

function initializeFormValidation() {
    const profileForm = document.getElementById('profileForm');
    if (!profileForm) return;

    profileForm.addEventListener('submit', function(e) {
        const fileInput = document.getElementById('avatar');
        if (fileInput.files[0] && !fileInput.files[0].type.startsWith('image/')) {
            e.preventDefault();
            alert('Пожалуйста, выберите файл изображения');
            return false;
        }

        const email = document.querySelector('input[name="email"]');
        if (email && email.value) {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email.value)) {
                e.preventDefault();
                alert('Пожалуйста, введите корректный email адрес');
                return false;
            }
        }
    });
}

function triggerFileInput() {
    document.getElementById('avatar').click();
}