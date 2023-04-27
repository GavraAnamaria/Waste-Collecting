package VM;

import Model.Tip;
import VM.Commands.AddUserCommand;
import VM.Commands.DeleteUserCommand;
import VM.Commands.ICommand;
import VM.Commands.UpdateUserListCommand;
import lombok.Getter;
import lombok.Setter;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;

import javax.swing.*;

@Setter
@Getter
public class AdminVM {
    private Property<String> name = PropertyFactory.createProperty("name", this, String.class);
    private Property<String> password = PropertyFactory.createProperty("password", this, String.class);
    private Property<String> tip = PropertyFactory.createProperty("tip", this, String.class);
    private Property<Integer> user = PropertyFactory.createProperty("user", this, Integer.class);
    private Property<String[]> users = PropertyFactory.createProperty("users", this, String[].class);
    private ICommand addCommand ;
    private ICommand deleteCommand ;
    private ICommand updateCommand ;

    public AdminVM()
    {
        this.name.set("");
        this.password.set("");
        this.addCommand = new AddUserCommand(this);
        this.deleteCommand = new DeleteUserCommand(this);
        this.updateCommand = new UpdateUserListCommand(this);
    }
}