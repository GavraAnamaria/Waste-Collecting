package Oras;

import Model.Locatie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Intersectie {
    Int1(new ArrayList<Locatie>(Arrays.asList(new Locatie(Strada.STR1, 7),new Locatie(Strada.STR2, 1)))),
    Int2(new ArrayList<Locatie>(Arrays.asList(new Locatie(Strada.STR1, 50),new Locatie(Strada.STR2, 40)))),
    Int3(new ArrayList<Locatie>(Arrays.asList(new Locatie(Strada.STR2, 55),new Locatie(Strada.STR3, 0)))),
    Int4(new ArrayList<Locatie>(Arrays.asList(new Locatie(Strada.STR3, 10),new Locatie(Strada.STR4, 9)))),
    Int5(new ArrayList<Locatie>(Arrays.asList(new Locatie(Strada.STR3, 35),new Locatie(Strada.STR4, 1)))),
    Int6(new ArrayList<Locatie>(Arrays.asList(new Locatie(Strada.STR4, 7),new Locatie(Strada.STR7, 0)))),
    Int7(new ArrayList<Locatie>(Arrays.asList(new Locatie(Strada.STR4, 11),new Locatie(Strada.STR6, 0)))),
    Int8(new ArrayList<Locatie>(Arrays.asList(new Locatie(Strada.STR4, 15),new Locatie(Strada.STR5, 0)))),
    Int9(new ArrayList<Locatie>(Arrays.asList(new Locatie(Strada.STR5, 17),new Locatie(Strada.STR6, 8)))),
    Int10(new ArrayList<Locatie>(Arrays.asList(new Locatie(Strada.STR5, 22),new Locatie(Strada.STR7, 8)))),
    Int11(new ArrayList<Locatie>(Arrays.asList(new Locatie(Strada.STR4, 0),new Locatie(Strada.STR5, 40))));
    private final ArrayList<Locatie> loc;

    Intersectie(ArrayList<Locatie> l) {
        this.loc = l;
    }

    public ArrayList<Locatie> getLoc(){
        return this.loc;
    }
    @Override
    public String toString() {
        return this.loc.toString();
    }
}
