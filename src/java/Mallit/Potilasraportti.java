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

    public Potilasraportti(int id, Timestamp lisaysajankohta, String raportti) {
        super(id, lisaysajankohta, raportti);
    }
    
    public Potilasraportti(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.lisaysajankohta = rs.getTimestamp("lisaysajankohta");
        this.lisattavaTeksti = rs.getString("raportti");
    }

    public Potilasraportti() {}
    
    public static List<Potilasraportti> haePotilasraportitAsiakasIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT potilasraportti_id, lisaysajankohta, raportti FROM Potilasraportti WHERE Potilasraportti_id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        List<Potilasraportti> l = new ArrayList<Potilasraportti>();
        while (rs.next()) {
            Potilasraportti p = new Potilasraportti();
            p.setId(rs.getInt("potilasraportti_id"));
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
        String sql = "INSERT INTO Potilasraportti(potilasraportti_id, lisaysajankohta, raportti) VALUES(?, ?, ?)";
        suoritaLisays(yhteys, sql);
    }
}
