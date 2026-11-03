package id.ac.ub.filkom.contoh.service;

import id.ac.ub.filkom.contoh.entity.TransaksiPinjam;
import java.time.LocalDate;

public class Bukti {

    private final String idTransaksi;
    private final String namaAnggota;
    private final String judulBuku;
    private final LocalDate tanggalPinjam;
    private final String status;

    private Bukti(TransaksiPinjam transaksi) {
        this.idTransaksi = transaksi.getIdTransaksi();
        this.namaAnggota = transaksi.getAnggota().getNama();
        this.judulBuku = transaksi.getBuku().getJudul();
        this.tanggalPinjam = transaksi.getTanggalPinjam();
        this.status = transaksi.getStatus();
    }

    public static Bukti dari(TransaksiPinjam transaksi) {
        return new Bukti(transaksi);
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getNamaAnggota() {
        return namaAnggota;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public String getStatus() {
        return status;
    }
}