package tn.itbs.flotte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehiculeFuelCostDTO {

    private int vehiculeId;
    private String immatriculation;
    private String modele;
    private double coutTotal;
}
