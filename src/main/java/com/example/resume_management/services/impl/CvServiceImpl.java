package com.example.resume_management.services.impl;
import com.example.resume_management.dto.requests.CvRequest;
import com.example.resume_management.dto.responses.CvResponse;
import com.example.resume_management.entities.Competence;
import com.example.resume_management.entities.Cv;
import com.example.resume_management.entities.Entreprise;
import com.example.resume_management.entities.Info;
import com.example.resume_management.repositories.*;
import com.example.resume_management.services.CvService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import org.modelmapper.TypeToken;
import java.util.ArrayList;
import java.util.List;


@Service
public class CvServiceImpl  implements CvService {

    @Autowired
    private CVRepository cv_repo;

    @Autowired
    private EntroRepository entro_repo;


    @Autowired
    private Cv_Entro_Reop cv_entro_repo;

    @Autowired
    private CompetenceRepository competence_repo;

    @Autowired
    private Cv_Compentece_Repo cv_compentece_repo;

    @Autowired
    private InfoRepository infoRepository;

    @Override
    public CvResponse addCv(CvRequest cvRequest) {

        Cv cv = new ModelMapper().map(cvRequest, Cv.class);
        int id_cv = cv_repo.save(cv);

        List<Entreprise> entreprises = new ArrayList<>();
        List<Integer> listIds = entro_repo.saveAll(cv.getEntreprises());

        for (Integer entro_id : listIds){
            Entreprise entreprise = entro_repo.getEntrepriseById(entro_id);
            cv_entro_repo.LinkCvToEntroprise(id_cv, entro_id);
            entreprises.add(entreprise);
        }

        List<Competence> competences = new ArrayList<>();
        List<Integer> listSkillIds = competence_repo.saveAll(cv.getCompetences());
        for (Integer skill_id : listSkillIds){
            Competence competence = competence_repo.getCompetenceById(skill_id);
            cv_compentece_repo.LinkCvToCompetence(id_cv,skill_id);
            competences.add(competence);
        }
        cv.getInfoPersonnel().setId_cv(id_cv);
        int infoId = infoRepository.save(cv.getInfoPersonnel());
        Info info = infoRepository.getInfoById(infoId);
        Cv newCv = cv_repo.getCvById(id_cv);
        newCv.setEntreprises(entreprises);
        newCv.setCompetences(competences);
        newCv.setInfoPersonnel(info);
        return new ModelMapper().map(newCv,CvResponse.class);
    }


    @Override
    public CvResponse GetCv(int id) {
        Cv cv = cv_repo.getCvById(id);
        List<Integer> integerList = cv_entro_repo.getEntreprisesIds(cv.getId());
        List<Entreprise> entreprises = new ArrayList<>();
        for (Integer entroId:integerList){
            Entreprise entreprise = entro_repo.getEntrepriseById(entroId);
            entreprises.add(entreprise);
        }
        cv.setEntreprises(entreprises);

        List<Integer> integerListC = cv_entro_repo.getEntreprisesIds(cv.getId());
        List<Competence> competences = new ArrayList<>();
        for (Integer skillId:integerListC){
            Competence competence = competence_repo.getCompetenceById(skillId);
            competences.add(competence);
        }
        cv.setCompetences(competences);
        Info info = infoRepository.getInfoByCvId(cv.getId());
        cv.setInfoPersonnel(info);
        return new ModelMapper().map(cv,CvResponse.class);
    }



    @Override
    public List<CvResponse> GetAllCv() {
        List<Cv> cvs = cv_repo.getAllCv();
        List<CvResponse> cvResponses = new ArrayList<>();
        for (Cv cv : cvs){
            CvResponse cv1 = this.GetCv(cv.getId());
            cvResponses.add(cv1);
        }
        return cvResponses;
    }


    public void editCv(Cv cv) {
        cv_repo.updateCv(cv);
    }


    public void deleteCv(int id) {
        cv_repo.deleteCv(id);
    }
}
