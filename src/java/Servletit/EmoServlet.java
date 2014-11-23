package Servletit;

import Mallit.Asiakas;
import Mallit.Kayttaja;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *Servlettien overlord-yliluokka.
 * @author leo
 */
public class EmoServlet extends HttpServlet {

    private Kayttaja kayttaja;

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
    /*Tämän yliluokan servletit voivat tämän metodin avulla näyttää eri jsp-sivuja.*/
    protected void naytaSivu(HttpServletRequest request, HttpServletResponse response, String sivunNimi) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(sivunNimi);
        dispatcher.forward(request, response);
    }
    /*Koodi erilaisten virheviestien tuottamiselle.*/
    protected void asetaVirhe(String virhe, HttpServletRequest request) {
        request.setAttribute("virheViesti", virhe);
    }
    
    public void naytaVirheSivu(String virheviesti, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        asetaVirhe(virheviesti, request);
        naytaSivu(request, response, "web/sqlVirheSivu.jsp");
    }

    protected Kayttaja getKayttaja() {
        return kayttaja;
    }
    /*Heittää käyttäjän kirjautumissivulle, kun kirjaudu ulos -painiketta painetaan sekä sulkee istunnon.*/
    protected void kirjauduUlos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("kirjautunut");
        response.sendRedirect("login");
    }
    /*Tarkistaa onko käyttäjän kirjautuminen onnistunut.*/
    protected boolean onkoKirjautunut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        kayttaja = (Kayttaja) session.getAttribute("kirjautunut");
        if (kayttaja != null) {
            return true;
        } else {
            naytaSivu(request, response, "web/kirjautuminen.jsp");
            return false;
        }
    }
}
