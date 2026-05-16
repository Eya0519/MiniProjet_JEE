package tn.itbs.flotte.convertors;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.itbs.flotte.dto.VehiculeDTO;
import tn.itbs.flotte.entities.Vehicule;

@Component
public class VehiculeConvertor {

    @Autowired
    private ModelMapper mmapper;

    public VehiculeDTO toDto(Vehicule v) {
        return mmapper.map(v, VehiculeDTO.class);
    }

    public Vehicule fromDto(VehiculeDTO vDto) {
        return mmapper.map(vDto, Vehicule.class);
    }

    public List<VehiculeDTO> toListDto(List<Vehicule> listeV) {
        return listeV.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
