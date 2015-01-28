/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Controllers;

import Superlaskuttaja.Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Augustus58
 */
public class LoginServlet extends HttpServlet {

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
            if (request.getParameter("univParam").equals("index")) {
                showJSP("/login/login.jsp", request, response);
            }
            if (request.getParameter("univParam").equals("logInAttempt")) {
                String salasana = request.getParameter("password");
                String kayttaja = request.getParameter("username");

                if (kayttaja == null && salasana == null) {
                    showJSP("/login/login.jsp", request, response);
                    return;
                }

                if (kayttaja == null || kayttaja.equals("")) {
                    setError("Kirjautuminen epäonnistui! Et antanut käyttäjätunnusta.", request);
                    showJSP("/login/login.jsp", request, response);
                    return;
                }

                request.setAttribute("kayttaja", kayttaja);

                if (salasana == null || salasana.equals("")) {
                    setError("Kirjautuminen epäonnistui! Et antanut salasanaa.", request);
                    showJSP("/login/login.jsp", request, response);
                    return;
                }
                
                User u = null;
                try {
                    u = User.searchUserByIDs(kayttaja, salasana);
                } catch (NamingException ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (u != null) {
                    response.sendRedirect("Laskuttaja");
                } else {
                    setError("Kirjautuminen epäonnistui! Antamasi tunnus tai salasana on väärä.", request);
                    showJSP("/login/login.jsp", request, response);
                }
            }
        } finally {
            out.close();
        }
    }

    private void showJSP(String address, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    private void setError(String errorMessage, HttpServletRequest request) {
        request.setAttribute("pageError", errorMessage);
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
