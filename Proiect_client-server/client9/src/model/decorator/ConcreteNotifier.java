package model.decorator;

import model.Utilizator;

public class ConcreteNotifier implements Notifier {
    @Override
    public void sendMessage(String message, Utilizator u) {
        System.out.println("message");
    }
}
