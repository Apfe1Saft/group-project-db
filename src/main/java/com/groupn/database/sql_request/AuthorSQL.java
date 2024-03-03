package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.ArtObject;
import com.groupn.entities.Author;
import com.groupn.entities.Purchase;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface AuthorSQL {
    default Author getAuthorById(int authorId, DBManager manager) {
        try {
            manager.getLogger().info(" RUN getAuthorById.");
            String sql = "SELECT * FROM Author WHERE author_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, authorId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {

                    return manager.getMapper().mapResultSetToAuthor(resultSet);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
        return null;
    }

    default List<Author> getAllAuthors(DBManager manager){
        List<Author> authors = new ArrayList<>();
        try {
            manager.getLogger().info(" RUN getAllAuthors.");
            String sql = "SELECT * FROM Author";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Author author = manager.getMapper().mapResultSetToAuthor(resultSet);
                    authors.add(author);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }

        return authors;
    }

    default Author getAuthorByArtObjectId(int artObjectId, DBManager manager){
        try {
            manager.getLogger().info(" RUN getAuthorById.");
            String sql = "SELECT * FROM Author WHERE author_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, artObjectId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {

                    return manager.getMapper().mapResultSetToAuthor(resultSet);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
        return null;
    }

    default void addAuthor(Author author, DBManager manager){
        try {
            manager.getLogger().info(" RUN addArtObject.");
            String sql = "INSERT INTO Author (author_name, author_description, author_date_of_birth) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, author.getName());
                preparedStatement.setString(2, author.getDescription());
                preparedStatement.setDate(3, Date.valueOf(author.getDateOfBirth()));
                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    author.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

    default List<Author> getAuthorsByFilter(String filter, DBManager manager) {
        List<Author> authors = new ArrayList<>();
        try {
            manager.getLogger().info("RUN getAuthorsByFilter.");

            String sql = "SELECT * FROM Author WHERE author_name LIKE ? OR author_description LIKE ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + filter + "%");
                preparedStatement.setString(2, "%" + filter + "%");

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Author author = manager.getMapper().mapResultSetToAuthor(resultSet);
                    authors.add(author);
                }
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }

        return authors;
    }
    default void updateAuthor(Author author, DBManager manager) {
        try {
            manager.getLogger().info("RUN updateAuthor.");

            String sql = "UPDATE Author " +
                    "SET author_name = ?, author_description = ?, author_date_of_birth = ? " +
                    "WHERE author_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, author.getName());
                preparedStatement.setString(2, author.getDescription());
                preparedStatement.setDate(3, Date.valueOf(author.getDateOfBirth()));
                preparedStatement.setInt(4, author.getId());
            }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }

    default void removeAuthor(int authorId, DBManager manager) {
        try {
            //! update ArtObject
            manager.getLogger().info("RUN removeAuthor.");

            String sql = "DELETE FROM Author WHERE author_id = ?";
            try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, authorId);

            for (ArtObject artObject : manager.getAllArtObjects().stream().filter(x -> x.getAuthor().getId() == authorId).collect(Collectors.toList())) {
                artObject.setAuthor(null);
                manager.updateArtObject(artObject);
            }

        }
        } catch (SQLException e) {
            manager.getLogger().severe("Error: " + e.getMessage());
        }
    }
}
