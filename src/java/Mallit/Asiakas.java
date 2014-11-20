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

    public Asiakas(int id, String nimi, String tunnus, String salasana, String syntymaaika, String henkilotunnus, String osoite) throws NamingException {
        super.id = id;
        super.nimi = nimi;
        super.tunnus = tunnus;
        super.salasana = salasana;
        super.syntymaaika = syntymaaika;
        super.henkilotunnus = henkilotunnus;
        super.osoite = osoite;
    }

    public Asiakas() {
    }
    
    public static Asiakas etsiAsiakasTunnuksilla(String username, String password) throws NamingException, SQLException {
        String sql = "SELECT id, nimi, tunnus, salasana, syntymaaika, henkilotunnus, osoite FROM Asiakas WHERE tunnus = ? AND salasana = ?";
        Yhteys Tietokanta = new Yhteys();
        Connection yhteys = Tietokanta.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, username);
        kysely.setString(2, password);
        ResultSet rs = kysely.executeQuery();
        
        Asiakas kirjautunut = null;
        
        if (rs.next()) {
            kirjautunut = new Asiakas();
            kirjautunut.setId(rs.getInt("id"));
            kirjautunut.setNimi(rs.getString("nimi"));
            kirjautunut.setSalasana(rs.getString("salasana"));
            kirjautunut.setSyntymaaika(rs.getDate("syntymaaika").toString());
            kirjautunut.setHenkilotunnus(rs.getString("henkilotunnus"));
            kirjautunut.setOsoite(rs.getString("osoite"));
        }
        try {
            rs.close();
        } catch (Exception e) {
        }
        try {
            kysely.close();
        } catch (Exception e) {
        }
        try {
            yhteys.close();
        } catch (Exception e) {
        }
        return kirjautunut;
    }

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
