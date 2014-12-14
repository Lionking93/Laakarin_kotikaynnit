package Mallit;

import Servletit.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author leo
 */
public class Paiva {
    
    private int id;
    private String paiva;
    
    public Paiva(int id, String paiva) {
        this.id = id;
        this.paiva = paiva;
    }

    public Paiva(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.paiva = rs.getString("paiva");
    }
    
    public Paiva() {}
    
    public int getId() {
        return this.id;
    }
    
    public String getPaiva() {
        return this.paiva;
    }
    
    public static Paiva haePaivaIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT * FROM Paiva WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        
        Paiva p = null;
        if (rs.next()) {
            p = new Paiva(rs);
        }
        try { rs.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
        return p;
    }
    
    public static List<Paiva> haePaivat() throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT * FROM Paiva";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet rs = kysely.executeQuery();
        
        List<Paiva> paivat = new ArrayList<Paiva>();
        while(rs.next()) {
            Paiva p = new Paiva(rs);
            paivat.add(p);
        }
        try { rs.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
        return paivat;
    }
} 

