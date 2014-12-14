package Mallit;

import Servletit.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author leo
 */
public class HoitoOhje extends Potilastieto {
    
    public HoitoOhje(int id, int varattava_aika_id, int asiakas_id, Timestamp lisaysajankohta, String ohje) {
        super(id, varattava_aika_id, lisaysajankohta, ohje);
    }
    
    public HoitoOhje(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.varausId = rs.getInt("varaus_id");
        this.lisaysajankohta = rs.getTimestamp("lisaysajankohta");
        this.lisattavaTeksti = rs.getString("ohje");
    }
    
    public HoitoOhje() {}
    
    public void lisaaKuvausKantaan() throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "INSERT INTO Hoito_ohje(varaus_id, lisaysajankohta, ohje) VALUES(?, ?, ?) RETURNING id";
        suoritaLisays(yhteys, sql);
    }
    
    public static List<HoitoOhje> haeHoitoOhjeetAsiakasIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT Hoito_ohje.id, Hoito_ohje.varaus_id, Hoito_ohje.lisaysajankohta, Hoito_ohje.ohje FROM Hoito_ohje, Varaus, Kayttaja WHERE Hoito_ohje.varaus_id = Varaus.id AND Varaus.asiakas_id = Kayttaja.id AND Kayttaja.id = ? ORDER BY Hoito_ohje.varaus_id asc";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        List<HoitoOhje> l = new ArrayList<HoitoOhje>();
        while (rs.next()) {
            HoitoOhje p = new HoitoOhje(rs);
            l.add(p);
        }
        suljeResurssit(rs, kysely, yhteys);
        return l;
    }
    
    public static HoitoOhje haeHoitoOhjeVarausIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT * FROM Hoito_ohje WHERE Hoito_ohje.varaus_id = ? ORDER BY hoito_ohje.varaus_id asc";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        HoitoOhje p = null;
        if (rs.next()) {
            p = new HoitoOhje(rs);
        }
        suljeResurssit(rs, kysely, yhteys);
        return p;
    }
    
    public static void poistaHoitoOhje(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "DELETE From Hoito_ohje WHERE Hoito_ohje.id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        kysely.executeUpdate();
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
    }
}
