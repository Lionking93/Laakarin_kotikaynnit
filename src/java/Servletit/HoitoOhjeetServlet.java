package Servletit;

import Mallit.HoitoOhje;
import Mallit.Kayttaja;
import Mallit.Varaus;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *Servlet-luokka, joka vastaa Asiakas-käyttäjän Hoito-ohjeet -tabista
 * @author leo
 */
public class HoitoOhjeetServlet extends EmoServlet {

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
                if (HoitoOhje.haeHoitoOhjeetAsiakasIdlla(getKayttaja().getId()).isEmpty()) {
                    request.setAttribute("hoitoOhjeenTila", "Sinulla ei ole hoito-ohjeita.");
                }
                List<HoitoOhje> hoitoOhjeet = HoitoOhje.haeHoitoOhjeetAsiakasIdlla(getKayttaja().getId());
                List<Kayttaja> laakarit = new ArrayList<Kayttaja>();
                List<String> paivamaarat = new ArrayList<String>();
                for (HoitoOhje h : hoitoOhjeet) {
                    Varaus v = Varaus.haeVarausIdlla(h.getVarausId());
                    Kayttaja l = v.getLaakari();
                    laakarit.add(l);
                }
                muunnaLisaysajankohdatSuomalaisiksi(request, hoitoOhjeet);
                request.setAttribute("hoitoOhjeet", hoitoOhjeet);
                request.setAttribute("laakarit", laakarit);
            } catch (Exception e) {
                naytaVirheSivu("Hoito-ohjeiden haku epäonnistui.", request, response);
            }
            avaaSivunakyma(request, response, "omatvaraukset", "viikkoaikataulu", "hoito-ohjeet", "web/hoitoOhjeet.jsp");
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

    public void muunnaLisaysajankohdatSuomalaisiksi(HttpServletRequest request, List<HoitoOhje> hoitoOhjeet) {
        List<String> pvm = new ArrayList<String>();
        for (HoitoOhje h : hoitoOhjeet) {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
            String muokattava = format.format(h.getLisaysajankohta());
            pvm.add(muokattava);
        }
        request.setAttribute("paivamaarat", pvm);
    }

}
