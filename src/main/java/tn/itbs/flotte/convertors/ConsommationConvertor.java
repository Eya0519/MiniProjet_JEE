package tn.itbs.flotte.convertors;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.itbs.flotte.dto.ConsommationDTO;
import tn.itbs.flotte.entities.Consommation;
import tn.itbs.flotte.entities.Vehicule;
import tn.itbs.flotte.repositories.VehiculeRepository;

@Component
public class ConsommationConvertor {

    @Autowired
    private ModelMapper mmapper;

    @Autowired
    private VehiculeRepository vrepo;

    public ConsommationDTO toDto(Consommation c) {
        ConsommationDTO cDto = mmapper.map(c, ConsommationDTO.class);

        if (c.getVehicule() != null) {
            cDto.setVehiculeId(c.getVehicule().getIdv());
            cDto.setVehiculeImmatriculation(c.getVehicule().getImmatriculation());
        }

        return cDto;
    }

    public Consommation fromDto(ConsommationDTO cDto) {
        Consommation c = new Consommation();
        c.setIdco(cDto.getIdco());
        c.setDateConsommation(cDto.getDateConsommation());
        c.setQuantiteCarburant(cDto.getQuantiteCarburant());
        c.setCoutTotal(cDto.getCoutTotal());

        if (cDto.getVehiculeId() != null) {
            Vehicule v = vrepo.findById(cDto.getVehiculeId()).orElse(null);
            c.setVehicule(v);
        }

        return c;
    }

    public List<ConsommationDTO> toListDto(List<Consommation> listeC) {
        return listeC.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
