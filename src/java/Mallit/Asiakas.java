package Mallit;

import Servletit.Yhteys;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;

/**
 *
 * @author leo
 */
public class Asiakas extends Kayttaja {

    public Asiakas(int id, String nimi, String tunnus, String salasana, String syntymaaika, String henkilotunnus, String osoite) throws NamingException  {
        super(id, nimi, tunnus, salasana, syntymaaika, henkilotunnus, osoite);
    }
    
    public Asiakas(ResultSet rs) throws SQLException {
        super.id = rs.getInt("id");
        super.nimi = rs.getString("nimi");
        super.tunnus = rs.getString("tunnus");
        super.syntymaaika = rs.getDate("syntymaaika").toString();
        super.henkilotunnus = rs.getString("henkilotunnus");
        super.osoite = rs.getString("osoite");
    }
    
    public Asiakas() {}
    
    /*Hakee tietokannasta asiakkaan käyttäjätunnuksen ja salasanan perusteella.*/
    public static Kayttaja etsiAsiakasTunnuksilla(String username, String password) throws NamingException, SQLException {
        String sql = "SELECT * FROM Asiakas WHERE tunnus = ? AND salasana = ?";
        Yhteys Tietokanta = new Yhteys();
        Connection yhteys = Tietokanta.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, username);
        kysely.setString(2, password);
        ResultSet rs = kysely.executeQuery();
       
        Kayttaja kirjautunut = null;
        if (rs.next()) {
            kirjautunut = new Kayttaja(rs);
        }

        try {
            kysely.close();
        } catch (Exception e) {
        }
        try { rs.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
        return kirjautunut;
    }
    
    public static Asiakas haeAsiakasIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT * FROM Asiakas WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        Asiakas a = null;
        if (rs.next()) {
            a = new Asiakas(rs);
        }
        try { rs.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
        return a;
    }

    /*Tallentaa kaikki tietokannan asiakas-käyttäjät listaan.*/
    public static List<Asiakas> getAsiakkaat() throws SQLException, NamingException {
        String sql = "SELECT * FROM Asiakas";
        Yhteys Tietokanta = new Yhteys();
        Connection yhteys = Tietokanta.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet rs = kysely.executeQuery();

        List<Asiakas> asiakkaat = new ArrayList<Asiakas>();
        while (rs.next()) {

            Asiakas a = new Asiakas(rs);

            asiakkaat.add(a);
        }
        try {
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            kysely.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            yhteys.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return asiakkaat;
    }
}
