package Superlaskuttaja.Models;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Augustus58
 */
public class DBConnection {
    
    private final InitialContext cxt;
    private final DataSource yhteysVarasto;

    public DBConnection() throws NamingException {
        this.cxt = new InitialContext();
        this.yhteysVarasto = (DataSource) cxt.lookup("java:/comp/env/jdbc/tietokanta");
    }
    
    public Connection getConnection() throws SQLException {
        return yhteysVarasto.getConnection();
    }
    
}
