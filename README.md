# LAPORAN MINI PROJECT 4 PEMROGRAMAN BERORIENTASI OBJEK

## SISTEM MANAJEMEN CV MANDIRI PRIMA KREATIF

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
- [BAB VI Koneksi Database, JDBC DAO & Dummy Data SQL](#bab-vi-koneksi-database-jdbc-dao--dummy-data-sql)
- [BAB VII Polymorphism dan Abstraction](#bab-vii-polymorphism-dan-abstraction)
- [BAB VIII Interface & Nilai Tambah ORM](#bab-viii-interface--nilai-tambah-orm)
- [BAB IX Kesimpulan](#bab-ix-kesimpulan)

---

## **BAB I PENDAHULUAN**

### **1.1 Deskripsi Singkat Program**

Sistem Manajemen CV Mandiri Prima Kreatif adalah program berbasis Java untuk membantu mengelola data pada CV Mandiri Prima Kreatif yang bergerak di bidang elektronik dan pengadaan barang.

Program mengelola tiga jenis data, yaitu data barang, data pemasok, dan data pengadaan, dengan konsep CRUD (Create, Read, Update, Delete). Khusus data barang, tersedia fitur tambahan Cari Barang (berdasarkan ID atau nama). Pada Mini Project 4 ini, penyimpanan data yang sebelumnya menggunakan *"ArrayList"* telah ditingkatkan menjadi **penyimpanan permanen berbasis Database MySQL**, dengan menerapkan pendekatan **JDBC DAO (Prepared Statement)** untuk data Pemasok & Pengadaan, serta penerapan **Framework ORM (Hibernate/JPA)** sebagai *Nilai Tambah* untuk data Barang.

### **1.2 Tujuan**

Sistem Manajemen CV Mandiri Prima Kreatif dirancang dengan tujuan sebagai berikut:

- Membantu mengelola data barang, pemasok, dan pengadaan secara terstruktur dengan integrasi database relasional MySQL.
- Memudahkan proses tambah, tampil, update, hapus, dan cari data (fungsi pencarian khusus pada data barang) terdapat dalam sistem.
- Menerapkan konsep pemrograman berorientasi objek, yaitu encapsulation, inheritance, polymorphism (overriding dan overloading), dan abstraction (abstract class dan abstract method) dalam program.
- Menerapkan struktur proyek MVC (Model, View, Controller) yang dipadukan dengan arsitektur akses data berbasis JDBC DAO dan ORM.
- Menerapkan interface sebagai kontrak yang menyeragamkan seluruh controller data.
- Memastikan data yang dimasukkan sesuai melalui proses validasi input pada view dan model.

### **1.3 Alur Singkat**
Alur program dimulai dengan menampilkan menu utama Sistem Manajemen CV Mandiri Prima Kreatif, yaitu kelola data barang, kelola data pemasok, kelola data pengadaan, dan keluar. Menu utama diatur oleh MenuController, dimana menu tersebut meneruskan pilihan pengguna ke controller data yang sesuai. Menu utama akan terus ditampilkan sampai pengguna memilih menu keluar. Setiap menu kelola data memiliki sub-menu sendiri yang juga berulang sampai pengguna memilih kembali.

-> Pada menu kelola data barang, pengguna dapat memilih menu tambah, tampilkan, hapus, update stok, cari barang dan kembali. 

- Saat menambahkan barang, pengguna memasukkan ID barang, nama barang, dan stok, lalu memilih jenis barang, yaitu Barang Elektronik atau Barang Non-Elektronik.
- Barang elektronik memiliki data tambahan berupa garansi, sedangkan barang non-elektronik memiliki data tambahan berupa kategori.
- Lalu ID barang tidak boleh sama dengan ID yang sudah ada, dan pengguna dapat membatalkan penambahan dengan memasukkan angka 0 pada ID.
- Data barang dikelola secara otomatis menggunakan **ORM Hibernate** (`model.manager.BarangManager`) dan langsung disimpan ke database.
- Pengguna juga dapat menghapus barang berdasarkan ID, memperbarui stok barang, serta mencari barang berdasarkan ID atau berdasarkan nama.

-> Pada menu kelola data pemasok, pengguna dapat menambahkan data pemasok dengan memasukkan ID pemasok, nama pemasok, alamat, dan nomor telepon yang hanya boleh berisi angka. Pengelolaan data dilakukan menggunakan **JDBC Prepared Statement & DAO** (`config.PemasokDAO`).
- Data pemasok yang telah tersimpan dapat ditampilkan, dihapus berdasarkan ID, serta diperbarui (nama, alamat, dan nomor telepon).

-> Pada menu kelola data pengadaan, pengguna dapat menambahkan data pengadaan dengan memasukkan ID pengadaan, tanggal pengadaan dengan format dd/MM/yyyy, dan alamat pengadaan. Pengelolaan data dilakukan menggunakan **JDBC Prepared Statement & DAO** (`config.PengadaanDAO`).
- Data pengadaan yang telah tersimpan dapat ditampilkan, dihapus berdasarkan ID, serta diperbarui (tanggal dan alamat).

-> Setiap proses input dilengkapi dengan validasi dua lapis, yaitu; 
- Lapis pertama ada di view, yang memastikan input tidak kosong, berupa angka jika yang diminta angka, dan memenuhi nilai minimal, lalu jika terdapat input yang salah, sistem akan langsung meminta input ulang sampai benar.
- Lapis kedua ada di model, yang menjaga aturan data seperti stok tidak boleh kurang dari 0, nomor telepon hanya angka (tidak boleh huruf atau simbol), dan tanggal harus sesuai format penulisan tanggal yaitu dd/MM/yyyy.
Jika aturan pada program dilanggar oleh pengguna, maka model akan melempar IllegalArgumentException dan controller menampilkan pesannya melalui view.

Dalam setiap proses, pembagian tugas mengikuti pola MVC, di mana controller meminta input kepada view, membuat atau mengubah objek model, lalu menyimpannya secara permanen ke Database MySQL, setelah itu meminta view menampilkan hasilnya kepada pengguna.

--------------

## **BAB II STRUKTUR PACKAGE**

Program menerapkan struktur **MVC (Model-View-Controller)** dengan tambahan package **`config`** dan sub-package **`model.manager`** untuk menangani interaksi database.

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

### **3.1 Alur Pemanggilan MVC & Database**

Contoh alur pada saat **Tambah Barang**:

1. `BarangController.tambah()` meminta input ID, nama, stok, dan jenis ke `BarangView`.
2. `BarangView` membaca input dan memvalidasinya sampai valid.
3. `BarangController` membuat objek `BarangElektronik` atau `BarangNonElektronik` (Model).
4. Model memeriksa aturan datanya. Jika dilanggar, Model melempar `IllegalArgumentException`.
5. `BarangController` memanggil `BarangManager` untuk menyimpan objek secara permanen ke database melalui ORM Hibernate.

Program dijalankan melalui class "SistemmanajemenCVMPK" yang berada pada package "main".

---

## **BAB IV VALIDASI INPUT**

Validasi dilakukan pada **dua lapis**:

1. **View** (`BaseView`): memastikan input tidak kosong, berupa angka, dan memenuhi nilai minimal. Input diulang sampai valid.
2. **Model**: menjaga aturan data. Jika dilanggar, Model melempar `IllegalArgumentException` dan Controller menampilkan pesannya lewat View.

| Data | Aturan |
|---|---|
| ID (barang, pemasok, pengadaan) | wajib diisi, harus angka, tidak boleh duplikat, ID pada update/hapus minimal 1 |
| Nama, alamat, garansi, kategori | tidak boleh kosong |
| Stok | harus angka, tidak boleh kurang dari 0 |
| Jenis barang | hanya boleh 1 atau 2 |
| No telepon | tidak boleh kosong, hanya angka |
| Tanggal pengadaan | tidak boleh kosong, format `dd/MM/yyyy`, dan tanggalnya harus valid (31/02/2026 akan ditolak) |
| Menu | harus angka dan sesuai pilihan yang tersedia |

---

## **BAB V ENCAPSULATION DAN INHERITANCE**

### **5.1 Encapsulation**

Semua atribut pada class Model dibuat "private" dan diakses melalui getter/setter. Setter juga berfungsi memvalidasi data sebelum disimpan.

Poin penerapan encapsulation:
- Atribut "private", sehingga tidak dapat diubah langsung dari class lain.
- ID dibuat "final" dan tidak punya setter, karena ID tidak boleh berubah setelah objek dibuat.
- Setter yang dibuka ("public") hanya untuk data yang memang boleh diubah.
- Konsep yang sama diterapkan pada "Pemasok" dan "Pengadaan".

### **5.2 Inheritance**

**Barang** adalah superclass, sedangkan **BarangElektronik** dan **BarangNonElektronik** adalah subclass. Atribut umum berupa ID barang, nama, dan stok diletakkan pada superclass barang, sementara atribut khusus (garansi dan kategori) berada di masing-masing subclass.

Inheritance juga dipakai pada package view:
