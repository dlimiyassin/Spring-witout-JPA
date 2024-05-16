package com.example.resume_management.dto.responses;
import lombok.Data;
import com.example.resume_management.entities.*;
import java.util.List;

@Data
public class CvResponse {
    int id;
    private String title;
    private InfoResponse infoPersonnel;
    private List<EntrepriseResponse> entreprises;
    private List<CompetenceResponse> competences;
}
