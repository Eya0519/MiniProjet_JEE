package tn.itbs.flotte.convertors;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.itbs.flotte.dto.MissionDTO;
import tn.itbs.flotte.entities.Chauffeur;
import tn.itbs.flotte.entities.Mission;
import tn.itbs.flotte.entities.Vehicule;
import tn.itbs.flotte.repositories.ChauffeurRepository;
import tn.itbs.flotte.repositories.VehiculeRepository;

@Component
public class MissionConvertor {

    @Autowired
    private ModelMapper mmapper;

    @Autowired
    private VehiculeRepository vrepo;

    @Autowired
    private ChauffeurRepository crepo;

    public MissionDTO toDto(Mission m) {
        MissionDTO mDto = mmapper.map(m, MissionDTO.class);

        if (m.getVehicule() != null) {
            mDto.setVehiculeId(m.getVehicule().getIdv());
            mDto.setVehiculeImmatriculation(m.getVehicule().getImmatriculation());
        }

        if (m.getChauffeur() != null) {
            mDto.setChauffeurId(m.getChauffeur().getIdc());
            mDto.setChauffeurNom(m.getChauffeur().getNom());
        }

        return mDto;
    }

    public Mission fromDto(MissionDTO mDto) {
        Mission m = new Mission();
        m.setIdm(mDto.getIdm());
        m.setPointDepart(mDto.getPointDepart());
        m.setDestination(mDto.getDestination());
        m.setDistance(mDto.getDistance());

        if (mDto.getVehiculeId() != null) {
            Vehicule v = vrepo.findById(mDto.getVehiculeId()).orElse(null);
            m.setVehicule(v);
        }

        if (mDto.getChauffeurId() != null) {
            Chauffeur c = crepo.findById(mDto.getChauffeurId()).orElse(null);
            m.setChauffeur(c);
        }

        return m;
    }

    public List<MissionDTO> toListDto(List<Mission> listeM) {
        return listeM.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
