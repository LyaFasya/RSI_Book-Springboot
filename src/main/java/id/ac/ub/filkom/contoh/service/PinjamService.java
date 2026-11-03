package id.ac.ub.filkom.contoh.service;

import id.ac.ub.filkom.contoh.entity.Anggota;
import id.ac.ub.filkom.contoh.entity.Buku;
import id.ac.ub.filkom.contoh.entity.TransaksiPinjam;
import id.ac.ub.filkom.contoh.repository.AnggotaRepository;
import id.ac.ub.filkom.contoh.repository.BukuRepository;
import id.ac.ub.filkom.contoh.repository.TransaksiPinjamRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PinjamService {

    private final AnggotaRepository anggotaRepository;
    private final BukuRepository bukuRepository;
    private final TransaksiPinjamRepository transaksiRepository;

    public PinjamService(
            AnggotaRepository anggotaRepository,
            BukuRepository bukuRepository,
            TransaksiPinjamRepository transaksiRepository) {

        this.anggotaRepository = anggotaRepository;
        this.bukuRepository = bukuRepository;
        this.transaksiRepository = transaksiRepository;
    }

    public List<Buku> daftarBuku() {
        return bukuRepository.findAll();
    }

    public List<Anggota> daftarAnggota() {
        return anggotaRepository.findAll();
    }

    public Anggota cariAnggota(String idAnggota) {
        return anggotaRepository.findById(idAnggota)
                .orElseThrow(() ->
                        PinjamGagalException.tidakDitemukan("Anggota"));
    }

    @Transactional
    public Bukti pinjamBuku(String idAnggota, String idBuku) {

        // Persiapan: mengambil anggota dan buku dari database.
        Anggota anggota = anggotaRepository.findById(idAnggota)
                .orElseThrow(() ->
                        PinjamGagalException.tidakDitemukan("Anggota"));

        Buku buku = bukuRepository.findById(idBuku)
                .orElseThrow(() ->
                        PinjamGagalException.tidakDitemukan("Buku"));

        // [3]-[4] Memeriksa batas peminjaman anggota.
        if (!anggota.cekBatasPinjam()) {
            throw PinjamGagalException.melewatiBatas();
        }

        // [5]-[6] Memeriksa ketersediaan stok buku.
        if (!buku.cekStok()) {
            throw PinjamGagalException.stokHabis();
        }

        // [7]-[8] Membuat dan menyimpan transaksi peminjaman.
        TransaksiPinjam transaksi =
                TransaksiPinjam.buat(anggota, buku);

        transaksiRepository.save(transaksi);

        // [9]-[10] Mengurangi dan menyimpan stok buku.
        buku.kurangiStok();
        bukuRepository.save(buku);

        // [11] Mengembalikan data bukti peminjaman.
        return Bukti.dari(transaksi);
    }
}