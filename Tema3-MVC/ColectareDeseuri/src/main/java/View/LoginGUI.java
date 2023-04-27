package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginGUI extends  JFrame{
    private final Container container = this.getContentPane();
    private final JLabel title = new JLabel("USER LOGIN");
    private final JLabel userLabel = new JLabel("Username: ");
    private final JLabel passLabel = new JLabel("Password: ");
    private final JTextField userNameTF = new JTextField();
    private final JPasswordField passwordTF = new JPasswordField();
    private final JCheckBox showPassword = new JCheckBox("Show Password");
    private final JButton login = new JButton("LOGIN");

    public LoginGUI(){
        container.setLayout(null);
        setLocationAndSize();
        setStile();
        addComponentsToContainer();

        this.pack();
        this.setTitle("Login");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(500, 200, 600, 340);
    }

    public void setLocationAndSize() {
        title.setBounds(180, 30, 220, 40);
        userLabel.setBounds(60, 100, 120, 30);
        passLabel.setBounds(60, 150, 120, 30);
        userNameTF.setBounds(180, 100, 350, 30);
        passwordTF.setBounds(180, 150, 350, 30);
        showPassword.setBounds(170, 180, 150, 30);
        login.setBounds(60, 230, 460, 40);
    }



    public void setStile() {
        userLabel.setFont(new Font("Serif", Font.BOLD, 25));
        passLabel.setFont(new Font("Serif", Font.BOLD, 25));
        title.setFont(new Font("Serif", Font.BOLD, 35));
        login.setFont(new Font("Serif", Font.BOLD, 22));
        title.setForeground(new Color(220,230,250));
        userLabel.setForeground(Color.WHITE);
        passLabel.setForeground(Color.WHITE);
        showPassword.setForeground(Color.WHITE);
        login.setForeground(new Color(30,30,90));
        container.setBackground(new Color(30,30,90));
        showPassword.setBackground(new Color(30,30,90));
        login.setBackground(new Color(220,230,250));
    }

    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(title);
        container.add(passLabel);
        container.add(userNameTF);
        container.add(passwordTF);
        container.add(showPassword);
        container.add(login);
    }


//----------------------------------[ GETTER ]------------------------------------------

    public String getUserName() {
        return userNameTF.getText();
    }


    public JPasswordField getPassword() {
        return passwordTF;
    }

    public JCheckBox getShowPasswordCBox() {
        return this.showPassword;
    }


    public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

//----------------------------------[ LISTENER ]------------------------------------------

    public void loginListener(ActionListener act) {
        login.addActionListener(act);
    }


    public void showPasswordListener(ActionListener act) {
        showPassword.addActionListener(act);
    }
}