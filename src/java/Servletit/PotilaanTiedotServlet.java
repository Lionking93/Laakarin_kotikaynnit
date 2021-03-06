package Servletit;

import Mallit.Kayttaja;
import Mallit.HoitoOhje;
import Mallit.Oirekuvaus;
import Mallit.Potilasraportti;
import Mallit.Varaus;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *Näyttää yksittäisen asiakkaan tietoja, kuten lisätyt potilasraportit, hoito-ohjeet ja oireet
 * @author leo
 */
public class PotilaanTiedotServlet extends EmoServlet {

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
        if (onkoKirjautunut(request, response)) {
            try {
                if (napinPainallus("etusivulle", request)) {
                    response.sendRedirect("potilaat");
                } else if (napinPainallus("lisaaHoitoOhje", request)) {
                    lahetaTyhjanHoitoOhjeenTiedotLisaaPotilasRaporttiServletille(request);
                    response.sendRedirect("lisaahoitoohje");
                } else if (napinPainallus("poistaHoitoOhje", request)) {
                    int hoitoOhjeenId = Integer.parseInt(request.getParameter("hoitoOhjeenId"));
                    HoitoOhje.poistaHoitoOhje(hoitoOhjeenId);
                    asetaAsiakkaanTiedot(request);
                    lisaaPotilasraportit(request, response);
                    lisaaOirekuvaukset(request, response);
                    lisaaHoitoOhjeet(request, response);
                    request.setAttribute("onnistunutPoisto", "Hoito-ohje poistettu onnistuneesti");
                    naytaSivu(request, response, "web/potilaanTiedot.jsp");
                } else {
                    asetaAsiakkaanTiedot(request);
                    lisaaPotilasraportit(request, response);
                    lisaaOirekuvaukset(request, response);
                    lisaaHoitoOhjeet(request, response);
                    naytaSivu(request, response, "web/potilaanTiedot.jsp");
                }
            } catch (NamingException ex) {
                Logger.getLogger(PotilaanTiedotServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PotilaanTiedotServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    public void asetaAsiakkaanTiedot(HttpServletRequest request) throws NamingException, SQLException {
        Kayttaja a = haeAsiakkaanTiedotAsiakasIdlla(request);
        request.setAttribute("asiakkaanNimi", a.getNimi());
        request.setAttribute("asiakkaanHetu", a.getHenkilotunnus());
        request.setAttribute("asiakkaanOsoite", a.getOsoite());
    }
    
    public Kayttaja haeAsiakkaanTiedotAsiakasIdlla(HttpServletRequest request) throws NamingException, SQLException {
        HttpSession session = request.getSession();
        String asiakasIdTeksti = (String) session.getAttribute("asiakasId");
        int asiakasId = Integer.parseInt(asiakasIdTeksti);
        Kayttaja k = Kayttaja.haeKayttajaIdlla(asiakasId);
        return k;
    }
    
    public void lisaaPotilasraportit(HttpServletRequest request, HttpServletResponse response) throws NamingException, SQLException, ServletException, IOException {
        Kayttaja a = haeAsiakkaanTiedotAsiakasIdlla(request);
        try {
            List<Potilasraportti> l = Potilasraportti.haePotilasraportitAsiakasIdlla(a.getId());
            List<String> pvm = new ArrayList<String>();
            for (Potilasraportti p : l) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                String muokattava = format.format(p.getLisaysajankohta());
                pvm.add(muokattava);
            }
            request.setAttribute("lisaysajankohdatPotilasraportti", pvm);
            request.setAttribute("potilasraportit", l);
        } catch (Exception e) {
            naytaVirheSivu("Potilasraporttien haku tietokannasta epäonnistui", request, response);
        }
    }

    public void lisaaHoitoOhjeet(HttpServletRequest request, HttpServletResponse response) throws NamingException, SQLException, ServletException, IOException {
        Kayttaja a = haeAsiakkaanTiedotAsiakasIdlla(request);
        try {
            List<HoitoOhje> h = new ArrayList<HoitoOhje>();
            List<Oirekuvaus> o = Oirekuvaus.haeOirekuvauksetAsiakasIdlla(a.getId());
            List<Integer> i = new ArrayList<Integer>();
            for (HoitoOhje hoo : HoitoOhje.haeHoitoOhjeetAsiakasIdlla(a.getId())) {
                i.add(hoo.getVarausId());
            }
            for (Oirekuvaus o1 : o) {
                if (i.contains(o1.getVarausId())) {
                    h.add(HoitoOhje.haeHoitoOhjeVarausIdlla(o1.getVarausId()));
                } else {
                    h.add(new HoitoOhje());
                }
            }
            request.setAttribute("hoitoOhjeet", h);
        } catch (Exception e) {
            naytaVirheSivu("Hoito-ohjeiden haku tietokannasta epäonnistui.", request, response);
        }
    }

    public void lisaaOirekuvaukset(HttpServletRequest request, HttpServletResponse response) throws NamingException, SQLException, ServletException, IOException {
        Kayttaja a = haeAsiakkaanTiedotAsiakasIdlla(request);
        try {
            List<Oirekuvaus> o = Oirekuvaus.haeOirekuvauksetAsiakasIdlla(a.getId());
            List<String> pvm = new ArrayList<String>();
            for (Oirekuvaus k : o) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                String muokattava = format.format(k.getLisaysajankohta());
                pvm.add(muokattava);
            }
            request.setAttribute("lisaysajankohdatOirekuvaus", pvm);
            request.setAttribute("oirekuvaukset", o);
        } catch (Exception e) {
            naytaVirheSivu("Oirekuvausten haku tietokannasta epäonnistui.", request, response);
        }
    }

    public void lahetaTyhjanHoitoOhjeenTiedotLisaaPotilasRaporttiServletille(HttpServletRequest request) throws SQLException, NamingException {
        HttpSession session = request.getSession();
        String varauksenId = request.getParameter("hoitoOhjeenVarattavaAikaId");
        Varaus v = Varaus.haeVarausIdlla(Integer.parseInt(varauksenId));
        Kayttaja k = Kayttaja.haeKayttajaIdlla(v.getAsiakas().getId());
        String asiakasId = "" + k.getId();
        session.setAttribute("asiakasId", asiakasId);
        session.setAttribute("varausId", varauksenId);
    }
}
