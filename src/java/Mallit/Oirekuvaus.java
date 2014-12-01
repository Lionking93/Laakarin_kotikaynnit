package Mallit;

import Servletit.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author leo
 */
public class Oirekuvaus {
    
    private int id;
    private Timestamp lisaysajankohta;
    private String kuvaus;
    private Map<String, String> virheet = new HashMap<String, String>();
    
    public Oirekuvaus(int id, Timestamp lisaysajankohta, String kuvaus) {
        this.id = id;
        this.lisaysajankohta = lisaysajankohta;
        this.kuvaus = kuvaus;
    }
    
    public Oirekuvaus(ResultSet rs) throws SQLException {
        this.id = rs.getInt("oirekuvaus_id");
        this.lisaysajankohta = rs.getTimestamp("lisaysajankohta");
        this.kuvaus = rs.getString("kuvaus");
    }
    
    public Oirekuvaus() {}
    
    public int getId() {
        return this.id;
    }
    
    public Timestamp getLisaysajankohta() {
        return this.lisaysajankohta;
    }
    
    public String getKuvaus() {
        return this.kuvaus;
    }
    
    public void setId(int uusiId) {
        this.id = uusiId;
    }
    
    public void setLisaysajankohta(Timestamp uusiLisaysajankohta) {
        this.lisaysajankohta = uusiLisaysajankohta;
    }
    
    public void setKuvaus(String uusiKuvaus) {
        this.kuvaus = uusiKuvaus;
        
        if (uusiKuvaus.trim().length() == 0) {
            virheet.put("kuvaus", "Oireen kuvaus ei saa olla tyhjä.");
        } else if (uusiKuvaus.length() >= 4000) {
            virheet.put("kuvaus", "Antamasi kuvaus on liian pitkä.");
        } else {
            virheet.remove("kuvaus");
        }
    }
    
    public static Oirekuvaus haeOirekuvausVarattavaAikaIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT oirekuvaus.oirekuvaus_id, oirekuvaus.lisaysajankohta, oirekuvaus.kuvaus FROM oirekuvaus, varattava_aika WHERE oirekuvaus.oirekuvaus_id = varattava_aika.id AND varattava_aika.id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        Oirekuvaus o = null;
        if (rs.next()) {
            o = new Oirekuvaus(rs);
        }
        try { rs.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
        return o;
    }
    
    public void lisaaKuvausKantaan() throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "INSERT INTO Oirekuvaus(oirekuvaus_id, lisaysajankohta, kuvaus) VALUES(?, ?, ?)";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, this.getId());
        kysely.setTimestamp(2, this.getLisaysajankohta());
        kysely.setString(3, this.getKuvaus());
        
        kysely.executeUpdate();
        
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
    }
    
    public Collection<String> getVirheet() {
        return virheet.values();
    }
    
    public boolean onkoKelvollinen() {
        return virheet.isEmpty();
    }
}
