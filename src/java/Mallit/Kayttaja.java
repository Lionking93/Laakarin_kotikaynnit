package Mallit;

import Servletit.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 * Yliluokka asiakkaalle ja lääkärille.
 *
 * @author leo
 */
public class Kayttaja {

    private int id;
    private int kayttoOikeus;
    private String nimi;
    private String tunnus;
    private String salasana;
    private String syntymaaika;
    private String henkilotunnus;
    private String osoite;

    public Kayttaja(int id, int kayttoOikeus, String nimi, String tunnus, String salasana) throws NamingException {
        this.id = id;
        this.kayttoOikeus = kayttoOikeus;
        this.nimi = nimi;
        this.tunnus = tunnus;
        this.salasana = salasana;
    }

    public Kayttaja(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.kayttoOikeus = rs.getInt("kaytto_oikeus");
        this.nimi = rs.getString("nimi");
        this.tunnus = rs.getString("tunnus");
        this.salasana = rs.getString("salasana");
        if (rs.getString("henkilotunnus") != null) {
            this.henkilotunnus = rs.getString("henkilotunnus");
        }
        if (rs.getDate("syntymaaika") != null) {
            this.syntymaaika = rs.getDate("syntymaaika").toString();
        }
        if (rs.getString("osoite") != null) {
            this.osoite = rs.getString("osoite");
        }
    }

    public Kayttaja() {
    }

    public int getId() {
        return this.id;
    }

    public int getKayttoOikeus() {
        return this.kayttoOikeus;
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

    public void setKayttoOikeus(int uusiKayttoOikeus) {
        this.kayttoOikeus = uusiKayttoOikeus;
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
        String sql = "SELECT * FROM Kayttaja WHERE tunnus = ? AND salasana = ?";
        Yhteys conn = new Yhteys();
        Connection yhteys = conn.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, username);
        kysely.setString(2, password);
        ResultSet rs = kysely.executeQuery();

        Kayttaja kirjautunut = null;
        if (rs.next()) {
            kirjautunut = new Kayttaja(rs);
        }
        suljeResurssit(rs, kysely, yhteys);
        return kirjautunut;
    }

    public static Kayttaja haeKayttajaIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT * FROM kayttaja WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        Kayttaja kayttaja = null;
        if (rs.next()) {
            kayttaja = new Kayttaja(rs);
        }
        suljeResurssit(rs, kysely, yhteys);
        return kayttaja;
    }

    public static List<Kayttaja> getKayttajat(int kayttoOikeus) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT * From Kayttaja WHERE Kaytto_oikeus = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, kayttoOikeus);
        ResultSet rs = kysely.executeQuery();
        List<Kayttaja> asiakkaat = new ArrayList<Kayttaja>();
        while (rs.next()) {
            Kayttaja k = new Kayttaja(rs);
            asiakkaat.add(k);
        }
        suljeResurssit(rs, kysely, yhteys);
        return asiakkaat;
    }
    
    public static List<Kayttaja> etsiLaakaritJoillaEiOleToitaAikaslotissa(int aikaslotti, int paiva) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT * FROM Kayttaja WHERE Kaytto_oikeus = 2 AND id NOT IN "
                + "(SELECT kayttaja_id FROM Tyovuorot, Kayttaja WHERE Tyovuorot.kayttaja_id = Kayttaja.id AND Tyovuorot.aikaslotti_id = ? AND Tyovuorot.paiva_id = ?)";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, aikaslotti);
        kysely.setInt(2, paiva);
        ResultSet rs = kysely.executeQuery();
        List<Kayttaja> laakarit = new ArrayList<Kayttaja>();
        while (rs.next()) {
            Kayttaja k = new Kayttaja(rs);
            laakarit.add(k);
        }
        suljeResurssit(rs, kysely, yhteys);
        return laakarit;
    }
    
    public static void suljeResurssit(ResultSet rs, PreparedStatement kysely, Connection yhteys) {
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
    }
}
