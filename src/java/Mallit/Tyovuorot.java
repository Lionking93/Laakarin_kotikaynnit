package Mallit;

import Servletit.Yhteys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author leo
 */
public class Tyovuorot {
    
    private int id;
    private int paivaId;
    private int aikaslottiId;
    private int kayttajaId;
    private Date tyopaivamaara;
    
    public Tyovuorot(int id, int paivaId, int aikaslottiId, int kayttajaId, Date tyopaivamaara) {
        this.id = id;
        this.paivaId = paivaId;
        this.aikaslottiId = aikaslottiId;
        this.kayttajaId = kayttajaId;
        this.tyopaivamaara = tyopaivamaara;
    }
    
    public Tyovuorot(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.paivaId = rs.getInt("paiva_id");
        this.aikaslottiId = rs.getInt("aikaslotti_id");
        this.kayttajaId = rs.getInt("kayttaja_id");
        this.tyopaivamaara = rs.getDate("tyopaivamaara");
    }

    public Tyovuorot() {
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getPaivaId() {
        return this.paivaId;
    }
    
    public int getAikaslottiId() {
        return this.aikaslottiId;
    }
    
    public int getKayttajaId() {
        return this.kayttajaId;
    }
    
    public Date getTyopaivamaara() {
        return this.tyopaivamaara;
    }
    
    public void setId(int uusiId) {
        this.id = uusiId;
    }
    
    public void setPaivaId(int uusiPaivaId) {
        this.paivaId = uusiPaivaId;
    }
    
    public void setAikaslottiId(int uusiAikaslottiId) {
        this.aikaslottiId = uusiAikaslottiId;
    }
    
    public void setKayttajaId(int uusiKayttajaId) {
        this.kayttajaId = uusiKayttajaId;
    }
    
    public void setTyopaivamaara(Date uusiTyopaivamaara) {
        this.tyopaivamaara = uusiTyopaivamaara;
    }
    
    public void luoTyovuoro() throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "INSERT INTO Tyovuorot(paiva_id, aikaslotti_id, kayttaja_id, tyopaivamaara) VALUES(?, ?, ?, ?) RETURNING id";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, this.getPaivaId());
        kysely.setInt(2, this.getAikaslottiId());
        kysely.setInt(3, this.getKayttajaId());
        kysely.setDate(4, this.getTyopaivamaara());
        ResultSet rs = kysely.executeQuery();
        rs.next();
        this.id = rs.getInt(1);
        suljeResurssit(rs, kysely, yhteys);
    }
    
    public static List<Tyovuorot> haeTyovuorotLaakariIdlla(int id) throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        String sql = "SELECT * FROM Tyovuorot WHERE Kayttaja_id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        List<Tyovuorot> tyovuorot = new ArrayList<Tyovuorot>();
        while (rs.next()) {
            Tyovuorot uusivuoro = new Tyovuorot(rs);
            tyovuorot.add(uusivuoro);
        }
        suljeResurssit(rs, kysely, yhteys);
        return tyovuorot;
    }
    
    public static void suljeResurssit(ResultSet rs, PreparedStatement kysely, Connection yhteys) {
        try { rs.close(); } catch (Exception e) {}
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
    }
}
