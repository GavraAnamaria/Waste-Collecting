package Presenter;

import Model.PersistentaUtilizatori;
import Model.Utilizator;
import View.IAdmin;

public class PAdmin {
    IAdmin interfata;
    PersistentaUtilizatori utilizatori = new PersistentaUtilizatori();

    public PAdmin(IAdmin i){
        interfata = i;
    }

    public void adaugare(){
        if(!utilizatori.adaugareUtilizator(new Utilizator(interfata.getNume(), interfata.getParola(), interfata.getTip())))
          interfata.showError("Exista deja un utilizator cu aceste date!");
        actualizareLista();
    }

    public void stergere(){
        try{utilizatori.stergereUtilizator(interfata.getSelectedItem());}
        catch (IndexOutOfBoundsException e){
           interfata.showError("Nu a fost selectat niciun utilizator!");
        }
       actualizareLista();
    }

    public void actualizareLista(){
        interfata.setList(this.utilizatori.toStringList());
        utilizatori.saveToXML();}
}
