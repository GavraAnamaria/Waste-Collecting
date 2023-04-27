package VM.Commands;

import Model.Locatie;
import VM.CoordonatorVM;
import lombok.AllArgsConstructor;
import net.sds.mvvm.bindings.BindingException;
import Model.Oras;
import Oras.Strada;

@AllArgsConstructor
public class AddLocCommand implements ICommand {
    private CoordonatorVM vm;

    @Override
    public void execute() throws BindingException {
        for(Strada s : Strada.values())
            if(s.toString2().equals(vm.getStrada().get()))
            {if((new Oras(vm.getUserName().get())).adaugareLocatieDeseu(new Locatie(s, Integer.parseInt(vm.getNr().get()))))
                vm.getLocListCommand().execute();
            break;}
    }
}