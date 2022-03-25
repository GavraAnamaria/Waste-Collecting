package Presenter;

import Model.PersistentaUtilizatori;
import Model.Tip;
import View.*;

import java.awt.*;


public class PLogin {
    ILogin iLogin;
    PersistentaUtilizatori persUtilizatori = new PersistentaUtilizatori();

    public PLogin(ILogin il){
        this.iLogin = il;
    }

    public void login (){
        Container content ;
        Tip tip = persUtilizatori.cautareUtilizator(iLogin.getNume(), String.valueOf(iLogin.getPassword1()));
        if (tip==Tip.ANGAJAT)
            content = (new AngajatGUI(iLogin.getNume())).getContent();
        else if (tip==Tip.COORDONATOR)
            content = (new CoordonatorGUI()).getContent();
        else if (tip==Tip.ADMINISTRATOR)
            content = (new AdminGUI()).getContent();
        else{
            iLogin.showError("DATE INCORECTE");
            return;
        }

        iLogin.setContent(content);
        iLogin.setSize(content.getBounds());

    }
}
