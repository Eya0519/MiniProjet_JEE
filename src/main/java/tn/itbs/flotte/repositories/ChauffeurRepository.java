package tn.itbs.flotte.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.itbs.flotte.entities.Chauffeur;

public interface ChauffeurRepository extends JpaRepository<Chauffeur, Integer> {
	List<Chauffeur> findByNom(String nom);

    List<Chauffeur> findByExperienceGreaterThan(int experience);

}
