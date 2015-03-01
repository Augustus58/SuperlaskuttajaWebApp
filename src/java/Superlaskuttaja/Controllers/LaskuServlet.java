/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Controllers;

import Superlaskuttaja.Models.Asiakas;
import Superlaskuttaja.Models.Lasku;
import Superlaskuttaja.Models.LaskunSumma;
import Superlaskuttaja.Models.Laskuttaja;
import Superlaskuttaja.Models.Pankkiviivakoodi;
import Superlaskuttaja.Models.PdfExtractor;
import Superlaskuttaja.Models.Suorite;
import Superlaskuttaja.Models.Tilinumero;
import Superlaskuttaja.Models.UnivClass;
import Superlaskuttaja.Models.Viite;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
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
public class LaskuServlet extends HttpServlet {

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
        PrintWriter out = null;
        if (!getServletConfig().getInitParameter("univParam").equals("topdf")) {
            out = response.getWriter();
        }
        try {
            if (getServletConfig().getInitParameter("univParam").equals("index")) {
                if (UnivClass.isUserLoggedIn(request)) {
                    try {
                        List<Lasku> laskut = Lasku.getLaskut();
                        request.setAttribute("laskut", laskut);
                        UnivClass.getNotification(request);
                        UnivClass.setAttributeUserLogged(request);
                        UnivClass.showJSP("/laskut/index.jsp", request, response);
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
                        request.setAttribute("tilaajat", Asiakas.getTilaajat());
                        UnivClass.setAttributeUserLogged(request);
                        UnivClass.showJSP("/laskut/new.jsp", request, response);
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
                        Lasku lasku = new Lasku();
                        lasku.setLaskunNumero(Laskuttaja.getLaskujaLahetettyFromDb() + 1);
                        lasku.setViittausAiempaanLaskuun(null);
                        lasku.setLaskuttaja(Laskuttaja.getLaskuttaja().getYrityksenNimi());
                        lasku.setLaskuttajanVersio(Laskuttaja.getLaskuttaja().getVersio());
                        lasku.setMaksuaika(request.getParameter("maksuaika"));
                        lasku.setPaivays(request.getParameter("paivays"));
                        lasku.setViivastysKorko(request.getParameter("viivastyskorko"));
                        lasku.setMaksuehto(request.getParameter("maksuehto"));
                        lasku.setLisatiedot(request.getParameter("lisatiedot"));
                        lasku.setOnkoMaksettu(false);
                        lasku.setAsiakasnumero(Integer.parseInt(request.getParameter("tilaaja")));
                        lasku.setAsiakas(Asiakas.getAsiakasByAsiakasnumero(Integer.parseInt(request.getParameter("tilaaja"))).getNimi());
                        BigDecimal summa = new BigDecimal(0.0);
                        try {
                            for (String parameterValue : request.getParameterValues("suoritteet")) {
                                summa = summa.add(Suorite.getSuoriteBySuoritteennumero(Integer.parseInt(parameterValue)).getYht());
                            }
                            lasku.getErrors().remove("Laskuttamattomat suoritteet");
                        } catch (NullPointerException nullPointerException) {
                            lasku.getErrors().put("Laskuttamattomat suoritteet", "Vähintään yksi suorite tulee olla valittuna");
                        }
                        lasku.setSumma(summa);
                        lasku.setErapaiva();
                        Double laskunsumma = summa.doubleValue();
                        Integer eurot = laskunsumma.intValue();
                        Integer sentit = (int) Math.round((laskunsumma - laskunsumma.intValue()) * 100.0);
                        if (sentit == 100) {
                            eurot++;
                            sentit = 0;
                        }
                        LaskunSumma laskunSumma = new LaskunSumma(eurot, sentit);
                        Laskuttaja laskuttaja = Laskuttaja.getLaskuttaja();
                        Tilinumero tilinumero = new Tilinumero(laskuttaja.getTilinumero(), laskuttaja.getTilinumeronPankki(), laskuttaja.getTilinumeronSwiftBic());
                        GregorianCalendar erapaiva = new GregorianCalendar();
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy.MM.dd");
                        try {
                            erapaiva.setTime(dateFormat1.parse(lasku.getErapaiva().toString()));
                        } catch (ParseException parseException) {
                        } catch (NullPointerException nullPointerException) {
                        }
                        Viite viite = new Viite(request.getParameter("tilaaja") + (Asiakas.getAsiakasByAsiakasnumero(Integer.parseInt(request.getParameter("tilaaja"))).getLaskujaLahetetty() + 1));
                        lasku.setViite(viite.toString());
                        Pankkiviivakoodi pankkiviivakoodi = new Pankkiviivakoodi(tilinumero, laskunSumma, viite, erapaiva);
                        lasku.setPankkiviivakoodi(pankkiviivakoodi.pankkiviivakoodiIlmanAloitustaJaLopetusta());
                        if (lasku.getErrors().isEmpty()) {
                            lasku.addToDb();
                            for (String parameterValue : request.getParameterValues("suoritteet")) {
                                Suorite s = Suorite.getSuoriteBySuoritteenNumero(Integer.parseInt(parameterValue));
                                s.setLasku(lasku.getLaskunNumero());
                                s.setTilaajanVersio(Asiakas.getHighestVersionByAsiakasnumero(s.getTilaaja()));
                                s.setVastaanottajanVersio(Asiakas.getHighestVersionByAsiakasnumero(s.getVastaanottaja()));
                                s.update();
                            }
                            Asiakas a = Asiakas.getAsiakasByAsiakasnumero(lasku.getAsiakasnumero());
                            a.setLaskujaLahetetty(Integer.toString(a.getLaskujaLahetetty() + 1));
                            a.update();
                            Laskuttaja l = Laskuttaja.getLaskuttaja();
                            l.setLaskujaLahetetty(Integer.toString(l.getLaskujaLahetetty() + 1));
                            l.update();
                            HttpSession session = request.getSession();
                            session.setAttribute("notification", "Lasku lisätty onnistuneesti.");
                            response.sendRedirect("LaskuServletIndex");
                        } else {
                            request.setAttribute("errors", lasku.getErrors());
                            request.setAttribute("lasku", lasku);
                            request.setAttribute("tilaajat", Asiakas.getAsiakkaat());
                            UnivClass.setAttributeUserLogged(request);
                            UnivClass.showJSP("/laskut/new.jsp", request, response);
                        }
                    } catch (NamingException namingException) {
                    } catch (SQLException sQLException) {
                    }
                } else {
                    UnivClass.setError("Yritit mennä kirjautumisen vaativaan osioon.", request);
                    UnivClass.showJSP("/login/login.jsp", request, response);
                }
            }

            if (getServletConfig().getInitParameter("univParam").equals("switchmaksettu")) {
                if (UnivClass.isUserLoggedIn(request)) {
                    try {
                        Lasku l = Lasku.getLaskuByLaskunnumero(Integer.parseInt(request.getParameter("laskunNumero")));
                        if (l.getOnkoMaksettu()) {
                            l.setOnkoMaksettu(false);
                        } else {
                            l.setOnkoMaksettu(true);
                        }
                        l.update();
                        HttpSession session = request.getSession();
                        session.setAttribute("notification", "Laskun " + l.getLaskunNumero() + " maksettu vaihdettu onnistuneesti.");
                        response.sendRedirect("LaskuServletIndex");
                    } catch (NamingException namingException) {
                    } catch (SQLException sQLException) {
                    }
                } else {
                    UnivClass.setError("Yritit mennä kirjautumisen vaativaan osioon.", request);
                    UnivClass.showJSP("/login/login.jsp", request, response);
                }
            }

            if (getServletConfig().getInitParameter("univParam").equals("topdf")) {
                if (UnivClass.isUserLoggedIn(request)) {
                    try {
                        Laskuttaja laskuttaja = Laskuttaja.getLaskuttaja();
                        Lasku lasku = Lasku.getLaskuByLaskunnumero(Integer.parseInt(request.getParameter("laskunNumero")));
                        List<Suorite> suoritteet = Suorite.getSuoritteetByLaskunnumero(Integer.parseInt(request.getParameter("laskunNumero")));
                        Asiakas tilaaja = Asiakas.getAsiakasByAsiakasnumero(suoritteet.get(0).getTilaaja());
                        Asiakas vastaanottaja = Asiakas.getAsiakasByAsiakasnumero(suoritteet.get(0).getVastaanottaja());
//                        response.setHeader("Content-Disposition", "attachment; filename=\"" + lasku.getViite() + ".pdf\"");
                        new PdfExtractor().getPdf(response, laskuttaja, lasku, suoritteet, tilaaja, vastaanottaja);
                    } catch (NamingException namingException) {
                    } catch (SQLException sQLException) {
                    } catch (DocumentException ex) {
                    } catch (ParseException ex) {
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