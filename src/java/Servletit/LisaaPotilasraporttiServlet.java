package Servletit;

import Mallit.Kayttaja;
import Mallit.Potilasraportti;
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
public class LisaaPotilasraporttiServlet extends LisaaPotilasTietoServlet {

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
        if (onkoKirjautunut(request, response)) {
            try {
                asetaAsiakkaanTiedot(request);
                if (napinPainallus("potilasraportti", request)) {
                    Potilasraportti p = luoPotilasraportti(request);
                    if (p.onkoKelvollinen()) {
                        p.lisaaKuvausKantaan();
                        lahetaTietoOnnistuneestaLisayksesta(request, "Potilasraportti lisätty onnistuneesti.");
                        response.sendRedirect("potilaantiedot");
                    } else {
                        Collection<String> virheet = p.getVirheet();
                        request.setAttribute("syotettyTeksti", p);
                        request.setAttribute("virheViesti", virheet.toArray()[0]);
                        naytaSivu(request, response, "web/lisaaPotilasraportti.jsp");
                    }
                } else {
                    naytaSivu(request, response, "web/lisaaPotilasraportti.jsp");
                }
            } catch (Exception e) {
                naytaVirheSivu("Potilasraportin lisäämisessä tapahtui virhe.", request, response);
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

    public Potilasraportti luoPotilasraportti(HttpServletRequest request) throws NamingException, SQLException {
        Potilasraportti p = new Potilasraportti();
        lisaaPotilastiedonAttribuutit(request, p);
        return p;
    }
}
