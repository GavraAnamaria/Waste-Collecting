package model.State;

import javax.swing.*;

public class ErrorState implements State {

    public void doAction(Context context) {
        JOptionPane.showMessageDialog(null, "Nu a fost selectat niciun utilizator!");
    }
}
