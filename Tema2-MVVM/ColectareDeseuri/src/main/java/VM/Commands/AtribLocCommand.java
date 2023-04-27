package VM.Commands;

import Model.Locatie;
import Model.Oras;
import VM.CoordonatorVM;
import lombok.AllArgsConstructor;
import net.sds.mvvm.bindings.BindingException;

@AllArgsConstructor
public class AtribLocCommand implements ICommand {
    private CoordonatorVM vm;

    @Override
    public void execute() throws BindingException {
        Oras oras = new Oras(vm.getUserName().get());
        Locatie l = oras.getPersistenta().getAllLoc().get(vm.getLocatie().get());
        oras.getDeseuri().insert(new Locatie(l.getStrada(), l.getNr(), vm.getUserName().get()));
        oras.getDeseuri().delete(l);
        l.setUtilizator(vm.getUserName().get());
        vm.getLocListCommand().execute();
    }
}
