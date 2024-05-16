package com.example.resume_management.repositories;

import com.example.resume_management.entities.Cv;

import java.util.List;

public interface CVRepository {

    public int save(Cv cv);

    public Cv getCvById(int id);

    public List<Cv> getAllCv();

    public void updateCv(Cv cv);

    public void deleteCv(int id);

    public boolean existsById(int id);
    public boolean existsByFullName(String name);
}
