package com.example.resume_management.repositories;

import java.util.List;

public interface Cv_Entro_Reop {
    public void LinkCvToEntroprise(int 	id_cv,int id_entreprise);

    public List<Integer> getEntreprisesIds(int id);
}
