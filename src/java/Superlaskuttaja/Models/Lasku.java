/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author Augustus58
 */
public class Lasku {

    private Integer laskunNumero;
    private Integer viittausAiempaanLaskuun;
    private String laskuttaja;
    private Integer laskuttajanVersio;
    private Integer maksuaika;
    private Date paivays;
    private Integer viivastysKorko;
    private String maksuehto;
    private String lisatiedot;
    private Boolean onkoMaksettu;
    private String pankkiviivakoodi;
    private String asiakas;
    private Integer asiakasnumero;
    private String viite;
    private BigDecimal summa;
    private Date erapaiva;
    private final DateFormat dateFormat1;
    private final DateFormat dateFormat2;
    private final DateFormat dateFormat3;
    private MerkkiJaMerkkijonoTarkistin tarkistin;
    private Map<String, String> errors;

    public Lasku() {
        this.dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
        this.dateFormat2 = new SimpleDateFormat("dd.MM.yyyy HH.mm");
        this.dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.tarkistin = new MerkkiJaMerkkijonoTarkistin();
        this.errors = new HashMap<String, String>();
    }

    public Lasku(Integer laskunNumero, Integer viittausAiempaanLaskuun, String laskuttaja, Integer laskuttajanVersio, Integer maksuaika, Date paivays, Integer viivastysKorko, String maksuehto, String lisatiedot, Boolean onkoMaksettu, String pankkiviivakoodi, String asiakas, Integer asiakasnumero, String viite, BigDecimal summa, Date erapaiva) {
        this.laskunNumero = laskunNumero;
        this.viittausAiempaanLaskuun = viittausAiempaanLaskuun;
        this.laskuttaja = laskuttaja;
        this.laskuttajanVersio = laskuttajanVersio;
        this.maksuaika = maksuaika;
        this.paivays = paivays;
        this.viivastysKorko = viivastysKorko;
        this.maksuehto = maksuehto;
        this.lisatiedot = lisatiedot;
        this.onkoMaksettu = onkoMaksettu;
        this.pankkiviivakoodi = pankkiviivakoodi;
        this.asiakas = asiakas;
        this.asiakasnumero = asiakasnumero;
        this.viite = viite;
        this.summa = summa;
        this.erapaiva = erapaiva;
        this.dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
        this.dateFormat2 = new SimpleDateFormat("dd.MM.yyyy HH.mm");
        this.dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.tarkistin = new MerkkiJaMerkkijonoTarkistin();
        this.errors = new HashMap<String, String>();
    }

    public static List<Lasku> getLaskut() throws NamingException, SQLException {
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("select distinct laskunNumero, viittausAiempaanLaskuun, laskuttaja, laskuttajanVersio, maksuaika, paivays, viivastyskorko, maksuehto,\n"
                + "lisatiedot, onkoMaksettu, Lasku.pankkiviivakoodi, nimi, asiakasnumero, viiteTarkisteella, laskunSumma, erapaiva\n"
                + "from Lasku, Pankkiviivakoodi, Suorite, Asiakas\n"
                + "where Lasku.pankkiviivakoodi = Pankkiviivakoodi.pankkiviivakoodi\n"
                + "and Lasku.laskunNumero = Suorite.lasku\n"
                + "and Suorite.tilaaja = Asiakas.asiakasnumero\n"
                + "and Suorite.tilaajanVersio = Asiakas.versio"
                + "");
        ArrayList<Lasku> al = new ArrayList();
        while (rs.next()) {
            Lasku s = new Lasku(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getDate(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getString(12), rs.getInt(13), rs.getString(14), rs.getBigDecimal(15), rs.getDate(16));
            al.add(s);
        }
        rs.close();
        st.close();
        c.close();
        return al;
    }

    public static Lasku getLaskuByLaskunnumero(Integer laskunnumero) throws NamingException, SQLException {
        String sql = "select distinct laskunNumero, viittausAiempaanLaskuun, laskuttaja, laskuttajanVersio, maksuaika, paivays, viivastyskorko, maksuehto,\n"
                + "lisatiedot, onkoMaksettu, Lasku.pankkiviivakoodi, nimi, asiakasnumero, viiteTarkisteella, laskunSumma, erapaiva\n"
                + "from Lasku, Pankkiviivakoodi, Suorite, Asiakas\n"
                + "where Lasku.pankkiviivakoodi = Pankkiviivakoodi.pankkiviivakoodi\n"
                + "and Lasku.laskunNumero = Suorite.lasku\n"
                + "and Suorite.tilaaja = Asiakas.asiakasnumero\n"
                + "and Suorite.tilaajanVersio = Asiakas.versio\n"
                + "and laskunNumero = ?\n"
                + "";
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setInt(1, laskunnumero);

        ResultSet rs = ps.executeQuery();
        Lasku l;
        if (rs.next()) {
            l = new Lasku(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getDate(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getBoolean(10), rs.getString(11), rs.getString(12), rs.getInt(13), rs.getString(14), rs.getBigDecimal(15), rs.getDate(16));
        } else {
            l = null;
        }
        rs.close();
        ps.close();
        c.close();
        return l;
    }

    public static void removeFromDb(Integer laskunNumero, String pankkiviivakoodi) throws SQLException, NamingException {
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        
        String sql = "delete from Lasku where laskunNumero = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, laskunNumero);
        ps.execute();
        
        String sql2 = "delete from Pankkiviivakoodi where pankkiviivakoodi = ?";
        PreparedStatement ps2 = c.prepareStatement(sql2);
        ps2.setString(1, pankkiviivakoodi);
        ps2.execute();
        
        ps.close();
        ps2.close();
        c.close();
    }

    public void update() throws NamingException, SQLException {
        String sql = "UPDATE Lasku SET (laskunNumero, viittausAiempaanLaskuun, laskuttaja, laskuttajanVersio, maksuaika, paivays, viivastyskorko, maksuehto, lisatiedot, onkoMaksettu, pankkiviivakoodi) \n"
                + "= (?,?,?,?,?,?,?,?,?,?,?)\n"
                + "where laskunnumero = ?";
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setInt(1, this.getLaskunNumero());
        ps.setNull(2, java.sql.Types.INTEGER);
        ps.setString(3, this.getLaskuttaja());
        ps.setInt(4, this.getLaskuttajanVersio());
        ps.setInt(5, this.getMaksuaika());
        ps.setDate(6, this.getPaivays());
        ps.setInt(7, this.getViivastyskorko());
        ps.setString(8, this.getMaksuehto());
        ps.setString(9, this.getLisatiedot());
        ps.setBoolean(10, this.getOnkoMaksettu());
        ps.setString(11, this.getPankkiviivakoodi());
        ps.setInt(12, this.getLaskunNumero());

        ps.execute();

        String sql2 = "UPDATE Pankkiviivakoodi SET (pankkiviivakoodi, viiteTarkisteella, laskunSumma, erapaiva) \n"
                + "= (?,?,?,?)\n"
                + "where pankkiviivakoodi = ?";

        PreparedStatement ps2 = c.prepareStatement(sql2);

        ps2.setString(1, this.getPankkiviivakoodi());
        ps2.setString(2, this.getViite());
        ps2.setBigDecimal(3, this.getSumma());
        ps2.setDate(4, this.getErapaiva());
        ps2.setString(5, this.getPankkiviivakoodi());

        ps2.execute();

        ps.close();
        ps2.close();
        c.close();
    }

    public void addToDb() throws NamingException, SQLException {
        String sql = "INSERT INTO Pankkiviivakoodi (pankkiviivakoodi, viiteTarkisteella, laskunSumma, erapaiva) \n"
                + "VALUES (?,?,?,?)";
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setString(1, this.getPankkiviivakoodi());
        ps.setString(2, this.getViite());
        ps.setBigDecimal(3, this.getSumma());
        ps.setDate(4, this.getErapaiva());

        ps.execute();

        String sql2 = "INSERT INTO Lasku (laskunNumero, viittausAiempaanLaskuun, laskuttaja, laskuttajanVersio, maksuaika, paivays, viivastyskorko, maksuehto, lisatiedot, onkoMaksettu, pankkiviivakoodi) \n"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps2 = c.prepareStatement(sql2);

        ps2.setInt(1, this.getLaskunNumero());
        ps2.setNull(2, java.sql.Types.INTEGER);
        ps2.setString(3, this.getLaskuttaja());
        ps2.setInt(4, this.getLaskuttajanVersio());
        ps2.setInt(5, this.getMaksuaika());
        ps2.setDate(6, this.getPaivays());
        ps2.setInt(7, this.getViivastyskorko());
        ps2.setString(8, this.getMaksuehto());
        ps2.setString(9, this.getLisatiedot());
        ps2.setBoolean(10, this.getOnkoMaksettu());
        ps2.setString(11, this.getPankkiviivakoodi());

        ps2.execute();

        ps.close();
        c.close();
    }

    public Boolean onkoMaksuaikaOikeanlainen() {
        Boolean b = maksuaika > 0;

        if (!b) {
            errors.put("Maksuaika", "Maksuajan tulee olla positiivinen kokonaisluku.");
        } else {
            errors.remove("Maksuaika");
        }
        return (b);
    }

    public Boolean onkoPaivaysOikeanlainen() {
        java.util.Date date = new java.util.Date();
        Boolean b = dateFormat1.format(paivays).equals(dateFormat1.format(date));

        if (!b) {
            errors.put("Päiväys", "Päiväyksen tulee olla luontihetken päiväys. Tarkista tietokoneen päiväys.");
        } else {
            errors.remove("Päiväys");
        }
        return (b);
    }

    public Boolean onkoViivastyskorkoOikeanlainen() {
        Boolean b = viivastysKorko >= 0 && viivastysKorko < 100;

        if (!b) {
            errors.put("Viivästyskorko", "0 ≤ viivästyskorko < 100");
        } else {
            errors.remove("Viivästyskorko");
        }
        return (b);
    }

    public Boolean onkoMaksuehtoOikeanlainen() {
        Boolean b = !tarkistin.onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(maksuehto);

        if (!b) {
            errors.put("Maksuehto", "Maksuehto ei saa olla tyhjä.");
        } else {
            errors.remove("Maksuehto");
        }
        return (b);
    }

    public String getAsiakas() {
        return this.asiakas;
    }

    public Integer getAsiakasnumero() {
        return this.asiakasnumero;
    }

    public String getViite() {
        return this.viite;
    }

    public BigDecimal getSumma() {
        return this.summa;
    }

    public Date getErapaiva() {
        return this.erapaiva;
    }

    public Integer getLaskunNumero() {
        return laskunNumero;
    }

    public Integer getViittausAiempaanLaskuun() {
        return viittausAiempaanLaskuun;
    }

    public String getLaskuttaja() {
        return laskuttaja;
    }

    public Integer getLaskuttajanVersio() {
        return laskuttajanVersio;
    }

    public Integer getMaksuaika() {
        return maksuaika;
    }

    public Date getPaivays() {
        return paivays;
    }

    public Integer getViivastyskorko() {
        return viivastysKorko;
    }

    public String getMaksuehto() {
        return maksuehto;
    }

    public String getLisatiedot() {
        return lisatiedot;
    }

    public Boolean getOnkoMaksettu() {
        return onkoMaksettu;
    }

    public String getPankkiviivakoodi() {
        return pankkiviivakoodi;
    }

    public void setLaskunNumero(Integer laskunNumero) {
        this.laskunNumero = laskunNumero;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setViittausAiempaanLaskuun(Integer viittausAiempaanLaskuun) {
        this.viittausAiempaanLaskuun = viittausAiempaanLaskuun;
    }

    public void setLaskuttaja(String laskuttaja) {
        this.laskuttaja = laskuttaja;
    }

    public void setLaskuttajanVersio(Integer laskuttajanVersio) {
        this.laskuttajanVersio = laskuttajanVersio;
    }

    public void setMaksuaika(String maksuaika) {
        try {
            this.maksuaika = Integer.parseInt(maksuaika);
            onkoMaksuaikaOikeanlainen();
        } catch (NumberFormatException numberFormatException) {
            errors.put("Maksuaika", "Maksuajan tulee olla positiivinen kokonaisluku.");
        }
    }

    public void setPaivays(String paivays) {
        try {
            this.paivays = new Date(dateFormat1.parse(paivays).getTime());
            onkoPaivaysOikeanlainen();
        } catch (ParseException ex) {
            errors.put("Päiväys", "Päiväyksen tulee olla muotoa pp.kk.vvvv");
        }
    }

    public void setViivastysKorko(String viivastysKorko) {
        try {
            this.viivastysKorko = Integer.parseInt(viivastysKorko);
        } catch (NumberFormatException numberFormatException) {
            errors.put("Viivästyskorko", "Viivästyskoron tulee olla kokonaisluku.");
        }
    }

    public void setMaksuehto(String maksuehto) {
        this.maksuehto = maksuehto;
        onkoMaksuehtoOikeanlainen();
    }

    public void setLisatiedot(String lisatiedot) {
        this.lisatiedot = lisatiedot;
    }

    public void setOnkoMaksettu(Boolean onkoMaksettu) {
        this.onkoMaksettu = onkoMaksettu;
    }

    public void setPankkiviivakoodi(String pankkiviivakoodi) {
        this.pankkiviivakoodi = pankkiviivakoodi;
    }

    public void setAsiakas(String asiakas) {
        this.asiakas = asiakas;
    }

    public void setAsiakasnumero(Integer asiakasnumero) {
        this.asiakasnumero = asiakasnumero;
    }

    public void setViite(String viite) {
        this.viite = viite;
    }

    public void setSumma(BigDecimal summa) {
        this.summa = summa;
    }

    public void setErapaiva() {
        long t = 0;
        try {
            t = this.paivays.getTime();
        } catch (NullPointerException nullPointerException) {
            errors.put("Päiväys", "Päiväyksen tulee olla muotoa pp.kk.vvvv");
        }
        try {
            t = t + 24 * 60 * 60 * 1000 * this.maksuaika;
        } catch (NullPointerException nullPointerException) {
            errors.put("Maksuaika", "Maksuajan tulee olla positiivinen kokonaisluku.");
        }
        this.erapaiva = new Date(t);
    }

}
