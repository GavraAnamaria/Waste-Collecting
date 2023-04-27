package Controller;

import Model.*;
import Oras.Strada;
import View.CoordonatorGUI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class CCoordonator {
    private CoordonatorGUI view;
    private PersistentaUtilizatori utilizatori;
    private Oras oras;

    public CCoordonator()
    {
        utilizatori = new PersistentaUtilizatori();
        view = new CoordonatorGUI();
        oras = new Oras(view.getUserName());
        oras.getLocatii().addObserver(view);
        view.setLocList(oras.getLocatii().toStringList());
        view.assignListener(new assignAction());
        view.deleteListener(new deleteAction());
        view.addListener(new addAction());
        view.rapListener(new rapAction());
        view.locListener(new locAction());
        view.routeListener(new routeAction());
        view.pieGraphListener(new pieGraphAction());
        view.linearGraphListener(new linearGraphAction());
        view.radialGraphListener(new radialGraphAction());
        view.roListener(new languageAction("ro"));
        view.enListener(new languageAction("en"));
        view.frListener(new languageAction("fr"));
        view.geListener(new languageAction("ge"));
    }


    private PieDataset createDatasetPie() {
        var dataset = new DefaultPieDataset();
        for(Utilizator u:(new PersistentaUtilizatori()).getListaUtilizatori())
            if(u.getTip() == Tip.ANGAJAT){
                dataset.setValue( u.getNume(), oras.getLocatii().getLocatii(u.getNume()).size());
            }
        return dataset;
    }

    private CategoryDataset createDatasetLin() {
        var dataset = new DefaultCategoryDataset();
        for(Utilizator u:this.utilizatori.getListaUtilizatori())
            if(u.getTip() == Tip.ANGAJAT){
                dataset.setValue(oras.getLocatii().getLocatii(u.getNume()).size(), "Nr Locatii", u.getNume());
            }
        return dataset;
    }

    class deleteAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(oras.stergereLocatieDeseu(view.getSelLocIndex()))
                view.setLocList(oras.getLocatii().toStringList());
            else
                view.showError("Nu a fost selectata nicio locatie!");
        }
    }


    class assignAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Locatie l = oras.getLocatii().getAllLoc().get(view.getLocCBoxSelectIndex());
            oras.getDeseuri().insert(new Locatie(l.getStrada(), l.getNr(), view.getUserName()));
            oras.getDeseuri().delete(l);
            l.setUtilizator(view.getUserName());
            view.setLocList(oras.getLocatii().toStringList());
        }
    }

    class addAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for(Strada s : Strada.values())
                if(s.toString2().equals(view.getStreet()))
                {if(oras.adaugareLocatieDeseu(new Locatie(s, Integer.parseInt(view.getNr()))))
                    view.setLocList(oras.getLocatii().toStringList());
                else
                    view.showError("Aceasta locatie exista deja!");
                    break;}
        }
    }


    class rapAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
                String path;
                try{
                    if (view.getRapPath().length() == 0 || view.getRapName().length() == 0)
                        path = "C:\\Users\\Anamaria\\Desktop\\an3\\rapoarte";
                    else
                        path = view.getRapPath() + "\\" + view.getRapName();
                    if (view.getRapType().equals("CSV"))
                        oras.writeCSV(path+ ".csv");
                    else
                        if (view.getRapType().equals("JSON"))
                            oras.writeJson(path+ ".json");
                        else
                            oras.writeXml(path+ ".xml");
                        view.showError("Raport generat in "+ path +"!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        }
    }

    class linearGraphAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.setChart(ChartFactory.createBarChart(
                    "Nr Locatii",
                    "",
                    "Nr Locatii",
                    createDatasetLin(),
                    PlotOrientation.VERTICAL,
                    false, true, false));
        }
    }

    class pieGraphAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.setChart(ChartFactory.createRingChart(
                    "Nr Locatii",
                    createDatasetPie(),
                    false, true, false));
        }
    }


        class radialGraphAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                view.setChart(ChartFactory.createPieChart(
                        "Nr Locatii",
                        createDatasetPie(),
                        false, true, false));
            }
    }


            class locAction implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    view.setLocList(oras.getLocatii().toStringList());
                }
            }

            class routeAction implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    Oras oras = new Oras(view.getUserName());
                    List<Locatie> l = (oras).traseuOptim();
                    String[] s = new String[l.size()+1];
                    // s[0] = name.toUpperCase(Locale.ROOT);
                    s[0] = "ANDREI";
                    for(int i = 0; i < l.size(); ++i) {
                        s[i+1] = "[ " + oras.getDeseuri().getAllLoc().indexOf(l.get(i)) + " ]   " + ( l.get(i)).toString1();
                    }
                    view.setLocList(s);
                }
            }

    @AllArgsConstructor
    class languageAction implements ActionListener {
        String language;

        public void actionPerformed(ActionEvent e) {
            String[] data = new String[]{"FORMAT", "LOCATIE-FISIER", "DENUMIRE-FISIER", "LINIAR","CIRCULAR","RADIAL","ADAUGARE", "ATRIBUIRE","STERGERE","GENERARE-RAPOARTE","VIZUALIZARE-LOCATII","TRASEU-OPTIM"};
            String[] result = new String[data.length];
            if(language.equals("ro")){
                result = data;
            }else {
                Map<String, String> trans = new Translator().traducere(data, language);
                for(int i=0; i<data.length;i++){
                    result[i] = trans.get(data[i]);
                }}
            view.setFormatLabel(result[0]);
            view.setFileNameLabel(result[2]);
            view.setFilePathLabel(result[1]);
            view.setLiniarButton(result[3]);
            view.setCircButton(result[4]);
            view.setRadButton(result[5]);
            view.setAddButton(result[6]);
            view.setDelButton(result[8]);
            view.setAssignButton(result[7]);
            view.setRapButton(result[9]);
            view.setViewLocButton(result[10]);
            view.setRouteButton(result[11]);
        }
    }
}
