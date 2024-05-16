package com.example.resume_management.services.impl;
import com.example.resume_management.dto.requests.CvRequest;
import com.example.resume_management.dto.requests.CvUpdate;
import com.example.resume_management.dto.responses.CvResponse;
import com.example.resume_management.dto.responses.InfoResponse;
import com.example.resume_management.entities.Competence;
import com.example.resume_management.entities.Cv;
import com.example.resume_management.entities.Entreprise;
import com.example.resume_management.entities.Info;
import com.example.resume_management.exceptions.RecordNotFoundException;
import com.example.resume_management.repositories.*;
import com.example.resume_management.services.CvService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
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

        // save entreprises and link them with cv
        List<Entreprise> entreprises = new ArrayList<>();
        for (Entreprise e : cv.getEntreprises()){
            if(entro_repo.entrepriseExistsByName(e.getName())){
                //get id_entro by name and link it with id_cv
                int entro_id = entro_repo.getIdByName(e.getName());
                cv_entro_repo.LinkCvToEntroprise(id_cv, entro_id);
                entreprises.add(e);
            }else {
                // save and get id_entro by name and link it with id_cv
                int entro_id = entro_repo.save(e);
                cv_entro_repo.LinkCvToEntroprise(id_cv, entro_id);
                entreprises.add(e);
            }
        }

        // save competences and link them with cv
        List<Competence> competences = new ArrayList<>();
        for (Competence c : cv.getCompetences()){
            if(competence_repo.skillExistsByName(c.getName())){
                //get id_skill by name and link it with id_cv
                int skill_id = competence_repo.getIdByName(c.getName());
                cv_compentece_repo.LinkCvToCompetence(id_cv, skill_id);
                competences.add(c);
            }else {
                // save and get id_skill by name and link it with id_cv
                int skill_id = competence_repo.save(c);
                cv_compentece_repo.LinkCvToCompetence(id_cv, skill_id);
                competences.add(c);
            }
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
        if(!cv_repo.existsById(id)){
            throw new RecordNotFoundException("Cv with id : " +id+ " does not exist");
        }
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


    public CvResponse editCv(CvUpdate cv) {

        //update cv
        cv_repo.updateCv(new ModelMapper().map(cv,Cv.class));
        Cv updatedCv = new ModelMapper().map(this.GetCv(cv.getId()),Cv.class);


        //update Entreprise
        List<Integer> idsEntro = cv_entro_repo.getEntreprisesIds(updatedCv.getId());
        for (Integer i : idsEntro){
            entro_repo.deleteEntreprise(i);
        }
        List<Entreprise> entreprises = new ArrayList<>();
        for (Entreprise e: cv.getEntreprises()){ // {java, sql}  =>  {java, dotnet}
            int entro_id = entro_repo.save(e);
            cv_entro_repo.LinkCvToEntroprise(updatedCv.getId(), entro_id);
            entreprises.add(entro_repo.getEntrepriseById(entro_id));
        }
        updatedCv.setEntreprises(entreprises);

        //update Competence
        List<Integer> idsSkill = cv_compentece_repo.getCompetencesIds(updatedCv.getId());
        for (Integer i : idsSkill){
            competence_repo.deleteCompetence(i);
        }
        List<Competence> competences = new ArrayList<>();
        for (Competence c : cv.getCompetences()){
            int skill_id = competence_repo.save(c);
            cv_compentece_repo.LinkCvToCompetence(updatedCv.getId(), skill_id);
            competences.add(competence_repo.getCompetenceById(skill_id));
        }

        //update Info
        infoRepository.updateInfo(cv.getInfoPersonnel(),updatedCv.getId());
        updatedCv.setInfoPersonnel(infoRepository.getInfoByCvId(updatedCv.getId()));
        updatedCv.setEntreprises(entreprises);
        updatedCv.setCompetences(competences);


        return new ModelMapper().map(updatedCv,CvResponse.class);
    }


    public void deleteCv(int id) {
        cv_repo.deleteCv(id);
        infoRepository.deleteInfo(id);
        cv_entro_repo.deleteRelation(id);
        cv_compentece_repo.deleteRelation(id);
        List<Integer> entroIds = cv_entro_repo.getEntreprisesIds(id);
        for (Integer entro_id : entroIds){
            entro_repo.deleteEntreprise(entro_id);
        }
        List<Integer> skillIds = cv_compentece_repo.getCompetencesIds(id);
        for (Integer skill_id : skillIds){
            competence_repo.deleteCompetence(skill_id);
        }
    }
}
