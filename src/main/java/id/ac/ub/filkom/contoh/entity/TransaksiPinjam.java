package id.ac.ub.filkom.contoh.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "transaksi_pinjam")
public class TransaksiPinjam {

    public static final String DIPINJAM = "DIPINJAM";

    @Id
    @Column(name = "id_transaksi", length = 10)
    private String idTransaksi;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_anggota")
    private Anggota anggota;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_buku")
    private Buku buku;

    @Column(name = "tanggal_pinjam")
    private LocalDate tanggalPinjam;

    @Column(name = "status", length = 20)
    private String status;

    protected TransaksiPinjam() {
    }

    public static TransaksiPinjam buat(Anggota anggota, Buku buku) {
        TransaksiPinjam transaksi = new TransaksiPinjam();

        transaksi.idTransaksi = "T" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

        transaksi.anggota = anggota;
        transaksi.buku = buku;
        transaksi.tanggalPinjam = LocalDate.now();
        transaksi.status = DIPINJAM;

        anggota.catatPinjaman(transaksi);

        return transaksi;
    }

    public boolean isDipinjam() {
        return DIPINJAM.equals(status);
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public Buku getBuku() {
        return buku;
    }

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public String getStatus() {
        return status;
    }
}