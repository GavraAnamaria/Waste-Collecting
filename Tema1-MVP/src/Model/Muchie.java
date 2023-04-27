package Model;


import java.util.Comparator;

public class Muchie  implements Comparator<Muchie> {
    private Locatie sursa= new Locatie();
    private Locatie destinatie = new Locatie();
    private int cost;

    public Muchie(){}

    public Muchie(Locatie s, Locatie d, int c){
        sursa = s;
        destinatie = d;
        cost = c;
    }

    public Locatie getSursa() {
        return this.sursa;
    }

    public int getCost() {
        return this.cost;
    }

    public Locatie getDest(){
        return this.destinatie;
    }
    @Override
    public int compare(Muchie m1, Muchie m2)
    {
        if (m1.cost < m2.cost)
            return -1;
        if (m1.cost > m2.cost)
            return 1;
        return 0;
    }
}
