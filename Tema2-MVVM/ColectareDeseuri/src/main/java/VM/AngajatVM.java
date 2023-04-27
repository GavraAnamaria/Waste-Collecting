package VM;

import VM.Commands.*;
import lombok.Getter;
import lombok.Setter;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;

@Setter
@Getter
public class AngajatVM {
    private Property<String[]> list = PropertyFactory.createProperty("list", this, String[].class);
    private ICommand locListCommand ;
    private ICommand optimalPathCommand ;

    public AngajatVM(String name)
    {
        this.locListCommand = new LocListCommand(list, name,false);
        this.optimalPathCommand = new OptimalPathCommand(list, name);
    }
}
