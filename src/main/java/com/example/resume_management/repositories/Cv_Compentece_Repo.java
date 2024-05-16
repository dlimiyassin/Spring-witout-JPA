package com.example.resume_management.repositories;

import java.util.List;

public interface Cv_Compentece_Repo {

    public void LinkCvToCompetence(int 	id_cv,int id_competence);

    public List<Integer> getCompetencesIds(int id);

    public void deleteRelation(int id_cv);
}
