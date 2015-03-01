/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Controllers;

import Superlaskuttaja.Models.Laskuttaja;
import Superlaskuttaja.Models.UnivClass;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Augustus58
 */
public class LaskuttajaServlet extends HttpServlet {

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
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            if (getServletConfig().getInitParameter("univParam").equals("index")) {
                try {
                    if (UnivClass.isUserLoggedIn(request)) {
                        if (Laskuttaja.isLaskuttajaAdded()) {
                            request.setAttribute("laskuttaja", Laskuttaja.getLaskuttaja());
                            UnivClass.getNotification(request);
                            UnivClass.setAttributeUserLogged(request);
                            UnivClass.showJSP("/laskuttaja/show.jsp", request, response);
                        } else {
                            UnivClass.setNotificationToSession("Laskuttajan tietoja ei ole lisätty.", request);
                            UnivClass.getNotification(request);
                            UnivClass.setAttributeUserLogged(request);
                            UnivClass.showJSP("/laskuttaja/new.jsp", request, response);
                        }
                    } else {
                        UnivClass.setError("Yritit mennä kirjautumisen vaativaan osioon.", request);
                        UnivClass.showJSP("/login/login.jsp", request, response);
                    }

                } catch (NamingException namingException) {
                } catch (SQLException sQLException) {
                }
            }

            if (getServletConfig().getInitParameter("univParam").equals("create")) {
                if (UnivClass.isUserLoggedIn(request)) {
                    try {
                        Laskuttaja laskuttaja = new Laskuttaja();
                        laskuttaja.setYrityksenNimi(request.getParameter("yrityksenNimi"));
                        laskuttaja.setAlvTunniste(request.getParameter("alvTunniste"));
                        laskuttaja.setNimi(request.getParameter("nimi"));
                        laskuttaja.setKatuosoite(request.getParameter("katuosoite"));
                        laskuttaja.setPostinumero(request.getParameter("postinumero"));
                        laskuttaja.setKaupunki(request.getParameter("kaupunki"));
                        laskuttaja.setPuhelinnumero(request.getParameter("puhelinnumero"));
                        laskuttaja.setEmail(request.getParameter("email"));
                        laskuttaja.setLaskujaLahetetty(request.getParameter("laskujaLahetetty"));
                        laskuttaja.setTilinumero(request.getParameter("tilinumero"));
                        laskuttaja.setTilinumeronPankki(request.getParameter("tilinumeronPankki"));
                        laskuttaja.setTilinumeronSwiftBic(request.getParameter("tilinumeronSwiftBic"));
                        laskuttaja.setVersio(1);
                        if (laskuttaja.getErrors().isEmpty()) {
                            laskuttaja.addToDb();
                            UnivClass.setNotificationToSession("Laskuttajan tiedot lisätty onnistuneesti.", request);
                            response.sendRedirect("LaskuttajaServletIndex");
                        } else {
                            request.setAttribute("errors", laskuttaja.getErrors());
                            request.setAttribute("laskuttaja", laskuttaja);
                            request.setAttribute("laskujaLahetetty", request.getParameter("laskujaLahetetty"));
                            UnivClass.setAttributeUserLogged(request);
                            UnivClass.showJSP("/laskuttaja/new.jsp", request, response);
                        }

                    } catch (NumberFormatException numberFormatException) {
                    } catch (NamingException namingException) {
                    } catch (SQLException sQLException) {
                    }
                } else {
                    UnivClass.setError("Yritit mennä kirjautumisen vaativaan osioon.", request);
                    UnivClass.showJSP("/login/login.jsp", request, response);
                }
            }

            if (getServletConfig().getInitParameter("univParam").equals("edit")) {
                if (UnivClass.isUserLoggedIn(request)) {
                    try {
                        Laskuttaja laskuttaja = Laskuttaja.getLaskuttaja();
                        request.setAttribute("laskuttaja", laskuttaja);
                        request.setAttribute("laskujaLahetetty", laskuttaja.getLaskujaLahetetty());
                        UnivClass.setAttributeUserLogged(request);
                        UnivClass.showJSP("/laskuttaja/edit.jsp", request, response);
                    } catch (NamingException namingException) {
                    } catch (SQLException sQLException) {
                    }
                } else {
                    UnivClass.setError("Yritit mennä kirjautumisen vaativaan osioon.", request);
                    UnivClass.showJSP("/login/login.jsp", request, response);
                }
            }

            if (getServletConfig().getInitParameter("univParam").equals("update")) {
                if (UnivClass.isUserLoggedIn(request)) {
                    try {
                        Laskuttaja laskuttaja = new Laskuttaja();
                        laskuttaja.setYrityksenNimi(request.getParameter("yrityksenNimi"));
                        laskuttaja.setAlvTunniste(request.getParameter("alvTunniste"));
                        laskuttaja.setNimi(request.getParameter("nimi"));
                        laskuttaja.setKatuosoite(request.getParameter("katuosoite"));
                        laskuttaja.setPostinumero(request.getParameter("postinumero"));
                        laskuttaja.setKaupunki(request.getParameter("kaupunki"));
                        laskuttaja.setPuhelinnumero(request.getParameter("puhelinnumero"));
                        laskuttaja.setEmail(request.getParameter("email"));
                        laskuttaja.setLaskujaLahetetty(request.getParameter("laskujaLahetetty"));
                        laskuttaja.setTilinumero(request.getParameter("tilinumero"));
                        laskuttaja.setTilinumeronPankki(request.getParameter("tilinumeronPankki"));
                        laskuttaja.setTilinumeronSwiftBic(request.getParameter("tilinumeronSwiftBic"));
                        Integer v = Laskuttaja.getHighestVersion();
                        if (laskuttaja.getErrors().isEmpty()) {
                            laskuttaja.setVersio(v + 1);
                            laskuttaja.addToDb();
                            UnivClass.setNotificationToSession("Laskuttajan tiedot päivitettiin onnistuneesti", request);
                            response.sendRedirect("LaskuttajaServletIndex");
                        } else {
                            request.setAttribute("errors", laskuttaja.getErrors());
                            request.setAttribute("laskuttaja", laskuttaja);
                            request.setAttribute("laskujaLahetetty", request.getParameter("laskujaLahetetty"));
                            UnivClass.setAttributeUserLogged(request);
                            UnivClass.showJSP("/laskuttaja/edit.jsp", request, response);
                        }
                    } catch (NamingException namingException) {
                    } catch (SQLException sQLException) {
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
