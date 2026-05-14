package tn.itbs.flotte.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.itbs.flotte.entities.Chauffeur;
import tn.itbs.flotte.entities.Mission;
import tn.itbs.flotte.entities.Vehicule;

public interface MissionRepository extends JpaRepository<Mission, Integer> {

    List<Mission> findByDestination(String destination);

    List<Mission> findByVehicule(Vehicule vehicule);
    List<Mission> findByChauffeur(Chauffeur chauffeur);
}
