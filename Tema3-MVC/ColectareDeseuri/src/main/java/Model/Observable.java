package Model;

import View.Observer;
import java.util.ArrayList;
import java.util.List;

public class Observable {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void delObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyUpdate() {
        for(Observer o: observers) {
            o.update(this);
        }
    }
}
