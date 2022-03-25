package View;

import Oras.Strada;

import java.awt.*;

public interface ICoordonator {
    void setList(String[] list);
    void setNumeAng(String[] list);

    Strada getStrada();
    int getNr();
    int getLocatie();

    String getNumeUtilizator();

    int getLocatieStergere();

    Container getContent();
}
