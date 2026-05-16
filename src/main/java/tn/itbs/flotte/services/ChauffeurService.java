package tn.itbs.flotte.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import tn.itbs.flotte.entities.Chauffeur;
import tn.itbs.flotte.repositories.ChauffeurRepository;

@Service
public class ChauffeurService {

    @Autowired
    private ChauffeurRepository crepo;

    public Chauffeur chercherParId(int id) {
        return crepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Chauffeur non trouve"));
    }

    public List<Chauffeur> chercherTout() {
        return crepo.findAll();
    }

    public ResponseEntity<String> ajouterChauffeur(Chauffeur c) {

        crepo.save(c);

        return ResponseEntity.ok("Chauffeur ajouté avec succès");
    }


    public ResponseEntity<String> supprimerChauffeur(int id) {

        crepo.deleteById(id);

        return ResponseEntity.ok("Chauffeur supprimé avec succès");
    }


    public ResponseEntity<String> mettreAJourChauffeur(int id, Chauffeur ch) {

        crepo.findById(id).ifPresentOrElse(

                c -> {
                    c.setNom(ch.getNom());
                    c.setPermis(ch.getPermis());
                    c.setExperience(ch.getExperience());

                    crepo.save(c);
                },

                () -> {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Chauffeur non trouvé");
                }

        );

        return ResponseEntity.ok("Chauffeur mis à jour avec succès");
    }

    //recherche
    public List<Chauffeur> getByNom(String nom) {
        return crepo.findByNom(nom);
    }

    public List<Chauffeur> getExperiencedChauffeurs(int exp) {
        return crepo.findByExperienceGreaterThan(exp);
    }
}
