package id.ac.ub.filkom.contoh;

import id.ac.ub.filkom.contoh.entity.Anggota;
import id.ac.ub.filkom.contoh.entity.Buku;
import id.ac.ub.filkom.contoh.entity.TransaksiPinjam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PinjamEntityTest {

    @Test
    void cekStok_dan_kurangiStok() {
        Buku buku = new Buku("B001", "Pemrograman Java", 1);

        assertTrue(buku.cekStok());

        buku.kurangiStok();

        assertEquals(0, buku.getStok());
        assertFalse(buku.cekStok());

        assertThrows(
                IllegalStateException.class,
                buku::kurangiStok
        );
    }

    @Test
    void cekBatasPinjam_menolak_saat_mencapai_batas() {
        Anggota anggota = new Anggota(
                "A001",
                "Asya",
                "Malang"
        );

        Buku buku = new Buku("B001", "Pemrograman Java", 3);

        // Batas awal anggota adalah 2.
        assertEquals(2, anggota.getBatasPinjam());
        assertTrue(anggota.cekBatasPinjam());

        // Setelah satu pinjaman, anggota masih boleh meminjam.
        TransaksiPinjam pertama =
                TransaksiPinjam.buat(anggota, buku);

        assertEquals(
                TransaksiPinjam.DIPINJAM,
                pertama.getStatus()
        );

        assertTrue(anggota.cekBatasPinjam());

        // Setelah dua pinjaman, anggota mencapai batas.
        TransaksiPinjam.buat(anggota, buku);

        assertFalse(anggota.cekBatasPinjam());
    }
}