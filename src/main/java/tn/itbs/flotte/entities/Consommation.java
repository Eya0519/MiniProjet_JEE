package tn.itbs.flotte.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Data

public class Consommation {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idco;

    private Date dateConsommation;

    private double quantiteCarburant;

    private double coutTotal;

    @ManyToOne
    private Vehicule vehicule;
}
