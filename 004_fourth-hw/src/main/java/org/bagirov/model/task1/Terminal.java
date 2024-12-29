package org.bagirov.model.task1;

public interface Terminal {
    void checkBalance();
    void withdraw(int amount);
    void deposit(int amount);
    void enterPin(char digit);
}

