package com.example.backend.Service;

import com.example.backend.Model.Day;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DayService {
    @Autowired
    DataSource dataSource;

    //CREATE
    public Day createDay(Day day) {
        String stmt = ("INSERT INTO day (start_time, end_time, date, timezone) VALUES()").formatted();
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
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
        return new Day();
    }
    //READ


    //UPDATE

    //DELETE
}
