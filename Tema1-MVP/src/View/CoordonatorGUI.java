package View;
import Model.PersistentaUtilizatori;
import Model.Tip;
import Model.Utilizator;
import Oras.Strada;
import Presenter.PCoordonator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CoordonatorGUI  extends JPanel implements ICoordonator, ActionListener {
    JList<String> list = new JList<>();
    JScrollPane listScroller;
    JLabel oras;
    JComboBox<String> strada = new JComboBox<>();
    JComboBox<String> locatie = new JComboBox<>();
    JComboBox<String> nume = new JComboBox<>();
    JTextField numar = new JTextField();
    private final JButton adaugare = new JButton("ADAUGARE");
    private final JButton stergere = new JButton("STERGERE");
    private final JButton atribuire = new JButton("ATRIBUIRE");
    private final JButton rapoarte = new JButton("RAPOARTE");
    private final JButton locatii = new JButton("VIZUALIZARE LOCATII");
    private final JButton traseuOptim = new JButton("TRASEU OPTIM");
    PCoordonator pres = new PCoordonator(this);

    public CoordonatorGUI() {
        this.setLayout(null);
        listScroller = new JScrollPane(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        ImageIcon icon = new ImageIcon("oras2.jpg");
        Image img =icon.getImage();
        Image newimg = img.getScaledInstance(900, 650,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        oras = new JLabel(newIcon);
        List<Utilizator> utilizatori = (new PersistentaUtilizatori()).getListaUtilizatori();
        String[] util = new String[utilizatori.size()];
        int j=0;
        for(Utilizator u: utilizatori)
            if(u.getTip() == Tip.ANGAJAT){
                util[j] = u.getNume();
                j++;}
        setNumeAng(util);

        for(Strada s: Strada.values())
            strada.addItem(s.toString2());
        addListeners();

        this.setBackground(new Color(220,230,250));
        list.setBackground(new Color(205,215,240));
        addComponentsToContainer();
        setLocationAndSize();

        this.setVisible(true);
        this.setBounds(200, 20, 1350, 720);
    }

    public void addListeners(){
        this.atribuire.addActionListener(this);
        this.adaugare.addActionListener(this);
        this.stergere.addActionListener(this);
        this.rapoarte.addActionListener(this);
        this.locatii.addActionListener(this);
        this.traseuOptim.addActionListener(this);
    }
    public void addComponentsToContainer() {
        this.add(listScroller);
        this.add(locatii);
        this.add(traseuOptim);
        this.add(oras);
        this.add(strada);
        this.add(numar);
        this.add(adaugare);
        this.add(stergere);
        this.add(locatie);
        this.add(nume);
        this.add(atribuire);
        this.add(rapoarte);
    }

    public void setLocationAndSize() {
        listScroller.setBounds(30, 20, 400, 595);
        locatii.setBounds(30,  630, 190, 30);
        traseuOptim.setBounds(235, 630, 190, 30);

        oras.setBounds(480, 20, 800, 500);
        strada.setBounds(480, 540, 300, 30);
        numar.setBounds(480, 585, 300, 30);
        adaugare.setBounds(480, 630, 140, 30);
        stergere.setBounds(640, 630, 140, 30);

        locatie.setBounds(830, 540, 300, 30);
        nume.setBounds(830, 585, 300, 30);
        atribuire.setBounds(830, 630, 140, 30);
        rapoarte.setBounds(990, 630, 140, 30);
    }

    @Override
    public void setList(String[] list) {
        this.list.setListData(list);
        this.locatie.setModel(new DefaultComboBoxModel<>(list));
    }

    @Override
    public void setNumeAng(String[] list) {
        this.nume.setModel(new DefaultComboBoxModel<>(list));
    }

    @Override
    public Strada getStrada() {
        Strada[] s = Strada.values();
        return s[strada.getSelectedIndex()];
    }

    @Override
    public int getNr() {
        return Integer.parseInt(numar.getText());
    }

    @Override
    public int getLocatie() {
        return locatie.getSelectedIndex();
    }

    @Override
    public String getNumeUtilizator() {
        return (String) nume.getSelectedItem();
    }

    @Override
    public int getLocatieStergere(){
        return list.getSelectedIndex();
    }

    @Override
    public Container getContent() {
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == locatii)
            pres.listaLocatii();
            else if(e.getSource() == traseuOptim)
                pres.traseuOptim();
            if(e.getSource() == adaugare)
                pres.adaugareLocatie();
            if(e.getSource() == stergere)
                pres.stergereLocatie();
            if(e.getSource() == atribuire)
                pres.atribuire();
            if(e.getSource() == rapoarte)
                pres.rapoarte();

    }
}
