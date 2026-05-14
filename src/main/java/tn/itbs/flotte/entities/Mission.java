package tn.itbs.flotte.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Data
public class Mission {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int idm;

	    private String pointDepart;

	    private String destination;

	    private double distance;

	    @ManyToOne
	    private Vehicule vehicule;

	    @ManyToOne
	    private Chauffeur chauffeur;
}
