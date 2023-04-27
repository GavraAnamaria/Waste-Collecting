package VM.Commands;

import Model.PersistentaUtilizatori;
import VM.AdminVM;
import lombok.AllArgsConstructor;
import net.sds.mvvm.bindings.BindingException;

@AllArgsConstructor
public class DeleteUserCommand implements ICommand {
    AdminVM vm;
    @Override
    public void execute() throws BindingException {
        PersistentaUtilizatori utilizatori = new PersistentaUtilizatori();
        utilizatori.stergereUtilizator(vm.getUser().get());
        vm.getUpdateCommand().execute();
    }
}
