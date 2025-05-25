/**
 * Created by Jannes on 09.04.20.
 */

 import java.util.LinkedList;
 import java.util.Queue;
 
 public class Drucker extends Elektrogeraete{
     String Netzwerkname;
     Queue<Druckauftrag> Druckauftraege;
     int Tinte;
 
     public Drucker (String Netzwerkname) {
         // todo
         this.Netzwerkname=Netzwerkname;
         this.Tinte = 100;
         this.TageBisPruefDatum = 365;
         this.Druckauftraege = new LinkedList<>();

 
     }
 
     public void DruckauftragEinreihen(Druckauftrag d) {
         this.Druckauftraege.add(d);
     }
 
     public void fill() {
         // todo
         this.Tinte = 100;
         
 
     }
 
     private void warten(int Sekunden) {
         try {
             for (int i = 0; i < 20; i++){
                 Thread.sleep(Sekunden * 50);
                 System.out.print(".");
             }
             System.out.println();
 
         } catch (InterruptedException ex) {
             Thread.currentThread().interrupt();
         }
     }
 
     public void getInfo(){
         System.out.println("Aktuelle infromationen zum Drucker: " + this.Netzwerkname);
         System.out.println("Füllstand:                       " + this.Tinte + "%");
         System.out.println("Anstehende Aufträge:             " + this.Druckauftraege.size() + "\n");
         System.out.println("Tage bis zum nächsten Prüfdatum: " + this.TageBisPruefDatum+ "\n");
 
     }
 
     public void drucken() {
         // todo
         while (!Druckauftraege.isEmpty()) {
             Druckauftrag d = Druckauftraege.poll();
             if (d.getSeitenanzahl() <= Tinte) {
                 System.out.print("Drucke Auftrag: ");
                 d.report();
                 warten(1);
                 Tinte -= d.getSeitenanzahl();
             } else {
                 System.out.println("Nicht genug Tinte für den Auftrag von " + d.getSeitenanzahl() + " Seiten.");
                 System.out.println("Bitte den Drucker nachfüllen.\n");
                 Druckauftraege.add(d); // reintroducem comanda inapoi in coada
                 break; // ne oprim pentru reumplere
             }
         }

     }
 }
 