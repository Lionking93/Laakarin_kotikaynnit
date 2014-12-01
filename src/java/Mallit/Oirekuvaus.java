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
public class Oirekuvaus extends Potilastieto {
    
    public Oirekuvaus(int id, Timestamp lisaysajankohta, String kuvaus) {
        super(id, lisaysajankohta, kuvaus);
    }
    
    public Oirekuvaus(ResultSet rs) throws SQLException {
        super.id = rs.getInt("oirekuvaus_id");
        super.lisaysajankohta = rs.getTimestamp("lisaysajankohta");
        super.lisattavaTeksti = rs.getString("kuvaus");
    }
    
    public Oirekuvaus() {}
    
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
    
    public static List<Oirekuvaus> haeOirekuvauksetAsiakasIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT DISTINCT Oirekuvaus_id, lisaysajankohta, kuvaus FROM Oirekuvaus, Varattava_aika, Asiakas WHERE Varattava_aika.asiakas_id = Asiakas.id AND Asiakas.id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        List<Oirekuvaus> o = new ArrayList<Oirekuvaus>();
        while (rs.next()) {
            Oirekuvaus uusiOire = new Oirekuvaus();
            uusiOire.setId(rs.getInt("oirekuvaus_id"));
            uusiOire.setLisaysajankohta(rs.getTimestamp("lisaysajankohta"));
            uusiOire.setLisattavaTeksti(rs.getString("kuvaus"));
            o.add(uusiOire);
        }
        return o;
    }
    
    public void lisaaKuvausKantaan() throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "INSERT INTO Oirekuvaus(oirekuvaus_id, lisaysajankohta, kuvaus) VALUES(?, ?, ?)";
        suoritaLisays(yhteys, sql);
    }
}
