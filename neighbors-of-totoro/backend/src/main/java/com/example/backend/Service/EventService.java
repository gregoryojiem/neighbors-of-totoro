package com.example.backend.Service;

import com.example.backend.Model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    DataSource dataSource;

    //CREATE
    public Object[] createEvent(Event event) {
        String stmt = ("INSERT INTO event (title) VALUES('%s')")
                .formatted(event.getTitle());
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            int rowsAffected = statement.executeUpdate(stmt, Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            UUID key = keys.getObject(2, UUID.class);
            Object[] results = {rowsAffected, key};
            return results;
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Object[2];
    }

    //READ
    public Event getEvent(UUID eventID) {
        String stmt = "SELECT * FROM event WHERE event_id='%s'".formatted(eventID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            Event event = new Event();
            while(rs.next()) {
                event.setTitle(rs.getString("title"));
                event.setEventID(rs.getObject("event_id", UUID.class));
            }
            return event;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //UPDATE
    public int updateEvent(UUID eventID, Event event) {
        String stmt = ("UPDATE event SET title='%s' WHERE event_id='%s'")
                .formatted(event.getTitle(), eventID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    //DELETE
    public int deleteEvent(UUID eventID) {
        String stmt = "DELETE FROM event WHERE event_id='%s'".formatted(eventID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}
