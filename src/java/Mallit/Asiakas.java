package Mallit;

import Tietokanta.Yhteys;
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
public class Asiakas {

    private int id;
    private String nimi;
    private String tunnus;
    private String salasana;
    private String syntymaaika;
    private String henkilotunnus;
    private String osoite;

    public Asiakas(int id, String nimi, String tunnus, String salasana, String syntymaaika, String henkilotunnus, String osoite) throws NamingException {
        this.id = id;
        this.tunnus = tunnus;
        this.salasana = salasana;
        this.syntymaaika = syntymaaika;
        this.henkilotunnus = henkilotunnus;
        this.osoite = osoite;
    }

    public Asiakas() {
    }

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
