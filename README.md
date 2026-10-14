# LAPORAN MINI PROJECT 4 PEMROGRAMAN BERORIENTASI OBJEK

## SISTEM MANAJEMEN CV MANDIRI PRIMA KREATIF (HYBRID ORM & JDBC)

---

Nama : **Elena Dementieva**

NIM : **2509116008**

Kelas : **Sistem Informasi A'25**

---

## **DAFTAR ISI**

- [BAB I Pendahuluan](#bab-i-pendahuluan)
- [BAB II Struktur Package](#bab-ii-struktur-package)
- [BAB III Alur Program](#bab-iii-alur-program)
- [BAB IV Validasi Input](#bab-iv-validasi-input)
- [BAB V Encapsulation dan Inheritance](#bab-v-encapsulation-dan-inheritance)
- [BAB VI Koneksi Database & Penyimpanan Data (Hybrid)](#bab-vi-koneksi-database--penyimpanan-data-hybrid)
- [BAB VII Polymorphism dan Abstraction](#bab-vii-polymorphism-dan-abstraction)
- [BAB VIII Interface & Arsitektur Akses Data](#bab-viii-interface--arsitektur-akses-data)
- [BAB IX Kesimpulan](#bab-ix-kesimpulan)

---

## **BAB I PENDAHULUAN**

### **1.1 Deskripsi Singkat Program**

Sistem Manajemen CV Mandiri Prima Kreatif adalah program berbasis Java (CLI) yang dikembangkan untuk mengelola data pada CV Mandiri Prima Kreatif yang bergerak di bidang elektronik dan pengadaan barang.

Program mengelola tiga jenis entitas data, yaitu **Barang** (Elektronik & Non-Elektronik), **Pemasok**, dan **Pengadaan**, yang dilengkapi dengan fungsionalitas CRUD (*Create, Read, Update, Delete*) serta fitur pencarian khusus pada data barang. 

Pada Mini Project 4 ini, program menerapkan **Arsitektur Hibrida (*Hybrid Architecture*)** dalam mengakses database MySQL:
1. **ORM (Object-Relational Mapping / Hibernate & JPA)** digunakan secara otomatis untuk mengelola entitas **Barang** melalui *EntityManager* di package `model.manager`.
2. **JDBC Prepared Statement** murni digunakan untuk mengelola entitas **Pemasok** dan **Pengadaan** melalui package `config`.

### **1.2 Tujuan**

Sistem Manajemen CV Mandiri Prima Kreatif dirancang dengan tujuan sebagai berikut:

- Membantu mengelola data barang, pemasok, dan pengadaan secara terstruktur dengan integrasi database relasional MySQL.
- Menerapkan konsep Pemrograman Berorientasi Objek (PBO) secara komprehensif, yaitu *encapsulation*, *inheritance*, *polymorphism* (overriding & overloading), dan *abstraction* (*abstract class* & *abstract method*).
- Menerapkan pola arsitektur **MVC (Model-View-Controller)** yang dipadukan dengan pemisahan lapisan akses data berbasis **JDBC** dan **ORM Hibernate**.
- Menerapkan interface sebagai kontrak penyeragam controller serta memanfaatkan *EntityManager* sebagai titik masuk (*entry point*) ORM.
- Memastikan integritas data tetap terjaga melalui validasi input berlapis di *View* dan aturan validasi pada *Model*.

### **1.3 Alur Singkat**
Alur program dimulai dari kelas utama `SistemmanajemenCVMPK` yang menginisialisasi `MenuController` untuk menampilkan menu utama secara interaktif. Pengguna dapat memilih menu Kelola Data Barang, Kelola Data Pemasok, Kelola Data Pengadaan, atau Keluar.

- **Kelola Data Barang (ORM Hibernate)**: Menggunakan `BarangManager` untuk memproses penyimpanan, pengambilan, pembaruan, dan penghapusan data secara objek melalui Hibernate tanpa query SQL manual. Pengguna dapat menambah barang (Elektronik dengan garansi atau Non-Elektronik dengan kategori), menampilkan, menghapus, mengubah stok, serta mencari barang berdasarkan ID atau nama.
- **Kelola Data Pemasok (JDBC)**: Menggunakan `PemasokDAO` berbasis JDBC *Prepared Statement* di package `config`. Memungkinkan penambahan data pemasok (dengan validasi nomor telepon khusus angka), penampilan, penghapusan, dan pembaruan data.
- **Kelola Data Pengadaan (JDBC)**: Menggunakan `PengadaanDAO` berbasis JDBC *Prepared Statement* di package `config`. Memungkinkan pengelolaan data pengadaan dengan format tanggal `dd/MM/yyyy` yang divalidasi secara ketat.
- **Validasi & Penanganan Exception**: Setiap input melalui *View* divalidasi agar sesuai tipe datanya, dan aturan bisnis pada *Model* dijaga menggunakan `IllegalArgumentException` yang ditangkap dan ditampilkan melalui pesan *View*.

--------------

## **BAB II STRUKTUR PACKAGE**

Program menerapkan struktur **MVC (Model-View-Controller)** yang diperkaya dengan package khusus **`config`** untuk koneksi dan JDBC, serta sub-package **`model.manager`** untuk manajemen ORM.

| Package | Class | Tugas |
|---|---|---|
| `main` | `SistemmanajemenCVMPK` | Titik awal (*entry point*) program. Membuat *Scanner*, meredam log *startup* Hibernate, dan menjalankan `MenuController`. |
| `config` | `Koneksi`, `PemasokDAO`, `PengadaanDAO`, `TestKoneksi`, `TestKoneksiOrm` | Menyediakan koneksi dasar database MySQL serta implementasi akses data berbasis **JDBC Prepared Statement** untuk Pemasok dan Pengadaan. |
| `model` | `Barang`, `BarangElektronik`, `BarangNonElektronik`, `Pemasok`, `Pengadaan`, `TanggalConverter` | Menyimpan data entitas POJO dan menjaga aturan integritas datanya. |
| `model.manager` | `EntityManagerProvider`, `BarangManager` | Mengelola *EntityManagerFactory* dan bertindak sebagai *entry point* ORM Hibernate untuk operasi CRUD entitas `Barang`. |
| `view` | `BaseView`, `EntitasView`, `BarangView`, `PemasokView`, `PengadaanView`, `MenuView` | Menangani seluruh interaksi antarmuka teks konsol (*CLI*), pembacaan input, dan validasi tampilan. |
| `controller` | `Kelola`, `MenuController`, `BarangController`, `PemasokController`, `PengadaanController` | Mengatur alur logika program dengan menghubungkan *View* dengan *Manager* (ORM) atau *DAO* (JDBC). |

---

## **BAB III ALUR PROGRAM**

### **3.1 Alur Pemanggilan Sistem Hibrida (ORM & JDBC)**

- **Alur Tambah Barang (ORM)**: `BarangController` meminta input ke `BarangView` -> membuat objek `BarangElektronik`/`BarangNonElektronik` -> memanggil `BarangManager.tambah()` -> `EntityManager` melakukan `em.persist()` ke database secara otomatis.
- **Alur Tambah Pemasok/Pengadaan (JDBC)**: `PemasokController` / `PengadaanController` meminta input ke *View* -> memanggil `PemasokDAO` / `PengadaanDAO` di package `config` -> mengeksekusi *Prepared Statement* SQL melalui koneksi `Koneksi.getConnection()`.

### **3.2 Menu Utama**

Saat program dijalankan, konsol menampilkan menu utama Sistem Manajemen CV Mandiri Prima Kreatif secara berulang hingga pengguna memilih opsi keluar.

### **3.4 Kelola Data Barang (ORM Hibernate)**
- Menyediakan menu Tambah, Tampilkan, Hapus, Update Stok, Cari Barang, dan Kembali.
- Penambahan barang membedakan jenis Elektronik (dengan garansi) dan Non-Elektronik (dengan kategori).
- Data ditarik langsung dari database menggunakan Hibernate ORM.

### **3.5 Kelola Data Pemasok (JDBC)**
- Menyediakan menu Tambah, Tampilkan, Hapus, Update, dan Kembali.
- Memproses data pemasok (ID, nama, alamat, no telepon berupa angka) menggunakan query SQL *Prepared Statement*.

### **3.6 Kelola Data Pengadaan (JDBC)**
- Menyediakan menu Tambah, Tampilkan, Hapus, Update, dan Kembali.
- Memproses data pengadaan dengan format tanggal `dd/MM/yyyy` menggunakan JDBC.

---

## **BAB IV VALIDASI INPUT**

Validasi dilakukan pada **dua lapis**:
1. **View** (`BaseView`): memastikan input tidak kosong, sesuai tipe data, dan memenuhi syarat minimal.
2. **Model**: menjaga aturan data. Jika dilanggar, melempar `IllegalArgumentException` yang ditangkap oleh *Controller* dan ditampilkan lewat *View*.

| Data | Aturan |
|---|---|
| ID (barang, pemasok, pengadaan) | Wajib diisi, harus angka, tidak boleh duplikat |
| Nama, alamat, garansi, kategori | Tidak boleh kosong |
| Stok | Harus angka, tidak boleh kurang dari 0 |
| No telepon | Wajib diisi, hanya berupa angka |
| Tanggal pengadaan | Format `dd/MM/yyyy`, divalidasi ketat secara kalender |

---

## **BAB V ENCAPSULATION DAN INHERITANCE**

### **5.1 Encapsulation**
Semua atribut pada class Model dibuat `private` dan diakses melalui getter/setter yang sekaligus memvalidasi nilai data sebelum disimpan, menjaga integritas objek dari akses luar yang tidak sah.

### **5.2 Inheritance**
- **Barang** bertindak sebagai *superclass* abstrak bagi **BarangElektronik** dan **BarangNonElektronik**.
- Pewarisan juga diterapkan pada package *View* melalui hierarki **`BaseView`** dan **`EntitasView<T>`**.

---

## **BAB VI KONEKSI DATABASE & PENYIMPANAN DATA (HYBRID)**

Berbeda dengan versi sebelumnya yang menggunakan penyimpanan memori sementara (*ArrayList*), pada Mini Project 4 ini seluruh data disimpan secara permanen di dalam **Database MySQL** (`sistemmanajemencvmpk`), dengan pembagian teknologi:
- **ORM Hibernate (`cvmpkPU`)**: Mengelola pemetaan tabel `barang`, `barang_elektronik`, dan `barang_non_elektronik`.
- **JDBC**: Mengelola tabel `pemasok` dan `pengadaan` menggunakan koneksi langsung dan *PreparedStatement*.

---

## **BAB VII POLYMORPHISM DAN ABSTRACTION**

### **7.1 Abstraction**
- **Abstract Class**: Diterapkan pada `Barang`, `BaseView`, dan `EntitasView<T>`.
- **Abstract Method**: Dideklarasikan pada class abstrak dan wajib diimplementasikan oleh seluruh *subclass*-nya.

### **7.2 Polymorphism: Overriding & Overloading**
- **Overriding**: Digunakan pada *subclass* entitas dan *view* untuk menyesuaikan perilaku spesifik (seperti `getJenis()`, `tampilDetail()`).
- **Overloading**: Diterapkan pada method pencarian dan fungsi pembacaan input di *View* dengan parameter yang berbeda.

---

## **BAB VIII INTERFACE & ARSITEKTUR AKSES DATA**

### **8.1 Penerapan Interface `Kelola`**
Interface `Kelola` berfungsi sebagai kontrak standar yang wajib diimplementasikan oleh seluruh *controller* (`BarangController`, `PemasokController`, `PengadaanController`), sehingga `MenuController` dapat menjalankan operasi menu secara seragam tanpa terikat pada detail teknis masing-masing entitas.

### **8.2 Penerapan Titik Masuk ORM (`EntityManagerProvider`)**
Sebagai bagian dari nilai tambah arsitektur, pengelolaan *EntityManager* dipusatkan pada package `model.manager` melalui `EntityManagerProvider` guna menjamin siklus hidup koneksi ORM terkelola dengan aman dan bersih.

---

## **BAB IX KESIMPULAN**

Program Sistem Manajemen CV Mandiri Prima Kreatif berhasil dikembangkan dengan menerapkan konsep Pemrograman Berorientasi Objek (PBO) serta arsitektur **Hibrida (*Hybrid Architecture*)** yang menggabungkan **ORM Hibernate** (untuk entitas Barang di package `model.manager`) dan **JDBC Prepared Statement** (untuk entitas Pemasok & Pengadaan di package `config`). 

Dengan penerapan pola MVC, validasi berlapis, exception handling, serta struktur package yang rapi sesuai standar industri, aplikasi ini mampu mengelola database relasional MySQL secara profesional, terstruktur, dan mudah dikembangkan lebih lanjut.
