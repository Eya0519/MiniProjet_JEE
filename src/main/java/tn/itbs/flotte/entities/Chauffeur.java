package tn.itbs.flotte.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.OneToMany;

@Entity
@Data

public class Chauffeur {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int idc;

	    private String nom;

	    private String permis;

	    private int experience;

	    @OneToMany(mappedBy = "chauffeur")
	    private List<Mission> missions = new ArrayList<Mission>();

}
