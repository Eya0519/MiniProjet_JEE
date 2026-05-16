package tn.itbs.flotte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardDTO {

    private long vehiculesDisponibles;
    private long vehiculesEnPanne;
    private double coutCarburantTotal;
}
