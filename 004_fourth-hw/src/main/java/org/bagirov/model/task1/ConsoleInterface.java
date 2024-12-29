package org.bagirov.model.task1;

public class ConsoleInterface implements UserInterface {
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}
