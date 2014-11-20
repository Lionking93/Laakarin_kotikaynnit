package Servletit;

import Mallit.Asiakas;
import java.io.IOException;
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

        /* TODO output your page here. You may use following sample code. */
        if (request.getParameter("kirjauduUlos") != null) {
            kirjauduUlos(request, response);
        } else if (onkoKirjautunut(request, response)) {
            String kayttajanNimi = getKayttaja().getNimi();
            request.setAttribute("kayttajanNimi", kayttajanNimi);
            if (request.getParameter("ekaTab") != null) {
                naytaSivu(request, response, "web/omatVaraukset.jsp");
            } else if (request.getParameter("tokaTab") != null) {
                naytaSivu(request, response, "web/viikkoaikataulu.jsp");
            } else if (request.getParameter("kolmasTab") != null) {
                naytaSivu(request, response, "web/hoitoOhjeet.jsp");
            } else {
                naytaSivu(request, response, "web/omatVaraukset.jsp");
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
