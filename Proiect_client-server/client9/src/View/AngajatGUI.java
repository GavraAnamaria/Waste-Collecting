package View;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class AngajatGUI extends Container{
    private final JList<String> list;
    private final JScrollPane listScroller;
    private final JLabel oras;
    private final JButton locatii = new JButton("VIZUALIZARE LOCATII");
    private final JButton traseuOptim = new JButton("TRASEU OPTIM");
    private final JButton ro = new JButton("RO");
    private final JButton back = new JButton("<-");
    private final JButton en = new JButton("EN");
    private final JButton ge = new JButton("GE");
    private final JButton fr = new JButton("FR");

    public AngajatGUI() {
        this.setLayout(null);
        list = new JList<>();
        listScroller = new JScrollPane(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);

        ImageIcon icon = new ImageIcon("oras2.jpg");
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(925, 500,  Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        oras = new JLabel(newIcon);

        this.setBackground(new Color(220,230,250));
        list.setBackground(new Color(205,215,240));
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


        addComponentsToContainer();
        setLocationAndSize();
        this.setVisible(true);
        this.setBounds(100, 100, 1370, 610);
    }


    private void addComponentsToContainer() {
        this.add(listScroller);
        this.add(locatii);
        this.add(traseuOptim);
        this.add(oras);
        this.add(ro);
        this.add(en);
        this.add(back);
        this.add(ge);
        this.add(fr);
    }

    private void setLocationAndSize() {
        ro.setBounds(1200, 10, 25, 27);
        back.setBounds(20, 10, 25, 27);
        en.setBounds(1230, 10, 25, 27);
        fr.setBounds(1260, 10, 25, 27);
        ge.setBounds(1290, 10, 25, 27);
        listScroller.setBounds(30, 45, 350, 450);
        locatii.setBounds(30,  515, 170, 30);
        traseuOptim.setBounds(210, 515, 170, 30);
        oras.setBounds(400, 45, 920, 500);
    }

    public void setList(String[] rezultat) {
        list.setListData(rezultat);
    }
    public void setViewLoc(String rezultat) {this.locatii.setText(rezultat);}
    public void setRoute(String rezultat) {
        traseuOptim.setText(rezultat);
    }



//----------------------------------[ LISTENER ]------------------------------------------

    public void addLocListener(ActionListener act) {
        locatii.addActionListener(act);
    }

    public void addRouteListener(ActionListener act) {
        traseuOptim.addActionListener(act);
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

}