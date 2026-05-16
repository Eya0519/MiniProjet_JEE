package tn.itbs.flotte.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChauffeurDTO {

    private int idc;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le permis est obligatoire")
    private String permis;

    @Min(value = 0, message = "L'experience doit etre positive")
    private int experience;
}
