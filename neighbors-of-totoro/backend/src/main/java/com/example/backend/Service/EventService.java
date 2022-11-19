package com.example.backend.Service;

import com.example.backend.Model.TimeRange;
import com.example.backend.Model.Day;
import com.example.backend.Model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
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
        } catch (Exception e) {
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
    public int[] deleteEvent(UUID eventID) {
        String stmt1 = "DELETE ALL FROM event_has_day WHERE event_id='%s'".formatted(eventID);
        String stmt2 = "DELETE FROM event WHERE event_id='%s'".formatted(eventID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            int[] results = {statement.executeUpdate(stmt1), statement.executeUpdate(stmt2)};
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new int[2];
    }

    //event_has_day RELATIONSHIP
    //CREATE
    public Object[] createEventHasDay(UUID eventID, UUID dayID, TimeRange range){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        String stmt = ("INSERT INTO event_has_day (event_id, day_id, start_time, end_time)" +
                " VALUES('%s', '%s', '%tc', '%tc')").formatted(eventID, dayID, range.getStartTime(), range.getEndTime());
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            int rowsAffected = statement.executeUpdate(stmt, Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            UUID eventKey = keys.getObject(3, UUID.class);
            UUID dayKey = keys.getObject(4, UUID.class);
            Object[] results = {rowsAffected, eventKey, dayKey};
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Object[3];
    }
    //READ
    public Day getDayInEvent(UUID eventID, UUID dayID) {
        String query = ("select * from day " +
                "inner join event_has_day ehd on day.day_id = ehd.day_id " +
                "where ehd.event_id='%s' and ehd.day_id='%s'").formatted(eventID, dayID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(query);
            Day day = new Day();
            while(rs.next()) {
                day.setDayID(rs.getObject("day_id", UUID.class));
                day.setStartTime(rs.getTimestamp("start_time"));
                day.setEndTime(rs.getTimestamp("end_time"));
                day.setDate(rs.getDate("date"));
                day.setTimezone(rs.getString("timezone"));
            }
            return day;
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

    public TimeRange getTimeRangeByDayInEvent(UUID eventID, UUID dayID) {
        String query = ("select start_time, end_time from event_has_day ehd " +
                "where ehd.event_id='%s' and ehd.day_id='%s'").formatted(eventID, dayID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(query);
            TimeRange range = new TimeRange();
            while(rs.next()) {
                range.setStartTime(rs.getTimestamp("start_time"));
                range.setEndTime(rs.getTimestamp("end_time"));
            }
            return range;
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

    public List<Day> getAllDaysInEvent(UUID eventID) {
        String query = ("select * from day " +
                "inner join event_has_day ehd on day.day_id = ehd.day_id " +
                "where ehd.event_id='%s'").formatted(eventID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(query);
            List<Day> days = new ArrayList<>();
            while(rs.next()) {
                Day day = new Day();
                day.setDayID(rs.getObject("day_id", UUID.class));
                day.setStartTime(rs.getTimestamp("start_time"));
                day.setEndTime(rs.getTimestamp("end_time"));
                day.setDate(rs.getDate("date"));
                day.setTimezone(rs.getString("timezone"));
                days.add(day);
            }
            return days;
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
    //DELETE
    public int deleteEventHasDay(UUID eventID, UUID dayID) {
        String stmt = ("DELETE FROM event_has_day " +
                "WHERE event_id='%s' and day_id='%s'").formatted(eventID, dayID);
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
