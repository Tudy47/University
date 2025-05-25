
public class Druckauftrag {
    private String Auftraggeber;
    private int Seitenanzahl;

    public Druckauftrag(String Auftraggeber, int Seitenanzahl) {
        this.Auftraggeber = Auftraggeber;
        this.Seitenanzahl = Seitenanzahl;
    }

    public int getSeitenanzahl() {
        return this.Seitenanzahl;
    }

    public void report(){
        System.out.println(this.Auftraggeber);
        System.out.println(this.Seitenanzahl);
    }
}
