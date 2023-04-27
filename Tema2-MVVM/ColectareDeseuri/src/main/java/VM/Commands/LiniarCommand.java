package VM.Commands;

import Model.*;
import VM.CoordonatorVM;
import lombok.AllArgsConstructor;
import net.sds.mvvm.bindings.BindingException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

@AllArgsConstructor
public class LiniarCommand implements ICommand {
    private CoordonatorVM vm;

    @Override
    public void execute() throws BindingException {
        vm.getChart().set( ChartFactory.createBarChart(
                "Nr Locatii",
                "",
                "Nr Locatii",
                createDataset(),
                PlotOrientation.VERTICAL,
                false, true, false));
    }


    private CategoryDataset createDataset() {
        var dataset = new DefaultCategoryDataset();
        for(Utilizator u:(new PersistentaUtilizatori()).getListaUtilizatori())
            if(u.getTip() == Tip.ANGAJAT){
                int nr = 0;
                for(Locatie l: new PersistentaLocatii().getLocatii(u.getNume())){
                    nr+=1;
                }
                dataset.setValue(nr, "Nr Locatii", u.getNume());
            }
        return dataset;
    }
}
