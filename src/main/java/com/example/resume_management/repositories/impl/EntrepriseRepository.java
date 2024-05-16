package com.example.resume_management.repositories.impl;
import com.example.resume_management.config.MyJDBC;
import com.example.resume_management.entities.Entreprise;
import com.example.resume_management.repositories.EntroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class EntrepriseRepository implements EntroRepository {

    @Autowired
    private MyJDBC myJDBC;
    public int save(Entreprise entreprise) {
        String sql = "INSERT INTO entreprise (name) VALUES (?)";

        int generatedId=0;
        try (Connection connection = myJDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entreprise.getName());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating entreprise failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = (int) generatedKeys.getLong(1);
                    System.out.println("Entreprise created with id :"+ generatedId);
                }
                else {
                    throw new SQLException("Creating entreprise failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public List<Integer> saveAll(List<Entreprise> entreprises) {
        List<Integer> longList = new ArrayList<>();

        for (Entreprise entreprise : entreprises) {
            longList.add(save(entreprise));
        }

        return longList;
    }

    public Entreprise getEntrepriseById(int id){
        Entreprise entreprise = new Entreprise();
        String sqlStatement = "SELECT * FROM entreprise WHERE id = ?";
        try(
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    entreprise.setId(resultSet.getInt("id"));
                    entreprise.setName(resultSet.getString("name"));
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entreprise;
    }


    public void updateEntreprise(Entreprise entreprise){
        String sqlStatement = "UPDATE entreprise SET name = ? WHERE id = ?";
        try(
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            statement.setString(1, entreprise.getName());
            statement.setInt(2, entreprise.getId());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEntreprise(int id){
        String sqlStatement = "DELETE FROM entreprise WHERE id = ?";
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
