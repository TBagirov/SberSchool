package org.bagirov.model.task1;

import org.bagirov.model.task1.execption.TerminalServerException;

public class TerminalServer {
    private int balance;

    public TerminalServer(int initialBalance) {
        if (initialBalance < 0) {
            throw new TerminalServerException("Начальный баланс не может быть отрицательным.");
        }
        this.balance = initialBalance;
    }

    public int getBalance(String accountId) {
        // accountId игнорируется для упрощения
        return balance;
    }

    public void deposit(String accountId, int amount) {
        if (amount <= 0) {
            throw new TerminalServerException("Сумма депозита должна быть положительной.");
        }
        balance += amount;
    }

    public void withdraw(String accountId, int amount) {
        if (amount <= 0) {
            throw new TerminalServerException("Сумма снятия должна быть положительной.");
        }
        if (balance < amount) {
            throw new TerminalServerException("Недостаточно средств.");
        }
        balance -= amount;
    }
}
