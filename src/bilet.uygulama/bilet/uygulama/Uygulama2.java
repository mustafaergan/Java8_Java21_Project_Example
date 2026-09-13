package bilet.uygulama;

/*
 * GOREV 2
 * Bu sinif bilerek HATALI birakildi.
 * README.md dosyasindaki adimlari izleyerek duzelt.
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
        String bilet = """Ucus: TK2410  Rota: Istanbul - Izmir  Fiyat: 1450.0 TL""";   // HATA 1

        System.out.print(bilet);
    }

    // ============ 2. var ============
    static void varOrnegi() {
        var yolcuSayisi;                       // HATA 2
        var fiyat = 1450.0;
        fiyat = "Bedava";                      // HATA 3

        System.out.println("Yolcu: " + yolcuSayisi + ", fiyat: " + fiyat);
    }

    // ============ 3. Yeni switch ============
    static void switchOrnegi() {
        String sinif = "BUSINESS";

        double carpan = switch (sinif) {       // HATA 4
            case "EKONOMI"  -> 1.0;
            case "BUSINESS" -> 2.5;
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
            };
            System.out.println(aciklama);
        }
    }
}

sealed interface Odeme permits KrediKarti, Nakit {}

record KrediKarti(String kartNo) implements Odeme {}

class Nakit implements Odeme {}                // HATA 5

record Havale(String iban) implements Odeme {} // HATA 6
