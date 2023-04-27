package View;

import Presenter.PAngajat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AngajatGUI extends Container implements IAngajat, ActionListener {
    JList<String> list;
    JScrollPane listScroller;
    JLabel oras;
    ImageIcon icon;
    private final JButton locatii = new JButton("VIZUALIZARE LOCATII");
    private final JButton traseuOptim = new JButton("TRASEU OPTIM");
    PAngajat presenter ;

    public AngajatGUI(String nume) {
        presenter = new PAngajat(this, nume);
        this.setLayout(null);
        list = new JList<>(); //data has type Object[]
        listScroller = new JScrollPane(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        addListener();

        icon = new ImageIcon("oras2.jpg");
        Image img =icon.getImage();
        Image newimg = img.getScaledInstance(900, 650,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        oras = new JLabel(newIcon);
        this.setBackground(new Color(220,230,250));
        list.setBackground(new Color(205,215,240));
        addComponentsToContainer();
        setLocationAndSize();

        this.setVisible(true);
        this.setBounds(200, 100, 1350, 650);
    }

    public void addListener(){
        this.traseuOptim.addActionListener(this);
        this.locatii.addActionListener(this);
    }

    public void addComponentsToContainer() {
        this.add(listScroller);
        this.add(locatii);
        this.add(traseuOptim);
        this.add(oras);
    }

    public void setLocationAndSize() {
        listScroller.setBounds(30, 20, 400, 500);
        locatii.setBounds(30,  530, 190, 30);
        traseuOptim.setBounds(235, 530, 190, 30);
        oras.setBounds(450, 20, 850, 550);
    }

    @Override
    public void setList(String[] list1) {
        this.list.setListData(list1);
    }

    @Override
    public Container getContent() {
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == traseuOptim)
            presenter.traseuOptim();
        else
            presenter.listaLocatii();
    }
}
