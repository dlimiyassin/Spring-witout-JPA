package com.example.resume_management.entities;

import lombok.Data;

import java.util.List;

@Data
public class Competence {
    private int id;
    private String name ;
    private int level;
    private List<Cv> cvs;
}
