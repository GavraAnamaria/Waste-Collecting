package VM.Commands;

import Model.PersistentaUtilizatori;
import Model.Tip;
import VM.LoginVM;
import View.AdminGUI;
import View.AngajatGUI;
import View.CoordonatorGUI;
import View.LoginGUI;
import net.sds.mvvm.bindings.BindingException;

import javax.swing.*;


public class LoginCommand implements VM.Commands.ICommand {
    private LoginVM vm;
    private LoginGUI v;

    public LoginCommand(LoginVM vm, LoginGUI v){
        this.vm = vm;
        this.v = v;
    }

    @Override
    public void execute() throws BindingException {
            PersistentaUtilizatori persUtilizatori = new PersistentaUtilizatori();
            Tip tip = persUtilizatori.cautareUtilizator(vm.getUserName().get(), (vm.getPassword()).get());
        System.out.println(vm.getPassword().get());
        System.out.println(vm.getUserName().get()+ "98999\n");
            if (tip==Tip.ANGAJAT)
                v.setContentPane(new AngajatGUI(vm.getUserName().get()));
            else if (tip==Tip.COORDONATOR)
                v.setContentPane(new CoordonatorGUI());
            else if (tip==Tip.ADMINISTRATOR)
                v.setContentPane((new AdminGUI()));
            else{
                JOptionPane.showMessageDialog(v, "Date incorecte!");
                return;}
            v.setBounds(v.getContentPane().getBounds());
        }
    }


