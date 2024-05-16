package com.example.resume_management.repositories.impl;

import com.example.resume_management.repositories.Cv_Compentece_Repo;
import org.springframework.beans.factory.annotation.*;
import com.example.resume_management.config.MyJDBC;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CvCompetenceRepo implements Cv_Compentece_Repo {

    @Autowired
    private MyJDBC myJDBC;

    public void LinkCvToCompetence(int 	id_cv,int id_competence) {
        String sql = "INSERT INTO `cv-competence` (id_cv,id_competence) VALUES (?,?)";

        int result = 0;

        try (Connection connection = myJDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id_cv);
            statement.setInt(2, id_competence);

            result = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Integer> getCompetencesIds(int id) {
        List<Integer> integerList = new ArrayList<>();
        String sqlStatement = "SELECT id_competence FROM `cv-competence` WHERE id_cv = ?";

        try (
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    integerList.add(resultSet.getInt("id_competence"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return integerList;
    }
}
