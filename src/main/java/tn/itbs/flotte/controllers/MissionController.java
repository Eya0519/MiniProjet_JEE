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
import tn.itbs.flotte.convertors.MissionConvertor;
import tn.itbs.flotte.dto.MissionDTO;
import tn.itbs.flotte.entities.Mission;
import tn.itbs.flotte.services.ChauffeurService;
import tn.itbs.flotte.services.MissionService;
import tn.itbs.flotte.services.VehiculeService;

@RestController
@RequestMapping("/missions")
public class MissionController {

    @Autowired
    private MissionService mServ;

    @Autowired
    private VehiculeService vServ;

    @Autowired
    private ChauffeurService cServ;

    @Autowired
    private MissionConvertor mConvert;

    @GetMapping("/get/{id}")
    public MissionDTO chercherParId(@PathVariable int id) {
        return mConvert.toDto(mServ.chercherParId(id));
    }

    @GetMapping("/getAll")
    public List<MissionDTO> chercherTout() {
        return mConvert.toListDto(mServ.chercherTout());
    }

    @PostMapping("/add")
    public void ajouter(@Valid @RequestBody MissionDTO mDto) {
        Mission m = mConvert.fromDto(mDto);
        mServ.ajouterMission(m);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        mServ.supprimerMission(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @Valid @RequestBody MissionDTO mDto) {
        Mission m = mConvert.fromDto(mDto);
        return mServ.mettreAJourMission(id, m);
    }

    @PutMapping("/affecter/{idMission}/{idVehicule}/{idChauffeur}")
    public ResponseEntity<String> affecterMission(
            @PathVariable int idMission,
            @PathVariable int idVehicule,
            @PathVariable int idChauffeur) {
        return mServ.affecterMission(idMission, idVehicule, idChauffeur);
    }

    @GetMapping("/destination/{destination}")
    public List<MissionDTO> chercherParDestination(@PathVariable String destination) {
        return mConvert.toListDto(mServ.trouverMissionParDestination(destination));
    }

    @GetMapping("/vehicule/{idVehicule}")
    public List<MissionDTO> chercherParVehicule(@PathVariable int idVehicule) {
        return mConvert.toListDto(mServ.trouverMissionParVehicule(vServ.chercherParId(idVehicule)));
    }

    @GetMapping("/chauffeur/{idChauffeur}")
    public List<MissionDTO> chercherParChauffeur(@PathVariable int idChauffeur) {
        return mConvert.toListDto(mServ.trouverMissionParChauffeur(cServ.chercherParId(idChauffeur)));
    }
}
