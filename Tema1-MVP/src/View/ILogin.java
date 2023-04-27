package View;

import java.awt.*;

public interface ILogin {
    String getNume();
    char[] getPassword1();
    void setContent(Container content);
    void setSize(Rectangle bounds);
    void showError(String err);
}
