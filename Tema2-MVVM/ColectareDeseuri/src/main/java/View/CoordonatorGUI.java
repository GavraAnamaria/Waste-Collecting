package View;
import Model.*;
import Oras.Strada;
import VM.CoordonatorVM;
import VM.Trigger.TriggerJComboBox;
import VM.Trigger.TriggerJList;
import net.sds.mvvm.bindings.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CoordonatorGUI  extends JPanel implements  ActionListener {
    private final JList<String> list = new JList<>();
    private final JLabel format  = new JLabel("FORMAT");
    private final JComboBox<String> formatFRap  = new JComboBox<>();

    @Bind(value = "text",target = "locatieRap.value", type = BindingType.BI_DIRECTIONAL)
    private final JTextField locatieFRap  = new JTextField();
    private final JLabel locatie_fisier  = new JLabel("LOCATIE FISIER");

    @Bind(value = "text",target = "denumireRap.value", type = BindingType.BI_DIRECTIONAL)
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

    @Bind(value = "text",target = "nr.value", type = BindingType.BI_DIRECTIONAL)
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
    private final CoordonatorVM vm = new CoordonatorVM();

    public CoordonatorGUI() {
        this.setLayout(null);
        try {
            Binder.bind(this, vm);
        } catch (BindingException e) {
            e.printStackTrace();
        }
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

        Bind();

        addListeners();
        this.setVisible(true);

        chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        chartPanel.setBounds(10, 10, 400, 570);
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
        this.setBackground(new Color(220,230,250));
        mentenanta.setBackground(new Color(220,230,250));
        generareRapoarte.setBackground(new Color(220,230,250));
        grafice.setBackground(new Color(220,230,250));
        grafic.setBackground(new Color(220,230,250));
        list.setBackground(new Color(205,215,240));
        this.setBounds(100, 20, 1400, 720);
    }

    public void addListeners(){
        this.atribuire.addActionListener(this);
        this.adaugare.addActionListener(this);
        this.stergere.addActionListener(this);
        this.rapoarte.addActionListener(this);
        this.locatii.addActionListener(this);
        this.traseuOptim.addActionListener(this);
        this.liniar.addActionListener(this);
        this.circular.addActionListener(this);
        this.rad.addActionListener(this);
    }
    public void addComponentsToContainer() {
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
    }

    public void setLocationAndSize() {

        locatie_fisier.setFont(new Font("Serif", Font.BOLD, 15));
        format.setFont(new Font("Serif", Font.BOLD, 15));
        denumire.setFont(new Font("Serif", Font.BOLD, 15));
        listScroller.setBounds(30, 20, 400, 595);
        locatii.setBounds(30,  630, 190, 30);
        stergere.setBounds(235, 630, 190, 30);
        tp.setBounds(450, 0, 850, 670);

        oras.setBounds(20, 10, 800, 500);
        strada.setBounds(20, 520, 350, 30);
        numar.setBounds(20, 560, 350, 30);
        adaugare.setBounds(20, 600, 350, 30);

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
        grafic.setBounds(30, 100, 790, 550);
    }


    public void Bind() {
        try {
            new BindingBuilder<String[], String[]>()
                    .withSourceSupplier(vm.getList()::get)
                    .withSourceTrigger((b, d) -> vm.getList().addPropertyChangeListener(e -> b.apply(d)))
                    .withTargetConsumer(list::setListData)
                    .build();
            new BindingBuilder<JFreeChart, JFreeChart>()
                    .withSourceSupplier(vm.getChart()::get)
                    .withSourceTrigger((b, d) -> vm.getChart().addPropertyChangeListener(e -> b.apply(d)))
                    .withTargetConsumer(s ->{ this.chartPanel.setChart(s);
                    chartPanel.setVisible(true);})
                    .build();
            new BindingBuilder<Integer, Integer>()
                    .withSourceSupplier(() -> Objects.requireNonNull(this.list.getSelectedIndex()))
                    .withTargetConsumer(vm.getDeletePoz()::set)
                    .withSourceTrigger(new TriggerJList(list))
                    .build().apply(Direction.UP);
            new BindingBuilder<Integer, Integer>()
                    .withSourceSupplier(() -> Objects.requireNonNull(this.locatie.getSelectedIndex()))
                    .withTargetConsumer(vm.getLocatie()::set)
                    .withSourceTrigger(new TriggerJComboBox(locatie))
                    .build().apply(Direction.UP);
            new BindingBuilder<String, String>()
                    .withSourceSupplier(() -> Objects.requireNonNull(this.strada.getSelectedItem()).toString())
                    .withTargetConsumer(vm.getStrada()::set)
                    .withSourceTrigger(new TriggerJComboBox(strada))
                    .build().apply(Direction.UP);
            new BindingBuilder<String, String>()
                    .withSourceSupplier(() -> Objects.requireNonNull(this.nume.getSelectedItem()).toString())
                    .withTargetConsumer(vm.getUserName()::set)
                    .withSourceTrigger(new TriggerJComboBox(nume))
                    .build().apply(Direction.UP);
            new BindingBuilder<String, String>()
                    .withSourceSupplier(() -> Objects.requireNonNull(this.formatFRap.getSelectedItem()).toString())
                    .withTargetConsumer(vm.getFormat()::set)
                    .withSourceTrigger(new TriggerJComboBox(formatFRap))
                    .build().apply(Direction.UP);
        } catch (BindingException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == locatii)
                vm.getLocListCommand().execute();
            else if (e.getSource() == traseuOptim)
                 vm.getOptimalPathCommand().execute();
                if (e.getSource() == adaugare)
                    vm.getAddLocCommand().execute();
                if (e.getSource() == stergere)
                    vm.getDelLocCommand().execute();
                if (e.getSource() == atribuire)
                    vm.getAtribLocCommand().execute();
                if (e.getSource() == rapoarte)
                    vm.getRapCommand().execute();
                if (e.getSource() == liniar)
                    vm.getLiniarCommand().execute();
                if (e.getSource() == circular)
                    vm.getCircularCommand().execute();
                if (e.getSource() == rad)
                    vm.getRadialCommand().execute();
        }catch (BindingException bindingException) {
            bindingException.printStackTrace();
        }
    }
}
