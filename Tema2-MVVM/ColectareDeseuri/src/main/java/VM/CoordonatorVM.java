package VM;

import Model.Locatie;
import Model.Oras;
import VM.Commands.*;
import lombok.Getter;
import lombok.Setter;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.JFreeChartEntity;

import java.util.List;
@Setter
@Getter
public class CoordonatorVM {
    private Property<String[]> list = PropertyFactory.createProperty("list", this, String[].class);
    private Property<String> userName = PropertyFactory.createProperty("userName", this, String.class);
    private Property<String> format = PropertyFactory.createProperty("format", this, String.class);
    private Property<String> strada = PropertyFactory.createProperty("street", this, String.class);
    private Property<String> nr = PropertyFactory.createProperty("nr", this, String.class);
    private Property<Integer> deletePoz = PropertyFactory.createProperty("deletePoz", this, Integer.class);
    private Property<Integer> locatie = PropertyFactory.createProperty("locatie", this, Integer.class);
    private Property<String> denumireRap = PropertyFactory.createProperty("denumireRap", this, String.class);
    private Property<String> locatieRap = PropertyFactory.createProperty("locatieRap", this, String.class);
    private Property<JFreeChart> chart = PropertyFactory.createProperty("locatieRap", this, JFreeChart.class);
//    private //Property<String> locatie = PropertyFactory.createProperty("locatieRap", this, String.class);

    private ICommand locListCommand ;
    private ICommand optimalPathCommand ;
    private ICommand addLocCommand;
    private ICommand delLocCommand;
    private ICommand atribLocCommand;
    private ICommand rapCommand;
    private ICommand liniarCommand;
    private ICommand circularCommand;
    private ICommand radialCommand;

    public CoordonatorVM()
    {
        this.locListCommand = new LocListCommand(list,userName.get(), true);
        this.optimalPathCommand = new OptimalPathCommand(list, userName.get());
        this.addLocCommand = new AddLocCommand(this);
        this.delLocCommand = new DelLocCommand(this);
        this.atribLocCommand = new AtribLocCommand(this);
        this.rapCommand = new RapCommand(this);
        this.liniarCommand = new LiniarCommand(this);
        this.circularCommand = new CircularCommand(this);
        this.radialCommand = new RadialCommand(this);
    }
}
