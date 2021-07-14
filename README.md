Bu proje Google Speech-To-Text API kullanılarak kullanıcıdan alınan ses verileri String haline
dönüştürülür. Dönüştürülen bu Stringler verileri Yazılım içerisinde daha önceden
tanımlanmış Stringler ile eşleştirilir. Herhangi bir eşleşme durumunda o eşleşme için daha
önceden tanımlanmış karakter Bluetooth aracılığıyla Arduino’ya gönderilir. Arduino
içerisinde ise Bluetooth aracılığıyla okunan char’a Karşılık gelen komut işlenir.
Bu Mobil bölümü Proje Java Programlama dili kullanılarak Android Studio Platformunda
Robot bölümü ise Arduino kullanılarak yapılmıştır. 
BluetoothControl klasörü içerisinde Android uygulamaya ait kodlar bulunmaktadır, RBT.ino dosyası altında ise arduino kodları mevcuttur.
