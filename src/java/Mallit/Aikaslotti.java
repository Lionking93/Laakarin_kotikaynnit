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
public class Aikaslotti {
    
    private int id;
    private String aikaslotti;
    
    public Aikaslotti(int id, String aikaslotti) {
        this.id = id;
        this.aikaslotti = aikaslotti;
    }

    public Aikaslotti(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.aikaslotti = rs.getString("aikaslotti");
    }
    
    public Aikaslotti() {}
    
    public int getId() {
        return this.id;
    }
    
    public String getAikaslotti() {
        return this.aikaslotti;
    }
    
    public static Aikaslotti haeAikaslottiIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT * FROM Aikaslotti WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        
        Aikaslotti a = null;
        if (rs.next()) {
            a = new Aikaslotti(rs);
        }
        try { rs.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
        return a;
    }
    
    public static List<Aikaslotti> haeAikaslotit() throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT * FROM Aikaslotti";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet rs = kysely.executeQuery();
        
        List<Aikaslotti> aikaslotit = new ArrayList<Aikaslotti>();
        while(rs.next()) {
            Aikaslotti a = new Aikaslotti(rs);
            aikaslotit.add(a);
        }
        try { rs.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
        return aikaslotit;
    }
}
