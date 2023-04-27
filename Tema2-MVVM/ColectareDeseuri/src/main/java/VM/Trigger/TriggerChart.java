package VM.Trigger;

import net.sds.mvvm.bindings.Binding;
import net.sds.mvvm.bindings.Direction;
import net.sds.mvvm.triggers.Trigger;
import org.jfree.chart.JFreeChart;


public class TriggerChart  implements Trigger {
    private JFreeChart chart;

    public void register(Binding binding, Direction direction) {
        this.chart.addChangeListener((e) -> {
            binding.apply(direction);
        });
    }
}
