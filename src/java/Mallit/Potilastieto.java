package Mallit;

import Servletit.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author leo
 */
public class Potilastieto {

    protected int id;
    protected int varausId;
    protected Timestamp lisaysajankohta;
    protected String lisattavaTeksti;
    protected Map<String, String> virheet = new HashMap<String, String>();

    public Potilastieto(int id, int varattava_aika_id, Timestamp lisaysajankohta, String lisattavaTeksti) {
        this.id = id;
        this.varausId = varattava_aika_id;
        this.lisaysajankohta = lisaysajankohta;
        this.lisattavaTeksti = lisattavaTeksti;
    }

    public Potilastieto() {
    }

    public int getId() {
        return this.id;
    }
    
    public int getVarausId() {
        return this.varausId;
    }

    public Timestamp getLisaysajankohta() {
        return this.lisaysajankohta;
    }

    public String getLisattavaTeksti() {
        return this.lisattavaTeksti;
    }

    public void setId(int uusiId) {
        this.id = uusiId;
    }
    
    public void setVarausId(int uusiId) {
        this.varausId = uusiId;
    }

    public void setLisaysajankohta(Timestamp uusiLisaysajankohta) {
        this.lisaysajankohta = uusiLisaysajankohta;
    }

    public void setLisattavaTeksti(String uusiLisattavaTeksti) {
        this.lisattavaTeksti = uusiLisattavaTeksti;

        if (uusiLisattavaTeksti.trim().length() == 0) {
            virheet.put("kuvaus", "Oireen kuvaus ei saa olla tyhjä.");
        } else if (uusiLisattavaTeksti.length() >= 4000) {
            virheet.put("kuvaus", "Antamasi kuvaus on liian pitkä.");
        } else {
            virheet.remove("kuvaus");
        }
    }

    public void suoritaLisays(Connection yhteys, String sql) throws SQLException {
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, this.getVarausId());
        kysely.setTimestamp(2, this.getLisaysajankohta());
        kysely.setString(3, this.getLisattavaTeksti());

        ResultSet rs = kysely.executeQuery();
        
        rs.next();
        this.id = rs.getInt(1);
        
        try { rs.close(); } catch (Exception e) {}
        try {
            kysely.close();
        } catch (Exception e) {
        }
        try {
            yhteys.close();
        } catch (Exception e) {
        }
    }

    public Collection<String> getVirheet() {
        return virheet.values();
    }

    public boolean onkoKelvollinen() {
        return virheet.isEmpty();
    }
    
    protected static void suljeResurssit(ResultSet rs, PreparedStatement kysely, Connection yhteys) {
        try { rs.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
    }
}
