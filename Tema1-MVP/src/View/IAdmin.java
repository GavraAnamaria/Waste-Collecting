package View;

import Model.Tip;

import java.awt.*;

public interface IAdmin {

    void setList(String[] list);

    Container getContent();

    String getNume();

    void showError(String err);

    String getParola();

    Tip getTip();

    int getSelectedItem();
}
