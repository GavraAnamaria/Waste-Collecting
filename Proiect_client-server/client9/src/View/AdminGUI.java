package View;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminGUI extends JPanel  {
    private final JList<String> list = new JList<>() ;
    private final JScrollPane listScroller;
    private final JComboBox<String> tip;
    private final JComboBox<String> filtrare;
    private final JTextField nume = new JTextField();
    private final JTextField password = new JTextField();
    private final JTextField email = new JTextField();
    private final JTextField nr = new JTextField();
    private final JLabel passwordL = new JLabel("PAROLĂ: ");
    private final JLabel numeL = new JLabel("NUME UTILIZATOR: ");
    private final JLabel emailL = new JLabel("EMAIL: ");
    private final JLabel nrL = new JLabel("NUMAR: ");
    private final JLabel tipL = new JLabel("TIP UTILIZATOR: ");
    private final JButton adaugare = new JButton("ADAUGARE");
    private final JButton stergere = new JButton("STERGERE");
    private final JButton ro = new JButton("RO");
    private final JButton back = new JButton("<-");
    private final JButton en = new JButton("EN");
    private final JButton ge = new JButton("GE");
    private final JButton fr = new JButton("FR");
    private final String[] listaTip ;
    private final String[] listaFiltre ;

    public AdminGUI() {
        this.setLayout(null);
        listScroller = new JScrollPane(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        listaTip = new String[]{"ANGAJAT", "ADMINISTRATOR", "COORDONATOR"};
        tip = new JComboBox<>(listaTip);
        listaFiltre = new String[]{"TOT", "ANGAJAT", "ADMINISTRATOR", "COORDONATOR"};
        filtrare = new JComboBox<>(listaFiltre);
        list.setSelectedIndex(1);
        addComponentsToContainer();
        setStyle();
        setLocationAndSize();
        this.setVisible(true);
        this.setBounds(400, 100, 780, 600);
    }

    private void setStyle(){
        ro.setFont(new Font("Serif", Font.BOLD, 10));
        ro.setBorder(new EmptyBorder(0, 0, 0, 0));
        back.setFont(new Font("Serif", Font.BOLD, 10));
        back.setBorder(new EmptyBorder(0, 0, 0, 0));
        en.setFont(new Font("Serif", Font.BOLD, 10));
        en.setBorder(new EmptyBorder(0, 0, 0, 0));
        ge.setFont(new Font("Serif", Font.BOLD, 10));
        ge.setBorder(new EmptyBorder(0, 0, 0, 0));
        fr.setFont(new Font("Serif", Font.BOLD, 10));
        fr.setBorder(new EmptyBorder(0, 0, 0, 0));
        numeL.setFont(new Font("Serif", Font.BOLD, 15));
        passwordL.setFont(new Font("Serif", Font.BOLD, 15));
        emailL.setFont(new Font("Serif", Font.BOLD, 15));
        nrL.setFont(new Font("Serif", Font.BOLD, 15));
        tipL.setFont(new Font("Serif", Font.BOLD, 15));
        this.setBackground(new Color(235,245,255));
        list.setBackground(new Color(228,238,255));
        filtrare.setBackground(new Color(207,215,255));
        nume.setBackground(new Color(225,235,255));
        password.setBackground(new Color(225,235,255));
        email.setBackground(new Color(225,235,255));
        nr.setBackground(new Color(225,235,255));
        tip.setBackground(new Color(225,235,255));
    }

    private void addComponentsToContainer() {
        this.add(listScroller);
        this.add(filtrare);
        this.add(numeL);
        this.add(nume);
        this.add(passwordL);
        this.add(password);
        this.add(emailL);
        this.add(email);
        this.add(nrL);
        this.add(nr);
        this.add(tipL);
        this.add(tip);
        this.add(stergere);
        this.add(adaugare);
        this.add(ro);
        this.add(en);
        this.add(ge);
        this.add(fr);
        this.add(back);
    }

    private void setLocationAndSize() {
        back.setBounds(20, 20, 25, 27);
        ro.setBounds(600, 20, 25, 27);
        en.setBounds(630, 20, 25, 27);
        fr.setBounds(660, 20, 25, 27);
        ge.setBounds(690, 20, 25, 27);
        listScroller.setBounds(50, 90, 250, 400);
        stergere.setBounds(50, 500, 250, 35);
        filtrare.setBounds(50, 50, 250, 30);
        numeL.setBounds(420,  55, 300, 35);
        nume.setBounds(420,  95, 300, 35);
        passwordL.setBounds(420, 140, 300, 35);
        password.setBounds(420, 180, 300, 35);
        emailL.setBounds(420, 230, 300, 35);
        email.setBounds(420, 270, 300, 35);
        nrL.setBounds(420, 320, 300, 35);
        nr.setBounds(420, 360, 300, 35);
        tipL.setBounds(420, 410, 300, 35);
        tip.setBounds(420, 450, 300, 35);
        adaugare.setBounds(420, 500, 300, 35);
    }

//----------------------------------[ GETTER ]---------------------------------------------------

    public String getUName() {
        return nume.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public String getType() {
        return listaTip[tip.getSelectedIndex()];
    }
    public String getFilter() {
        return listaFiltre[filtrare.getSelectedIndex()];
    }
    public int getDeleteIndex() {
        return this.list.getSelectedIndex();
    }

    public void setFiltre(String[] filtre){
        filtrare.setModel(new DefaultComboBoxModel<>(filtre));
    }
    public void setTypeCBox(String[] filtre){
        tip.setModel(new DefaultComboBoxModel<>(filtre));
    }
    public void setUsernameLabel(String username){
        this.numeL.setText(username);
    }
    public void setPasswordLabel(String txt){
        this.passwordL.setText(txt);
    }
    public void setTypeLabel(String txt){
        this.tipL.setText(txt);
    }
    public void setAddButton(String text){
        this.adaugare.setText(text);
    }
    public void setDelButton(String text){
        this.stergere.setText(text);
    }
    public void setNrLabel(String text){
        this.nrL.setText(text);
    }

//--------------------------------------[ AFISARE ]------------------------------------------


    public void setList(String[] rezultat) {
        list.setListData(rezultat);
    }

    public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

//----------------------------------[ LISTENER ]------------------------------------------

    public void addListener(ActionListener act) {
        adaugare.addActionListener(act);
    }

    public void deleteListener(ActionListener act) {
        stergere.addActionListener(act);
    }
    public void filterListener(ActionListener act) {
        filtrare.addActionListener(act);
    }
    public void roListener(ActionListener act) {
        ro.addActionListener(act);
    }
    public void enListener(ActionListener act) {
        en.addActionListener(act);
    }
    public void frListener(ActionListener act) {
        fr.addActionListener(act);
    }
    public void geListener(ActionListener act) {
        ge.addActionListener(act);
    }
    public void backListener(ActionListener act) {
        back.addActionListener(act);
    }

    public String getEmail() {
        return this.email.getText();
    }

    public String getNr() {
        return this.nr.getText();
    }

}