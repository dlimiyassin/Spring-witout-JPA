package com.example.resume_management.entities;
import lombok.Data;
import java.util.List;
@Data
public class Entreprise {

    private int id;
    private String name;
    private List<Cv> cvs;
}
