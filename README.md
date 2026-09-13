> **Bu dal Görev 1'in çözümüdür.** Sıradaki görev için `gorev2` dalına geç:
>
> ```bash
> git fetch
> git checkout gorev2
> ```

---

# Görev 1 — Modüler uçak bileti iskeleti

**Java:** 21

## 1. Projeyi çek

İlk iş projeyi bilgisayarına al:

```bash
git clone -b master https://github.com/mustafaergan/Java8_Java21_Project_Example.git
cd Java8_Java21_Project_Example
```

JDK sürümünü kontrol et. Çıktı `javac 21` ile başlamalı:

```bash
javac -version
```

## 2. Durum

Projede üç modül ve `Uygulama` sınıfı hazır. Ama proje **derlenmiyor**, çünkü `Uygulama`'nın kullandığı sınıflar henüz yok:

```
error: package bilet.model does not exist
error: package bilet.servis does not exist
```

Görevin, bu hataları eksik parçaları yazarak gidermek. `Uygulama.java` dosyasına dokunma.

## 3. Bu görevde öğreneceğin iki şey

| Kavram | Java 8'de | Java 21'de |
|---|---|---|
| **record** | Alan, kurucu, getter, `equals`, `toString` elle yazılır | Tek satır. Alan okuma `ucus.fiyat()`, `getFiyat()` değil. Alanlar değiştirilemez. |
| **Modül** | Her `public` sınıf herkese açık | Paket `exports` edilmezse başka modül göremez. Kullanılan modül `requires` ile yazılır. |

## 4. Yapılacaklar

### `bilet.model`

- `Ucus` **record**'u: `ucusNo` (String), `nereden` (String), `nereye` (String), `fiyat` (double)
- `Bilet` **record**'u: `no` (int), `ucus` (Ucus), `yolcu` (String)
- `module-info.java`: `bilet.model` paketini dışarı aç.

### `bilet.servis`

- `BiletServisi` sınıfı, tek metot: `public Bilet sat(Ucus ucus, String yolcu)`
  - Bilet numarası 1'den başlar, her satışta bir artar.
- `module-info.java`: `bilet.model`'i kullan, `bilet.servis` paketini dışarı aç.

### `bilet.uygulama`

- `module-info.java`: `bilet.model` ve `bilet.servis`'i kullan.

> `class` değil `record` kullan. Getter, setter, `equals`, `toString` yazma.

## 5. Derle ve çalıştır

```bash
javac -d out --module-source-path src -m bilet.model,bilet.servis,bilet.uygulama
```

```bash
java --module-path out -m bilet.uygulama/bilet.uygulama.Uygulama
```

Beklenen çıktı:

```
Bilet[no=1, ucus=Ucus[ucusNo=TK2410, nereden=Istanbul, nereye=Izmir, fiyat=1450.0], yolcu=Ali]
```

`toString` yazmadığın halde çıktının okunur gelmesini record sağladı.

## 6. Tamamlandı mı?

- [ ] `Ucus` ve `Bilet` record.
- [ ] Üç `module-info.java` dolu.
- [ ] Program beklenen çıktıyı veriyor.

## 7. Dene

Çalıştıktan sonra şunları tek tek dene, sonra geri al:

1. `bilet.model`'deki `exports` satırını sil. Derleyici ne diyor?
2. `Uygulama`'ya `ucus.getFiyat()` yaz. Neden bulunamadı?
3. `Uygulama`'ya `ucus.fiyat = 999.0;` yaz. Neden değiştirilemedi?

Java 8'de bu üç durumdan hangileri hata verirdi?
