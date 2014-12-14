package Servletit;

import Mallit.Kayttaja;
import Mallit.Oirekuvaus;
import Mallit.Varaus;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author leo
 */
public class TyotehtavatServlet extends EmoServlet {

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
                if (Varaus.haeAjatLaakariIdlla(getKayttaja().getId()).isEmpty()) {
                    request.setAttribute("tyotehtavienTila", "Sinulla ei ole tyotehtäviä.");
                    avaaSivunakyma(request, response, "tyotehtavat", "laakarinviikkoaikataulu", "potilaat", "web/tyotehtavat.jsp");
                } else {
                    if (napinPainallus("kuittaus", request)) {
                        int varauksenId = Integer.parseInt((request.getParameter("varauksenId")));
                        Varaus.peruAika(varauksenId);
                        lahetaTyotehtavanTiedotLisaaPotilasRaporttiServletille(request);
                        response.sendRedirect("lisaapotilasraportti");
                    } else {
                        List<Varaus> tyot = Varaus.haeAjatLaakariIdlla(getKayttaja().getId());
                        request.setAttribute("tyot", tyot);
                        List<Oirekuvaus> l = new ArrayList<Oirekuvaus>();
                        for (Varaus tyot1 : tyot) {
                            Oirekuvaus oire = Oirekuvaus.haeOirekuvausVarausIdlla(tyot1.getId());
                            l.add(oire);
                        }
                        request.setAttribute("oireet", l);
                        avaaSivunakyma(request, response, "tyotehtavat", "laakarinviikkoaikataulu", "potilaat", "web/tyotehtavat.jsp");
                    }
                }
            } catch (Exception e) {
                naytaVirheSivu("Lääkärin työtehtävien näyttäminen epäonnistui.", request, response);
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

    public void lahetaTyotehtavanTiedotLisaaPotilasRaporttiServletille(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String asiakasId = request.getParameter("kuittaus");
        String varauksenId = request.getParameter("varauksenId");
        session.setAttribute("asiakasId", asiakasId);
        session.setAttribute("varausId", varauksenId);
    }
}
