package com.example.resume_management.services;

import com.example.resume_management.dto.requests.CvRequest;
import com.example.resume_management.dto.requests.CvUpdate;
import com.example.resume_management.dto.responses.CvResponse;

import java.util.List;

public interface CvService {

    CvResponse addCv(CvRequest cv);

    CvResponse GetCv(int id);

    List<CvResponse> GetAllCv();

    CvResponse editCv(CvUpdate cv);

    void deleteCv(int id);

}
