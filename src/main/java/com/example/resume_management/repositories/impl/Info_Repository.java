package com.example.resume_management.repositories.impl;

import com.example.resume_management.config.MyJDBC;
import com.example.resume_management.entities.Entreprise;
import com.example.resume_management.entities.Info;
import com.example.resume_management.repositories.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class Info_Repository implements InfoRepository {

    @Autowired
    private MyJDBC myJDBC;

    public int save(Info info) {
        String sql = "INSERT INTO info (fullName,age,id_cv) VALUES (?,?,?)";

        int generatedId=0;
        try (Connection connection = myJDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            System.out.println(info);
            statement.setString(1, info.getFullName());
            statement.setInt(2, info.getAge());
            statement.setInt(3, info.getId_cv());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating info failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = (int) generatedKeys.getLong(1);
                    System.out.println("info created with id :"+ generatedId);
                }
                else {
                    throw new SQLException("Creating info failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }


    public Info getInfoById(int id){
        Info info = new Info();
        String sqlStatement = "SELECT * FROM info WHERE id = ?";
        try(
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    info.setId(resultSet.getInt("id"));
                    info.setFullName(resultSet.getString("fullName"));
                    info.setAge(resultSet.getInt("age"));
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return info;
    }

    public Info getInfoByCvId(int id_cv){
        Info info = new Info();
        String sqlStatement = "SELECT * FROM info WHERE id_cv = ?";
        try(
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement))
        {
            statement.setInt(1, id_cv);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    info.setId(resultSet.getInt("id"));
                    info.setFullName(resultSet.getString("fullName"));
                    info.setAge(resultSet.getInt("age"));
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return info;
    }

    public void updateInfo(Info info, int id_cv) {
        String sqlStatement = "UPDATE info SET fullName = ?, age = ? WHERE id = ?";
        try (
                Connection connection = myJDBC.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement)
        ) {
            statement.setString(1, info.getFullName());
            statement.setInt(2, info.getAge());
            statement.setInt(3, id_cv);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Updating info failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating info with ID: " + info.getId(), e);
        }
    }
    public void deleteInfo(int id_cv){
        String sqlStatement = "DELETE FROM info WHERE id_cv = ?";
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
