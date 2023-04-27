package model.decorator;

import lombok.AllArgsConstructor;
import model.Utilizator;

@AllArgsConstructor
public class Decorator implements Notifier {
    private Notifier n;

    @Override
    public void sendMessage(String message, Utilizator u) {
        n.sendMessage(message, u);
    }
}
