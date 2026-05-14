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
public class Vehicule {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idv;

    private String immatriculation;

    private String modele;

    private String type;

    private double kilometrage;

    private String statut;

    @OneToMany(mappedBy = "vehicule")
    private List<Mission> missions = new ArrayList<Mission>();

    @OneToMany(mappedBy = "vehicule")
    private List<Consommation> consommations = new ArrayList<Consommation>();
}
