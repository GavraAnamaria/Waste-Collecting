package VM.Commands;

import Model.PersistentaUtilizatori;
import VM.AdminVM;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateUserListCommand implements ICommand{
    AdminVM vm ;
    @Override
    public void execute() {vm.getUsers().set(new PersistentaUtilizatori().toStringList());
         }
}
