package bilet.uygulama;

import bilet.model.Havale;
import bilet.model.KrediKarti;
import bilet.model.Nakit;
import bilet.model.Odeme;

/*
 * GOREV 2 - COZUM
 */
public class Uygulama2 {

    public static void main(String[] args) {
        textBlockOrnegi();
        varOrnegi();
        switchOrnegi();
        sealedOrnegi();
    }

    // ============ 1. Text Blocks ============
    static void textBlockOrnegi() {
        String bilet = """
                Ucus  : TK2410
                Rota  : Istanbul - Izmir
                Fiyat : 1450.0 TL
                """;

        System.out.print(bilet);
    }

    // ============ 2. var ============
    static void varOrnegi() {
        var yolcuSayisi = 180;
        var fiyat = 1450.0;

        System.out.println("Yolcu: " + yolcuSayisi + ", fiyat: " + fiyat);
    }

    // ============ 3. Yeni switch ============
    static void switchOrnegi() {
        String sinif = "BUSINESS";

        double carpan = switch (sinif) {
            case "EKONOMI"  -> 1.0;
            case "BUSINESS" -> 2.5;
            default         -> 1.0;
        };

        System.out.println(sinif + " fiyat carpani: " + carpan);
    }

    // ============ 4. sealed ============
    static void sealedOrnegi() {
        Odeme[] odemeler = { new KrediKarti("4111-XXXX"), new Nakit(), new Havale("TR12-0001") };

        for (Odeme odeme : odemeler) {
            String aciklama = switch (odeme) {
                case KrediKarti k -> "Kredi karti: " + k.kartNo();
                case Nakit n      -> "Nakit odeme";
                case Havale h     -> "Havale: " + h.iban();
            };
            System.out.println(aciklama);
        }
    }
}
