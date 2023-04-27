package model;

import Enum.Strada;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Locatie implements Comparable<Locatie>, Serializable {
    private static final long serialVersionUID = 1L;
    private Strada strada;
    private Integer nr;
    private String utilizator;

    public Locatie(Strada strada, int nr){
        this.strada = strada;
        this.nr = nr;
        this.utilizator = "ana";
    }

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
