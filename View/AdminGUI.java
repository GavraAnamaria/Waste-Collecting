package View;

import Model.Tip;
import Presenter.PAdmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminGUI extends JPanel implements IAdmin, ActionListener {
    PAdmin presenter = new PAdmin(this);
    private JList<String> list = new JList<>();
    private JScrollPane listScroller;
    private JComboBox<String> tip;
    private JTextField nume = new JTextField();
    private JTextField parola = new JTextField();
    private JLabel parolaL = new JLabel("PAROLA: ");
    private JLabel numeL = new JLabel("NUME UTILIZATOR: ");
    private JLabel tipL = new JLabel("TIP UTILIZATOR: ");
    private final JButton adaugare = new JButton("ADAUGARE");
    private final JButton stergere = new JButton("STERGERE");
    private final JButton reload = new JButton("REINCARARE");

    public AdminGUI() {
        this.setLayout(null);
        String[] tipAngajati = { "ANGAJAT","ADMINISTRATOR","COORDONATOR"};
        listScroller = new JScrollPane(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);

        tip = new JComboBox<>(tipAngajati);

        presenter.actualizareLista();

        addComponentsToContainer();
        setStyle();
        setLocationAndSize();
        addLis();

        this.setVisible(true);
        this.setBounds(400, 200, 950, 370);
    }

    public void setStyle(){
        numeL.setFont(new Font("Serif", Font.BOLD, 15));
        parolaL.setFont(new Font("Serif", Font.BOLD, 15));
        tipL.setFont(new Font("Serif", Font.BOLD, 15));
        this.getContent().setBackground(new Color(235,245,255));
        list.setBackground(new Color(228,238,255));
        nume.setBackground(new Color(225,235,255));
        parola.setBackground(new Color(225,235,255));
        tip.setBackground(new Color(225,235,255));
    }

    public void addComponentsToContainer() {
        this.add(listScroller);
        this.add(numeL);
        this.add(nume);
        this.add(parolaL);
        this.add(parola);
        this.add(tipL);
        this.add(tip);
        this.add(stergere);
        this.add(adaugare);
        //this.add(reload);
    }

    public void setLocationAndSize() {
        listScroller.setBounds(50, 50, 300, 235);
       // reload.setBounds(50, 250, 300, 35);
        numeL.setBounds(410,  50, 160, 35);
        nume.setBounds(580,  50, 300, 35);
        parolaL.setBounds(410, 115, 160, 35);
        parola.setBounds(580, 115, 300, 35);
        tipL.setBounds(410, 180, 160, 35);
        tip.setBounds(580, 180, 300, 35);
        adaugare.setBounds(410, 250, 220, 35);
        stergere.setBounds(660, 250, 220, 35);
    }

    public void addLis(){
        adaugare.addActionListener(this);
        stergere.addActionListener(this);
        reload.addActionListener(this);
    }

    @Override
    public void setList(String[] list) {
        this.list.setListData(list);
    }

    @Override
    public Container getContent() {
        return this;
    }

    @Override
    public String getNume() {
        return this.nume.getText();
    }

    @Override
    public void showError(String err) {
        JOptionPane.showMessageDialog(this, err);
    }

    @Override
    public String getParola() {
        return this.parola.getText();
    }

    @Override
    public Tip getTip() {
       return Tip.valueOf((String)tip.getSelectedItem());
    }

    @Override
    public int getSelectedItem() {
       return list.getSelectedIndex();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    //    PAdmin presenter = new PAdmin(this);
        if(e.getSource() == adaugare){
            presenter.adaugare();
        }
       else if(e.getSource() == stergere){
            presenter.stergere();
        }
       else if(e.getSource() == reload){
            presenter.actualizareLista();
        }

    }

}
