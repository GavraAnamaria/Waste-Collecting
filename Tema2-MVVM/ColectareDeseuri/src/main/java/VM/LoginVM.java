package VM;

import VM.Commands.ICommand;
import VM.Commands.LoginCommand;
import View.LoginGUI;
import lombok.Getter;
import lombok.Setter;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;

@Getter
@Setter
public class LoginVM{
    private Property<String> userName = PropertyFactory.createProperty("userName", this, String.class);
    private Property<String> password = PropertyFactory.createProperty("password", this, String.class);
    private ICommand login ;

    public LoginVM(LoginGUI v)
    {
        this.userName.set("");
        this.password.set("");
        this.login = new LoginCommand(this,v);
    }


}
