package Controller;
import Model.PersistentaUtilizatori;
import Model.Tip;
import Model.Translator;
import Model.Utilizator;
import View.AdminGUI;
import android.database.Observable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Setter
@Getter
public class CAdmin {
    private AdminGUI view;
    private PersistentaUtilizatori utilizatori;

    public CAdmin(){
        utilizatori = new PersistentaUtilizatori();
        view = new AdminGUI();
        view.setList(utilizatori.toStringList());
        view.deleteListener(new deleteAction());
        view.addListener(new addAction());
        view.filterListener(new filterAction());
        view.roListener(new languageAction("ro"));
        view.enListener(new languageAction("en"));
        view.frListener(new languageAction("fr"));
        view.geListener(new languageAction("ge"));
    }


    class deleteAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(utilizatori.stergereUtilizator(view.getDeleteIndex()))
                view.setList(utilizatori.toStringList());
            else
                view.showError("Nu a fost selectat niciun utilizator!");
        }
    }

    class addAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(utilizatori.adaugareUtilizator(new Utilizator(view.getUName(), view.getPassword(), Tip.valueOf(view.getType()))))
                view.setList(utilizatori.toStringList());
            else
                view.showError("Utilizator existent!");
        }
    }

    class filterAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            List<Utilizator> allUtil = new PersistentaUtilizatori().getListaUtilizatori();
            String f= view.getFilter();
            if(f.equals("ALL"))
                utilizatori.setUtilizatori(allUtil);
            else for(Tip t:Tip.values())
                if(f.equals(t.name()))
                    utilizatori.setUtilizatori(allUtil.stream().filter(u->u.getTip().name().equals(f)).collect(Collectors.toList()));
            view.setList(utilizatori.toStringList());
        }
    }
@AllArgsConstructor
    class languageAction implements ActionListener {
        String language;
        public void actionPerformed(ActionEvent e) {
            String[] data = new String[]{"TOT", "ANGAJAT", "ADMINISTRATOR", "COORDONATOR","PAROLA","NUMEUTILIZATOR","TIPUTILIZATOR", "ADAUGARE","STERGERE"};
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
        }
    }
}