package com.groupn.database.mapper;

import com.groupn.database.DBManager;
import com.groupn.entities.*;
import lombok.Getter;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class EntityMapper {
    private final DBManager dbManager;

    public EntityMapper(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public Location mapResultSetToLocation(ResultSet resultSet) throws SQLException {
        int locationId = resultSet.getInt("location_id");
        String locationName = resultSet.getString("location_name");
        String locationDescription = resultSet.getString("location_description");

        LocalDate dateOfOpening = null;
        Date dateOfOpeningSql = resultSet.getDate("date_of_opening");
        if (dateOfOpeningSql != null) {
            dateOfOpening = dateOfOpeningSql.toLocalDate();
        }

        String placement = resultSet.getString("placement");
        LocationType locationType = dbManager.getLocationTypeById(resultSet.getInt("location_type_id"));

        return new Location(locationId, locationName, locationDescription, dateOfOpening, placement, locationType);
    }

    public Owner mapResultSetToOwner(ResultSet resultSet) throws SQLException {
        return new Owner(
                resultSet.getInt("owner_id"),
                resultSet.getString("owner_name"),
                resultSet.getString("owner_description")
        );
    }

    public ArtObject mapResultSetToArtObject(ResultSet resultSet) throws SQLException {
        String date = resultSet.getString("date_of_creation");
        return new ArtObject(
                resultSet.getInt("art_object_id"),
                resultSet.getString("art_object_name"),
                resultSet.getString("art_object_description"),
                date,
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
        LocalDate dateOfBirth = null;
        String date = resultSet.getString("author_date_of_birth");
        if (date != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dateOfBirth = LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                dbManager.getLogger().info(" Author date of birth cannot be converted into normal date.");
            }
        }
        return new Author(
                resultSet.getInt("author_id"),
                resultSet.getString("author_name"),
                resultSet.getString("author_description"),
                date
        );
    }

    public Event mapResultSetToEvent(ResultSet resultSet) throws SQLException {
        List<EventObjects> eventObjectsList = dbManager.getAllEventObjects().stream().filter(x -> {
            try {
                return x.getEventId() == resultSet.getInt("event_id");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        List<ArtObject> artObjectList = new ArrayList<>();
        for(EventObjects eventObjects1: eventObjectsList){
            artObjectList.add(dbManager.getArtObjectById(eventObjects1.getArtObjectId()));
        }
        return new Event(
                resultSet.getInt("event_id"),
                resultSet.getString("event_name"),
                dbManager.getEventTypeById(resultSet.getInt("event_type_id")),
                resultSet.getString("event_description"),
                resultSet.getString("event_start_date"),
                resultSet.getString("event_end_date"),
                mapResultSetToLocation(resultSet),
                resultSet.getInt("event_price"),
                artObjectList
        );
    }

}
