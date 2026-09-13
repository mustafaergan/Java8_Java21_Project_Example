package bilet.uygulama;

import bilet.model.Bilet;
import bilet.model.Ucus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * GOREV 3 - COZUM
 */
public class Uygulama3 {

    static final Path DOSYA = Path.of("biletler.csv");
    static final int BILET_SAYISI = 100_000;
    static final int SURE_LIMITI_SN = 5;

    public static void main(String[] args) throws Exception {
        dosyaOlustur();

        List<String> satirlar = Files.readAllLines(DOSYA);
        System.out.println(satirlar.size() + " bilet dosyadan okundu. Kontrol basliyor...");

        AtomicInteger kontrolEdilen = new AtomicInteger();
        long baslangic = System.currentTimeMillis();

        ExecutorService havuz = Executors.newVirtualThreadPerTaskExecutor();

        for (String satir : satirlar) {
            havuz.submit(() -> {
                Bilet bilet = biletOku(satir);
                rezervasyonSistemindeKontrolEt(bilet);
                kontrolEdilen.incrementAndGet();
            });
        }

        havuz.shutdown();
        boolean bitti = havuz.awaitTermination(SURE_LIMITI_SN, TimeUnit.SECONDS);
        long sure = System.currentTimeMillis() - baslangic;

        if (!bitti) {
            int tamamlanan = kontrolEdilen.get();
            havuz.shutdownNow();
            throw new IllegalStateException("Sure asildi! " + SURE_LIMITI_SN + " saniyede sadece "
                    + tamamlanan + " / " + satirlar.size() + " bilet kontrol edilebildi.");
        }

        System.out.println(kontrolEdilen.get() + " bilet " + sure + " ms'de kontrol edildi.");
    }

    // Dosyadaki bir satiri Bilet nesnesine cevirir. Ornek satir: 42;TK2410;Yolcu42;1450.0
    static Bilet biletOku(String satir) {
        String[] alan = satir.split(";");
        Ucus ucus = new Ucus(alan[1], "Istanbul", "Izmir", Double.parseDouble(alan[3]));
        return new Bilet(Integer.parseInt(alan[0]), ucus, alan[2]);
    }

    // Her bilet havayolunun rezervasyon sistemine sorulur.
    // Gercekte bu bir ag cagrisidir ve yaklasik 100 ms surer. Burada bekleme ile taklit ediliyor.
    static void rezervasyonSistemindeKontrolEt(Bilet bilet) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Her calistirmada proje klasorune BILET_SAYISI satirlik bir bilet dosyasi uretir.
    static void dosyaOlustur() throws IOException {
        String[] ucuslar = { "TK2410", "PC1102", "AJ3305", "TK2020" };
        List<String> satirlar = new ArrayList<>();
        for (int no = 1; no <= BILET_SAYISI; no++) {
            satirlar.add(no + ";" + ucuslar[no % ucuslar.length] + ";Yolcu" + no + ";" + (1000 + no % 900) + ".0");
        }
        Files.write(DOSYA, satirlar);
        System.out.println(DOSYA.toAbsolutePath() + " olusturuldu (" + (Files.size(DOSYA) / 1024) + " KB).");
    }
}
