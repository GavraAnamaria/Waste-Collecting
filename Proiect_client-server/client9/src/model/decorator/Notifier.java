package model.decorator;

import model.Utilizator;

public interface Notifier {
    void sendMessage(String message, Utilizator u);
}
