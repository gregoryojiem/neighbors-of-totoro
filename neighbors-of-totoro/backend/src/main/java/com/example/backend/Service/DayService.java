package com.example.backend.Service;

import com.example.backend.Model.Day;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DayService {
    @Autowired
    DataSource dataSource;

    //CREATE
    public Object[] createDay(Day day) {
        String stmt = ("insert into day (start_time, end_time, date, timezone) " +
                "values('%tc', '%tc', '%tF', '%s')").formatted();
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            int rowsAffected = statement.executeUpdate(stmt, Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            UUID key = keys.getObject(5, UUID.class);
            return new Object[]{rowsAffected, key};
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
    public Day getDay(UUID dayID){
        String query = ("select * from day where day_id='%s'").formatted(dayID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(query);
            Day day = new Day();
            while(rs.next()){
                day.setDayID(rs.getObject("day_id", UUID.class));
                day.setStartTime(rs.getTimestamp("start_time"));
                day.setEndTime(rs.getTimestamp("end_time"));
                day.setDate(rs.getDate("date"));
                day.setTimeZone(rs.getString("timezone"));
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

    public Day getDayByEvent(UUID eventID){
        String query = ("").formatted(eventID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(query);
            Day day = new Day();
            while(rs.next()){
                day.setDayID(rs.getObject("day_id", UUID.class));
                day.setStartTime(rs.getTimestamp("start_time"));
                day.setEndTime(rs.getTimestamp("end_time"));
                day.setDate(rs.getDate("date"));
                day.setTimeZone(rs.getString("timezone"));
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

    public List<Day> getDays(){
        String query = "select * from day";
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(query);
            List<Day> days = new ArrayList<>();
            while(rs.next()){
                Day day = new Day();
                day.setDayID(rs.getObject("day_id", UUID.class));
                day.setStartTime(rs.getTimestamp("start_time"));
                day.setEndTime(rs.getTimestamp("end_time"));
                day.setDate(rs.getDate("date"));
                day.setTimeZone(rs.getString("timezone"));
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



    //UPDATE
    public int updateDay(UUID dayID, Day day) {
        String stmt = ("update day set start_time='%tc', end_time='%tc', " +
                "date='%tF', timezone='%s' where day_id='%s'").formatted(day.getStartTime(),
                day.getEndTime(), day.getDate(), day.getTimeZone(), dayID);
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
    public int deleteDay(UUID dayID) {
        String stmt = ("delete from day where day_id='%s'").formatted(dayID);
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
