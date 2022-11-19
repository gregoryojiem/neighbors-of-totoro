package com.example.backend.Service;

import com.example.backend.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    DataSource dataSource;

    //CREATE
    public Object[] createUser(User user) {
        String stmt = ("INSERT INTO \"user\" (email, username, password) VALUES('%s', '%s', '%s')")
                .formatted(user.getEmail(), user.getUsername(), user.getPassword());
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            int rowsAffected = statement.executeUpdate(stmt, Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            UUID key = keys.getObject(4, UUID.class);
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
    public User getUser(UUID userID) {
        String stmt = "SELECT * FROM \"user\" WHERE user_id='%s'".formatted(userID);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try {
            Statement statement = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(stmt);
            User user = new User();
            while(rs.next()) {
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUserID(rs.getObject("user_id", UUID.class));
            }
            return user;
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
    public int updateUser(UUID userID, User user) {
        String stmt = ("UPDATE \"user\" SET email='%s', username='%s', password='%s' WHERE user_id='%s'")
                .formatted(user.getEmail(), user.getUsername(), user.getPassword(), userID);
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
    public int deleteUser(UUID userID) {
        String stmt = "DELETE FROM \"user\" WHERE user_id='%s'".formatted(userID);
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
