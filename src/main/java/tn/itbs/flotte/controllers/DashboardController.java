package tn.itbs.flotte.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.itbs.flotte.dto.DashboardDTO;
import tn.itbs.flotte.dto.VehiculeFuelCostDTO;
import tn.itbs.flotte.dto.VehiculeUsageDTO;
import tn.itbs.flotte.entities.Consommation;
import tn.itbs.flotte.entities.Vehicule;
import tn.itbs.flotte.services.ConsommationService;
import tn.itbs.flotte.services.MissionService;
import tn.itbs.flotte.services.VehiculeService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private VehiculeService vServ;

    @Autowired
    private ConsommationService conServ;

    @Autowired
    private MissionService mServ;

    @GetMapping("/summary")
    public DashboardDTO summary() {
        double coutTotal = 0;

        for (Consommation c : conServ.chercherTout()) {
            coutTotal += c.getCoutTotal();
        }

        return new DashboardDTO(
                vServ.nombreVehiculesDisponibles(),
                vServ.nombreVehiculesEnPanne(),
                coutTotal);
    }

    @GetMapping("/fuel-costs")
    public List<VehiculeFuelCostDTO> coutCarburantParVehicule() {
        List<VehiculeFuelCostDTO> result = new ArrayList<>();

        for (Vehicule v : vServ.chercherTout()) {
            result.add(new VehiculeFuelCostDTO(
                    v.getIdv(),
                    v.getImmatriculation(),
                    v.getModele(),
                    conServ.getTotalCostByVehicule(v.getIdv())));
        }

        return result;
    }

    @GetMapping("/fleet-usage")
    public List<VehiculeUsageDTO> utilisationFlotte() {
        List<VehiculeUsageDTO> result = new ArrayList<>();

        for (Vehicule v : vServ.chercherTout()) {
            result.add(new VehiculeUsageDTO(
                    v.getIdv(),
                    v.getImmatriculation(),
                    v.getModele(),
                    mServ.trouverMissionParVehicule(v).size()));
        }

        result.sort(Comparator.comparing(VehiculeUsageDTO::getNombreMissions).reversed());

        return result;
    }
}
