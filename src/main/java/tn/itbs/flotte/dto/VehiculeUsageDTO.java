package tn.itbs.flotte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehiculeUsageDTO {

    private int vehiculeId;
    private String immatriculation;
    private String modele;
    private int nombreMissions;
}
