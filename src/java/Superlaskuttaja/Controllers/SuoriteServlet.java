/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Controllers;

import Superlaskuttaja.Models.Asiakas;
import Superlaskuttaja.Models.Suorite;
import Superlaskuttaja.Models.SuoriteAdapter;
import Superlaskuttaja.Models.UnivClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class SuoriteServlet extends HttpServlet {

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
                if (UnivClass.isUserLoggedIn(request)) {
                    try {
                        List<Suorite> suoritteet = Suorite.getSuoritteet();
                        request.setAttribute("suoritteet", suoritteet);
                        UnivClass.getNotification(request);
                        UnivClass.setAttributeUserLogged(request);
                        UnivClass.showJSP("/suoritteet/index.jsp", request, response);
                    } catch (NamingException namingException) {
                    } catch (SQLException sQLException) {
                    }
                } else {
                    UnivClass.setError("Yritit mennä kirjautumisen vaativaan osioon.", request);
                    UnivClass.showJSP("/login/login.jsp", request, response);
                }
            }

            if (getServletConfig().getInitParameter("univParam").equals("new")) {
                if (UnivClass.isUserLoggedIn(request)) {
                    try {
                        if (Asiakas.getAsiakkaat().size() > 0) {
                            request.setAttribute("asiakkaat", Asiakas.getAsiakkaat());
                            UnivClass.setAttributeUserLogged(request);
                            UnivClass.showJSP("/suoritteet/new.jsp", request, response);
                        } else {
                            UnivClass.setNotificationToSession("Yhtään asiakasta ei ole lisätty.", request);
                            response.sendRedirect("AsiakasServletIndex");
                        }
                    } catch (NamingException namingException) {
                    } catch (SQLException sQLException) {
                    }
                } else {
                    UnivClass.setError("Yritit mennä kirjautumisen vaativaan osioon.", request);
                    UnivClass.showJSP("/login/login.jsp", request, response);
                }
            }

            if (getServletConfig().getInitParameter("univParam").equals("create")) {
                if (UnivClass.isUserLoggedIn(request)) {
                    try {
                        Suorite suorite = new Suorite();
                        suorite.setSuoritteenNumero(Suorite.getHighestSuoritteenNumero() + 1);
                        suorite.setLasku(null);
                        suorite.setTilaaja(request.getParameter("tilaaja"));
                        suorite.setKuvaus(request.getParameter("kuvaus"));
                        try {
                            suorite.setTilaajanVersio(Asiakas.getHighestVersionByAsiakasnumero(Integer.parseInt(request.getParameter("tilaaja"))));
                        } catch (NumberFormatException numberFormatException) {
                        }
                        suorite.setVastaanottaja(request.getParameter("vastaanottaja"));
                        try {
                            suorite.setVastaanottajanVersio(Asiakas.getHighestVersionByAsiakasnumero(Integer.parseInt(request.getParameter("vastaanottaja"))));
                        } catch (NumberFormatException numberFormatException) {
                        }
                        suorite.setMaara(request.getParameter("maara"));
                        suorite.setMaaranYksikot(request.getParameter("maaranYksikot"));
                        suorite.setAlvProsentti(request.getParameter("alvProsentti"));
                        suorite.setaHintaVeroton(request.getParameter("aHintaVerollinen"));
                        suorite.setAloitusaika(request.getParameter("aloitusaika"));
                        if (suorite.getErrors().isEmpty()) {
                            suorite.addToDb();
                            HttpSession session = request.getSession();
                            session.setAttribute("notification", "Suorite lisätty onnistuneesti.");
                            response.sendRedirect("SuoriteServletIndex");
                        } else {
                            request.setAttribute("errors", suorite.getErrors());
                            request.setAttribute("suorite", suorite);
                            request.setAttribute("asiakkaat", Asiakas.getAsiakkaat());
                            UnivClass.setAttributeUserLogged(request);
                            UnivClass.showJSP("/suoritteet/new.jsp", request, response);
                        }
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
                        Integer suoritteennumero = Integer.parseInt(request.getParameter("suoritteenNumero"));
                        Suorite suorite = Suorite.getSuoriteBySuoritteenNumero(suoritteennumero);
                        request.setAttribute("suorite", suorite);
                        request.setAttribute("asiakkaat", Asiakas.getAsiakkaat());
                        HttpSession session = request.getSession();
                        session.setAttribute("suoritteenNumero", suorite.getSuoritteenNumero());
                        UnivClass.setAttributeUserLogged(request);
                        UnivClass.showJSP("/suoritteet/edit.jsp", request, response);
                    } catch (NumberFormatException numberFormatException) {
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
                        HttpSession session = request.getSession();
                        Suorite suorite = Suorite.getSuoriteBySuoritteenNumero((Integer) session.getAttribute("suoritteenNumero"));
                        suorite.setTilaaja(request.getParameter("tilaaja"));
                        suorite.setKuvaus(request.getParameter("kuvaus"));
                        try {
                            suorite.setTilaajanVersio(Asiakas.getHighestVersionByAsiakasnumero(Integer.parseInt(request.getParameter("tilaaja"))));
                        } catch (NumberFormatException numberFormatException) {
                        }
                        suorite.setVastaanottaja(request.getParameter("vastaanottaja"));
                        try {
                            suorite.setVastaanottajanVersio(Asiakas.getHighestVersionByAsiakasnumero(Integer.parseInt(request.getParameter("vastaanottaja"))));
                        } catch (NumberFormatException numberFormatException) {
                        }
                        suorite.setMaara(request.getParameter("maara"));
                        suorite.setMaaranYksikot(request.getParameter("maaranYksikot"));
                        suorite.setAlvProsentti(request.getParameter("alvProsentti"));
                        suorite.setaHintaVeroton(request.getParameter("aHintaVerollinen"));
                        suorite.setAloitusaika(request.getParameter("aloitusaika"));
                        if (suorite.getErrors().isEmpty()) {
                            suorite.update();
                            session = request.getSession();
                            session.removeAttribute("suoritteenNumero");
                            session.setAttribute("notification", "Suoritetta muokattu onnistuneesti.");
                            response.sendRedirect("SuoriteServletIndex");
                        } else {
                            request.setAttribute("errors", suorite.getErrors());
                            request.setAttribute("suorite", suorite);
                            request.setAttribute("asiakkaat", Asiakas.getAsiakkaat());
                            UnivClass.setAttributeUserLogged(request);
                            UnivClass.showJSP("/suoritteet/edit.jsp", request, response);
                        }
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
                        Integer suoritteenNumero = Integer.parseInt(request.getParameter("suoritteenNumero"));
                        if (Suorite.getSuoriteBySuoritteenNumero(suoritteenNumero).getLasku() == null || Suorite.getSuoriteBySuoritteenNumero(suoritteenNumero).getLasku() == 0) {
                            Suorite.removeFromDb(suoritteenNumero);
                            HttpSession session = request.getSession();
                            session.setAttribute("notification", "Suorite " + suoritteenNumero + " poistettiin onnistuneesti.");
                            response.sendRedirect("SuoriteServletIndex");
                        } else {
                            UnivClass.setNotificationToSession("Suorite on laskutettu. Poista lasku ensin.", request);
                            response.sendRedirect("SuoriteServletIndex");
                        }
                    } catch (NumberFormatException numberFormatException) {
                    } catch (SQLException sQLException) {
                    } catch (NamingException namingException) {
                    }
                } else {
                    UnivClass.setError("Yritit mennä kirjautumisen vaativaan osioon.", request);
                    UnivClass.showJSP("/login/login.jsp", request, response);
                }
            }

            if (getServletConfig().getInitParameter("univParam").equals("getsuoritteet")) {
                if (UnivClass.isUserLoggedIn(request)) {
                    try {
                        Integer tilaaja = Integer.parseInt(request.getParameter("tilaaja"));
                        Integer vastaanottaja = Integer.parseInt(request.getParameter("vastaanottaja"));
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(Suorite.class, new SuoriteAdapter());
                        gsonBuilder.setPrettyPrinting();
                        Gson gson = gsonBuilder.create();
                        String json = gson.toJson(Suorite.getSuoritteet(tilaaja, vastaanottaja));
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(json);
                    } catch (NumberFormatException numberFormatException) {
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
