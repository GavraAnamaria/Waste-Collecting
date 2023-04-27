package VM.Trigger;

import lombok.AllArgsConstructor;
import net.sds.mvvm.bindings.Binding;
import net.sds.mvvm.bindings.Direction;
import net.sds.mvvm.triggers.Trigger;

import javax.swing.*;
@AllArgsConstructor
public class TriggerJComboBox  implements Trigger {
    private JComboBox comboBox;

    public void register(Binding binding, Direction direction) {
        this.comboBox.addItemListener((e) -> {
            binding.apply(direction);
        });
    }
}
