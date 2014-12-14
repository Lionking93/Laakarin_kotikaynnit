package Servletit;

import Mallit.Kayttaja;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlettien overlord-yliluokka.
 *
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

    protected void onnistunutLisays(String onnistui, HttpServletRequest request) {
        request.setAttribute("onnistunutLisays", onnistui);
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

    protected boolean napinPainallus(String nappi, HttpServletRequest request) {
        return request.getParameter(nappi) != null;
    }

    protected boolean kirjaudutaankoUlos(HttpServletRequest request) {
        return request.getParameter("kirjauduUlos") != null;
    }

    protected void asetaSivunKayttajanNimi(HttpServletRequest request) {
        String kayttajanNimi = getKayttaja().getNimi();
        request.setAttribute("kayttajanNimi", kayttajanNimi);
    }

    protected void avaaSivunakyma(HttpServletRequest request, HttpServletResponse response, String ekasivu, String tokasivu, String kolmassivu, String oletussivu) throws ServletException, IOException {
        asetaSivunKayttajanNimi(request);
        if (request.getParameter("ekaTab") != null) {
            response.sendRedirect(ekasivu);
        } else if (request.getParameter("tokaTab") != null) {
            response.sendRedirect(tokasivu);
        } else if (request.getParameter("kolmasTab") != null) {
            response.sendRedirect(kolmassivu);
        } else {
            naytaSivu(request, response, oletussivu);
        }
    }

    protected Timestamp luoLisaysajankohta() {
        Calendar uusiPaiva = Calendar.getInstance();
        long nykyhetkiMillisekunneissa = uusiPaiva.getTimeInMillis();
        Timestamp tamaHetki = new Timestamp(nykyhetkiMillisekunneissa);
        return tamaHetki;
    }

    protected static List<String> lataaPaivat(Calendar c) {
        List<String> viikonPaivat = new ArrayList<String>();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        for (int i = 0; i < 6; i++) {
            viikonPaivat.add(df.format(c.getTime()));
            c.add(Calendar.DATE, 1);
        }
        return viikonPaivat;
    }

    protected static Calendar haeTamaPaiva() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.add(Calendar.DATE, -1);
        return c;
    }

    protected Date muunnaTyopaivamaara(String paiva) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date pvm = format.parse(paiva);
        java.sql.Date sqlPvm = new java.sql.Date(pvm.getTime());
        return sqlPvm;
    }

    protected List<String> haeNykyisenViikonPaivamaarat(HttpServletRequest request) {
        Calendar c = haeTamaPaiva();
        HttpSession session = request.getSession();
        int kuinkaPaljon = Integer.parseInt((String) session.getAttribute("siirtyma"));
        c.add(Calendar.DATE, kuinkaPaljon * 7);
        return lataaPaivat(c);
    }
}
