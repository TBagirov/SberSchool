package org.bagirov.model.task1;

import org.bagirov.model.task1.execption.AccountIsLockedException;
import org.bagirov.model.task1.execption.InvalidPinException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PinValidator {
    private final String correctPin;
    private int failedAttempts = 0;
    private LocalDateTime lockUntil;

    public PinValidator(String correctPin) {
        if (correctPin == null || correctPin.length() != 4 || !correctPin.matches("\\d{4}")) {
            throw new InvalidPinException("PIN-код должен состоять из 4 цифр.");
        }
        this.correctPin = correctPin;
    }

    public void validate(String enteredPin) {
        // Проверяем, заблокирован ли аккаунт
        if (isLocked()) {
            long secondsLeft = ChronoUnit.SECONDS.between(LocalDateTime.now(), lockUntil);
            throw new AccountIsLockedException("Аккаунт заблокирован. Попробуйте через " + secondsLeft + " секунд.");
        }

        // Проверяем корректность PIN-кода
        if (!enteredPin.equals(correctPin)) {
            failedAttempts++;
            if (failedAttempts >= 3) {
                lockUntil = LocalDateTime.now().plusSeconds(10);
                throw new AccountIsLockedException("Три неверных попытки. Аккаунт заблокирован на 10 секунд.");
            }
            throw new InvalidPinException("Неверный PIN-код. Осталось попыток: " + (3 - failedAttempts));
        }

        // Сбрасываем счетчик при успешном вводе
        failedAttempts = 0;
    }

    private boolean isLocked() {
        return lockUntil != null && LocalDateTime.now().isBefore(lockUntil);
    }
}
