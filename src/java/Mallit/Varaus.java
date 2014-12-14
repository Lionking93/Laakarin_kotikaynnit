package Mallit;

import Servletit.Yhteys;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author leo
 */
public class Varaus {
    
    private int id;
    private Paiva paiva;
    private Aikaslotti aikaslotti;
    private Kayttaja asiakas;
    private Kayttaja laakari;
    private Date lisaysajankohta;

    public Varaus(int id, Paiva viikonpaiva, Aikaslotti aika, Kayttaja asiakas, Kayttaja laakari, Date lisaysajankohta) {
        this.id = id;
        this.paiva = viikonpaiva;
        this.aikaslotti = aika;
        this.asiakas = asiakas;
        this.laakari = laakari;
        this.lisaysajankohta = lisaysajankohta;
    }
    
    public Varaus(ResultSet rs) throws SQLException, NamingException {
        this.id = rs.getInt("id");
        this.paiva = Paiva.haePaivaIdlla(rs.getInt("paiva_id"));
        this.aikaslotti = Aikaslotti.haeAikaslottiIdlla(rs.getInt("aikaslotti_id"));
        this.asiakas = Kayttaja.haeKayttajaIdlla(rs.getInt("asiakas_id"));
        this.laakari = Kayttaja.haeKayttajaIdlla(rs.getInt("laakari_id"));
        this.lisaysajankohta = rs.getDate("lisaysajankohta");
    }
    
    public Varaus() {}
    
    public int getId() {
        return this.id;
    }
    
    public Paiva getPaiva() {
        return this.paiva;
    }
    
    public Aikaslotti getAikaslotti() {
        return this.aikaslotti;
    }
    
    public Kayttaja getAsiakas() {
        return this.asiakas;
    }
    
    public Kayttaja getLaakari() {
        return this.laakari;
    }
    
    public Date getLisaysajankohta() {
        return this.lisaysajankohta;
    }
    
    public void setId(int uusiId){
        this.id = uusiId;
    }
    
    public void setPaiva(Paiva uusiViikonpaiva) {
        this.paiva = uusiViikonpaiva;
    }
    
    public void setAikaslotti(Aikaslotti uusiAika) {
        this.aikaslotti = uusiAika;
    }
    
    public void setAsiakas(Kayttaja uusiAsiakas) {
        this.asiakas = uusiAsiakas;
    }
    
    public void setLaakari(Kayttaja uusiLaakari) {
        this.laakari = uusiLaakari;
    }
    
    public void setLisaysajankohta(Date uusiLisaysajankohta) {
        this.lisaysajankohta = uusiLisaysajankohta;
    }
    
    public static Varaus haeVarausIdlla(int id) throws SQLException, NamingException {
        Connection yhteys = luoYhteys();
        String sql = "SELECT id,  FROM Varaus WHERE varaus.id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        
        Varaus aika = null;
        if (rs.next()) {
            aika = new Varaus(rs);
        }
        suljeResurssit(rs, kysely, yhteys);
        return aika;
    }
    
    public static List<Varaus> haeAjatLaakariIdlla(int laakariId) throws NamingException, SQLException {
        Connection yhteys = luoYhteys();
        String sql = "SELECT * FROM Varaus WHERE laakari_id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, laakariId);
        ResultSet rs = kysely.executeQuery();
        
        List<Varaus> ajat = new ArrayList<Varaus>();
        while (rs.next()) {
            Varaus aika = new Varaus(rs);
            ajat.add(aika);
        }
        suljeResurssit(rs, kysely, yhteys);
        return ajat;
    }
    
    public static List<Varaus> haeAjatAsiakasIdlla(int asiakasId) throws NamingException, SQLException {
        Connection yhteys = luoYhteys();
        String sql = "SELECT * FROM Varaus WHERE asiakas_id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, asiakasId);
        ResultSet rs = kysely.executeQuery();
        
        List<Varaus> ajat = new ArrayList<Varaus>();
        while (rs.next()) {
            Varaus aika = new Varaus(rs);                       
            ajat.add(aika);
        }
        suljeResurssit(rs, kysely, yhteys);
        return ajat;
    }
    
    public void luoVaraus() throws NamingException, SQLException {
        Connection yhteys = luoYhteys();
        String sql = "INSERT INTO Varaus(paiva_id, aikaslotti_id, laakari_id, asiakas_id, lisaysajankohta) VALUES(?, ?, ?, ?, ?) RETURNING id";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, this.getPaiva().getId());
        kysely.setInt(2, this.getAikaslotti().getId());
        kysely.setInt(3, this.getLaakari().getId());
        kysely.setInt(4, this.getAsiakas().getId());
        kysely.setDate(5, this.getLisaysajankohta());
        ResultSet rs = kysely.executeQuery();
        rs.next();
        this.id = rs.getInt(1);
        
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
    }
    
    public static void peruAika(int id) throws NamingException, SQLException {
        Connection yhteys = luoYhteys();
        String sql = "DELETE FROM Varaus WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        
        kysely.executeUpdate();
        
        try { kysely.close(); } catch (Exception e) {}
        try { yhteys.close(); } catch (Exception e) {}
    }
    
    public static String muotoilePaivamaara(Date date) {
        String paivamaara = date.toString();
        String uusiPaiva = paivamaara.charAt(8) + "" + paivamaara.charAt(9) + ".";
        String uusiKuukausi = paivamaara.charAt(5) + "" + paivamaara.charAt(6) + ".";
        String uusiVuosi = "";
        for (int i = 0; i < 4; i++) {
            uusiVuosi = uusiVuosi + paivamaara.charAt(i) + "";
        }
        String uusiPaivamaara = uusiPaiva + uusiKuukausi + uusiVuosi;
        return uusiPaivamaara;
    }
    
    public static void suljeResurssit(ResultSet rs, PreparedStatement kysely, Connection yhteys) {
        try {
            rs.close();
        } catch (Exception e) {       
        }
        try {
            kysely.close();
        } catch(Exception e) {}
        try {
            yhteys.close();
        } catch (Exception e) {}
    }
    
    public static Connection luoYhteys() throws NamingException, SQLException {
        Yhteys tietokanta = new Yhteys();
        Connection yhteys = tietokanta.getYhteys();
        return yhteys;
    }
    
    
    
}
