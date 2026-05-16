package tn.itbs.flotte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MissionDTO {

    private int idm;

    @NotBlank(message = "Le point de depart est obligatoire")
    private String pointDepart;

    @NotBlank(message = "La destination est obligatoire")
    private String destination;

    @Positive(message = "La distance doit etre positive")
    private double distance;

    private Integer vehiculeId;
    private Integer chauffeurId;

    private String vehiculeImmatriculation;
    private String chauffeurNom;
}
