> **Bu dal Görev 3'ün çözümüdür.**

---

# Görev 3 — Virtual Threads

**Java:** 21

## 1. Dala geç

Projeyi daha önce çektiysen:

```bash
git fetch
git checkout gorev3
```

İlk kez çekiyorsan:

```bash
git clone -b gorev3 https://github.com/mustafaergan/Java8_Java21_Project_Example.git
```

## 2. Durum

Bu dalda Görev 2'nin çözümü hazır. Yeni olarak `bilet.uygulama` modülüne **`Uygulama3`** sınıfı eklendi. Bu sefer kod **derlenir ve çalışır**, ama işini zamanında bitiremez.

Program her çalıştığında sırasıyla şunları yapar:

1. Proje klasörüne 100.000 satırlık, yaklaşık 3 MB büyüklüğünde `biletler.csv` dosyası üretir.
2. Dosyayı okuyup her satırı bir `Bilet` nesnesine çevirir.
3. Her bileti havayolunun rezervasyon sistemine sorar. Her sorgu 100 ms sürer; gerçekte bu bir ağ çağrısıdır, burada `Thread.sleep` ile taklit ediliyor.

Bu işi Java 8'den bildiğimiz yöntemle, **2 thread'lik bir havuzla** yapıyor. İşin 5 saniyede bitmesi gerekiyor, bitmezse program hata veriyor:

```
Exception in thread "main" java.lang.IllegalStateException: Sure asildi! 5 saniyede sadece 90 / 100000 bilet kontrol edilebildi.
```

Görevin, programı **5 saniyenin altında** bitirmek. Bilet sayısına, bekleme süresine ve süre limitine dokunma.

## 3. Bu görevde öğreneceğin şey

| | Platform thread · Java 8'den beri | Virtual thread · Java 21 |
|---|---|---|
| Kim yönetir | İşletim sistemi | JVM |
| Bellek | Thread başına yaklaşık 1 MB | Birkaç KB |
| Kaç tane açılabilir | Birkaç bin | Milyonlarca |
| Beklerken ne olur | Thread boşta kilitli kalır | Altındaki gerçek thread serbest kalır, başka işe geçer |

2 thread ile hesap şöyle: 100.000 bilet × 100 ms ÷ 2 thread = 5.000 saniye, yani yaklaşık **83 dakika**.

> **Önemli:** Sanal thread'ler diskten okumayı ya da hesaplamayı hızlandırmaz. Hızlandırdıkları şey **beklemektir**: ağ çağrısı, veritabanı sorgusu, dış servis. Bu örnekte 3 MB'lık dosyayı okumak bir saniyeden kısa sürüyor; zamanın neredeyse tamamı rezervasyon sistemini beklerken kayboluyor.

## 4. Yapılacak

`Uygulama3.java` içinde `// GOREV 3` işaretli satırda 2 thread'lik havuz oluşturuluyor. Bu satırı, **her görev için yeni bir sanal thread açan** bir havuzla değiştir.

> **İpucu:** `Executors` sınıfında Java 21 ile gelen bir fabrika metodu var, adında `Virtual` geçiyor. Tek satır değişiklik yeterli.

## 5. Derle ve çalıştır

```bash
javac -d out --module-source-path src -m bilet.model,bilet.servis,bilet.uygulama
```

```bash
java --module-path out -m bilet.uygulama/bilet.uygulama.Uygulama3
```

Beklenen çıktı; süre makineye göre değişir:

```
...\biletler.csv olusturuldu (3103 KB).
100000 bilet dosyadan okundu. Kontrol basliyor...
100000 bilet 625 ms'de kontrol edildi.
```

`biletler.csv` proje klasöründe oluşur ve git'e eklenmez.

## 6. Tamamlandı mı?

- [ ] Program hata vermeden bitiyor.
- [ ] Süre 5 saniyenin altında.
- [ ] Değiştirdiğin tek şey havuzun oluşturulduğu satır.

## 7. Dene

1. Havuzu geri platform thread'e çevir ama 2 yerine `200` thread ver. Program neden hâlâ yetişemiyor? Neden 100.000 platform thread açmayı denemiyoruz?
2. `Thread.sleep(100)` yerine işlemciyi 100 ms boyunca meşgul eden bir hesap olsaydı, sanal thread'ler yine bu kadar hızlandırır mıydı?
