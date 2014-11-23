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

    
    public Laakari(int id, String nimi, String tunnus, String salasana) throws NamingException {
        super(id, nimi, tunnus, salasana);
    }
    
    public Laakari(ResultSet rs) throws SQLException {
        super.id = rs.getInt("id");
        super.nimi = rs.getString("nimi");
        super.tunnus = rs.getString("tunnus");
        super.salasana = rs.getString("salasana");
    }

    public Laakari() {
       
    }

    /*Hakee lääkäri-tilin käyttäjän käyttäjätunnuksen ja salasanan perusteella.*/
    public static Kayttaja etsiLaakariTunnuksilla(String username, String password) throws NamingException, SQLException {
        String sql = "SELECT * FROM laakari WHERE tunnus = ? AND salasana = ?";
        Yhteys Tietokanta = new Yhteys();
        Connection yhteys = Tietokanta.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, username);
        kysely.setString(2, password);
        
        Kayttaja kirjautunut = haeKayttaja(kysely, yhteys);
        
        try {
            kysely.close();
        } catch (Exception e) {}
        
        return kirjautunut;
    }
    
    public static Kayttaja etsiLaakariId(int id) throws NamingException, SQLException {
        String sql = "SELECT * FROM Laakari Where id = ?";
        Yhteys Tietokanta = new Yhteys();
        Connection yhteys = Tietokanta.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, Integer.toString(id));
        
        Kayttaja laakari = haeKayttaja(kysely, yhteys);
        
        try {
            kysely.close();
        } catch (Exception e) {}
        
        return laakari;
    }
}
