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
public class VarattavaAika {
    
    private int id;
    private String viikonpaiva;
    private String aika;
    private int asiakasId;
    private String laakari;
    private boolean onkoVarattu;

    public VarattavaAika(int id, String viikonpaiva, String aika, int asiakasId, String laakari, boolean onkoVarattu) {
        this.id = id;
        this.viikonpaiva = viikonpaiva;
        this.aika = aika;
        this.asiakasId = asiakasId;
        this.laakari = laakari;
        this.onkoVarattu = onkoVarattu;
    }
    
    public VarattavaAika(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.viikonpaiva = muotoilePaivamaara(rs.getDate("viikonpaiva"));
        this.aika = rs.getString("aika");
        this.laakari = rs.getString("nimi");
        this.asiakasId = rs.getInt("asiakas_id");
        this.onkoVarattu = rs.getBoolean("onko_varattu");
    }
    
    public VarattavaAika() {}
    
    public int getId() {
        return this.id;
    }
    
    public String getViikonpaiva() {
        return this.viikonpaiva;
    }
    
    public String getAika() {
        return this.aika;
    }
    
    public int getAsiakasId() {
        return this.asiakasId;
    }
    
    public String getLaakari() {
        return this.laakari;
    }
    
    public boolean getOnkoVarattu() {
        return this.onkoVarattu;
    }
    
    public void setId(int uusiId){
        this.id = uusiId;
    }
    
    public void setViikonpaiva(String uusiViikonpaiva) {
        this.viikonpaiva = uusiViikonpaiva;
    }
    
    public void setAika(String uusiAika) {
        this.aika = uusiAika;
    }
    
    public void setAsiakasId(int uusiAsiakasId) {
        this.asiakasId = uusiAsiakasId;
    }
    
    public void setLaakari(String uusiLaakari) {
        this.laakari = uusiLaakari;
    }
    
    public void setOnkoVarattu(boolean uusiOnkoVarattu) {
        this.onkoVarattu = uusiOnkoVarattu;
    }
    
    public static VarattavaAika haeVarattavaAikaIdlla(int id) throws SQLException, NamingException {
        Connection yhteys = luoYhteys();
        String sql = "SELECT varattava_aika.id, varattava_aika.viikonpaiva, varattava_aika.aika, Laakari.nimi, varattava_aika.onko_varattu FROM Laakari, Varattava_aika WHERE varattava_aika.id = ? AND laakari.id = varattava_aika.laakari_id";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        
        VarattavaAika aika = null;
        if (rs.next()) {
            aika = new VarattavaAika();
            aika.setId(rs.getInt("id"));
            aika.setAika(rs.getString("aika"));
            aika.setViikonpaiva(muotoilePaivamaara(rs.getDate("viikonpaiva")));
            aika.setLaakari(rs.getString("nimi"));
        }
        suljeResurssit(rs, kysely, yhteys);
        return aika;
    }
    
    public static List<VarattavaAika> haeVarattavatLaakarit(int moneskoRivi) throws SQLException, NamingException {
        Connection yhteys = luoYhteys();
        int montako = 5;
        int rivi = moneskoRivi;
        
        String sql = "SELECT varattava_aika.id, varattava_aika.viikonpaiva, varattava_aika.aika, Laakari.nimi, varattava_aika.onko_varattu FROM Laakari, Varattava_aika WHERE laakari.id = varattava_aika.laakari_id LIMIT ? OFFSET ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, montako);
        kysely.setInt(2, (rivi-1)*montako);
        ResultSet rs = kysely.executeQuery();
        
        List<VarattavaAika> varattavatLaakarit = new ArrayList<VarattavaAika>();
        while (rs.next()) {
            VarattavaAika v = new VarattavaAika();
            v.setId(rs.getInt("id"));
            v.setViikonpaiva(muotoilePaivamaara(rs.getDate("viikonpaiva")));
            v.setAika(rs.getString("aika"));
            v.setLaakari(rs.getString("nimi"));
            v.setOnkoVarattu(rs.getBoolean("onko_varattu"));
            varattavatLaakarit.add(v);
        }
        suljeResurssit(rs, kysely, yhteys);
        return varattavatLaakarit;
    }
    
    public static List<String> haeViikonPaivat() throws NamingException, SQLException {
        String sql = "SELECT DISTINCT viikonpaiva FROM varattava_aika ORDER BY viikonpaiva";
        Connection yhteys = luoYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet rs = kysely.executeQuery();
        
        String[] viikko = {"Ma", "Ti", "Ke", "To", "Pe"};
        List<String> viikonpaivat = new ArrayList<String>();
        int i = 0;
        while (rs.next()) {
            String lisattavaPaivamaara = muotoilePaivamaara(rs.getDate("viikonpaiva"));
            lisattavaPaivamaara = viikko[i] + " " + lisattavaPaivamaara;
            viikonpaivat.add(lisattavaPaivamaara);
            i++;
        }
        suljeResurssit(rs, kysely, yhteys);
        
        return viikonpaivat;
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
