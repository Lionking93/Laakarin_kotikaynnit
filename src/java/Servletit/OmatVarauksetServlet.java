package Servletit;

import Mallit.Kayttaja;
import Mallit.Oirekuvaus;
import Mallit.VarattavaAika;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class OmatVarauksetServlet extends EmoServlet {

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
            try {
                if (VarattavaAika.haeAjatAsiakasIdlla(getKayttaja().getId()).isEmpty()) {
                    request.setAttribute("varauksenTila", "Sinulla ei ole ajanvarauksia.");
                } else {
                    if (ajanPerumisNapinPainallus(request)) {
                        Kayttaja kayttaja = getKayttaja();
                        int id = haePeruttavanAjanId(request);
                        VarattavaAika.peruAika(kayttaja, id);
                        Oirekuvaus.poistaOirekuvaus(id);
                        request.setAttribute("varauksenTila", "Varaus peruttu onnistuneesti.");
                    }
                    List<VarattavaAika> ajat = VarattavaAika.haeAjatAsiakasIdlla(getKayttaja().getId());
                    haeVaraustieto(request);
                    request.setAttribute("varaukset", ajat);
                }
            } catch (NamingException e) {

            } catch (SQLException e) {
            }
            avaaSivunakyma(request, response,"omatvaraukset", "viikkoaikataulu", "hoito-ohjeet", "web/omatVaraukset.jsp");
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

    public int haeAjanvarausId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int ajanvarausId;
        ajanvarausId = (Integer) session.getAttribute("ajanvarausId");
        return ajanvarausId;
    }

    public int haePeruttavanAjanId(HttpServletRequest request) {
        int peruttavaAika = Integer.parseInt(request.getParameter("peruaika"));
        return peruttavaAika;
    }

    public void haeVaraustieto(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String varaustieto;
        if (session.getAttribute("varaustieto") != null) {
            varaustieto = session.getAttribute("varaustieto").toString();
            if (varaustieto != null) {
                session.removeAttribute("varaustieto");
                request.setAttribute("varauksenTila", varaustieto);
            }
        }
    }

    public boolean ajanPerumisNapinPainallus(HttpServletRequest request) {
        return request.getParameter("peruaika") != null;
    }

}
