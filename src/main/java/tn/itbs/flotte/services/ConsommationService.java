package tn.itbs.flotte.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Consommation chercherParId(int id) {
        return crepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Consommation non trouvee"));
    }

    public List<Consommation> chercherTout() {
        return crepo.findAll();
    }

    public ResponseEntity<String> ajouterConsommation(Consommation c) {
        crepo.save(c);
        return ResponseEntity.ok("Consommation ajoutee avec succes");
    }

    public ResponseEntity<String> supprimerConsommation(int id) {
        crepo.deleteById(id);
        return ResponseEntity.ok("Consommation supprimee avec succes");
    }

    public ResponseEntity<String> mettreAJourConsommation(int id, Consommation co) {
        crepo.findById(id).ifPresentOrElse(
                c -> {
                    c.setDateConsommation(co.getDateConsommation());
                    c.setQuantiteCarburant(co.getQuantiteCarburant());
                    c.setCoutTotal(co.getCoutTotal());
                    c.setVehicule(co.getVehicule());
                    crepo.save(c);
                },
                () -> {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Consommation non trouvee");
                });

        return ResponseEntity.ok("Consommation mise a jour avec succes");
    }

    public List<Consommation> getByVehicule(int idVehicule) {
        Vehicule v = vrepo.findById(idVehicule)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Vehicule non trouve"));

        return crepo.findByVehicule(v);
    }

    public List<Consommation> getByDate(Date date) {
        return crepo.findByDateConsommation(date);
    }

    public double getTotalCostByVehicule(int idVehicule) {
        Vehicule v = vrepo.findById(idVehicule)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Vehicule non trouve"));

        List<Consommation> list = crepo.findByVehicule(v);
        double total = 0;

        for (Consommation c : list) {
            total += c.getCoutTotal();
        }

        return total;
    }
}
