package Model;

public class Utilizator {
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
}
