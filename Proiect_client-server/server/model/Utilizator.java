package model;

import Enum.Tip;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
public class Utilizator implements Comparable<Utilizator>, Serializable {
    private String nume;
    private String parola;
    private String email;
    private String nr;
    private Tip tip;

    public Utilizator(String nume, String parola, Tip tip, String email, String nr){
        this.nume = nume;
        this.parola = parola;
        this.tip = tip;
        this.email=email;
        this.nr=nr;
    }


    @Override
    public boolean equals(Object obj){
        if(obj instanceof Utilizator)
            return (nume.equals(((Utilizator)obj).getNume()) && parola.equals(((Utilizator)obj).getParola()) && tip == ((Utilizator)obj).getTip());
        return false;
    }
    @Override
    public int compareTo(Utilizator o) {
        if(o.getTip().compareTo(tip)==0)
            return -o.getNume().compareTo(nume);
        return -o.getTip().compareTo(tip);
    }

    @Override
    public String toString(){
        return this.tip + ": " + this.nume + "   ||   " +this.parola ;
    }
}
