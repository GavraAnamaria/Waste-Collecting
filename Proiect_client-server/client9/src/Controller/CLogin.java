package Controller;

import Enum.Tip;
import View.LoginGUI;
import model.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CLogin {
    private final LoginGUI view;
    private Client client;

    public CLogin(Client c) {
        view = new LoginGUI();
        client = c;
        view.loginListener(new loginAction());
        view.showPasswordListener(new showPasswordAction());
    }

    public JFrame getView() {
        return this.view;
    }

    class loginAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println(String.valueOf(view.getPassword().getPassword()));
            client.sendCommand("searchUser");
            client.sendCommand(view.getUserName());
            client.sendCommand(String.valueOf(view.getPassword().getPassword()));
            Tip tip = Tip.valueOf(client.readMessage());

            if (tip == Tip.ANGAJAT)
                view.setContentPane(new CAngajat(view.getUserName(), client).getView());
            else if (tip == Tip.COORDONATOR)
                view.setContentPane(new CCoordonator(client).getView());
            else if (tip == Tip.ADMINISTRATOR)
                view.setContentPane((new CAdmin(client).getView()));
            else {
                view.showError( "Date incorecte!");
                return;
            }
            view.setBounds(view.getContentPane().getBounds());
        }
    }

    class showPasswordAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (view.getShowPasswordCBox().isSelected()) {
                view.getPassword().setEchoChar((char)0);
            } else {
                view.getPassword().setEchoChar('*');
            }
        }
    }
}
