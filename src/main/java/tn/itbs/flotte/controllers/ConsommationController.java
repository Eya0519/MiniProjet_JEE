package tn.itbs.flotte.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tn.itbs.flotte.convertors.ConsommationConvertor;
import tn.itbs.flotte.dto.ConsommationDTO;
import tn.itbs.flotte.entities.Consommation;
import tn.itbs.flotte.services.ConsommationService;

@RestController
@RequestMapping("/consommations")
public class ConsommationController {

    @Autowired
    private ConsommationService cServ;

    @Autowired
    private ConsommationConvertor cConvert;

    @GetMapping("/get/{id}")
    public ConsommationDTO chercherParId(@PathVariable int id) {
        return cConvert.toDto(cServ.chercherParId(id));
    }

    @GetMapping("/getAll")
    public List<ConsommationDTO> chercherTout() {
        return cConvert.toListDto(cServ.chercherTout());
    }

    @PostMapping("/add")
    public void ajouter(@Valid @RequestBody ConsommationDTO cDto) {
        Consommation c = cConvert.fromDto(cDto);
        cServ.ajouterConsommation(c);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        cServ.supprimerConsommation(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @Valid @RequestBody ConsommationDTO cDto) {
        Consommation c = cConvert.fromDto(cDto);
        return cServ.mettreAJourConsommation(id, c);
    }

    @GetMapping("/vehicule/{idVehicule}")
    public List<ConsommationDTO> chercherParVehicule(@PathVariable int idVehicule) {
        return cConvert.toListDto(cServ.getByVehicule(idVehicule));
    }

    @GetMapping("/date/{date}")
    public List<ConsommationDTO> chercherParDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return cConvert.toListDto(cServ.getByDate(date));
    }

    @GetMapping("/vehicule/{idVehicule}/cout-total")
    public double coutTotalParVehicule(@PathVariable int idVehicule) {
        return cServ.getTotalCostByVehicule(idVehicule);
    }
}
