package com.travelfellows.util;

import com.travelfellows.models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // Кодируем массив байтов в строку Base64 для удобного хранения в БД. Base64 преобразует бинарные данные в текстовый формат (A-Z, a-z, 0-9, +, /, =)
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        try {
            // Получаем экземпляр алгоритма хеширования SHA-256. Он создает хэш длиной 256 бит (32 байта) и считается криптографически стойким
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Декодируем соль из Base64 обратно в байты и добавляем ее в хэшируемый материал
            md.update(Base64.getDecoder().decode(salt));

            // Хэшируем пароль: преобразуем строку в байты и выполняем финальное хеширование
            // Метод digest() завершает операцию хеширования (пароль + соль)
            byte[] hashedPassword = md.digest(password.getBytes());

            // Кодируем полученный хеш в Base64
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static boolean isCorrectPassword(User user, String inputPassword) {
        boolean isCorrectPassword;

        String userSalt = user.getSalt();
        String inputPasswordHash = hashPassword(inputPassword, userSalt);
        isCorrectPassword = user.getPasswordHash().equals(inputPasswordHash);

        return isCorrectPassword;
    }

    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }

        /*
         * Регулярное выражение для проверки пароля:
         * ^ - начало строки
         * (?=.*[a-z]) - минимум одна строчная буква
         * (?=.*[A-Z]) - минимум одна заглавная буква
         * (?=.*\d) - минимум одна цифра
         * (?=.*[@$!%*?&]) - минимум один специальный символ
         * [A-Za-z\d@$!%*?&] - разрешенные символы
         * {8,} - минимум 8 символов
         */
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        return password.matches(regex);
    }
}
