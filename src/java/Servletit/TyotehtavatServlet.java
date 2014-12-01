package Servletit;

import Mallit.Asiakas;
import Mallit.Oirekuvaus;
import Mallit.VarattavaAika;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
                if (VarattavaAika.haeAjatLaakariIdlla(getKayttaja().getId()).isEmpty()) {
                    request.setAttribute("tyotehtavienTila", "Sinulla ei ole tyotehtäviä.");
                } else {
                    List<VarattavaAika> tyot = VarattavaAika.haeAjatLaakariIdlla(getKayttaja().getId());
                    request.setAttribute("tyot", tyot);
                    List<Oirekuvaus> l = new ArrayList<Oirekuvaus>();
                    for (VarattavaAika tyot1 : tyot) {
                        Oirekuvaus oire = Oirekuvaus.haeOirekuvausVarattavaAikaIdlla(tyot1.getId());
                        l.add(oire);
                    }
                    request.setAttribute("oireet", l);
                }
            } catch (NamingException e) {
            } catch (SQLException e) {
            }
            if (kuittaaSuoritetuksiNapinPainallus(request)) {
                lahetaAsiakkaanTiedotPotilastiedonKasittelyServletille(request);
                response.sendRedirect("potilastiedonkasittely");
            } else {
                avaaSivunakyma(request, response, "tyotehtavat", "laakarinviikkoaikataulu", "potilaat", "web/tyotehtavat.jsp");
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

    public boolean kuittaaSuoritetuksiNapinPainallus(HttpServletRequest request) {
        return request.getParameter("kuittaus") != null;
    }

    public void lahetaAsiakkaanTiedotPotilastiedonKasittelyServletille(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String asiakasId = request.getParameter("kuittaus");
        session.setAttribute("asiakasId", asiakasId);
    }
}
