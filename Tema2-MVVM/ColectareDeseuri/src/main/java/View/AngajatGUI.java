package View;
import VM.AngajatVM;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingBuilder;
import net.sds.mvvm.bindings.BindingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AngajatGUI extends Container implements ActionListener {
    JList<String> list;
    JScrollPane listScroller;
    JLabel oras;
    ImageIcon icon;
    private final JButton locatii = new JButton("VIZUALIZARE LOCATII");
    private final JButton traseuOptim = new JButton("TRASEU OPTIM");
    AngajatVM vm;

    public AngajatGUI(String name) {
        this.setLayout(null);
        vm = new AngajatVM(name);
        list = new JList<>();
        listScroller = new JScrollPane(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        addListener();
        try {
            Binder.bind(this, vm);
        } catch (BindingException e) {
            e.printStackTrace();
        }

        icon = new ImageIcon("oras2.jpg");
        Image img =icon.getImage();
        Image newimg = img.getScaledInstance(925, 500,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        oras = new JLabel(newIcon);
        this.setBackground(new Color(220,230,250));
        list.setBackground(new Color(205,215,240));

        addComponentsToContainer();
        setLocationAndSize();
        Binding();
        this.setVisible(true);
        this.setBounds(100, 100, 1370, 580);
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
        listScroller.setBounds(30, 20, 350, 450);
        locatii.setBounds(30,  490, 170, 30);
        traseuOptim.setBounds(210, 490, 170, 30);
        oras.setBounds(400, 20, 920, 500);
    }

    public void Binding() {
        try {
            new BindingBuilder<String[], String[]>()
                    .withSourceSupplier(vm.getList()::get)
                    .withSourceTrigger((b, d) -> vm.getList().addPropertyChangeListener(e -> b.apply(d)))
                    .withTargetConsumer(s ->{ list.setListData(s);})
                    .build();
        } catch (BindingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            try {
                if(e.getSource() == traseuOptim)
                    vm.getOptimalPathCommand().execute();
                else
                    vm.getLocListCommand().execute();
            } catch (BindingException bindingException) {
                bindingException.printStackTrace();
            }
        }
    }
