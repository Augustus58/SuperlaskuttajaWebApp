/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Controllers;

import Superlaskuttaja.Models.Asiakas;
import Superlaskuttaja.Models.UnivClass;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Augustus58
 */
public class AsiakasServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
            if (getServletConfig().getInitParameter("univParam").equals("index")) {
                try {
                    if (UnivClass.isUserLoggedIn(request)) {
                        List<Asiakas> asiakkaat = Asiakas.getAsiakkaat();
                        request.setAttribute("asiakkaat", asiakkaat);
                        UnivClass.setAttributeUserLogged(request);
                        UnivClass.showJSP("/asiakkaat/index.jsp", request, response);
                    } else {
                        UnivClass.setError("Yritit mennä kirjautumisen vaativaan osioon.", request);
                        UnivClass.showJSP("/login/login.jsp", request, response);
                    }
                } catch (NamingException namingException) {
                } catch (SQLException sQLException) {
                }
            }

            if (getServletConfig().getInitParameter("univParam").equals("new")) {
                if (UnivClass.isUserLoggedIn(request)) {
                    Integer anro = 0;
                    if (Asiakas.getHighestAsiakasnumero() == 0) {
                        anro = 1000;
                    } else {
                        anro = Asiakas.getHighestAsiakasnumero() + 1;
                    }
                    request.setAttribute("asiakasnumero", anro.toString());
                    UnivClass.setAttributeUserLogged(request);
                    UnivClass.showJSP("/asiakkaat/new.jsp", request, response);
                } else {
                    UnivClass.setError("Yritit mennä kirjautumisen vaativaan osioon.", request);
                    UnivClass.showJSP("/login/login.jsp", request, response);
                }
            }

            if (getServletConfig().getInitParameter("univParam").equals("create")) {
                if (UnivClass.isUserLoggedIn(request)) {
                    try {
                        Asiakas uusiAsiakas = new Asiakas();
                        uusiAsiakas.setNimi(request.getParameter("nimi"));
                        uusiAsiakas.setKatuosoite(request.getParameter("katuosoite"));
                        uusiAsiakas.setPostinumero(request.getParameter("postinumero"));
                        uusiAsiakas.setKaupunki(request.getParameter("kaupunki"));
                        uusiAsiakas.setEmail(request.getParameter("email"));
                        uusiAsiakas.setAsiakasnumero(request.getParameter("asiakasnumero"));
                        uusiAsiakas.setLaskujaLahetetty(request.getParameter("laskujaLahetetty"));
                        uusiAsiakas.setVersio(1);
                        if (uusiAsiakas.onkoTiedotOikeanlaiset()) {
                            uusiAsiakas.addToDb();
                            response.sendRedirect("AsiakasServletIndex");
                            HttpSession session = request.getSession();
                            session.setAttribute("notification", "Asiakas lisätty onnistuneesti.");
                        } else {
                            request.setAttribute("errors", uusiAsiakas.getErrors());
                            request.setAttribute("asiakas", uusiAsiakas);
                            request.setAttribute("asiakasnumero", request.getParameter("asiakasnumero"));
                            request.setAttribute("laskujaLahetetty", request.getParameter("laskujaLahetetty"));
                            UnivClass.setAttributeUserLogged(request);
                            UnivClass.showJSP("/asiakkaat/new.jsp", request, response);
                        }
                    } catch (NumberFormatException numberFormatException) {
                        numberFormatException.printStackTrace();
                    } catch (NamingException namingException) {
                        namingException.printStackTrace();
                    } catch (SQLException sQLException) {
                        sQLException.printStackTrace();
                    }
                } else {
                    UnivClass.setError("Yritit mennä kirjautumisen vaativaan osioon.", request);
                    UnivClass.showJSP("/login/login.jsp", request, response);
                }
            }
        } finally {
            out.close();
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

}
