package VM.Commands;

import VM.CoordonatorVM;
import lombok.AllArgsConstructor;
import net.sds.mvvm.bindings.BindingException;
import Model.Oras;

@AllArgsConstructor
public class DelLocCommand implements ICommand {
    private CoordonatorVM vm;
    @Override
    public void execute() throws BindingException {
        if((new Oras(vm.getUserName().get())).stergereLocatieDeseu(vm.getDeletePoz().get()))
        vm.getLocListCommand().execute();
    }
}
