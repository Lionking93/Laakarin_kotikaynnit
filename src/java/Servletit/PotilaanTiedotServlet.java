package Servletit;

import Mallit.Asiakas;
import Mallit.HoitoOhje;
import Mallit.Oirekuvaus;
import Mallit.Potilasraportti;
import Mallit.VarattavaAika;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
public class PotilaanTiedotServlet extends EmoServlet {

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
        if (onkoKirjautunut(request, response)) {
            try {
                asetaAsiakkaanTiedot(request);
                lisaaPotilasraportit(request, response);
                lisaaHoitoOhjeet(request, response);
                lisaaOirekuvaukset(request, response);
            } catch (NamingException ex) {
                Logger.getLogger(PotilaanTiedotServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PotilaanTiedotServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (palaaEtusivulleNapinPainallus(request)) {
                response.sendRedirect("potilaat");
            } else {
                naytaSivu(request, response, "web/potilaanTiedot.jsp");
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

    public void asetaAsiakkaanTiedot(HttpServletRequest request) throws NamingException, SQLException {
        Asiakas a = haeAsiakkaanTiedot(request);
        request.setAttribute("asiakkaanNimi", a.getNimi());
        request.setAttribute("asiakkaanHetu", a.getHenkilotunnus());
        request.setAttribute("asiakkaanOsoite", a.getOsoite());
    }

    public Asiakas haeAsiakkaanTiedot(HttpServletRequest request) throws NamingException, SQLException {
        HttpSession session = request.getSession();
        String asiakasIdTeksti = (String) session.getAttribute("asiakasId");
        int asiakasId = Integer.parseInt(asiakasIdTeksti);
        Asiakas a = Asiakas.haeAsiakasIdlla(asiakasId);
        return a;
    }

    public void lisaaPotilasraportit(HttpServletRequest request, HttpServletResponse response) throws NamingException, SQLException, ServletException, IOException {
        Asiakas a = haeAsiakkaanTiedot(request);
        try {
            List<Potilasraportti> l = Potilasraportti.haePotilasraportitAsiakasIdlla(a.getId());
            List<String> pvm = new ArrayList<String>();
            for (Potilasraportti p : l) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                String muokattava = format.format(p.getLisaysajankohta());
                pvm.add(muokattava);
            }
            request.setAttribute("lisaysajankohdatPotilasraportti", pvm);
            request.setAttribute("potilasraportit", l);
        } catch (Exception e) {
            naytaVirheSivu("Tietokannasta haku epäonnistui", request, response);
        }
    }

    public void lisaaHoitoOhjeet(HttpServletRequest request, HttpServletResponse response) throws NamingException, SQLException, ServletException, IOException {
        Asiakas a = haeAsiakkaanTiedot(request);
        try {
            List<HoitoOhje> o = HoitoOhje.haeHoitoOhjeetAsiakasIdlla(a.getId());
            List<String> pvm = new ArrayList<String>();
            for (HoitoOhje h : o) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                String muokattava = format.format(h.getLisaysajankohta());
                pvm.add(muokattava);
            }
            request.setAttribute("lisaysajankohdatHoitoOhje", pvm);
            request.setAttribute("hoito_ohjeet", o);
        } catch (Exception r) {
            naytaVirheSivu("Tietokannasta haku epäonnistui", request, response);
        }
    }

    public void lisaaOirekuvaukset(HttpServletRequest request, HttpServletResponse response) throws NamingException, SQLException, ServletException, IOException {
        Asiakas a = haeAsiakkaanTiedot(request);
        try {
            List<Oirekuvaus> k = Oirekuvaus.haeOirekuvauksetAsiakasIdlla(a.getId());
            List<String> pvm = new ArrayList<String>();
            for (Oirekuvaus o : k) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                String muokattava = format.format(o.getLisaysajankohta());
                pvm.add(muokattava);
            }
            request.setAttribute("lisaysajankohdatOirekuvaus", pvm);
            request.setAttribute("oirekuvaukset", k);
        } catch (Exception r) {
            naytaVirheSivu("Tietokannasta haku epäonnistui", request, response);
        }
    }

    public boolean palaaEtusivulleNapinPainallus(HttpServletRequest request) {
        return request.getParameter("etusivulle") != null;
    }
}
