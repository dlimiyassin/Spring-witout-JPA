package com.example.resume_management.repositories.impl;

import com.example.resume_management.entities.Competence;
import com.example.resume_management.entities.Entreprise;
import com.example.resume_management.repositories.CompetenceRepository;
import org.springframework.beans.factory.annotation.*;
import com.example.resume_management.config.MyJDBC;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Competence_Repository implements CompetenceRepository {

    @Autowired
    private MyJDBC myJDBC;

    public int save(Competence competence) {
        String sql = "INSERT INTO competence (name,level) VALUES (?,?)";

        int generatedId=0;
        try (Connection connection = myJDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, competence.getName());
            statement.setInt(2, competence.getLevel());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating competence failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = (int) generatedKeys.getLong(1);
                    System.out.println("Competence created with id :"+ generatedId);
                }
                else {
                    throw new SQLException("Creating competence failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public List<Integer> saveAll(List<Competence> competences) {
        List<Integer> longList = new ArrayList<>();

        for (Competence competence : competences) {
            longList.add(save(competence));
        }

        return longList;
    }

    public Competence getCompetenceById(int id){
        Competence competence = new Competence();
        String sqlStatement = "SELECT * FROM competence WHERE id = ?";
        try(
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    competence.setId(resultSet.getInt("id"));
                    competence.setName(resultSet.getString("name"));
                    competence.setLevel(resultSet.getInt("level"));
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return competence;
    }


    public void updateCompetence(Competence competence) {
        String sqlStatement = "UPDATE competence SET name = ?, level = ? WHERE id = ?";
        try (
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement)
        ) {
            statement.setString(1, competence.getName());
            statement.setInt(2, competence.getLevel());
            statement.setInt(3, competence.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Updating com failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating com with ID: " + competence.getId(), e);
        }
    }
    public void deleteCompetence(int id_competence){
        String sqlStatement = "DELETE FROM competence WHERE id = ?";
        try(
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            statement.setInt(1, id_competence);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean skillExistsByName(String name) {
        String sql = "SELECT 1 FROM competence WHERE name = ?";
        try (Connection connection = myJDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Returns true if a record exists, false otherwise
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // In case of an exception, assume entreprise does not exist
        }
    }

    public int getIdByName(String name){
        String sql = "SELECT id FROM competence WHERE name = ?";
        try(Connection connection = myJDBC.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,name);
            try(ResultSet rs = statement.executeQuery()){
                if (rs.next()){
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}
