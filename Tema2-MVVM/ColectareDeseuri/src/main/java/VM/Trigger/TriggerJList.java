package VM.Trigger;

import lombok.AllArgsConstructor;
import net.sds.mvvm.bindings.Binding;
import net.sds.mvvm.bindings.BindingException;
import net.sds.mvvm.bindings.Direction;
import net.sds.mvvm.triggers.Trigger;

import javax.swing.*;
@AllArgsConstructor
public class TriggerJList implements Trigger {
    private JList list;
    @Override

    public void register(Binding binding, Direction direction) throws BindingException {
                this.list.addListSelectionListener((e) -> {
                    binding.apply(direction);
                });
            }
        }
