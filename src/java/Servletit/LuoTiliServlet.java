package Servletit;

import Mallit.Kayttaja;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *Servlet, joka hoitaa uuden käyttäjätilin luomisen
 * @author leo
 */
public class LuoTiliServlet extends EmoServlet {

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
        if (request.getParameter("palaaAloitussivulle") != null) {
            response.sendRedirect("login");
        } else {
            String nimi = request.getParameter("nimi");
            String tunnus = request.getParameter("tunnus");
            String salasana = request.getParameter("salasana");
            String syntymaaika = request.getParameter("syntymaaika");
            String henkilotunnus = request.getParameter("henkilotunnus");
            String osoite = request.getParameter("osoite");

            if (nimi == null && tunnus == null && salasana == null && syntymaaika == null && henkilotunnus == null && osoite == null) {
                naytaSivu(request, response, "web/luoUusiTili.jsp");
                return;
            }

            if (nimi == null || nimi.equals("")) {
                asetaVirhe("Et ole syöttänyt nimeä!", request);
                naytaSivu(request, response, "web/luoUusiTili.jsp");
                return;
            }

            request.setAttribute("nimi", nimi);

            if (tunnus == null || tunnus.equals("")) {
                asetaVirhe("Et ole syöttänyt käyttäjätunnusta!", request);
                naytaSivu(request, response, "web/luoUusiTili.jsp");
                return;
            }

            request.setAttribute("tunnus", tunnus);

            if (salasana == null || salasana.equals("")) {
                asetaVirhe("Et ole syöttänyt salasanaa!", request);
                naytaSivu(request, response, "web/luoUusiTili.jsp");
                return;
            }

            request.setAttribute("salasana", salasana);

            if (henkilotunnus == null || henkilotunnus.equals("")) {
                asetaVirhe("Et ole syöttänyt henkilötunnusta!", request);
                naytaSivu(request, response, "web/luoUusiTili.jsp");
                return;
            }

            request.setAttribute("henkilotunnus", henkilotunnus);

            if (osoite == null || osoite.equals("")) {
                asetaVirhe("Et ole syöttänyt osoitetta!", request);
                naytaSivu(request, response, "web/luoUusiTili.jsp");
                return;
            }

            request.setAttribute("osoite", osoite);

            if (request.getParameter("luoTili") != null) {
                Kayttaja k = new Kayttaja();
                k.setKayttoOikeus(3);
                k.setNimi(nimi);
                k.setTunnus(tunnus);
                k.setSalasana(salasana);
                k.setHenkilotunnus(henkilotunnus);
                k.setOsoite(osoite);
                if (k.onkoKelvollinen()) {
                    try {
                        k.luoKayttaja();
                        request.setAttribute("onnistunutLisays", "Käyttäjän luominen onnistui!");
                    } catch (Exception e) {
                        naytaVirheSivu("Käyttäjän luominen epäonnistui!", request, response);
                    }
                } else {
                    Collection<String> virheet = k.getVirheet();
                    request.setAttribute("virheViesti", virheet.toArray()[0]);
                }
            }

            naytaSivu(request, response, "web/luoUusiTili.jsp");
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
