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
import tn.itbs.flotte.convertors.ChauffeurConvertor;
import tn.itbs.flotte.dto.ChauffeurDTO;
import tn.itbs.flotte.entities.Chauffeur;
import tn.itbs.flotte.services.ChauffeurService;

@RestController
@RequestMapping("/chauffeurs")
public class ChauffeurController {

    @Autowired
    private ChauffeurService cServ;

    @Autowired
    private ChauffeurConvertor cConvert;

    @GetMapping("/get/{id}")
    public ChauffeurDTO chercherParId(@PathVariable int id) {
        return cConvert.toDto(cServ.chercherParId(id));
    }

    @GetMapping("/getAll")
    public List<ChauffeurDTO> chercherTout() {
        return cConvert.toListDto(cServ.chercherTout());
    }

    @PostMapping("/add")
    public void ajouter(@Valid @RequestBody ChauffeurDTO cDto) {
        Chauffeur c = cConvert.fromDto(cDto);
        cServ.ajouterChauffeur(c);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        cServ.supprimerChauffeur(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @Valid @RequestBody ChauffeurDTO cDto) {
        Chauffeur c = cConvert.fromDto(cDto);
        return cServ.mettreAJourChauffeur(id, c);
    }

    @GetMapping("/nom/{nom}")
    public List<ChauffeurDTO> chercherParNom(@PathVariable String nom) {
        return cConvert.toListDto(cServ.getByNom(nom));
    }

    @GetMapping("/experience/{exp}")
    public List<ChauffeurDTO> chauffeursExperimentes(@PathVariable int exp) {
        return cConvert.toListDto(cServ.getExperiencedChauffeurs(exp));
    }
}
