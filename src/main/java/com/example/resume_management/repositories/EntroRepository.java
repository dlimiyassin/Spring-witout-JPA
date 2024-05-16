package com.example.resume_management.repositories;

import com.example.resume_management.entities.Cv;
import com.example.resume_management.entities.Entreprise;

import java.util.List;

public interface EntroRepository {

    public int save(Entreprise entreprise);

    public boolean entrepriseExistsByName(String name);

    public List<Integer> saveAll(List<Entreprise> entreprises);

    public Entreprise getEntrepriseById(int id);

    public void updateEntreprise(Entreprise entreprise);

    public void deleteEntreprise(int id);

    public int getIdByName(String name);

}
