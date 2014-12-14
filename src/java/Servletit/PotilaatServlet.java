package Servletit;

import Mallit.Kayttaja;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
public class PotilaatServlet extends EmoServlet {

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
                List<Kayttaja> asiakkaat = Kayttaja.getKayttajat(3);
                request.setAttribute("asiakkaat", asiakkaat);
            } catch (Exception e) {
                naytaVirheSivu("Asiakkaiden hakeminen tietokannasta epäonnistui.", request, response);
            }
            if (napinPainallus("potilaanTiedot", request)) {
                HttpSession session = request.getSession();
                String asiakasId = request.getParameter("asiakasId");
                session.setAttribute("asiakasId", asiakasId);
                response.sendRedirect("potilaantiedot");
            } else {
                avaaSivunakyma(request, response, "tyotehtavat", "laakarinviikkoaikataulu", "potilaat", "web/potilaat.jsp");
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

}
