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
        
        Kayttaja kirjautunut = haeKayttaja(kysely, yhteys);

        try {
            kysely.close();
        } catch (Exception e) {
        }
        return kirjautunut;
    }

    /*Tallentaa kaikki tietokannan asiakas-käyttäjät listaan.*/
    public static List<Asiakas> getAsiakkaat() throws SQLException, NamingException {
        String sql = "SELECT id, nimi, tunnus, salasana, syntymaaika, henkilotunnus, osoite FROM Asiakas";
        Yhteys Tietokanta = new Yhteys();
        Connection yhteys = Tietokanta.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet rs = kysely.executeQuery();

        List<Asiakas> asiakkaat = new ArrayList<Asiakas>();
        while (rs.next()) {

            Asiakas a = new Asiakas();
            a.setId(rs.getInt("id"));
            a.setNimi(rs.getString("nimi"));
            a.setSalasana(rs.getString("salasana"));
            a.setSyntymaaika(rs.getDate("syntymaaika").toString());
            a.setHenkilotunnus(rs.getString("henkilotunnus"));
            a.setOsoite(rs.getString("osoite"));

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
