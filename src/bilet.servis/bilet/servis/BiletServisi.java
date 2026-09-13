package bilet.servis;

import bilet.model.Bilet;
import bilet.model.Ucus;

public class BiletServisi {

    private int sonNo = 0;

    public Bilet sat(Ucus ucus, String yolcu) {
        sonNo++;
        return new Bilet(sonNo, ucus, yolcu);
    }
}
