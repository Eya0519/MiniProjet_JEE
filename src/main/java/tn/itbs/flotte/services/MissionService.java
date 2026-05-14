package tn.itbs.flotte.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import tn.itbs.flotte.entities.Chauffeur;
import tn.itbs.flotte.entities.Mission;
import tn.itbs.flotte.entities.Vehicule;
import tn.itbs.flotte.repositories.ChauffeurRepository;
import tn.itbs.flotte.repositories.MissionRepository;
import tn.itbs.flotte.repositories.VehiculeRepository;

@Service
public class MissionService {
	@Autowired
    private MissionRepository mrepo;

    @Autowired
    private VehiculeRepository vrepo;

    @Autowired
    private ChauffeurRepository crepo;

    // -------------------------
    // AJOUT MISSION
    // -------------------------

    public ResponseEntity<String> ajouterMission(Mission m) {

        mrepo.save(m);

        return ResponseEntity.ok("Mission ajoutée avec succès");
    }

    // -------------------------
    // SUPPRESSION
    // -------------------------

    public ResponseEntity<String> supprimerMission(int idMission) {

        mrepo.deleteById(idMission);

        return ResponseEntity.ok("Mission supprimée avec succès");
    }

    // -------------------------
    // MISE À JOUR
    // -------------------------

    public ResponseEntity<String> mettreAJourMission(int idMission, Mission mi) {

        mrepo.findById(idMission).ifPresentOrElse(

                m -> {

                    m.setPointDepart(mi.getPointDepart());
                    m.setDestination(mi.getDestination());
                    m.setDistance(mi.getDistance());

                    mrepo.save(m);

                },

                () -> {

                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Mission non trouvée");

                }

        );

        return ResponseEntity.ok("Mission mise à jour avec succès");
    }

    // -------------------------
    // RECHERCHE
    // -------------------------

    public List<Mission> trouverMissionParDestination(String destination) {

        return mrepo.findByDestination(destination);
    }

    public List<Mission> trouverMissionParVehicule(Vehicule v) {

        return mrepo.findByVehicule(v);
    }

    public List<Mission> trouverMissionParChauffeur(Chauffeur c) {

        return mrepo.findByChauffeur(c);
    }
    
//partie metier
    
    public ResponseEntity<String> affecterMission(
            int idMission,
            int idVehicule,
            int idChauffeur) {

        Mission mission = mrepo.findById(idMission)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Mission non trouvée"));

        Vehicule vehicule = vrepo.findById(idVehicule)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Véhicule non trouvé"));

        Chauffeur chauffeur = crepo.findById(idChauffeur)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Chauffeur non trouvé"));

        mission.setVehicule(vehicule);
        mission.setChauffeur(chauffeur);

        mrepo.save(mission);

        return ResponseEntity.ok("Mission affectée avec succès");
    }


}
