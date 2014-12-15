package Mallit;

import Servletit.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private String henkilotunnus;
    private String osoite;
    private Map<String, String> virheet = new HashMap<String, String>();

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

        if (this.nimi.trim().length() == 0) {
            virheet.put("nimi", "Nimi ei saa olla tyhjä!");
        } else if (this.nimi.length() >= 50) {
            virheet.put("nimi", "Nimi ei saa olla yli 50 merkkiä pitkä!");
        } else {
            virheet.remove("nimi");
        }
    }

    public void setTunnus(String uusiTunnus) {
        this.tunnus = uusiTunnus;

        if (this.tunnus.trim().length() == 0) {
            virheet.put("tunnus", "Tunnus ei saa olla tyhjä!");
        } else if (this.tunnus.length() >= 21) {
            virheet.put("tunnus", "Tunnus ei saa olla yli 20 merkkiä pitkä!");
        } else {
            virheet.remove("tunnus");
        }
    }

    public void setSalasana(String uusiSalasana) {
        this.salasana = uusiSalasana;

        if (this.salasana.trim().length() == 0) {
            virheet.put("salasana", "Salasana ei saa olla tyhjä!");
        } else if (this.tunnus.length() >= 21) {
            virheet.put("salasana", "Salasana ei saa olla yli 20 merkkiä pitkä!");
        } else {
            virheet.remove("salasana");
        }
    }

    public void setHenkilotunnus(String uusiHenkilotunnus) {
        this.henkilotunnus = uusiHenkilotunnus;

        if (this.henkilotunnus.trim().length() == 0) {
            virheet.put("henkilotunnus", "Henkilötunnus ei saa olla tyhjä!");
        } else if (this.henkilotunnus.length() >= 12) {
            virheet.put("henkilotunnus", "Henkilötunnus ei saa olla yli 11 merkkiä pitkä!");
        } else {
            virheet.remove("henkilotunnus");
        }
    }

    public void setOsoite(String uusiOsoite) {
        this.osoite = uusiOsoite;

        if (this.osoite.trim().length() == 0) {
            virheet.put("osoite", "Osoite ei saa olla tyhjä!");
        } else if (this.osoite.length() >= 76) {
            virheet.put("osoite", "Osoite ei saa olla yli 75 merkkiä pitkä!");
        } else {
            virheet.remove("osoite");
        }
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
    
    public void luoKayttaja() throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "INSERT INTO Kayttaja(kaytto_oikeus, nimi, tunnus, salasana, henkilotunnus, osoite) VALUES(?, ?, ?, ?, ?, ?) RETURNING id";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, this.getKayttoOikeus());
        kysely.setString(2, this.getNimi());
        kysely.setString(3, this.getTunnus());
        kysely.setString(4, this.getSalasana());
        kysely.setString(5, this.getHenkilotunnus());
        kysely.setString(6, this.getOsoite());
        ResultSet rs = kysely.executeQuery();
        rs.next();
        this.id = rs.getInt(1);
        suljeResurssit(rs, kysely, yhteys);
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

    public boolean onkoKelvollinen() {
        return virheet.isEmpty();
    }

    public Collection<String> getVirheet() {
        return virheet.values();
    }
}
