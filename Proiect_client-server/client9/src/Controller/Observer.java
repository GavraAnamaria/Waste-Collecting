package Controller;

import model.Observable;

public interface Observer {
    void update(Observable o);
}
