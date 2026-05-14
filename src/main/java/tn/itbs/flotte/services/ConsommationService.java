package tn.itbs.flotte.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.itbs.flotte.entities.Consommation;
import tn.itbs.flotte.entities.Vehicule;
import tn.itbs.flotte.repositories.ConsommationRepository;
import tn.itbs.flotte.repositories.VehiculeRepository;

@Service

public class ConsommationService {
	@Autowired
    private ConsommationRepository crepo;

    @Autowired
    private VehiculeRepository vrepo;

    
    public ResponseEntity<String> ajouterConsommation(Consommation c) {
        crepo.save(c);
        return ResponseEntity.ok("Consommation ajoutée avec succès");
    }

   
    public List<Consommation> getByVehicule(int idVehicule) {

        Vehicule v = vrepo.findById(idVehicule).orElse(null);

        return crepo.findByVehicule(v);
    }

    
    public List<Consommation> getByDate(Date date) {

        return crepo.findByDateConsommation(date);
    }

    // Total cout par vehicule
    public double getTotalCostByVehicule(int idVehicule) {

        Vehicule v = vrepo.findById(idVehicule).orElse(null);

        List<Consommation> list = crepo.findByVehicule(v);

        double total = 0;

        for (Consommation c : list) {
            total += c.getCoutTotal();
        }

        return total;
    }
}
