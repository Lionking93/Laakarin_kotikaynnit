package Servletit;

import Mallit.Asiakas;
import Mallit.Kayttaja;
import Mallit.Laakari;
import java.io.IOException;
import java.sql.SQLException;
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
public class KirjautuminenServlet extends EmoServlet {

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

        String password = request.getParameter("password");
        String username = request.getParameter("username");
        HttpSession session = request.getSession();

        if (username == null && password == null) {
            naytaSivu(request, response, "web/kirjautuminen.jsp");
        }

        if (username == null || username.equals("")) {
            asetaVirhe("Et ole syöttanyt käyttäjätunnusta!", request);
            naytaSivu(request, response, "web/kirjautuminen.jsp");
        }

        request.setAttribute("username", username);

        if (password == null || password.equals("")) {
            asetaVirhe("Et ole syöttänyt salasanaa", request);
            naytaSivu(request, response, "web/kirjautuminen.jsp");
        }

        try {
            if (Asiakas.etsiAsiakasTunnuksilla(username, password) != null) {
                Asiakas kayttaja = Asiakas.etsiAsiakasTunnuksilla(username, password);
                if (kayttaja != null) {
                    session.setAttribute("kirjautunut", kayttaja);
                    response.sendRedirect("asiakkaanetusivu");
                }
            } else if (Laakari.etsiLaakariTunnuksilla(username, password) != null) {
                Laakari kayttaja = Laakari.etsiLaakariTunnuksilla(username, password);
                if (kayttaja != null) {
                    session.setAttribute("kirjautunut", kayttaja);
                    response.sendRedirect("laakarinetusivu");
                }
            } else {
                asetaVirhe("Antamasi käyttäjätunnus tai salasana on väärä.", request);
                naytaSivu(request, response, "web/kirjautuminen.jsp");
            }

        } catch (NamingException ex) {
            Logger.getLogger(KirjautuminenServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(KirjautuminenServlet.class.getName()).log(Level.SEVERE, null, ex);
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
