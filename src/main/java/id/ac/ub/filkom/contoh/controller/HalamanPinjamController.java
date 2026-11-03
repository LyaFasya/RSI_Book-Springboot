package id.ac.ub.filkom.contoh.controller;

import id.ac.ub.filkom.contoh.service.Bukti;
import id.ac.ub.filkom.contoh.service.PinjamGagalException;
import id.ac.ub.filkom.contoh.service.PinjamService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pinjam")
public class HalamanPinjamController {

    private final PinjamService pinjamService;

    public HalamanPinjamController(PinjamService pinjamService) {
        this.pinjamService = pinjamService;
    }

    @GetMapping
    public String tampilHalaman(HttpSession session, Model model) {
        model.addAttribute("idAnggota", idAnggotaLogin(session));
        model.addAttribute("daftarBuku", pinjamService.daftarBuku());
        model.addAttribute("daftarAnggota", pinjamService.daftarAnggota());

        return "pinjam";
    }

    // Memilih anggota untuk simulasi session.
    @PostMapping("/anggota")
    public String pilihAnggota(
            @RequestParam String idAnggota,
            HttpSession session,
            Model model) {

        try {
            pinjamService.cariAnggota(idAnggota);

            session.setAttribute("idAnggota", idAnggota);

            return "redirect:/pinjam";

        } catch (PinjamGagalException e) {
            model.addAttribute("pesan", e.getMessage());

            return tampilHalaman(session, model);
        }
    }

    // [1] Menerima pilihan buku dari form.
    @PostMapping
    public String pilihBuku(
            @RequestParam String idBuku,
            HttpSession session,
            Model model) {

        String idAnggota = idAnggotaLogin(session);

        if (idAnggota == null) {
            model.addAttribute(
                    "pesan",
                    "Pilih anggota terlebih dahulu"
            );

            return tampilHalaman(session, model);
        }

        try {
            // [2] Meneruskan proses peminjaman ke service.
            Bukti bukti = pinjamService.pinjamBuku(
                    idAnggota,
                    idBuku
            );

            // [12] Menampilkan bukti peminjaman.
            return tampilBukti(bukti, model);

        } catch (PinjamGagalException e) {
            // [13]-[14] Menampilkan pesan kegagalan.
            model.addAttribute("pesan", e.getMessage());
            model.addAttribute("bisaReservasi", e.isBisaReservasi());
            model.addAttribute("idBukuReservasi", idBuku);

            return tampilHalaman(session, model);
        }
    }

    private String tampilBukti(Bukti bukti, Model model) {
        model.addAttribute("bukti", bukti);

        return "bukti";
    }

    private String idAnggotaLogin(HttpSession session) {
        return (String) session.getAttribute("idAnggota");
    }
}