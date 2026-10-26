package id.ac.ub.filkom.contoh.controller;

import id.ac.ub.filkom.contoh.service.AnggotaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnggotaController {

    private final AnggotaService anggotaService;

    public AnggotaController(AnggotaService anggotaService) {
        this.anggotaService = anggotaService;
    }

    @GetMapping("/anggota")
    public String daftarAnggota(
            @RequestParam(required = false) String kata,
            Model model) {

        model.addAttribute(
                "daftarAnggota",
                anggotaService.cariAnggota(kata));
        model.addAttribute("kata", kata);

        return "anggota";
    }

    @PostMapping("/anggota")
    public String tambahAnggota(
            @RequestParam String idAnggota,
            @RequestParam String nama,
            @RequestParam String alamat) {

        anggotaService.tambahAnggota(idAnggota, nama, alamat);

        return "redirect:/anggota";
    }

    @GetMapping("/anggota/tambah")
    public String formTambahAnggota() {
        return "tambah-anggota";
    }
}