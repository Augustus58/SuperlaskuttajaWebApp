/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Models;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Augustus58
 */
public class UnivClass {

    public static Boolean isUserLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loggedIn = (User) session.getAttribute("loggedIn");
        if (loggedIn != null) {
            return true;
        }
        return false;
    }

    public static User getLoggedUser(HttpServletRequest request) {
        User u = null;
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession();
            u = (User) session.getAttribute("loggedIn");
            return u;
        }
        return u;
    }

    public static void setError(String errorMessage, HttpServletRequest request) {
        request.setAttribute("pageError", errorMessage);
    }

    public static void showJSP(String address, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    public static void setAttributeUserLogged(HttpServletRequest request) {
        if (isUserLoggedIn(request)) {
            request.setAttribute("userLogged", UnivClass.getLoggedUser(request).getUsername());
        }
    }

    public static void getNotification(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String notification = (String) session.getAttribute("notification");
        if (notification != null) {
            session.removeAttribute("notification");
            request.setAttribute("notification", notification);
        }
    }

    public static void setNotificationToSession(String notificationMessage, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("notification", notificationMessage);
    }

    public static void setNotificationToRequest(String notificationMessage, HttpServletRequest request) {
        request.setAttribute("notification", notificationMessage);
    }

}
