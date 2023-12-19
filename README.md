# GüvenliTık AI: Phishing URL Tespiti ve Mobil Uygulama

<img src="https://i.hizliresim.com/10f66p9.png" witdh="200" height="200">

## Hakkında
GuvenliTik AI, internet kullanıcılarını zararlı web sitelerinden korumak amacıyla geliştirilmiş, yapay zeka tabanlı bir Phishing URL tespit mekanizması ve bu mekanizmayı entegre eden mobil uygulamadan oluşan iki aşamalı bir projedir.

<img src="https://i.hizliresim.com/6jpuc7h.png" witdh="400" height="600">


### Projenin Aşamaları
1. **Phishing URL Tespiti Yapan Yapay Zeka Modeli:** Bu aşama, yapay zeka tekniklerini kullanarak zararlı web sitelerini tespit edebilen bir modelin geliştirilmesini içerir. Model, URL'lerin güvenli olup olmadığını değerlendirebilmek için eğitilmiştir.

2. **Mobil Uygulama Entegrasyonu:** İkinci aşama, geliştirilen yapay zeka modelini bir mobil uygulamayla entegre etmeyi içerir. Bu uygulama, kullanıcıların mobil cihazları üzerinden internete güvenli bir şekilde erişmelerini sağlamak için tasarlanmıştır.

## Kurulum ve Kullanım

### Yapay Zeka Modelinin En Baştan Eğitilmesi
Modeli eğitmek için aşağıdaki adımları izleyin:

```bash
# Gerekli kütüphaneleri yükleyin
pip install -r requirements.txt

# Veri seti üzerinde ön işlemleri gerçekleştirin ve eğitim için uygun hale getirin
# "Data Preprocessing.ipynb" notebook'unu çalıştırın

# Modelin eğitim işlemini başlatmak için "Train.ipynb" notebook'unu kullanın
```
### Yapay Zeka Modelinin Çalıştırılması
Hazır olan veya en baştan eğittiniz modeli çalıştırıp Firebase ile entegre etmek için aşağıdaki adımları izleyin

```bash
# Gerekli kütüphaneleri yükleyin
pip install -r requirements.txt

# Kendi Firebase bağlantı ayarlarınızı yapılandırın ve anahtar dosyanızı projenin içerisine ekleyin
# "Firebase Handler.ipynb" notebook'unu kullanarak modeli çalıştırabilirsiniz
```

## Katkıda Bulunma ##

Projeye katkıda bulunmak isteyenler için katkı kuralları ve adımları CONTRIBUTING.md dosyasında açıklanmıştır.

##  Lisans ## 
Bu proje MIT Lisansı altında lisanslanmıştır.

