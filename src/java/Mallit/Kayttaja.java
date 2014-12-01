package Mallit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 * Yliluokka asiakkaalle ja lääkärille.
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

    public Kayttaja(int id, String nimi, String tunnus, String salasana, String syntymaaika, String henkilotunnus, String osoite) throws NamingException {
        this.id = id;
        this.nimi = nimi;
        this.tunnus = tunnus;
        this.salasana = salasana;
        this.syntymaaika = syntymaaika;
        this.henkilotunnus = henkilotunnus;
        this.osoite = osoite;
    }

    public Kayttaja(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.nimi = rs.getString("nimi");
        this.tunnus = rs.getString("tunnus");
        this.salasana = rs.getString("salasana");
        /*this.syntymaaika = rs.getDate("syntymaaika").toString();
        this.osoite = rs.getString("osoite");*/
    }

    public Kayttaja(int id, String nimi, String tunnus, String salasana) {
        this.id = id;
        this.nimi = nimi;
        this.tunnus = tunnus;
        this.salasana = salasana;
    }

    public Kayttaja() {
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

    public static Kayttaja haeKayttaja(PreparedStatement kysely, Connection yhteys) throws SQLException {
        ResultSet rs = kysely.executeQuery();
        Kayttaja kayttaja = null;
        if (rs.next()) {
            /*if (!onkoAsiakasAttribuutteja(rs)) {
                kayttaja = new Kayttaja(rs);
            } else {
                kayttaja = new Kayttaja(rs);
                asetaAsiakasAttribuutit(rs, kayttaja);
            }*/
            kayttaja = new Kayttaja(rs);
        }
        try { rs.close(); } catch (Exception e) {}
        return kayttaja;
    }

    public static void asetaAsiakasAttribuutit(ResultSet rs, Kayttaja k) throws SQLException {
        k.setHenkilotunnus(rs.getString("henkilotunnus"));
        k.setOsoite(rs.getString("osoite"));
        k.setSyntymaaika(rs.getDate("syntymaaika").toString());
    }

    public static boolean onkoAsiakasAttribuutteja(ResultSet rs) throws SQLException {
        return onkoSyntymaaikaa(rs) && onkoHenkilotunnusta(rs) && onkoOsoitetta(rs) == true;
    }

    public static boolean onkoSyntymaaikaa(ResultSet rs) throws SQLException {
        return rs.getDate("syntymaaika") != null;
    }

    public static boolean onkoHenkilotunnusta(ResultSet rs) throws SQLException {
        return rs.getString("henkilotunnus") != null;
    }

    public static boolean onkoOsoitetta(ResultSet rs) throws SQLException {
        return rs.getString("osoite") != null;
    }
}
