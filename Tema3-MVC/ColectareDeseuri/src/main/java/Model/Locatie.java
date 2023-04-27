package Model;

import Oras.Strada;
public class Locatie implements Comparable<Locatie>{
    private Strada strada;
    private int nr;
    private String utilizator;

    public Locatie(Strada strada, int nr){
        this.strada = strada;
        this.nr = nr;
        this.utilizator = "ana";
    }

    public Locatie(Strada strada, int nr, String u){
        this.strada = strada;
        this.nr = nr;
        this.utilizator = u;
    }

    public Locatie(){
        super();
    }

    public int getNr(){return this.nr;}
    public String getUtilizator(){return this.utilizator;}
    public Strada getStrada(){return this.strada;}
    public void setUtilizator(String utilizator){ this.utilizator = utilizator;}

@Override
    public boolean equals(Object obj){
        if(obj instanceof Locatie)
            return (this.strada.equals(((Locatie)obj).getStrada()) && this.nr == ((Locatie)obj).getNr());
        return false;
}
@Override
    public String toString(){
        return("Strada: "+ strada.toString2() + ", nr: " + nr + "           ->"+ utilizator );
}


    public String toString1(){
        return("Strada: "+ strada.toString2() + ", nr: " + nr  );
    }

    @Override
    public int compareTo(Locatie obj){
        if(obj.getStrada().toString2().compareTo(strada.toString2()) ==0)
                return nr-obj.getNr();
        else
            return -(obj.getStrada().toString2().compareTo(strada.toString2()));
    }
}
