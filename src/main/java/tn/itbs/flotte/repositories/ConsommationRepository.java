package tn.itbs.flotte.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.itbs.flotte.entities.Consommation;
import tn.itbs.flotte.entities.Vehicule;

public interface ConsommationRepository extends JpaRepository<Consommation, Integer> {

    List<Consommation> findByDateConsommation(Date dateConsommation);

    List<Consommation> findByVehicule(Vehicule vehicule);

}