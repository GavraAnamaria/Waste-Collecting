package VM.Commands;

import Model.Locatie;
import VM.AngajatVM;
import lombok.AllArgsConstructor;
import net.sds.mvvm.bindings.BindingException;

import java.util.List;
import java.util.Locale;
import Model.Oras;
import net.sds.mvvm.properties.Property;

@AllArgsConstructor
public class OptimalPathCommand implements ICommand {
    private Property<String[]> list ;
    private String name;

    @Override
    public void execute() throws BindingException {
            Oras oras = new Oras("andrei");
            List<Locatie> l = (oras).traseuOptim();
            String[] s = new String[l.size()+1];
           // s[0] = name.toUpperCase(Locale.ROOT);
            s[0] = "ANDREI";
            for(int i = 0; i < l.size(); ++i) {
                s[i+1] = "[ " + oras.getDeseuri().getAllLoc().indexOf((Locatie) l.get(i)) + " ]   " + ((Locatie) l.get(i)).toString1();
            }
            list.set(s);
    }
}
