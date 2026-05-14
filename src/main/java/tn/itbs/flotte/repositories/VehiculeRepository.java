package tn.itbs.flotte.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import tn.itbs.flotte.entities.Vehicule;

public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {
	List<Vehicule> findByStatut(String statut);

    List<Vehicule> findByType(String type);

}
