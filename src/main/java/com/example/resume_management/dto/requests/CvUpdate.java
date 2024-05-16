package com.example.resume_management.dto.requests;

import com.example.resume_management.entities.Competence;
import com.example.resume_management.entities.Entreprise;
import com.example.resume_management.entities.Info;
import lombok.Data;

import java.util.List;
@Data
public class CvUpdate {
    private int id;
    private String title;
    private Info infoPersonnel;
    private List<Entreprise> entreprises;
    private List<Competence> competences;
}
