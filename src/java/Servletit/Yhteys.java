package Servletit;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *Luokka, joka luo yhteyden tietokantaan
 * @author leo
 */
public class Yhteys {

    private DataSource yhteysVarasto;
    
    public Yhteys() throws NamingException, SQLException {

        try {
            InitialContext cxt = new InitialContext();
            yhteysVarasto = (DataSource) cxt.lookup("java:/comp/env/jdbc/tietokanta");
        } catch (NamingException e) {
            System.out.println(e);
        }
    }
    
    public Connection getYhteys() throws SQLException {
        Connection yhteys = yhteysVarasto.getConnection();
        return yhteys;
    }
    
    public void suljeYhteys(Connection yhteys) {
        try {
            yhteys.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}