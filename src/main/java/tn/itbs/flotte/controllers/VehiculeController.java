package tn.itbs.flotte.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import tn.itbs.flotte.convertors.VehiculeConvertor;
import tn.itbs.flotte.dto.VehiculeDTO;
import tn.itbs.flotte.entities.Vehicule;
import tn.itbs.flotte.services.VehiculeService;

@RestController
@RequestMapping("/vehicules")
public class VehiculeController {

    @Autowired
    private VehiculeService vServ;

    @Autowired
    private VehiculeConvertor vConvert;

    @GetMapping("/get/{id}")
    public VehiculeDTO chercherParId(@PathVariable int id) {
        return vConvert.toDto(vServ.chercherParId(id));
    }

    @GetMapping("/getAll")
    public List<VehiculeDTO> chercherTout() {
        return vConvert.toListDto(vServ.chercherTout());
    }

    @PostMapping("/add")
    public void ajouter(@Valid @RequestBody VehiculeDTO vDto) {
        Vehicule v = vConvert.fromDto(vDto);
        vServ.ajouterVehicule(v);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        vServ.supprimerVehicule(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @Valid @RequestBody VehiculeDTO vDto) {
        Vehicule v = vConvert.fromDto(vDto);
        return vServ.mettreAJourVehicule(id, v);
    }

    @GetMapping("/statut/{statut}")
    public List<VehiculeDTO> chercherParStatut(@PathVariable String statut) {
        return vConvert.toListDto(vServ.trouverVehiculesParStatut(statut));
    }

    @GetMapping("/type/{type}")
    public List<VehiculeDTO> chercherParType(@PathVariable String type) {
        return vConvert.toListDto(vServ.trouverVehiculesParType(type));
    }

    @GetMapping("/maintenance")
    public List<VehiculeDTO> vehiculesNecessitantMaintenance() {
        return vConvert.toListDto(vServ.vehiculesNecessitantMaintenance());
    }
}
