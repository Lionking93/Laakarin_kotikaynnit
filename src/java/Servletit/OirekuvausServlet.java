package Servletit;

import Mallit.Kayttaja;
import Mallit.Oirekuvaus;
import Mallit.VarattavaAika;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author leo
 */
public class OirekuvausServlet extends EmoServlet {

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
            asetaAjanvarauksenTiedot(request);
            if (varaaAikaNapinPainallus(request)) {
                try {
                    Kayttaja kayttaja = getKayttaja();
                    int nroId = haeAjanvarauksenId(request);
                    VarattavaAika.lisaaAsiakasId(kayttaja, nroId);
                    Oirekuvaus k = luoOirekuvaus(request);
                    if (k.onkoKelvollinen()) {
                        k.lisaaKuvausKantaan();
                        lahetaVaraustietoOmatVarauksetServletille(request);
                        response.sendRedirect("omatvaraukset");
                    } else {
                        Collection<String> virheet = k.getVirheet();
                        request.setAttribute("syotettyTeksti", k);
                        request.setAttribute("virheViesti", virheet.toArray()[0]);
                        naytaSivu(request, response, "web/oirekuvaus.jsp");
                    }
                } catch (NamingException ex) {
                    Logger.getLogger(OirekuvausServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(OirekuvausServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (palaaAjanvaraukseenNapinPainallus(request)) {
                response.sendRedirect("viikkoaikataulu");
            } else {
                naytaSivu(request, response, "web/oirekuvaus.jsp");
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

    public void asetaAjanvarauksenTiedot(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String ajanPvm = (String) session.getAttribute("ajanPvm");
        request.setAttribute("ajanPvm", ajanPvm);
        String laakarinNimi = (String) session.getAttribute("laakarinNimi");
        request.setAttribute("laakarinNimi", laakarinNimi);
        String laakarinAika = (String) session.getAttribute("laakarinAika");
        request.setAttribute("laakarinAika", laakarinAika);
    }

    public boolean varaaAikaNapinPainallus(HttpServletRequest request) {
        return request.getParameter("varaaAika") != null;
    }

    public boolean palaaAjanvaraukseenNapinPainallus(HttpServletRequest request) {
        return request.getParameter("palaaAjanvaraukseen") != null;
    }

    public void lahetaVaraustietoOmatVarauksetServletille(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("varaustieto", "Varaus lisätty onnistuneesti!");
    }

    public int haeAjanvarauksenId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String ajanId = (String) session.getAttribute("ajanId");
        int idNro = Integer.parseInt(ajanId);
        return idNro;
    }

    public Oirekuvaus luoOirekuvaus(HttpServletRequest request) throws NamingException, SQLException {
        Oirekuvaus k = new Oirekuvaus();
        k.setId(haeAjanvarauksenId(request));
        k.setLisaysajankohta(luoLisaysajankohta());
        k.setLisattavaTeksti(request.getParameter("oirekuvaus"));
        return k;
    }

}
