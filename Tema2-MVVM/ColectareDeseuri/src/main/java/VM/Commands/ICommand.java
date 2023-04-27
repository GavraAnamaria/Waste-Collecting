package VM.Commands;

import net.sds.mvvm.bindings.BindingException;

public interface ICommand {
    void execute() throws BindingException;
}
