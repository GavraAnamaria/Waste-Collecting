package Presenter;

import Model.Locatie;
import Model.Oras;
import View.IAngajat;

import java.util.List;

public class PAngajat {
    IAngajat interfata;
    Oras oras;

    public PAngajat(IAngajat i, String nume){
        interfata = i;
        oras = new Oras(nume);
    }
    public void listaLocatii() {
        interfata.setList(oras.getDeseuri().toStringList());
    }

    public void traseuOptim() {
        List<Locatie> l = oras.traseuOptim();
        String[] s = new String[l.size()];
        for(int i = 0; i < l.size(); ++i) {
            s[i] = "[ " + oras.getDeseuri().getAllLoc().indexOf((Locatie) l.get(i)) + " ]   " + ((Locatie) l.get(i)).toString();
        }
        interfata.setList(s);
    }
}
