package com.groupn.database.sql_request;

import com.groupn.database.DBManager;
import com.groupn.entities.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface AuthorSQL {
    default Author getAuthorById(int authorId, DBManager manager) {
        try {
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
}
