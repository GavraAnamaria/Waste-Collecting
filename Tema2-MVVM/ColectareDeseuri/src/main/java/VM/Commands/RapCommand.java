package VM.Commands;

import VM.CoordonatorVM;
import lombok.AllArgsConstructor;
import net.sds.mvvm.bindings.BindingException;
import Model.Oras;

@AllArgsConstructor
public class RapCommand implements ICommand {
    private CoordonatorVM vm;

    @Override
    public void execute() throws BindingException {
        String path;
        if (vm.getLocatieRap().get().length() == 0 || vm.getDenumireRap().get().length() == 0)
            path = "C:\\Users\\Anamaria\\Desktop\\an3\\rapoarte.csv";
        else
            path = vm.getLocatieRap().get() + "\\" + vm.getDenumireRap().get();
        if (vm.getFormat().get().equals("CSV"))
            (new Oras(vm.getUserName().get())).writeCSV(path);
        else if (vm.getFormat().get().equals("JSON")) {
            try {
                (new Oras(vm.getUserName().get())).writeJson(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
