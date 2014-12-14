package Servletit;

import Mallit.HoitoOhje;
import Mallit.Kayttaja;
import Mallit.Potilastieto;
import Mallit.Varaus;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author leo
 */
public class LisaaHoitoOhjeServlet extends LisaaPotilasTietoServlet {

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
                asetaAsiakkaanTiedot(request);
                if (napinPainallus("lisaaHoitoOhje", request)) {
                    HoitoOhje h = luoHoitoOhje(request);
                    if (h.onkoKelvollinen()) {
                        h.lisaaKuvausKantaan();
                        lahetaTietoOnnistuneestaLisayksesta(request, "Hoito-ohje lisätty onnistuneesti.");
                        response.sendRedirect("potilaantiedot");
                    } else {
                        Collection<String> virheet = h.getVirheet();
                        request.setAttribute("syotettyTeksti", h);
                        request.setAttribute("virheViesti", virheet.toArray()[0]);
                        naytaSivu(request, response, "web/lisaaHoitoOhje.jsp");
                    }
                } else {
                    naytaSivu(request, response, "web/lisaaHoitoOhje.jsp");
                }
            } catch (Exception e) {
                naytaVirheSivu("Hoito-ohjeen lisäämisessä tapahtui virhe.", request, response);
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

    public HoitoOhje luoHoitoOhje(HttpServletRequest request) throws NamingException, SQLException {
        HoitoOhje h = new HoitoOhje();
        lisaaPotilastiedonAttribuutit(request, h);
        return h;
    }
}
