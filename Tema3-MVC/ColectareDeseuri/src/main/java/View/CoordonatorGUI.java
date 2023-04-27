package View;
import Model.*;
import Oras.Strada;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class CoordonatorGUI  extends JPanel implements Observer {
    private final JList<String> list = new JList<>();
    private final JLabel format  = new JLabel("FORMAT");
    private final JComboBox<String> formatFRap  = new JComboBox<>();

    private final JTextField locatieFRap  = new JTextField();
    private final JLabel locatie_fisier  = new JLabel("LOCATIE FISIER");

    private final JTextField denumireFRap  = new JTextField();
    private final JLabel denumire  = new JLabel("DENUMIRE FISIER");

    private final JButton liniar = new JButton("LINIAR");
    private final JButton circular = new JButton("CIRCULAR");
    private final JButton rad = new JButton("RADIAL");
    private final JPanel grafic = new JPanel();

    private final JScrollPane listScroller;
    private final JLabel oras;
    private final JComboBox<String> strada = new JComboBox<>();
    private final JComboBox<String> locatie = new JComboBox<>();
    private final JComboBox<String> nume = new JComboBox<>();

    private final JTextField numar = new JTextField();
    private final JButton adaugare = new JButton("ADAUGARE");
    private final JButton stergere = new JButton("STERGERE");
    private final JButton atribuire = new JButton("ATRIBUIRE");
    private final JButton rapoarte = new JButton("GENERARE RAPOARTE");
    private final JButton locatii = new JButton("VIZUALIZARE LOCATII");
    private final JButton traseuOptim = new JButton("TRASEU OPTIM");
    private final JTabbedPane tp = new JTabbedPane();
    private final JPanel mentenanta = new JPanel();
    private final JPanel generareRapoarte = new JPanel();
    private final JPanel grafice = new JPanel();
    JFreeChart chart;
    ChartPanel chartPanel;
    private final JButton ro = new JButton("RO");
    private final JButton en = new JButton("EN");
    private final JButton ge = new JButton("GE");
    private final JButton fr = new JButton("FR");

    public CoordonatorGUI() {
        this.setLayout(null);
        mentenanta.setLayout(null);
        generareRapoarte.setLayout(null);
        grafice.setLayout(null);
        mentenanta.setLayout(null);
        listScroller = new JScrollPane(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        ImageIcon icon = new ImageIcon("oras2.jpg");
        Image img =icon.getImage();
        Image newimg = img.getScaledInstance(800, 530,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        oras = new JLabel(newIcon);
        this.locatie.setModel(new DefaultComboBoxModel<>((new PersistentaLocatii()).toStringList1()));

        setComboBoxData();

        addComponentsToContainer();
        setLocationAndSize();
        setStyle();

        this.setVisible(true);

        chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        chartPanel.setBounds(15, 10, 650, 650);
        grafic.add(chartPanel);
        chartPanel.setVisible(true);
    }

    private void setComboBoxData(){
        List<Utilizator> utilizatori = (new PersistentaUtilizatori()).getListaUtilizatori();
        String[] util = new String[utilizatori.size()];
        int j=0;
        for(Utilizator u: utilizatori)
            if(u.getTip() == Tip.ANGAJAT){
                util[j] = u.getNume();
                j++;}
        this.nume.setModel(new DefaultComboBoxModel<>(util));

        for(Strada s: Strada.values())
            strada.addItem(s.toString2());
        for(TipRaport t: TipRaport.values())
            formatFRap.addItem(t.toString());
    }

    private void setStyle(){
        ro.setFont(new Font("Serif", Font.BOLD, 10));
        ro.setBorder(new EmptyBorder(0, 0, 0, 0));
        en.setFont(new Font("Serif", Font.BOLD, 10));
        en.setBorder(new EmptyBorder(0, 0, 0, 0));
        ge.setFont(new Font("Serif", Font.BOLD, 10));
        ge.setBorder(new EmptyBorder(0, 0, 0, 0));
        fr.setFont(new Font("Serif", Font.BOLD, 10));
        fr.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setBackground(new Color(220,230,250));
        locatie_fisier.setFont(new Font("Serif", Font.BOLD, 15));
        format.setFont(new Font("Serif", Font.BOLD, 15));
        denumire.setFont(new Font("Serif", Font.BOLD, 15));
        mentenanta.setBackground(new Color(220,230,250));
        generareRapoarte.setBackground(new Color(220,230,250));
        grafice.setBackground(new Color(220,230,250));
        grafic.setBackground(new Color(220,230,250));
        list.setBackground(new Color(205,215,240));
        this.setBounds(100, 20, 1350, 750);
    }


    private void addComponentsToContainer() {
        this.add(listScroller);
        this.add(locatii);
        this.add(stergere);
        this.add(rapoarte);
        this.add(tp);
        mentenanta.add(oras);
        mentenanta.add(traseuOptim);
        mentenanta.add(strada);
        mentenanta.add(numar);
        mentenanta.add(adaugare);
        mentenanta.add(locatie);
        mentenanta.add(nume);
        mentenanta.add(atribuire);
        generareRapoarte.add(format);
        generareRapoarte.add(formatFRap);
        generareRapoarte.add(locatie_fisier);
        generareRapoarte.add(locatieFRap);
        generareRapoarte.add(denumire);
        generareRapoarte.add(denumireFRap);
        generareRapoarte.add(rapoarte);
        grafice.add(liniar);
        grafice.add(circular);
        grafice.add(rad);
        grafice.add(grafic);
        tp.add(mentenanta, "Mentenanta");
        tp.add(generareRapoarte, "Generare Rapoarte");
        tp.add(grafice, "Vizualizare Grafice");
        this.add(ro);
        this.add(en);
        this.add(ge);
        this.add(fr);
    }

    public void setLocationAndSize() {
        ro.setBounds(1200, 10, 25, 27);
        en.setBounds(1230, 10, 25, 27);
        fr.setBounds(1260, 10, 25, 27);
        ge.setBounds(1290, 10, 25, 27);
        listScroller.setBounds(30, 35, 400, 620);
        locatii.setBounds(30,  665, 190, 30);
        stergere.setBounds(235, 665, 190, 30);
        tp.setBounds(450, 30, 865, 670);

        oras.setBounds(30, 10, 800, 500);
        strada.setBounds(30, 520, 350, 30);
        numar.setBounds(30, 560, 350, 30);
        adaugare.setBounds(30, 600, 350, 30);

        locatie.setBounds(440, 520, 370, 30);
        nume.setBounds(440, 560, 370, 30);
        atribuire.setBounds(440, 600, 180, 30);
        traseuOptim.setBounds(630, 600, 180, 30);

        format.setBounds(150, 120, 500, 30);
        formatFRap.setBounds(150, 150, 500, 40);
        locatie_fisier.setBounds(150, 220, 500, 30);
        locatieFRap.setBounds(150, 250, 500, 40);
        denumire.setBounds(150, 320, 500, 30);
        denumireFRap.setBounds(150, 350, 500, 40);
        rapoarte.setBounds(150, 430, 500, 50);

        circular.setBounds(30, 30, 250, 50);
        liniar.setBounds(300, 30, 250, 50);
        rad.setBounds(570, 30, 250, 50);
        grafic.setBounds(20, 120, 800, 650);

    }


//----------------------------------[ GETTER ]---------------------------------------------------

    public String getRapPath() {
        return locatieFRap.getText();
    }

    public String getRapName() {
        return denumireFRap.getText();
    }

    public String getRapType() {
        return Objects.requireNonNull(this.formatFRap.getSelectedItem()).toString();
    }

    public String getStreet() {
        return Objects.requireNonNull(this.strada.getSelectedItem()).toString();
    }

    public int getSelLocIndex() {
        return this.list.getSelectedIndex();
    }

    public int getLocCBoxSelectIndex() {
        return this.locatie.getSelectedIndex();
    }

    public String getUserName() {
        return Objects.requireNonNull(this.nume.getSelectedItem()).toString();
    }

    public String getNr() {
        return numar.getText();
    }


    public void setLocList(String[] rezultat) {
        list.setListData(rezultat);
    }

    public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

    public void setChart(JFreeChart c) {
        this.chart = c;
        chartPanel.setChart(chart);
    }

    public void setAddButton(String rezultat) {
        adaugare.setText(rezultat);
    }
    public void setLiniarButton(String rezultat) {
        liniar.setText(rezultat);
    }
    public void setCircButton(String rezultat) {
        circular.setText(rezultat);
    }
    public void setRadButton(String rezultat) {
        rad.setText(rezultat);
    }
    public void setViewLocButton(String rezultat) {
        locatii.setText(rezultat);
    }
    public void setRouteButton(String rezultat) {
        traseuOptim.setText(rezultat);
    }
    public void setRapButton(String rezultat) {
        rapoarte.setText(rezultat);
    }
    public void setDelButton(String rezultat) { stergere.setText(rezultat);}
    public void setAssignButton(String rezultat) {
        atribuire.setText(rezultat);
    }
    public void setFormatLabel(String rezultat) {
        format.setText(rezultat);
    }
    public void setFilePathLabel(String rezultat) {
        locatie_fisier.setText(rezultat);
    }
    public void setFileNameLabel(String rezultat) {
        denumire.setText(rezultat);
    }


    //----------------------------------[ LISTENER ]------------------------------------------

    public void assignListener(ActionListener act) {
        atribuire.addActionListener(act);
    }

    public void addListener(ActionListener act) {
        adaugare.addActionListener(act);
    }

    public void deleteListener(ActionListener act) {
        stergere.addActionListener(act);
    }

    public void rapListener(ActionListener act) {
        rapoarte.addActionListener(act);
    }

    public void locListener(ActionListener act) {
        locatii.addActionListener(act);
    }

    public void routeListener(ActionListener act) {
        traseuOptim.addActionListener(act);
    }

    public void linearGraphListener(ActionListener act) {
        liniar.addActionListener(act);
    }

    public void pieGraphListener(ActionListener act) {
        circular.addActionListener(act);
    }

    public void radialGraphListener(ActionListener act) {
        rad.addActionListener(act);
    }

    @Override
    public void update(Observable o) {
        this.locatie.setModel(new DefaultComboBoxModel<>(((PersistentaLocatii)o).toStringList1()));
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
}