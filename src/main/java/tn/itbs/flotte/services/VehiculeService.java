package tn.itbs.flotte.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import tn.itbs.flotte.entities.Vehicule;
import tn.itbs.flotte.repositories.VehiculeRepository;

@Service

public class VehiculeService {
	@Autowired
    private VehiculeRepository vrepo;

    public Vehicule chercherParId(int id) {
        return vrepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Vehicule non trouve"));
    }

    public List<Vehicule> chercherTout() {
        return vrepo.findAll();
    }

    public List<Vehicule> trouverVehiculesParStatut(String statut) {
        return vrepo.findByStatut(statut);
    }

    public List<Vehicule> trouverVehiculesParType(String type) {
        return vrepo.findByType(type);
    }



    public ResponseEntity<String> ajouterVehicule(Vehicule v) {
        vrepo.save(v);
        return ResponseEntity.ok("Véhicule ajouté avec succès");
    }


    public ResponseEntity<String> supprimerVehicule(int idVehicule) {

        vrepo.deleteById(idVehicule);

        return ResponseEntity.ok("Véhicule supprimé avec succès");
    }

 

    public ResponseEntity<String> mettreAJourVehicule(int idVehicule, Vehicule ve) {

        vrepo.findById(idVehicule).ifPresentOrElse(

                v -> {

                    v.setImmatriculation(ve.getImmatriculation());
                    v.setModele(ve.getModele());
                    v.setType(ve.getType());
                    v.setKilometrage(ve.getKilometrage());
                    v.setStatut(ve.getStatut());

                    vrepo.save(v);

                },

                () -> {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Véhicule non trouvé");
                }

        );

        return ResponseEntity.ok("Véhicule mis à jour avec succès");
    }

 
    public List<Vehicule> vehiculesNecessitantMaintenance() {
        return vrepo.findByKilometrageGreaterThan(100000);
    }

    
    public long nombreVehiculesDisponibles() {
        return vrepo.findByStatut("DISPONIBLE").size();
    }

    public long nombreVehiculesEnPanne() {
        return vrepo.findByStatut("EN_PANNE").size();
    }
}
