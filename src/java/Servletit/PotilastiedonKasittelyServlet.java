package Servletit;

import Mallit.Asiakas;
import Mallit.HoitoOhje;
import Mallit.Potilasraportti;
import Mallit.Potilastieto;
import Mallit.VarattavaAika;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author leo
 */
public class PotilastiedonKasittelyServlet extends EmoServlet {

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
                if (lisaaPotilasraporttiNapinPainallus(request)) {
                    Potilasraportti p = luoPotilasraportti(request);
                    if (p.onkoKelvollinen()) {
                        p.lisaaKuvausKantaan();
                        lahetaTietoOnnistuneestaLisayksesta(request, "Potilasraportti lisätty onnistuneesti.");
                        response.sendRedirect("potilaantiedot");
                    } else {
                        Collection<String> virheet = p.getVirheet();
                        request.setAttribute("syotettyTeksti", p);
                        request.setAttribute("virheViesti", virheet.toArray()[0]);
                        naytaSivu(request, response, "web/luoPotilastieto.jsp");
                    }
                } else if (lisaaHoitoOhjeNapinPainallus(request)) {
                    HoitoOhje h = luoHoitoOhje(request);
                    if (h.onkoKelvollinen()) {
                        h.lisaaKuvausKantaan();
                        lahetaTietoOnnistuneestaLisayksesta(request, "Hoito-ohje lisätty onnistuneesti.");
                        response.sendRedirect("potilaantiedot");
                    } else {
                        Collection<String> virheet = h.getVirheet();
                        request.setAttribute("syotettyTeksti", h);
                        request.setAttribute("virheViesti", virheet.toArray()[0]);
                        naytaSivu(request, response, "web/luoPotilastieto.jsp");
                    }
                } else {
                    naytaSivu(request, response, "web/luoPotilastieto.jsp");
                }
            } catch (NamingException ex) {
                Logger.getLogger(PotilastiedonKasittelyServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PotilastiedonKasittelyServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    public Asiakas haeAsiakkaanTiedot(HttpServletRequest request) throws NamingException, SQLException {
        HttpSession session = request.getSession();
        String asiakasIdTeksti = (String) session.getAttribute("asiakasId");
        int asiakasId = Integer.parseInt(asiakasIdTeksti);
        Asiakas a = Asiakas.haeAsiakasIdlla(asiakasId);
        return a;
    }
    
    public VarattavaAika haeVarauksenTiedot(HttpServletRequest request) throws SQLException, NamingException {
        HttpSession session = request.getSession();
        String varausIdTeksti = (String) session.getAttribute("varausId");
        int varausId = Integer.parseInt(varausIdTeksti);
        VarattavaAika v = VarattavaAika.haeVarattavaAikaIdlla(varausId);
        return v;
    }

    public void asetaAsiakkaanTiedot(HttpServletRequest request) throws NamingException, SQLException {
        Asiakas a = haeAsiakkaanTiedot(request);
        request.setAttribute("asiakkaanNimi", a.getNimi());
        request.setAttribute("asiakkaanHetu", a.getHenkilotunnus());
        request.setAttribute("asiakkaanOsoite", a.getOsoite());
    }

    public void lahetaTietoOnnistuneestaLisayksesta(HttpServletRequest request, String lisays) {
        HttpSession session = request.getSession();
        session.setAttribute("onnistunutLisays", lisays);
    }

    public HoitoOhje luoHoitoOhje(HttpServletRequest request) throws NamingException, SQLException {
        HoitoOhje h = new HoitoOhje();
        lisaaPotilastiedonAttribuutit(request, h);
        return h;
    }

    public void lisaaPotilastiedonAttribuutit(HttpServletRequest request, Potilastieto p) throws SQLException, NamingException {
        p.setVarattavaAikaId(haeVarauksenTiedot(request).getId());
        p.setAsiakasId(haeAsiakkaanTiedot(request).getId());
        p.setLisaysajankohta(luoLisaysajankohta());
        p.setLisattavaTeksti(request.getParameter("potilastieto"));
    }

    public Potilasraportti luoPotilasraportti(HttpServletRequest request) throws NamingException, SQLException {
        Potilasraportti p = new Potilasraportti();
        lisaaPotilastiedonAttribuutit(request, p);
        return p;
    }

    public boolean lisaaPotilasraporttiNapinPainallus(HttpServletRequest request) {
        return request.getParameter("potilasraportti") != null;
    }

    public boolean lisaaHoitoOhjeNapinPainallus(HttpServletRequest request) {
        return request.getParameter("hoito-ohje") != null;
    }
}
