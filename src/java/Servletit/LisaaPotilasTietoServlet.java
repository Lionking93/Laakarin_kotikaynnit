package Servletit;

import Mallit.Kayttaja;
import Mallit.Potilastieto;
import Mallit.Varaus;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class LisaaPotilasTietoServlet extends EmoServlet {

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
    
     protected Kayttaja haeAsiakkaanTiedot(HttpServletRequest request) throws NamingException, SQLException {
        HttpSession session = request.getSession();
        String asiakasIdTeksti = (String) session.getAttribute("asiakasId");
        int asiakasId = Integer.parseInt(asiakasIdTeksti);
        Kayttaja a = Kayttaja.haeKayttajaIdlla(asiakasId);
        return a;
    }

    protected Varaus haeVarauksenTiedot(HttpServletRequest request) throws SQLException, NamingException {
        HttpSession session = request.getSession();
        String varausIdTeksti = (String) session.getAttribute("varausId");
        int varausId = Integer.parseInt(varausIdTeksti);
        Varaus v = Varaus.haeVarausIdlla(varausId);
        return v;
    }

    protected void asetaAsiakkaanTiedot(HttpServletRequest request) throws NamingException, SQLException {
        Kayttaja a = haeAsiakkaanTiedot(request);
        request.setAttribute("asiakkaanNimi", a.getNimi());
        request.setAttribute("asiakkaanHetu", a.getHenkilotunnus());
        request.setAttribute("asiakkaanOsoite", a.getOsoite());
    }

    protected void lahetaTietoOnnistuneestaLisayksesta(HttpServletRequest request, String lisays) {
        HttpSession session = request.getSession();
        session.setAttribute("onnistunutLisays", lisays);
    }
    
    protected void lisaaPotilastiedonAttribuutit(HttpServletRequest request, Potilastieto p) throws SQLException, NamingException {
        p.setVarausId(haeVarauksenTiedot(request).getId());
        p.setLisaysajankohta(luoLisaysajankohta());
        p.setLisattavaTeksti(request.getParameter("potilastieto"));
    }
}
