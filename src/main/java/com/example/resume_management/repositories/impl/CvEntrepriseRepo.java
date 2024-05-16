package com.example.resume_management.repositories.impl;

import com.example.resume_management.entities.Cv;
import com.example.resume_management.entities.CvEntreprise;
import com.example.resume_management.repositories.Cv_Entro_Reop;
import org.springframework.beans.factory.annotation.*;
import com.example.resume_management.config.MyJDBC;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class CvEntrepriseRepo implements Cv_Entro_Reop {
    
    @Autowired
    private MyJDBC myJDBC;

    public void LinkCvToEntroprise(int 	id_cv,int id_entreprise) {
        String sql = "INSERT INTO `cv-entreprise` (id_cv,id_entreprise) VALUES (?,?)";

        int result = 0;

        try (Connection connection = myJDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id_cv);
            statement.setInt(2, id_entreprise);

            result = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Integer> getEntreprisesIds(int id) {
        List<Integer> integerList = new ArrayList<>();
        String sqlStatement = "SELECT id_entreprise FROM `cv-entreprise` WHERE id_cv = ?";

        try (
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    integerList.add(resultSet.getInt("id_entreprise"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return integerList;
    }

    public void deleteRelation(int id_cv){
        String sqlStatement = "DELETE FROM `cv-entreprise` WHERE id_cv = ?";
        try(
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            statement.setInt(1, id_cv);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
