package Mallit;

import Servletit.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author leo
 */
public class Laakari extends Kayttaja {

    public Laakari(int id, String nimi, String tunnus, String salasana) {
        super.id = id;
        super.nimi = nimi;
        super.tunnus = tunnus;
        super.salasana = salasana;
    }

    public Laakari() {

    }

    public static Laakari etsiLaakariTunnuksilla(String username, String password) throws NamingException, SQLException {
        String sql = "SELECT id, nimi, tunnus, salasana FROM laakari WHERE tunnus = ? AND salasana = ?";
        Yhteys Tietokanta = new Yhteys();
        Connection yhteys = Tietokanta.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, username);
        kysely.setString(2, password);
        ResultSet rs = kysely.executeQuery();

        Laakari kirjautunut = null;
        if (rs.next()) {
            kirjautunut = new Laakari();
            kirjautunut.setId(rs.getInt("id"));
            kirjautunut.setNimi(rs.getString("nimi"));
            kirjautunut.setTunnus(rs.getString("tunnus"));
            kirjautunut.setSalasana(rs.getString("salasana"));
        }
        
        try {
            rs.close();
        } catch(Exception e) {}
        try {
            kysely.close();
        } catch(Exception e) {}
        try {
            yhteys.close();
        } catch(Exception e) {}
        
        return kirjautunut;
    }
}
