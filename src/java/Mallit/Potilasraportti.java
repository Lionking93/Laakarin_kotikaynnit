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
public class Potilasraportti extends Potilastieto {

    public Potilasraportti(int id, int varattava_aika_id, int asiakas_id, Timestamp lisaysajankohta, String raportti) {
        super(id, varattava_aika_id, asiakas_id, lisaysajankohta, raportti);
    }
    
    public Potilasraportti(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.varattavaAikaId = rs.getInt("potilasraportti_varattava_aika_id");
        this.asiakas_id = rs.getInt("potilasraportti_asiakas_id");
        this.lisaysajankohta = rs.getTimestamp("lisaysajankohta");
        this.lisattavaTeksti = rs.getString("raportti");
    }

    public Potilasraportti() {}
    
    public static List<Potilasraportti> haePotilasraportitAsiakasIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT id, potilasraportti_varattava_aika_id, potilasraportti_asiakas_id, lisaysajankohta, raportti FROM Potilasraportti WHERE Potilasraportti_asiakas_id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        List<Potilasraportti> l = new ArrayList<Potilasraportti>();
        while (rs.next()) {
            Potilasraportti p = new Potilasraportti();
            p.setId(rs.getInt("id"));
            p.setVarattavaAikaId(rs.getInt("potilasraportti_varattava_aika_id"));
            p.setAsiakasId(rs.getInt("potilasraportti_asiakas_id"));
            p.setLisaysajankohta(rs.getTimestamp("lisaysajankohta"));
            p.setLisattavaTeksti(rs.getString("raportti"));
            l.add(p);
        }
        try { rs.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
        return l;
    }
    
    public void lisaaKuvausKantaan() throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "INSERT INTO Potilasraportti(potilasraportti_varattava_aika_id, potilasraportti_asiakas_id, lisaysajankohta, raportti) VALUES(?, ?, ?, ?) RETURNING id";
        suoritaLisays(yhteys, sql);
    }
}
