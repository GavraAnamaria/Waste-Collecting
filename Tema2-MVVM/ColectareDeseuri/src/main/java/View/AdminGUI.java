package View;

import VM.AdminVM;
import VM.Trigger.TriggerJComboBox;
import VM.Trigger.TriggerJList;
import net.sds.mvvm.bindings.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AdminGUI extends JPanel implements ActionListener {
    AdminVM vm = new AdminVM();
    private JList<String> list = new JList<>() ;
    private JScrollPane listScroller;
    String[] tipAngajati1 = { "ANGAJAT","ADMINISTRATOR","COORDONATOR"};
    private JComboBox<String> tip = new JComboBox<>(tipAngajati1);
    @Bind(value="text", target = "name.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField nume = new JTextField();
    @Bind(value="text", target = "password.value",  type = BindingType.BI_DIRECTIONAL)
    private JTextField parola = new JTextField();
    private JLabel parolaL = new JLabel("PAROLĂ: ");
    private JLabel numeL = new JLabel("NUME UTILIZATOR: ");
    private JLabel tipL = new JLabel("TIP UTILIZATOR: ");
    private final JButton adaugare = new JButton("ADAUGARE");
    private final JButton stergere = new JButton("STERGERE");
    private final JButton reload = new JButton("REINCARARE");

    public AdminGUI() throws BindingException {
        this.setLayout(null);
        String[] tipAngajati = { "ANGAJAT","ADMINISTRATOR","COORDONATOR"};
        Binder.bind(this, vm);
        listScroller = new JScrollPane(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        tip = new JComboBox<>(tipAngajati);
        list.setSelectedIndex(1);
        Binding();
        addComponentsToContainer();
        setStyle();
        setLocationAndSize();
        addLis();
        vm.getUpdateCommand().execute();
        this.setVisible(true);
        this.setBounds(400, 200, 780, 500);
    }

    public void setStyle(){
        numeL.setFont(new Font("Serif", Font.BOLD, 15));
        parolaL.setFont(new Font("Serif", Font.BOLD, 15));
        tipL.setFont(new Font("Serif", Font.BOLD, 15));
        this.setBackground(new Color(235,245,255));
        list.setBackground(new Color(228,238,255));
        nume.setBackground(new Color(225,235,255));
        parola.setBackground(new Color(225,235,255));
        tip.setBackground(new Color(225,235,255));
    }

    public void Binding() {
        try {
            new BindingBuilder<String[], String[]>()
                    .withSourceSupplier(vm.getUsers()::get)
                    .withSourceTrigger((b, d) -> vm.getUsers().addPropertyChangeListener(e -> b.apply(d)))
                    .withTargetConsumer(s ->{ list.setListData(s); list.setSelectedIndex(1);})
                    .build();
            new BindingBuilder<Integer, Integer>()
                    .withSourceSupplier(() -> Objects.requireNonNull(this.list.getSelectedIndex()))
                    .withTargetConsumer(vm.getUser()::set)
                    .withSourceTrigger(new TriggerJList(list))
                    .build().apply(Direction.UP);
            new BindingBuilder<String, String>()
                    .withSourceSupplier(() -> Objects.requireNonNull(this.tip.getSelectedItem()).toString())
                    .withTargetConsumer(vm.getTip()::set)
                    .withSourceTrigger(new TriggerJComboBox(tip))
                    .build().apply(Direction.UP);
        } catch (BindingException e) {
            e.printStackTrace();
        }
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
        listScroller.setBounds(50, 50, 300, 300);
        stergere.setBounds(50, 360, 300, 35);
        numeL.setBounds(420,  55, 300, 35);
        nume.setBounds(420,  95, 300, 35);
        parolaL.setBounds(420, 140, 300, 35);
        parola.setBounds(420, 180, 300, 35);
        tipL.setBounds(420, 230, 300, 35);
        tip.setBounds(420, 270, 300, 35);
        adaugare.setBounds(420, 360, 300, 35);
    }

    public void addLis(){
        adaugare.addActionListener(this);
        stergere.addActionListener(this);
        reload.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == adaugare){
            try {
                vm.getAddCommand().execute();
            } catch (BindingException bindingException) {
                bindingException.printStackTrace();
            }
        }
       else if(e.getSource() == stergere){
            try {
                vm.getDeleteCommand().execute();
            } catch (BindingException bindingException) {
                bindingException.printStackTrace();
            }
        }

    }


}
