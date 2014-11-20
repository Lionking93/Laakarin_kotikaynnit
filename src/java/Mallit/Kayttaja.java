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
public class Kayttaja {

    protected int id;
    protected String nimi;
    protected String tunnus;
    protected String salasana;
    protected String syntymaaika;
    protected String henkilotunnus;
    protected String osoite;

    public int getId() {
        return this.id;
    }

    public String getNimi() {
        return this.nimi;
    }

    public String getTunnus() {
        return this.tunnus;
    }

    public String getSalasana() {
        return this.salasana;
    }

    public String getSyntymaaika() {
        return this.syntymaaika;
    }

    public String getHenkilotunnus() {
        return this.henkilotunnus;
    }

    public String getOsoite() {
        return this.osoite;
    }

    public void setId(int uusiId) {
        this.id = uusiId;
    }

    public void setNimi(String uusiNimi) {
        this.nimi = uusiNimi;
    }

    public void setTunnus(String uusiTunnus) {
        this.tunnus = uusiTunnus;
    }

    public void setSalasana(String uusiSalasana) {
        this.salasana = uusiSalasana;
    }

    public void setSyntymaaika(String uusiSyntymaaika) {
        this.syntymaaika = uusiSyntymaaika;
    }

    public void setHenkilotunnus(String uusiHenkilotunnus) {
        this.henkilotunnus = uusiHenkilotunnus;
    }

    public void setOsoite(String uusiOsoite) {
        this.osoite = uusiOsoite;
    }

    public static Kayttaja etsiKayttajaTunnuksilla(String username, String password) throws NamingException, SQLException {
        String sql = "SELECT id, nimi, tunnus, salasana, syntymaaika, henkilotunnus, osoite FROM asiakas, laakari WHERE tunnus = ? AND salasana = ?";
        Yhteys Tietokanta = new Yhteys();
        Connection yhteys = Tietokanta.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, username);
        kysely.setString(2, password);
        ResultSet rs = kysely.executeQuery();

        Kayttaja kayttaja = null;
        try {
            if (rs.next()) {
                kayttaja = new Kayttaja();
                kayttaja.setId(rs.getInt("id"));
                kayttaja.setNimi(rs.getString("nimi"));
                kayttaja.setTunnus(rs.getString("tunnus"));
                kayttaja.setSalasana(rs.getString("salasana"));
                kayttaja.setSyntymaaika(rs.getDate("syntymaaika").toString());
                kayttaja.setHenkilotunnus(rs.getString("henkilotunnus"));
                kayttaja.setOsoite(rs.getString("osoite"));
            }
        } catch (Exception e) {
            System.out.println(e);
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

        return kayttaja;
    }
}
