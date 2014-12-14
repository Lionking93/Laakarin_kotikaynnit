package Servletit;

import Mallit.Aikaslotti;
import Mallit.Kayttaja;
import Mallit.Paiva;
import Mallit.Tyovuorot;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author leo
 */
public class AikatauluServlet extends EmoServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    protected void lataaPaivat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Paiva> paivat = Paiva.haePaivat();
            request.setAttribute("paivat", paivat);
        } catch (Exception e) {
            naytaVirheSivu("Päivien haku tietokannasta epäonnistui.", request, response);
        }
    }

    protected void asetaSivunTiedot(HttpServletRequest request, HttpServletResponse response) throws ServletException, NamingException, SQLException, IOException {
        asetaSivunKayttajanNimi(request);
        asetaLaakarinNimi(request);
        lataaResurssit(request, response);
    }

    protected void siirraKalenteria(HttpServletRequest request, String nappain) {
        HttpSession session = request.getSession();
        int siirtyma = Integer.parseInt((String) session.getAttribute("siirtyma"));
        int nappaimenArvo = Integer.parseInt(request.getParameter(nappain));
        int uudenSiirtymanArvo = siirtyma + nappaimenArvo;
        String uusiSiirtyma = "" + uudenSiirtymanArvo;
        session.setAttribute("siirtyma", uusiSiirtyma);
    }

    protected void lataaLaakarit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Kayttaja> laakarit = Kayttaja.getKayttajat(2);
            request.setAttribute("laakarit", laakarit);
        } catch (Exception e) {
            naytaVirheSivu("Lääkäreiden haku tietokannasta epäonnistui.", request, response);
        }
    }

    protected void lataaAikaslotit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Aikaslotti> ajat = Aikaslotti.haeAikaslotit();
            request.setAttribute("ajat", ajat);
        } catch (Exception e) {
            naytaVirheSivu("Aikojen hakeminen tietokannasta epäonnistui.", request, response);
        }
    }

    protected void lataaPaivamaarat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<String> paivamaarat = haeNykyisenViikonPaivamaarat(request);
            List<Date> paivamaaratDatena = new ArrayList<Date>();
            for (String s : paivamaarat) {
                Date d = muunnaTyopaivamaara(s);
                paivamaaratDatena.add(d);
            }
            request.setAttribute("paivamaarat", paivamaarat);
            request.setAttribute("paivamaaratDatena", paivamaaratDatena);
        } catch (Exception e) {
            naytaVirheSivu("Viikon päivämäärien haku epäonnistui.", request, response);
        }
    }

    protected void lataaResurssit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        lataaPaivat(request, response);
        lataaLaakarit(request, response);
        lataaAikaslotit(request, response);
        lataaPaivamaarat(request, response);
    }

    protected void laakarilleMaaratytTyovuorot(HttpServletRequest request) throws NamingException, SQLException {
        HttpSession session = request.getSession();
        int laakarinId = Integer.parseInt((String) session.getAttribute("laakarinId"));
        if (!Tyovuorot.haeTyovuorotLaakariIdlla(laakarinId).isEmpty()) {
            request.setAttribute("maaratytVuorot", Tyovuorot.haeTyovuorotLaakariIdlla(laakarinId));
        }
    }

    protected void asetaLaakarinNimi(HttpServletRequest request) throws NamingException, SQLException {
        HttpSession session = request.getSession();
        int laakarinId = Integer.parseInt((String) session.getAttribute("laakarinId"));
        Kayttaja l = Kayttaja.haeKayttajaIdlla(laakarinId);
        request.setAttribute("laakari", l);
    }

    protected void naytaTokaTab(HttpServletRequest request, HttpServletResponse response, String sivu) throws ServletException, IOException {
        try {
            laakarilleMaaratytTyovuorot(request);
        } catch (Exception e) {
            naytaVirheSivu("Lääkärin työvuorojen näyttäminen epäonnistui.", request, response);
        }
        naytaSivu(request, response, sivu);
    }
    
    protected List<Integer> haePaivaTietoja(HttpServletRequest request, int kirjaimenSijainti) {
        String[] tyovuorot = request.getParameterValues("lisattyAika");
        List<Integer> haettavatIdt = new ArrayList<Integer>();
        for (String s : tyovuorot) {
            String uusiPaivaId = "" + s.charAt(kirjaimenSijainti);
            haettavatIdt.add(Integer.parseInt(uusiPaivaId));
        }
        return haettavatIdt;
    }

    protected List<String> haePaivamaarat(HttpServletRequest request) {
        String[] tyovuorot = request.getParameterValues("lisattyAika");
        List<String> paivamaarat = new ArrayList<String>();
        String paivamaara = "";
        for (String s : tyovuorot) {
            paivamaara = "";
            for (int i = 6; i < 16; i++) {
                paivamaara = paivamaara + s.charAt(i);
            }
            paivamaarat.add(paivamaara);
        }
        return paivamaarat;
    }
}
