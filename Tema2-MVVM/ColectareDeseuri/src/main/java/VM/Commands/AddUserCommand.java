package VM.Commands;

import Model.PersistentaUtilizatori;
import Model.Tip;
import Model.Utilizator;
import VM.AdminVM;
import lombok.AllArgsConstructor;
import net.sds.mvvm.bindings.BindingException;

@AllArgsConstructor
public class AddUserCommand  implements ICommand{
    AdminVM vm ;
    @Override
    public void execute() throws BindingException {
        PersistentaUtilizatori utilizatori = new PersistentaUtilizatori();
        utilizatori.adaugareUtilizator(new Utilizator(vm.getName().get(), vm.getPassword().get(), Tip.valueOf(vm.getTip().get())));
        vm.getUpdateCommand().execute();
    }
}
