package Servletit;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *Servlet-luokka, joka vastaa asiakkaan lääkäriaikojen valitsemisesta
 * @author leo
 */
public class ViikkoaikatauluServlet extends AikatauluServlet {

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
            HttpSession session = request.getSession();
            if (napinPainallus("valitseLaakari", request)) {
                try {
                    session.setAttribute("laakarinId", request.getParameter("laakarinId"));
                    asetaSivunTiedot(request, response);
                    asetaLaakarilleMaaratytVuorot(request, response);
                    avaaSivunakyma(request, response, "omatvaraukset", "viikkoaikataulu", "hoito-ohjeet", "web/viikkoaikataulu.jsp");
                } catch (Exception e) {
                    naytaVirheSivu("Lääkärin valitseminen epäonnistui.", request, response);
                }
            } else if (napinPainallus("vasenNuoli", request)) {
                try {
                    siirraKalenteria(request, "vasenNuoli");
                    asetaSivunTiedot(request, response);
                    asetaLaakarilleMaaratytVuorot(request, response);
                    avaaSivunakyma(request, response, "omatvaraukset", "viikkoaikataulu", "hoito-ohjeet", "web/viikkoaikataulu.jsp");
                } catch (Exception e) {
                    naytaVirheSivu("Aikataulussa taaksepäin siirtyminen epäonnistui.", request, response);
                }
            } else if (napinPainallus("oikeaNuoli", request)) {
                try {
                    siirraKalenteria(request, "oikeaNuoli");
                    asetaSivunTiedot(request, response);
                    asetaLaakarilleMaaratytVuorot(request, response);
                    avaaSivunakyma(request, response, "omatvaraukset", "viikkoaikataulu", "hoito-ohjeet", "web/viikkoaikataulu.jsp");
                } catch (Exception e) {
                    naytaVirheSivu("Aikataulussa eteenpäin siirtyminen epäonnistui,", request, response);
                }
            } else if (napinPainallus("palaaLaakarinValintaan", request)) {
                lataaLaakarit(request, response);
                avaaSivunakyma(request, response, "omatvaraukset", "viikkoaikataulu", "hoito-ohjeet", "web/viikkoaikatauluEkatab.jsp");
            } else if (napinPainallus("ajanvaraus", request)) {
                lahetaVaraustiedotOirekuvausServletille(request);
                response.sendRedirect("oirekuvaus");
            } else {
                lataaLaakarit(request, response);
                avaaSivunakyma(request, response, "omatvaraukset", "viikkoaikataulu", "hoito-ohjeet", "web/viikkoaikatauluEkatab.jsp");
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

    public void lahetaVaraustiedotOirekuvausServletille(HttpServletRequest request) {
        int paivaId = haePaivaTietoja(request, 0).get(0);
        int aikaslottiId = haePaivaTietoja(request, 3).get(0);
        String paivamaara = haePaivamaarat(request).get(0);
        HttpSession session = request.getSession();
        String laakarinIdTeksti = (String) session.getAttribute("laakarinId");
        int laakarinId = Integer.parseInt(laakarinIdTeksti);
        session.setAttribute("laakarinId", laakarinId);
        session.setAttribute("ajanPvm", paivamaara);
        session.setAttribute("aikaslottiId", aikaslottiId);
        session.setAttribute("paivaId", paivaId);
    }

    public void asetaLaakarilleMaaratytVuorot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            laakarilleMaaratytTyovuorot(request);
        } catch (Exception e) {
            naytaVirheSivu("Lääkärin työvuorojen näyttäminen epäonnistui.", request, response);
        }
    }
}
