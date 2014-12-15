package Servletit;

import Mallit.Aikaslotti;
import Mallit.Kayttaja;
import Mallit.Paiva;
import Mallit.Tyovuorot;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *Servlet, joka vastaa pomo-käyttäjän toiminnoista eli pomonEkatab ja pomonTokatab jsp-sivuista. Näillä sivuilla pomo pystyy lisäämään
 * palkollisille lääkäreilleen työvuoroja
 * @author leo
 */
public class PomoServlet extends AikatauluServlet {

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
                    naytaTokaTab(request, response, "web/pomonTokatab.jsp");
                } catch (Exception e) {
                    naytaVirheSivu("Lääkärin valitseminen epäonnistui.", request, response);
                }
            } else if (napinPainallus("vasenNuoli", request)) {
                try {
                    siirraKalenteria(request, "vasenNuoli");
                    asetaSivunTiedot(request, response);
                    naytaTokaTab(request, response, "web/pomonTokatab.jsp");
                } catch (Exception e) {
                    naytaVirheSivu("Aikataulussa taaksepäin siirtyminen epäonnistui.", request, response);
                }
            } else if (napinPainallus("oikeaNuoli", request)) {
                try {
                    siirraKalenteria(request, "oikeaNuoli");
                    asetaSivunTiedot(request, response);
                    naytaTokaTab(request, response, "web/pomonTokatab.jsp");
                } catch (Exception e) {
                    naytaVirheSivu("Aikataulussa eteenpäin siirtyminen epäonnistui,", request, response);
                }
            } else if (napinPainallus("lisaaTyovuorot", request)) {
                if (request.getParameterValues("lisattyAika") != null) {
                    try {
                        luoTyovuorot(request);
                        onnistunutLisays("Työvuorot lisätty onnistuneesti!", request);
                        asetaSivunTiedot(request, response);
                        naytaTokaTab(request, response, "web/pomonTokatab.jsp");
                    } catch (Exception e) {
                        naytaVirheSivu("Työvuorojen lisääminen kantaan epäonnistui", request, response);
                    }
                } else {
                    try {
                        asetaVirhe("Et ole lisännyt työvuoroja!", request);
                        asetaSivunTiedot(request, response);
                        naytaTokaTab(request, response, "web/pomonTokatab.jsp");
                    } catch (Exception e) {
                        naytaVirheSivu("Tapahtui virhe, kun yritettiin lisätä olemattomia vuoroja.", request, response);
                    }
                }
            } else if (napinPainallus("palaaLaakarinValintaan", request)) {
                asetaSivunKayttajanNimi(request);
                lataaResurssit(request, response);
                naytaSivu(request, response, "web/pomonEkatab.jsp");
            } else {
                asetaSivunKayttajanNimi(request);
                lataaResurssit(request, response);
                naytaSivu(request, response, "web/pomonEkatab.jsp");
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
    //Luo pomon kalenteriin suorittamien valintojen mukaan lääkäreille työvuoroja
    public void luoTyovuorot(HttpServletRequest request) throws ParseException, NamingException, SQLException {
        List<Integer> paivaIdt = haePaivaTietoja(request, 0);
        List<Integer> aikaslottiIdt = haePaivaTietoja(request, 3);
        HttpSession session = request.getSession();
        String laakarinIdTeksti = (String) session.getAttribute("laakarinId");
        int laakarinId = Integer.parseInt(laakarinIdTeksti);
        List<String> paivamaarat = haePaivamaarat(request);
        for (int i = 0; i < paivaIdt.size(); i++) {
            Tyovuorot vuoro = new Tyovuorot();
            vuoro.setPaivaId(paivaIdt.get(i));
            vuoro.setAikaslottiId(aikaslottiIdt.get(i));
            vuoro.setKayttajaId(laakarinId);
            vuoro.setTyopaivamaara(muunnaTyopaivamaara(paivamaarat.get(i)));
            vuoro.luoTyovuoro();
        }
    }
}
