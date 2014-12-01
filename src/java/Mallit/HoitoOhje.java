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
    
    public HoitoOhje(int id, Timestamp lisaysajankohta, String ohje) {
        super(id, lisaysajankohta, ohje);
    }
    
    public HoitoOhje() {}
    
    public void lisaaKuvausKantaan() throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "INSERT INTO Hoito_ohje(hoito_ohje_id, lisaysajankohta, ohje) VALUES(?, ?, ?)";
        suoritaLisays(yhteys, sql);
    }
    
    public static List<HoitoOhje> haeHoitoOhjeetAsiakasIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT hoito_ohje_id, lisaysajankohta, ohje FROM Hoito_ohje WHERE Hoito_ohje_id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        List<HoitoOhje> l = new ArrayList<HoitoOhje>();
        while (rs.next()) {
            HoitoOhje p = new HoitoOhje();
            p.setId(rs.getInt("hoito_ohje_id"));
            p.setLisaysajankohta(rs.getTimestamp("lisaysajankohta"));
            p.setLisattavaTeksti(rs.getString("ohje"));
            l.add(p);
        }
        try { rs.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
        return l;
    }
}
