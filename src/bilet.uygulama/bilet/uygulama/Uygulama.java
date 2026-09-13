package bilet.uygulama;

import bilet.model.Bilet;
import bilet.model.Ucus;
import bilet.servis.BiletServisi;

public class Uygulama {

    public static void main(String[] args) {
        Ucus ucus = new Ucus("TK2410", "Istanbul", "Izmir", 1450.0);
        BiletServisi servis = new BiletServisi();

        Bilet bilet = servis.sat(ucus, "Ali");

        System.out.println(bilet);
    }
}
