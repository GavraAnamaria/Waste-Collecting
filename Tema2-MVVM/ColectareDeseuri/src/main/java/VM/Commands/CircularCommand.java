package VM.Commands;

import Model.*;
import VM.CoordonatorVM;
import lombok.AllArgsConstructor;
import net.sds.mvvm.bindings.BindingException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

@AllArgsConstructor
public class CircularCommand implements ICommand {
    private CoordonatorVM vm;

    @Override
    public void execute() throws BindingException {
        vm.getChart().set( ChartFactory.createRingChart(
                "Nr Locatii",
                (PieDataset) createDataset(),
                false, true, false));
    }


    private PieDataset createDataset() {
        var dataset = new DefaultPieDataset();
        for(Utilizator u:(new PersistentaUtilizatori()).getListaUtilizatori())
            if(u.getTip() == Tip.ANGAJAT){
                int nr = 0;
                for(Locatie l: new PersistentaLocatii().getLocatii(u.getNume())){
                    nr+=1;
                }
                dataset.setValue( u.getNume(), nr);
            }
        return dataset;
    }
}





