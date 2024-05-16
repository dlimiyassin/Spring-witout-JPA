package com.example.resume_management.entities;
import lombok.Data;

import java.util.List;

@Data
public class Cv {
    int id;
    private String title;
    private Info infoPersonnel;
    private List<Entreprise> entreprises;
    private List<Competence> competences;
}
