package tn.itbs.flotte.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VehiculeDTO {

    private int idv;

    @NotBlank(message = "L'immatriculation est obligatoire")
    private String immatriculation;

    @NotBlank(message = "Le modele est obligatoire")
    private String modele;

    @NotBlank(message = "Le type est obligatoire")
    private String type;

    @Min(value = 0, message = "Le kilometrage doit etre positif")
    private double kilometrage;

    @NotBlank(message = "Le statut est obligatoire")
    private String statut;
}
