package Controller;

import Enum.Tip;
import View.AdminGUI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import model.Client;
import model.Translator;
import model.Utilizator;
import model.decorator.ConcreteNotifier;
import model.decorator.EmailDecorator;
import model.decorator.Notifier;
import model.decorator.SMSDecorator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;

@Setter
@Getter
public class CAdmin {
    private AdminGUI view;
    Client client;

    public CAdmin(Client c){
        client = c;
        view = new AdminGUI();
        display();
        view.deleteListener(new deleteAction());
        view.addListener(new addAction());
        view.filterListener(new filterAction());
        view.roListener(new languageAction("ro"));
        view.enListener(new languageAction("en"));
        view.frListener(new languageAction("fr"));
        view.geListener(new languageAction("ge"));
        view.backListener(new backAction());
    }


    public void display(){
        String m;
        int i=0;
        String[] s = new String[100];
        client.sendCommand("allUsers");
        do {
            m = client.readMessage();
            if(!(m.equals("STOP"))) {
                s[i] = m;
                i++;
            }
        }while(!(m.equals("STOP")));
        view.setList(Arrays.copyOfRange(s, 0, i));
    }
    public void notifyUser(String message, Utilizator u){
        Notifier n = new ConcreteNotifier();
        Notifier sms = new SMSDecorator(n);
        Notifier mail = new EmailDecorator(sms);
        mail.sendMessage(message, u);
    }

    class deleteAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            client.sendCommand("deleteUser");
            client.sendCommand(String.valueOf(view.getDeleteIndex()));
            Utilizator u =  client.readU();
            if(u != null) {
                notifyUser("Contul dumneavoastra a fost sters.", u);
               display();
            }
            else
                view.showError("Nu a fost selectat niciun utilizator!");
        }
    }

    class addAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            client.sendCommand("addUser");
            Utilizator u = new Utilizator(view.getUName(),view.getPassword(), Tip.valueOf(view.getType()),view.getEmail(),view.getNr());
            client.sendCommand(view.getUName());
            client.sendCommand(view.getPassword());
            client.sendCommand(view.getType());
            client.sendCommand(view.getEmail());
            client.sendCommand(view.getNr());
            if(client.readMessage().equals("true")) {
                notifyUser("A fost creat un nou cont pentru firma de colectare a deseurilor utilizand aceste date. Va puteti conecta la acest cont utilizand credentialele:\n nume urilizator: "+ u.getNume() +",\n parola: "+u.getParola(), u);
                display();
            }
            else
                view.showError("Utilizator existent!");
        }
    }

    class filterAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            client.sendCommand("filter");
            client.sendCommand(view.getFilter());
            display();
        }
    }

    class backAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
            topFrame.setVisible(false);
            new CLogin(client);
        }
    }
@AllArgsConstructor
    class languageAction implements ActionListener {
        String language;
        public void actionPerformed(ActionEvent e) {
            String[] data = new String[]{"TOT", "ANGAJAT", "ADMINISTRATOR", "COORDONATOR","PAROLA","NUMEUTILIZATOR","TIPUTILIZATOR", "ADAUGARE","STERGERE", "NUMAR"};
            String[] result = new String[data.length];
            if(language.equals("ro")){
                result = data;
                result[5]="NUME UTILIZATOR";
                result[6]="TIP UTILIZATOR";
            }else {
                Map<String, String> trans = new Translator().traducere(data, language);
                for(int i=0; i<data.length;i++){
                    result[i] = trans.get(data[i]);
            }}
            view.setFiltre(Arrays.copyOfRange(result, 0, 4));
            view.setTypeCBox(Arrays.copyOfRange(result, 1, 4));
            view.setAddButton(result[7]);
            view.setDelButton(result[8]);
            view.setPasswordLabel(result[4]);
            view.setUsernameLabel(result[5]);
            view.setTypeLabel(result[6]);
            view.setNrLabel(result[9]);
        }
    }
}