package Presenter;

import Model.Locatie;
import Model.Oras;
import View.IAngajat;
import View.ICoordonator;

import java.util.List;

public class PCoordonator {
    ICoordonator interfata;
    Oras oras;

    public PCoordonator(ICoordonator i){
        interfata = i;
        oras = new Oras(interfata.getNumeUtilizator());
    }

    public void listaLocatii() {
        interfata.setList(oras.getPersistenta().toStringList());
        oras.writeData();
    }

    public void traseuOptim() {
        List<Locatie> l = oras.traseuOptim();
        String[] s = new String[l.size()];
        for(int i = 0; i < l.size(); ++i) {
            s[i] = "[ " + oras.getDeseuri().getAllLoc().indexOf((Locatie) l.get(i)) + " ]   " + ((Locatie) l.get(i)).toString();
        }
        interfata.setList(s);
    }

    public void stergereLocatie(){
        oras.stergereLocatieDeseu(interfata.getLocatieStergere());
        listaLocatii();
    }

    public void adaugareLocatie(){
        oras.adaugareLocatieDeseu(new Locatie(interfata.getStrada(), interfata.getNr()));
        listaLocatii();
    }

    public void atribuire() {
        oras.getPersistenta().getAllLoc().get(interfata.getLocatie()).setUtilizator(interfata.getNumeUtilizator());
        listaLocatii();
    }

    public void rapoarte() {
        oras.writeCSV();
    }
}
