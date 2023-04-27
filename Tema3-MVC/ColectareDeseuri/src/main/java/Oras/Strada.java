package Oras;

public enum Strada {
    STR1("Mihai Eminescu"),
    STR2("Mihai Viteazu"),
    STR3("Fabricii"),
    STR4("Observatorului"),
    STR5("1 Decembrie 1918"),
    STR6("Eroilor"),
    STR7("Emil Isac");

    private final String text;
    Strada(String s) {
        this.text = s;
    }

    public String toString2(){
        return this.text;
    }

}



