package com.example.resume_management.dto.requests;
import lombok.Data;
import com.example.resume_management.entities.*;
import java.util.List;

@Data
public class CvRequest {
    private String title;
    private Info infoPersonnel;
    private List<Entreprise> entreprises;
    private List<Competence> competences;
}
