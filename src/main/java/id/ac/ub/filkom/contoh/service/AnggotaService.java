package id.ac.ub.filkom.contoh.service;

import id.ac.ub.filkom.contoh.entity.Anggota;
import id.ac.ub.filkom.contoh.repository.AnggotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnggotaService {
    private final AnggotaRepository anggotaRepository;

    public AnggotaService(AnggotaRepository anggotaRepository) {
        this.anggotaRepository = anggotaRepository;
    }

    public List<Anggota> cariAnggota(String kata) {
        if (kata == null || kata.isBlank()) {
            return anggotaRepository.findAll();
        }

        return anggotaRepository
                .findByNamaContainingIgnoreCase(kata.trim());
    }

    public Anggota tambahAnggota(
            String idAnggota, String nama, String alamat) {

        return anggotaRepository.save(
                new Anggota(
                        idAnggota.trim(),
                        nama.trim(),
                        alamat.trim()
                )
        );
    }
}
