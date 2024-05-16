package com.example.resume_management.repositories;

import com.example.resume_management.entities.Competence;

import java.util.List;

public interface CompetenceRepository {

    public int save(Competence competence);

    public List<Integer> saveAll(List<Competence> competences);

    public Competence getCompetenceById(int id);

    public void updateCompetence(Competence competence);

    public boolean skillExistsByName(String name);

    public int getIdByName(String name);

    public void deleteCompetence(int id_competence);
}
