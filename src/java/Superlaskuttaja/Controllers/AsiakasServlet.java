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
import java.util.List;
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
                        UnivClass.getNotification(request);
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
                        if (Asiakas.getAsiakasByAsiakasnumero(uusiAsiakas.getAsiakasnumero()) != null) {
                            uusiAsiakas.getErrors().put("asiakasnumero", "Asiakasnumero on käytössä");
                        }
                        if (uusiAsiakas.getErrors().isEmpty()) {
                            uusiAsiakas.addToDb();
                            HttpSession session = request.getSession();
                            session.setAttribute("notification", "Asiakas lisätty onnistuneesti.");
                            response.sendRedirect("AsiakasServletIndex");
                        } else {
                            request.setAttribute("errors", uusiAsiakas.getErrors());
                            request.setAttribute("asiakas", uusiAsiakas);
                            request.setAttribute("asiakasnumero", request.getParameter("asiakasnumero"));
                            request.setAttribute("laskujaLahetetty", request.getParameter("laskujaLahetetty"));
                            UnivClass.setAttributeUserLogged(request);
                            UnivClass.showJSP("/asiakkaat/new.jsp", request, response);
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
                try {
                    Integer asiakasnumero = Integer.parseInt(request.getParameter("asiakasnumero"));
                    Asiakas asiakas = Asiakas.getAsiakasByAsiakasnumero(asiakasnumero);
                    request.setAttribute("asiakas", asiakas);
                    request.setAttribute("asiakasnumero", asiakas.getAsiakasnumero());
                    request.setAttribute("laskujaLahetetty", asiakas.getLaskujaLahetetty());
                    HttpSession session = request.getSession();
                    session.setAttribute("asiakasnumero", asiakas.getAsiakasnumero());
                    UnivClass.setAttributeUserLogged(request);
                    UnivClass.showJSP("/asiakkaat/edit.jsp", request, response);
                } catch (NumberFormatException numberFormatException) {
                } catch (NamingException namingException) {
                } catch (SQLException sQLException) {
                }
            }

            if (getServletConfig().getInitParameter("univParam").equals("update")) {
                if (UnivClass.isUserLoggedIn(request)) {
                    try {
                        Asiakas asiakas = new Asiakas();
                        asiakas.setNimi(request.getParameter("nimi"));
                        asiakas.setKatuosoite(request.getParameter("katuosoite"));
                        asiakas.setPostinumero(request.getParameter("postinumero"));
                        asiakas.setKaupunki(request.getParameter("kaupunki"));
                        asiakas.setEmail(request.getParameter("email"));
                        HttpSession session = request.getSession();
                        asiakas.setAsiakasnumero(((Integer) session.getAttribute("asiakasnumero")).toString());
                        asiakas.setLaskujaLahetetty(request.getParameter("laskujaLahetetty"));
                        if (asiakas.getErrors().isEmpty()) {
                            asiakas.setVersio(Asiakas.getHighestVersionByAsiakasnumero(asiakas.getAsiakasnumero()) + 1);
                            asiakas.addToDb();
                            session.removeAttribute("asiakasnumero");
                            session.setAttribute("notification", "Asiakasta " + asiakas.getAsiakasnumero() + " muokattu onnistuneesti.");
                            response.sendRedirect("AsiakasServletIndex");
                        } else {
                            request.setAttribute("errors", asiakas.getErrors());
                            request.setAttribute("asiakas", asiakas);
                            request.setAttribute("asiakasnumero", request.getParameter("asiakasnumero"));
                            request.setAttribute("laskujaLahetetty", request.getParameter("laskujaLahetetty"));
                            UnivClass.setAttributeUserLogged(request);
                            UnivClass.showJSP("/asiakkaat/edit.jsp", request, response);
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

            if (getServletConfig().getInitParameter("univParam").equals("destroy")) {
                if (UnivClass.isUserLoggedIn(request)) {
                    try {
                        Integer asiakasnumero = Integer.parseInt(request.getParameter("asiakasnumero"));
                        Asiakas.removeFromDb(asiakasnumero);
                        HttpSession session = request.getSession();
                        session.setAttribute("notification", "Asiakas " + asiakasnumero + " poistettiin onnistuneesti.");
                        response.sendRedirect("AsiakasServletIndex");
                    } catch (NumberFormatException numberFormatException) {
                        numberFormatException.printStackTrace();
                    } catch (SQLException sQLException) {
                        sQLException.printStackTrace();
                    } catch (NamingException namingException) {
                        namingException.printStackTrace();
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
