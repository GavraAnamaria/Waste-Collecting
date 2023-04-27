package VM.Commands;
import Model.Oras;
import Model.PersistentaLocatii;
import VM.AngajatVM;
import lombok.AllArgsConstructor;
import net.sds.mvvm.bindings.BindingException;
import net.sds.mvvm.properties.Property;

@AllArgsConstructor
public class LocListCommand implements ICommand {
    private Property<String[]> list ;
    private String name;
    private boolean all;

    @Override
    public void execute() throws BindingException {
        if(!all)
        list.set((new Oras(name)).getDeseuri().toStringList1());
        else
            list.set((new PersistentaLocatii()).toStringList());
    }
}
