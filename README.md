# Görev 2 — Text Blocks, var, yeni switch, sealed

**Süre:** 30 dakika · **Java:** 21

## 1. Dala geç

Projeyi daha önce çektiysen:

```bash
git fetch
git checkout gorev2
```

İlk kez çekiyorsan:

```bash
git clone -b gorev2 https://github.com/mustafaergan/Java8_Java21_Project_Example.git
```

## 2. Durum

Bu dalda Görev 1'in çözümü hazır. Yeni olarak `bilet.uygulama` modülüne **`Uygulama2`** sınıfı eklendi ve bu sınıf **bilerek hatalı**. Proje derlenmiyor.

Görevin, yalnızca `Uygulama2.java` dosyasını düzeltmek. Hatalı satırların yanında `// HATA 1`, `// HATA 2` gibi işaretler var.

> **Not:** Derleyici hataları parça parça gösterir. İlk derlemede sadece HATA 1 görünür. Biri düzeldikçe yenileri çıkar, bu normal.

> **IntelliJ kullanıyorsan:** "package is not visible" hatası alırsan **File | Project Structure | Modules | Dependencies** ekranından `bilet.servis` modülüne `bilet.model`'i, `bilet.uygulama` modülüne ikisini de ekle.

## 3. Bu görevde öğreneceğin dört şey

| Konu | Sürüm | Java 8'de | Java 21'de |
|---|---|---|---|
| **Text Blocks** | 15 | Çok satırlı metin `"\n"` ve `+` ile birleştirilir | `"""` ile metin olduğu gibi yazılır |
| **var** | 10 | Tip her seferinde açıkça yazılır | Tip sağ taraftan çıkarılır, ama derlemede sabitlenir |
| **Yeni switch** | 14 | `case "X":` ve `break` gerekir, değer döndürmez | `case "X" ->` ile değer döndürür, her durum karşılanmalı |
| **sealed** | 17 | Bir arayüzü herkes uygulayabilir | `permits` ile kimin uygulayabileceği sınırlanır |

## 4. Yapılacaklar

### Adım 1 — Text Blocks · HATA 1

`"""` ile açılan metin aynı satırda devam ediyor. Text block'ta açılış `"""` işaretinden sonra **satır bitmeli**.

Metni üç satır olacak şekilde yeniden yaz: `Ucus`, `Rota`, `Fiyat`. Çıktı aşağıdaki gibi hizalı görünmeli.

### Adım 2 — var · HATA 2 ve 3

- **HATA 2:** `var` başlangıç değeri olmadan kullanılamaz, çünkü derleyici tipi o değerden çıkarır. Yolcu sayısını `180` olarak ver.
- **HATA 3:** `fiyat` değişkeninin tipi ilk satırda `double` olarak belirlendi. Sonradan metin atanamaz, çünkü `var` **dinamik tip değildir**. Bu satırı sil.

### Adım 3 — Yeni switch · HATA 4

Bu `switch` bir değer döndürüyor, bu yüzden **her olasılığı karşılamak zorunda**. `String` sonsuz değer alabilir. Listede olmayan tüm sınıflar için çarpan `1.0` olsun.

### Adım 4 — sealed · HATA 5 ve 6

- **HATA 5:** Sealed bir arayüzü uygulayan sınıf `final`, `sealed` ya da `non-sealed` olmak zorunda. `Nakit` sınıfını `final` yap.
- **HATA 6:** `Havale`, `Odeme` arayüzünün `permits` listesinde yok. Listeye ekle.

Bunları düzelttiğinde derleyici yeni bir şey söyleyecek: `sealedOrnegi` içindeki `switch` artık `Havale`'yi karşılamıyor. `Havale` için `"Havale: "` ve IBAN'ı yazan bir `case` ekle.

> Buraya `default` **yazma**. Sealed'ın faydası tam olarak bu: yeni bir ödeme türü eklediğinde derleyici, onu unuttuğun her `switch`'i sana gösterir.

## 5. Derle ve çalıştır

```bash
javac -d out --module-source-path src -m bilet.model,bilet.servis,bilet.uygulama
```

```bash
java --module-path out -m bilet.uygulama/bilet.uygulama.Uygulama2
```

Beklenen çıktı:

```
Ucus  : TK2410
Rota  : Istanbul - Izmir
Fiyat : 1450.0 TL
Yolcu: 180, fiyat: 1450.0
BUSINESS fiyat carpani: 2.5
Kredi karti: 4111-XXXX
Nakit odeme
Havale: TR12-0001
```

## 6. Tamamlandı mı?

- [ ] Proje hatasız derleniyor.
- [ ] `Uygulama2` beklenen çıktıyı veriyor.
- [ ] `sealedOrnegi` içindeki `switch`'te `default` yok.

## 7. Düşün

1. `sealedOrnegi` içindeki `switch`'e `default` yazsaydın, `Havale`'yi eklediğinde derleyici seni uyarır mıydı?
2. `switchOrnegi` metodunu Java 8'de nasıl yazardın? Kaç satır sürerdi?
