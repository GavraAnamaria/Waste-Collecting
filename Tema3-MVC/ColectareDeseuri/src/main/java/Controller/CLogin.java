package Controller;

import Model.PersistentaUtilizatori;
import Model.Tip;
import View.LoginGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CLogin {
    private final LoginGUI view;
    private final PersistentaUtilizatori utilizatori;

    public CLogin() {
        view = new LoginGUI();
        utilizatori = new PersistentaUtilizatori();
        view.loginListener(new loginAction());
        view.showPasswordListener(new showPasswordAction());
    }

    class loginAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println(view.getPassword().getPassword());
            Tip tip = utilizatori.cautareUtilizator(view.getUserName(), new String(view.getPassword().getPassword()));

            if (tip == Tip.ANGAJAT)
                view.setContentPane(new CAngajat(view.getUserName()).getView());
            else if (tip == Tip.COORDONATOR)
                view.setContentPane(new CCoordonator().getView());
            else if (tip == Tip.ADMINISTRATOR)
                view.setContentPane((new CAdmin().getView()));
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
