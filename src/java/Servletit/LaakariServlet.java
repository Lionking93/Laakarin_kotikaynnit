package Servletit;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author leo
 */
public class LaakariServlet extends EmoServlet {

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

        if (request.getParameter("kirjauduUlos") != null) {
            kirjauduUlos(request, response);
        } else if (onkoKirjautunut(request, response)) {
            String kayttajanNimi = getKayttaja().getNimi();
            request.setAttribute("kayttajanNimi", kayttajanNimi);
            if (request.getParameter("ekaTab") != null) {
                naytaSivu(request, response, "web/tyotehtavat.jsp");
            } else if (request.getParameter("tokaTab") != null) {
                naytaSivu(request, response, "web/viikkoaikatauluLaakari.jsp");
            } else if (request.getParameter("kolmasTab") != null) {
                naytaSivu(request, response, "web/potilaat.jsp");
            } else {
                naytaSivu(request, response, "web/tyotehtavat.jsp");
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
