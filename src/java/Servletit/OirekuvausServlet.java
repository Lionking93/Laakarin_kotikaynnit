package Servletit;

import Mallit.Aikaslotti;
import Mallit.Kayttaja;
import Mallit.Oirekuvaus;
import Mallit.Paiva;
import Mallit.Varaus;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *Servlet, joka vastaa luoOirekuvaus.jsp-sivusta. Vastaa sekä uuden lääkärin ja asiakkaan välisen varauksen, 
 * että uuden asiakkaan oirekuvauksen luomisesta
 * @author leo
 */
public class OirekuvausServlet extends EmoServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        if (kirjaudutaankoUlos(request)) {
            kirjauduUlos(request, response);
        } else if (onkoKirjautunut(request, response)) {
            try {
                asetaAjanvarauksenTiedot(request, response);
            } catch (Exception e) {
                naytaVirheSivu("Ajanvarauksen tietojen näyttäminen epäonnistui.", request, response);
            }
            if (napinPainallus("varaaAika", request)) {
                try {
                    Varaus v = luoVaraus(request, response);
                    Oirekuvaus k = luoOirekuvaus(request);
                    if (k.onkoKelvollinen()) {
                        v.luoVaraus();
                        k.setVarausId(v.getId());
                        k.lisaaKuvausKantaan();
                        lahetaVaraustietoOmatVarauksetServletille(request);
                        response.sendRedirect("omatvaraukset");
                    } else {
                        Collection<String> virheet = k.getVirheet();
                        request.setAttribute("syotettyTeksti", k);
                        request.setAttribute("virheViesti", virheet.toArray()[0]);
                        naytaSivu(request, response, "web/oirekuvaus.jsp");
                    }
                } catch (Exception e) {
                    naytaVirheSivu("Varauksen ja oirekuvauksen luominen epäonnistui.", request, response);
                }
            } else if (napinPainallus("palaaAjanvaraukseen", request)) {
                response.sendRedirect("viikkoaikataulu");
            } else {
                naytaSivu(request, response, "web/oirekuvaus.jsp");
            }
        }
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
    //Asettaa ajanvarauksen tiedot asiakkaalle nähtäväksi oirekuvauksen luomissivulle
    public void asetaAjanvarauksenTiedot(HttpServletRequest request, HttpServletResponse response) throws NamingException, SQLException, ServletException, IOException {
        request.setAttribute("ajanPvm", haeVarausAjanPaivamaara(request, response));
        request.setAttribute("laakarinNimi", haeLaakari(request, response).getNimi());
        request.setAttribute("laakarinAika", haeAikaslotti(request, response).getAikaslotti());
        request.setAttribute("paiva", haePaiva(request, response).getPaiva());
    }
    //Hakee asiakkaan viikkoaikataulussa valitseman päivän lääkärinajalle
    public Paiva haePaiva(HttpServletRequest request, HttpServletResponse response) throws NamingException, SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        Paiva paiva = null;
        try {
            int paivaId = (Integer) session.getAttribute("paivaId");
            paiva = Paiva.haePaivaIdlla(paivaId);
        } catch (Exception e) {
            naytaVirheSivu("Päivän hakeminen epäonnistui.", request, response);
        }
        return paiva;
    }
    //Hakee asiakkaan viikkoaikataulussa lääkärinajalle valitseman kellonajan
    public Aikaslotti haeAikaslotti(HttpServletRequest request, HttpServletResponse response) throws NamingException, SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        Aikaslotti aikaslotti = null;
        try {
            int aikaslottiId = (Integer) session.getAttribute("aikaslottiId");
            aikaslotti = Aikaslotti.haeAikaslottiIdlla(aikaslottiId);
        } catch (Exception e) {
            naytaVirheSivu("Aikaslotin hakeminen epäonnistui.", request, response);
        }
        return aikaslotti;
    }
    //Hakee varaukseen liitetyn lääkärin
    public Kayttaja haeLaakari(HttpServletRequest request, HttpServletResponse response) throws NamingException, SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        Kayttaja laakari = null;
        try {
            int laakarinId = (Integer) session.getAttribute("laakarinId");
            laakari = Kayttaja.haeKayttajaIdlla(laakarinId);
        } catch (Exception e) {
            naytaVirheSivu("Lääkärin hakeminen epäonnistui.", request, response);
        }
        return laakari;
    }
    //Hakee päivämäärän, jolle asiakas varauksen haluaa
    public String haeVarausAjanPaivamaara(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String ajanPvm = "";
        try {
            ajanPvm = (String) session.getAttribute("ajanPvm");
        } catch (Exception r) {
            naytaVirheSivu("Päivämäärän hakeminen epäonnistui.", request, response);
        }
        return ajanPvm;
    }
    //Lähettää tiedon onnistuneesta varauksesta eteenpäin OmatVarauksetServletille
    public void lahetaVaraustietoOmatVarauksetServletille(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("varaustieto", "Varaus lisätty onnistuneesti!");
    }
    //Luo uuden lääkärin ja asiakkaan välisen varauksen
    public Varaus luoVaraus(HttpServletRequest request, HttpServletResponse response) throws NamingException, SQLException, ParseException, ServletException, IOException {
        Varaus v = new Varaus();
        v.setAikaslotti(haeAikaslotti(request, response));
        v.setPaiva(haePaiva(request, response));
        v.setAsiakas(getKayttaja());
        v.setLaakari(haeLaakari(request, response));
        v.setLisaysajankohta(muunnaTyopaivamaara(haeVarausAjanPaivamaara(request, response)));
        return v;
    }
    //Luo uuden varaukseen liittyvän oirekuvauksen asiakkaalle
    public Oirekuvaus luoOirekuvaus(HttpServletRequest request) throws NamingException, SQLException {
        Oirekuvaus k = new Oirekuvaus();
        k.setLisaysajankohta(luoLisaysajankohta());
        k.setLisattavaTeksti(request.getParameter("oirekuvaus"));
        return k;
    }

}
