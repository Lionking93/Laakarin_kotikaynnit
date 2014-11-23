package Servletit;

import Mallit.Asiakas;
import Mallit.VarattavaAika;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author leo
 */
public class AsiakasServlet extends EmoServlet {

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

        /* Erilaisten nappien painallukset. Mitä tapahtuu, kun kirjautuu ulos tai painaa eri päävalikon tabeja. */
        if (kirjaudutaankoUlos(request)) {
            kirjauduUlos(request, response);
        } else if (onkoKirjautunut(request, response)) {
            avaaSivunakyma(request, response, "web/omatVaraukset.jsp");
        }
    }

    protected boolean kirjaudutaankoUlos(HttpServletRequest request) {
        return request.getParameter("kirjauduUlos") != null;
    }

    protected void asetaSivunKayttajanNimi(HttpServletRequest request) {
        String kayttajanNimi = getKayttaja().getNimi();
        request.setAttribute("kayttajanNimi", kayttajanNimi);
    }

    protected void avaaSivunakyma(HttpServletRequest request, HttpServletResponse response, String oletussivu) throws ServletException, IOException {
        asetaSivunKayttajanNimi(request);
        if (request.getParameter("ekaTab") != null) {
            naytaSivu(request, response, "web/omatVaraukset.jsp");
        } else if (request.getParameter("tokaTab") != null) {
            response.sendRedirect("viikkoaikataulu");
        } else if (request.getParameter("kolmasTab") != null) {
            naytaSivu(request, response, "web/hoitoOhjeet.jsp");
        } else {
            naytaSivu(request, response, oletussivu);
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

}
