package Controller;

import View.AngajatGUI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import model.Client;
import model.Locatie;
import model.Oras;
import model.Translator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class CAngajat {
    private AngajatGUI view;
    private Client client;
    private Oras oras;

    public CAngajat(String name, Client c)
    {
        client = c;
        oras = new Oras(name, c);
        view = new AngajatGUI();
        view.setList(oras.getDeseuri().toStringList());
        view.addLocListener(new locAction());
        view.addRouteListener(new routeAction());
        view.backListener(new backAction());
        view.roListener(new languageAction("ro"));
        view.enListener(new languageAction("en"));
        view.frListener(new languageAction("fr"));
        view.geListener(new languageAction("ge"));
    }

    class backAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
            topFrame.setVisible(false);
            new CLogin(client);
        }
    }
    class locAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.setList(oras.getDeseuri().toStringList());
        }
    }
    class routeAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            List<Locatie> l = oras.traseuOptim();
            String[] s = new String[l.size()+1];
            // s[0] = name.toUpperCase(Locale.ROOT);
            s[0] = "ANDREI";
            for(int i = 0; i < l.size(); ++i) {
                s[i+1] = "[ " + oras.getDeseuri().getAllLoc().indexOf(l.get(i)) + " ]   " + (l.get(i)).toString1();
            }
            view.setList(s);
        }
    }

    @AllArgsConstructor
    class languageAction implements ActionListener {
        String language;
        public void actionPerformed(ActionEvent e) {
            String[] data = new String[]{"VIZUALIZARE-LOCATII", "TRASEU-OPTIM"};
            String[] result = new String[data.length];
            if(language.equals("ro")){
                result = data;
            }else {
                Map<String, String> trans = new Translator().traducere(data, language);
                for(int i=0; i<data.length;i++){
                    result[i] = trans.get(data[i]);
                }}
            view.setViewLoc(result[0]);
            view.setRoute(result[1]);
        }
    }
}
