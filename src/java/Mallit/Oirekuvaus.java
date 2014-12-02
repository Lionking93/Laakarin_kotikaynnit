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
    
    public Oirekuvaus(int id, int varattava_aika_id, int asiakas_id, Timestamp lisaysajankohta, String kuvaus) {
        super(id, varattava_aika_id, asiakas_id, lisaysajankohta, kuvaus);
    }
    
    public Oirekuvaus(ResultSet rs) throws SQLException {
        super.id = rs.getInt("id");
        super.varattavaAikaId = rs.getInt("oirekuvaus_varattava_aika_id");
        super.asiakas_id = rs.getInt("oirekuvaus_asiakas_id");
        super.lisaysajankohta = rs.getTimestamp("lisaysajankohta");
        super.lisattavaTeksti = rs.getString("kuvaus");
    }
    
    public Oirekuvaus() {}
    
    public static Oirekuvaus haeOirekuvausVarattavaAikaIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT oirekuvaus.id, oirekuvaus.oirekuvaus_varattava_aika_id, oirekuvaus.oirekuvaus_asiakas_id, oirekuvaus.lisaysajankohta, oirekuvaus.kuvaus FROM oirekuvaus, varattava_aika WHERE oirekuvaus.oirekuvaus_varattava_aika_id = varattava_aika.id AND varattava_aika.id = ?";
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
        String sql = "SELECT Oirekuvaus.id, Oirekuvaus.oirekuvaus_varattava_aika_id, Oirekuvaus.oirekuvaus_asiakas_id, oirekuvaus.lisaysajankohta, oirekuvaus.kuvaus FROM Oirekuvaus, Asiakas WHERE Oirekuvaus.oirekuvaus_asiakas_id = Asiakas.id AND Asiakas.id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        List<Oirekuvaus> o = new ArrayList<Oirekuvaus>();
        while (rs.next()) {
            Oirekuvaus uusiOire = new Oirekuvaus();
            uusiOire.setId(rs.getInt("id"));
            uusiOire.setVarattavaAikaId(rs.getInt("oirekuvaus_varattava_aika_id"));
            uusiOire.setAsiakasId(rs.getInt("oirekuvaus_asiakas_id"));
            uusiOire.setLisaysajankohta(rs.getTimestamp("lisaysajankohta"));
            uusiOire.setLisattavaTeksti(rs.getString("kuvaus"));
            o.add(uusiOire);
        }
        return o;
    }
    
    public void lisaaKuvausKantaan() throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "INSERT INTO Oirekuvaus(oirekuvaus_varattava_aika_id, oirekuvaus_asiakas_id, lisaysajankohta, kuvaus) VALUES(?, ?, ?, ?) RETURNING id";
        suoritaLisays(yhteys, sql);
    }
    
    public static void poistaOirekuvaus(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "DELETE FROM Oirekuvaus WHERE Oirekuvaus.oirekuvaus_varattava_aika_id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        kysely.executeUpdate();
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
    }
}
