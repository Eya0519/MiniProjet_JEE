package tn.itbs.flotte.convertors;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.itbs.flotte.dto.ChauffeurDTO;
import tn.itbs.flotte.entities.Chauffeur;

@Component
public class ChauffeurConvertor {

    @Autowired
    private ModelMapper mmapper;

    public ChauffeurDTO toDto(Chauffeur c) {
        return mmapper.map(c, ChauffeurDTO.class);
    }

    public Chauffeur fromDto(ChauffeurDTO cDto) {
        return mmapper.map(cDto, Chauffeur.class);
    }

    public List<ChauffeurDTO> toListDto(List<Chauffeur> listeC) {
        return listeC.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
