package Superlaskuttaja.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Augustus58
 */
public class User {

    private int id;
    private String username;
    private String password;

    public User() {
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static User searchUserByIDs(String kayttaja, String salasana) throws NamingException, SQLException {
        String sql = "SELECT id, username, password from users where username = ? AND password = ?";
        DBConnection dbc = new DBConnection();
        Connection conn = dbc.getConnection();
        PreparedStatement query = conn.prepareStatement(sql);
        query.setString(1, kayttaja);
        query.setString(2, salasana);
        ResultSet rs = query.executeQuery();

        User loggedIn = null;

        if (rs.next()) {
            loggedIn = new User();
            loggedIn.setId(rs.getInt("id"));
            loggedIn.setUsername(rs.getString("username"));
            loggedIn.setPassword(rs.getString("password"));
        }

        try {
            rs.close();
        } catch (Exception e) {
        }
        try {
            query.close();
        } catch (Exception e) {
        }
        try {
            conn.close();
        } catch (Exception e) {
        }

        return loggedIn;
    }

    public static List<User> getUsers() throws NamingException, SQLException {
        String sql = "SELECT id, username, password from users";
        DBConnection dbc = new DBConnection();
        Connection conn = dbc.getConnection();
        PreparedStatement que = conn.prepareStatement(sql);
        ResultSet rs = que.executeQuery();

        ArrayList<User> users = new ArrayList();
        while (rs.next()) {
            //Luodaan tuloksia vastaava olio ja palautetaan olio:
            User k = new User();
            k.setId(rs.getInt("id"));
            k.setUsername(rs.getString("username"));
            k.setPassword(rs.getString("password"));

            users.add(k);
        }

        try {
            rs.close();
        } catch (Exception e) {
        }
        try {
            que.close();
        } catch (Exception e) {
        }
        try {
            conn.close();
        } catch (Exception e) {
        }

        return users;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
