package Model;

public class Utilizator implements Comparable<Utilizator>{
    private String nume;
    private String parola;
    private Tip tip;

    public Utilizator(String nume,String parola,Tip tip){
        this.nume = nume;
        this.parola = parola;
        this.tip = tip;
    }
    public Utilizator(){
        super();
    }

    public String getNume(){
        return this.nume;
    }

    public String getParola(){
        return this.parola;
    }

    public Tip getTip(){
        return this.tip;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Utilizator)
            return (nume.equals(((Utilizator)obj).getNume()) && parola.equals(((Utilizator)obj).getParola()) && tip == ((Utilizator)obj).getTip());
        return false;
    }

    @Override
    public String toString(){
        return this.tip + ": " + this.nume + "   ||   " +this.parola ;
}

    @Override
    public int compareTo(Utilizator o) {
        if(o.getTip().compareTo(tip)==0)
            return -o.getNume().compareTo(nume);
        return -o.getTip().compareTo(tip);
    }
}
