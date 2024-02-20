package com.groupn.database.mapper;

import com.groupn.database.DBManager;
import com.groupn.entities.*;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class EntityMapper {
    private final DBManager dbManager;

    public EntityMapper(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public Location mapResultSetToLocation(ResultSet resultSet) throws SQLException {
        return new Location(
                resultSet.getInt("location_id"),
                resultSet.getString("location_name"),
                resultSet.getString("location_description"),
                resultSet.getDate("date_of_opening").toLocalDate(),
                resultSet.getString("placement"),
                dbManager.getLocationTypeById(resultSet.getInt("location_type_id"))
        );
    }

    public Owner mapResultSetToOwner(ResultSet resultSet) throws SQLException {
        return new Owner(
                resultSet.getInt("owner_id"),
                resultSet.getString("owner_name"),
                resultSet.getString("owner_description")
        );
    }

    public ArtObject mapResultSetToArtObject(ResultSet resultSet) throws SQLException {
        return new ArtObject(
                resultSet.getInt("art_object_id"),
                resultSet.getString("art_object_name"),
                resultSet.getString("art_object_description"),
                resultSet.getDate("date_of_creation").toLocalDate(),
                mapResultSetToAuthor(resultSet),
                mapResultSetToOwner(resultSet),
                mapResultSetToLocation(resultSet)
        );
    }

    public Purchase mapResultSetToPurchase(ResultSet resultSet) throws SQLException {
        return new Purchase(
                resultSet.getInt("purchase_id"),
                resultSet.getDate("purchase_date").toLocalDate(),
                resultSet.getInt("purchase_price"),
                dbManager.getArtObjectById(resultSet.getInt("art_object_id")),
                dbManager.getOwnerById(resultSet.getInt("seller_id")),
                dbManager.getOwnerById(resultSet.getInt("buyer_id"))
        );
    }

    public Author mapResultSetToAuthor(ResultSet resultSet) throws SQLException {
        return new Author(
                resultSet.getInt("author_id"),
                resultSet.getString("author_name"),
                resultSet.getString("author_description"),
                resultSet.getDate("author_date_of_birth").toLocalDate()
        );
    }

    public Event mapResultSetToEvent(ResultSet resultSet) throws SQLException {
        return new Event(
                resultSet.getInt("event_id"),
                resultSet.getString("event_name"),
                dbManager.getEventTypeById(resultSet.getInt("event_type_id")),
                resultSet.getString("event_description"),
                resultSet.getDate("event_date").toLocalDate(),
                mapResultSetToLocation(resultSet),
                resultSet.getInt("event_price")
        );
    }
}
