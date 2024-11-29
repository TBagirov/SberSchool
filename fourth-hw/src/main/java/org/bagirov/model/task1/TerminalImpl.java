package org.bagirov.model.task1;

import org.bagirov.model.task1.execption.AccountIsLockedException;
import org.bagirov.model.task1.execption.InvalidAmountException;
import org.bagirov.model.task1.execption.InvalidPinException;
import org.bagirov.model.task1.execption.TerminalServerException;

public class TerminalImpl implements Terminal {
    private final TerminalServer server;
    private final PinValidator pinValidator;
    private final UserInterface userInterface;
    private final String accountId = "default";
    private final StringBuilder currentPin = new StringBuilder();
    private boolean authenticated = false;

    public TerminalImpl(TerminalServer server, PinValidator pinValidator, UserInterface userInterface) {
        this.server = server;
        this.pinValidator = pinValidator;
        this.userInterface = userInterface;
    }

    @Override
    public void checkBalance() {
        try {
            ensureAuthenticated();
            userInterface.showMessage("Ваш баланс: " + server.getBalance(accountId));
        } catch (InvalidPinException e) {
            userInterface.showMessage("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public void withdraw(int amount) {
        try {
            ensureAuthenticated();
            validateAmount(amount);
            if (server.getBalance(accountId) < amount) {
                throw new InvalidAmountException("Недостаточно средств для снятия.");
            }
            server.withdraw(accountId, amount);
            userInterface.showMessage("Вы сняли " + amount + ". Остаток: " + server.getBalance(accountId));
        } catch (InvalidPinException | InvalidAmountException | TerminalServerException e) {
            userInterface.showMessage("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public void deposit(int amount) {
        try {
            ensureAuthenticated();
            validateAmount(amount);
            server.deposit(accountId, amount);
            userInterface.showMessage("Вы пополнили на " + amount + ". Баланс: " + server.getBalance(accountId));
        } catch (InvalidPinException | InvalidAmountException | TerminalServerException e) {
            userInterface.showMessage("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public void enterPin(char digit) {
        if (!Character.isDigit(digit)) {
            userInterface.showMessage("Ошибка: введите только цифры.");
            return;
        }

        currentPin.append(digit);

        if (currentPin.length() == 4) {
            try {
                pinValidator.validate(currentPin.toString());
                userInterface.showMessage("Пин-код принят.");
                authenticated = true;
            } catch (AccountIsLockedException | InvalidPinException e) {
                userInterface.showMessage("Ошибка: " + e.getMessage());
            } finally {
                currentPin.setLength(0); // Очищаем после проверки
            }
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    private void ensureAuthenticated() {
        if (!authenticated) {
            throw new InvalidPinException("Пин-код не подтвержден.");
        }
    }

    private void validateAmount(int amount) {
        if (amount % 100 != 0) {
            throw new InvalidAmountException("Сумма должна быть кратна 100.");
        }
    }
}
