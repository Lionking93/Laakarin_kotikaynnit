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

    public Potilasraportti(int id, int varattava_aika_id, Timestamp lisaysajankohta, String raportti) {
        super(id, varattava_aika_id, lisaysajankohta, raportti);
    }
    
    public Potilasraportti(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.varausId = rs.getInt("varaus_id");
        this.lisaysajankohta = rs.getTimestamp("lisaysajankohta");
        this.lisattavaTeksti = rs.getString("raportti");
    }

    public Potilasraportti() {}
    
    public static List<Potilasraportti> haePotilasraportitAsiakasIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT Potilasraportti.id, Potilasraportti.varaus_id, Potilasraportti.lisaysajankohta, Potilasraportti.raportti FROM Potilasraportti, Varaus, Kayttaja WHERE Potilasraportti.varaus_id = Varaus.id AND Varaus.asiakas_id = Kayttaja.id AND Kayttaja.id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        List<Potilasraportti> l = new ArrayList<Potilasraportti>();
        while (rs.next()) {
            Potilasraportti p = new Potilasraportti(rs);
            l.add(p);
        }
        suljeResurssit(rs, kysely, yhteys);
        return l;
    }
    
    public void lisaaKuvausKantaan() throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "INSERT INTO Potilasraportti(varaus_id, lisaysajankohta, raportti) VALUES(?, ?, ?) RETURNING id";
        suoritaLisays(yhteys, sql);
    }
}
