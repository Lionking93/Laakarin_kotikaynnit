package Kokeilut;

import Servletit.Yhteys;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author leo
 */
public class SQLTesti2 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NamingException {
        Yhteys Tietokanta = new Yhteys();
        Connection yhteys = Tietokanta.getYhteys(); //Haetaan tietokantaluokalta yhteysolio
        PreparedStatement kysely;
        ResultSet tulokset;
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain;charset=UTF-8");

        try {
            //Alustetaan muuttuja jossa on Select-kysely, joka palauttaa lukuarvon:
            String sqlkysely = "SELECT osoite from Asiakas";

            kysely = yhteys.prepareStatement(sqlkysely);
            tulokset = kysely.executeQuery();
            if (tulokset.next()) {
                //Tuloksen arvoksi pitäisi tulla numero kaksi.
                String tulos = tulokset.getString("osoite");
                out.println("Tulos: " + tulos);
            } else {
                out.println("Virhe!");
            }
            tulokset.close();
            kysely.close();
        } catch (Exception e) {
            out.println("Virhe: " + e.getMessage());
        }
        Tietokanta.suljeYhteys(yhteys);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SQLTesti2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(SQLTesti2.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SQLTesti2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(SQLTesti2.class.getName()).log(Level.SEVERE, null, ex);
        }
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

}
