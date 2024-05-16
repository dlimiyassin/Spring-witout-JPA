package com.example.resume_management.repositories.impl;

import com.example.resume_management.config.MyJDBC;
import com.example.resume_management.entities.Cv;
import com.example.resume_management.repositories.CVRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class CvRepository implements CVRepository {

    @Autowired
    private MyJDBC myJDBC;

    public int save(Cv cv) {
        String sql = "INSERT INTO cv (title) VALUES (?)";

        int generatedId=0;
        try (Connection connection = myJDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, cv.getTitle());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating cv failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = (int) generatedKeys.getLong(1);
                    System.out.println("cv created with id :"+ generatedId);
                }
                else {
                    throw new SQLException("Creating cv failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }


    public Cv getCvById(int id){
        Cv cv = new Cv();
        String sqlStatement = "SELECT * FROM cv WHERE id = ?";
        try(
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cv.setId(resultSet.getInt("id"));
                    cv.setTitle(resultSet.getString("title"));
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cv;
    }


    public List<Cv> getAllCv(){
        List<Cv> cvs = new ArrayList<>();
        String sqlStatement = "SELECT * FROM cv";
        try(
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Cv cv = new Cv();
                    cv.setId(resultSet.getInt("id"));
                    cv.setTitle(resultSet.getString("title"));
                    cvs.add(cv);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cvs;
    }


    public void updateCv(Cv cv){
        String sqlStatement = "UPDATE cv SET title = ? WHERE id = ?";
        try(
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            statement.setString(1, cv.getTitle());
            statement.setInt(2, cv.getId());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCv(int id){
        String sqlStatement = "DELETE FROM cv WHERE id = ?";
        try(
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
