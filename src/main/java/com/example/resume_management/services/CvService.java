package com.example.resume_management.services;

import com.example.resume_management.dto.requests.CvRequest;
import com.example.resume_management.dto.responses.CvResponse;
import com.example.resume_management.entities.Cv;

import java.util.List;

public interface CvService {

    CvResponse addCv(CvRequest cv);

    CvResponse GetCv(int id);

    void editCv(Cv cv);

    void deleteCv(int id);

    List<CvResponse> GetAllCv();
}
