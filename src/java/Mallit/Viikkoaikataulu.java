package Mallit;

import Servletit.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author leo
 */
public class Viikkoaikataulu {
    
    private int id;
    private String paiva;
    private int aikaslotti;
    
    public Viikkoaikataulu(int id, String paiva, int aikaslotti) {
        this.id = id;
        this.paiva = paiva;
        this.aikaslotti = aikaslotti;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getPaiva() {
        return this.paiva;
    }
    
    public int getAikaslotti() {
        return this.aikaslotti;
    }
    
    public void setId(int uusiId) {
        this.id = uusiId;
    }
    
    public void setPaiva(String uusiPaiva) {
        this.paiva = uusiPaiva;
    }
    
    public void setAikaslotti(int uusiAikaslotti) {
        this.aikaslotti = uusiAikaslotti;
    }
    
    public void luoUusiAika(String paiva, int slotti) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "INSERT INTO Aika VALUES(?, ?)";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, paiva);
        kysely.setInt(2, slotti);
        kysely.executeUpdate();
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
    }
}
