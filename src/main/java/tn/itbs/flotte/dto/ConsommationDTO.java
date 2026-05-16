package tn.itbs.flotte.dto;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ConsommationDTO {

    private int idco;

    @NotNull(message = "La date est obligatoire")
    private Date dateConsommation;

    @Positive(message = "La quantite de carburant doit etre positive")
    private double quantiteCarburant;

    @PositiveOrZero(message = "Le cout total doit etre positif")
    private double coutTotal;

    private Integer vehiculeId;
    private String vehiculeImmatriculation;
}
