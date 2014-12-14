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
        super(id, varattava_aika_id, lisaysajankohta, kuvaus);
    }
    
    public Oirekuvaus(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.varausId = rs.getInt("varaus_id");
        this.lisaysajankohta = rs.getTimestamp("lisaysajankohta");
        this.lisattavaTeksti = rs.getString("kuvaus");
    }
    
    public Oirekuvaus() {}
    
    public static Oirekuvaus haeOirekuvausVarausIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT * FROM Oirekuvaus WHERE Oirekuvaus.varaus_id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        Oirekuvaus o = null;
        if (rs.next()) {
            o = new Oirekuvaus(rs);
        }
        suljeResurssit(rs, kysely, yhteys);
        return o;
    }
    
    public static List<Oirekuvaus> haeOirekuvauksetAsiakasIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT Oirekuvaus.id, Oirekuvaus.varaus_id, Oirekuvaus.lisaysajankohta, Oirekuvaus.kuvaus FROM Oirekuvaus, Varaus, Kayttaja WHERE Oirekuvaus.varaus_id = Varaus.id AND Varaus.asiakas_id = Kayttaja.id AND Kayttaja.id = ? ORDER BY Oirekuvaus.varaus_id asc";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        List<Oirekuvaus> o = new ArrayList<Oirekuvaus>();
        while (rs.next()) {
            Oirekuvaus uusiOire = new Oirekuvaus(rs);
            o.add(uusiOire);
        }
        suljeResurssit(rs, kysely, yhteys);
        return o;
    }
    
    public void lisaaKuvausKantaan() throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "INSERT INTO Oirekuvaus(varaus_id, lisaysajankohta, kuvaus) VALUES(?, ?, ?) RETURNING id";
        suoritaLisays(yhteys, sql);
    }
    
    public static void poistaOirekuvaus(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "DELETE FROM Oirekuvaus WHERE Oirekuvaus.varaus_id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        kysely.executeUpdate();
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
    }
}
