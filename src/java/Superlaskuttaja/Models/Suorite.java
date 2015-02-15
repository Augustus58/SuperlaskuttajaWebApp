/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
public class Suorite {

    private Integer suoritteenNumero;
    private Integer lasku;
    private String kuvaus;
    private Integer tilaaja;
    private Integer tilaajanVersio;
    private String tilaajanNimi;
    private Integer vastaanottaja;
    private Integer vastaanottajanVersio;
    private BigDecimal maara;
    private String maaranYksikot;
    private BigDecimal aHintaVeroton;
    private Integer alvProsentti;
    private Timestamp aloitusaika;
    private Timestamp lopetusaika;
    private final DateFormat dateFormat1;
    private final DateFormat dateFormat2;
    private final DateFormat dateFormat3;
    private Boolean doesAloitusaikaIncludeHoursAndMinutes;
    private MerkkiJaMerkkijonoTarkistin tarkistin;
    private Map<String, String> errors;

    public Suorite() {
        this.dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
        this.dateFormat2 = new SimpleDateFormat("dd.MM.yyyy HH.mm");
        this.dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.tarkistin = new MerkkiJaMerkkijonoTarkistin();
        this.errors = new HashMap<String, String>();
    }

    public Suorite(Integer suoritteenNumero, Integer lasku, String kuvaus, Integer tilaaja, Integer tilaajanVersio, String tilaajanNimi, Integer vastaanottaja, Integer vastaanottajanVersio, BigDecimal maara, String maaranYksikot, BigDecimal aHintaVeroton, Integer alvProsentti, Timestamp aloitusaika, Timestamp lopetusaika) {
        this.suoritteenNumero = suoritteenNumero;
        this.lasku = lasku;
        this.kuvaus = kuvaus;
        this.tilaaja = tilaaja;
        this.tilaajanVersio = tilaajanVersio;
        this.tilaajanNimi = tilaajanNimi;
        this.vastaanottaja = vastaanottaja;
        this.vastaanottajanVersio = vastaanottajanVersio;
        this.maara = maara;
        this.maaranYksikot = maaranYksikot;
        this.aHintaVeroton = aHintaVeroton;
        this.alvProsentti = alvProsentti;
        this.aloitusaika = aloitusaika;
        this.lopetusaika = lopetusaika;
        this.dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
        this.dateFormat2 = new SimpleDateFormat("dd.MM.yyyy HH.mm");
        this.dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.tarkistin = new MerkkiJaMerkkijonoTarkistin();
        this.errors = new HashMap<String, String>();
    }

    public static List<Suorite> getSuoritteet() throws NamingException, SQLException {
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("select distinct suoritteenNumero, lasku, kuvaus, tilaaja, tilaajanVersio, nimi, vastaanottaja, vastaanottajanVersio,\n"
                + "maara, maaranYksikot, aHintaVeroton, alvProsentti, alkuaika, loppuaika\n"
                + "from Suorite, Asiakas\n"
                + "where Suorite.tilaaja = Asiakas.asiakasnumero\n"
                + "and Suorite.tilaajanVersio = Asiakas.versio\n"
                + "");
        ArrayList<Suorite> al = new ArrayList();
        while (rs.next()) {
            Suorite s = new Suorite(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getBigDecimal(9), rs.getString(10), rs.getBigDecimal(11), rs.getInt(12), rs.getTimestamp(13), rs.getTimestamp(14));
            al.add(s);
        }
        rs.close();
        st.close();
        c.close();
        return al;
    }

    public static Integer getHighestSuoritteenNumero() {
        try {
            DBConnection dbc = new DBConnection();
            Connection c = dbc.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select max(suoritteenNumero) from Suorite");
            Integer i = 0;
            rs.next();
            i = rs.getInt(1);
            rs.close();
            st.close();
            c.close();
            return i;
        } catch (NamingException namingException) {
            return 0;
        } catch (SQLException sQLException) {
            return 0;
        }
    }

    public void addToDb() throws NamingException, SQLException {
        String sql = "INSERT INTO Suorite (suoritteenNumero, lasku, kuvaus, tilaaja, tilaajanVersio, vastaanottaja, vastaanottajanVersio, maara, maaranYksikot, aHIntaVeroton, alvProsentti, alkuaika, loppuaika) \n"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setInt(1, this.getSuoritteenNumero());
        ps.setNull(2, java.sql.Types.INTEGER);
        ps.setString(3, this.getKuvaus());
        ps.setInt(4, this.getTilaaja());
        ps.setInt(5, this.getTilaajanVersio());
        ps.setInt(6, this.getVastaanottaja());
        ps.setInt(7, this.getVastaanottajanVersio());
        ps.setBigDecimal(8, this.getMaara());
        ps.setString(9, this.getMaaranYksikot());
        ps.setBigDecimal(10, this.getaHintaVeroton());
        ps.setInt(11, this.getAlvProsentti());
        ps.setTimestamp(12, this.getAloitusaika());
        ps.setTimestamp(13, this.getLopetusaika());

        ps.execute();

        ps.close();
        c.close();
    }
    
    public void update() throws NamingException, SQLException {
        String sql = "UPDATE Suorite SET (suoritteenNumero, lasku, kuvaus, tilaaja, tilaajanVersio, vastaanottaja, vastaanottajanVersio, maara, maaranYksikot, aHIntaVeroton, alvProsentti, alkuaika, loppuaika)\n"
                + "= (?,?,?,?,?,?,?,?,?,?,?,?,?)\n"
                + "where suoritteenNumero = ?";
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setInt(1, this.getSuoritteenNumero());
        if (this.lasku != null && this.lasku != 0) {
            ps.setInt(2, this.getLasku());
        } else {
            ps.setNull(2, java.sql.Types.INTEGER);
        }
        ps.setString(3, this.getKuvaus());
        ps.setInt(4, this.getTilaaja());
        ps.setInt(5, this.getTilaajanVersio());
        ps.setInt(6, this.getVastaanottaja());
        ps.setInt(7, this.getVastaanottajanVersio());
        ps.setBigDecimal(8, this.getMaara());
        ps.setString(9, this.getMaaranYksikot());
        ps.setBigDecimal(10, this.getaHintaVeroton());
        ps.setInt(11, this.getAlvProsentti());
        ps.setTimestamp(12, this.getAloitusaika());
        ps.setTimestamp(13, this.getLopetusaika());
        ps.setInt(14, this.getSuoritteenNumero());
        ps.execute();

        ps.close();
        c.close();
    }
    
    public static void removeFromDb(Integer suoritteenNumero) throws SQLException, NamingException {
        String sql = "delete from Suorite where suoritteenNumero = ?";
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        
        ps.setInt(1, suoritteenNumero);
        
        ps.execute();
        
        ps.close();
        c.close();
    }

    public static Suorite getSuoriteBySuoritteenNumero(Integer suoritteenNumero) throws NamingException, SQLException {
        String sql = "select distinct suoritteenNumero, lasku, kuvaus, tilaaja, tilaajanVersio, nimi, vastaanottaja, vastaanottajanVersio,\n"
                + "maara, maaranYksikot, aHintaVeroton, alvProsentti, alkuaika, loppuaika\n"
                + "from Suorite, Asiakas\n"
                + "where Suorite.tilaaja = Asiakas.asiakasnumero\n"
                + "and Suorite.tilaajanVersio = Asiakas.versio\n"
                + "and suoritteenNumero = ?";
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setInt(1, suoritteenNumero);

        ResultSet rs = ps.executeQuery();

        Suorite s = null;

        if (rs.next()) {
            s = new Suorite(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getBigDecimal(9), rs.getString(10), rs.getBigDecimal(11), rs.getInt(12), rs.getTimestamp(13), rs.getTimestamp(14));
        }

        rs.close();
        ps.close();
        c.close();

        return s;

    }

    public Boolean onkoMaaraOikeanlainen() {
        Boolean b = getMaara().compareTo(new BigDecimal(0.0)) == 1;
        if (!b) {
            errors.put("Määrä", "Määrän tulee olla enemmän kuin nolla.");
        } else {
            errors.remove("Määrä");
        }
        return (b);
    }

    public Boolean onkoMaaranYksikotOikeanlainen() {
        Boolean b = !tarkistin.onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(maaranYksikot);
        if (!b) {
            errors.put("Määrän yksiköt", "Määrän yksiköt ei saa olla tyhjä.");
        } else {
            errors.remove("Määrän yksiköt");
        }
        return (b);
    }

    public Boolean onkoKuvausOikeanlainen() {
        Boolean b = !tarkistin.onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(kuvaus);
        if (!b) {
            errors.put("Kuvaus", "Kuvaus ei saa olla tyhjä.");
        } else {
            errors.remove("Kuvaus");
        }
        return (b);
    }

    public Boolean onkoAHintaVerotonOikeanlainen() {
        Boolean b = getaHintaVeroton().compareTo(new BigDecimal(0.0)) == 1;
        if (!b) {
            errors.put("à hinta verollinen", "Hinnan tulee olla enemmän kuin nolla.");
        } else {
            errors.remove("à hinta verollinen");
        }
        return (b);
    }

    public Boolean onkoAlvProsenttiOikeanlainen() {
        Boolean b = getAlvProsentti() > 0 && getAlvProsentti() < 100;
        if (!b) {
            errors.put("Alv-prosentti", "0 < Alv-prosentti < 100");
        } else {
            errors.remove("Alv-prosentti");
        }
        return (b);
    }

    public Boolean onkoAloitusaikaOikeanlainen() {
        Boolean b = dateFormat1.isLenient();
        if (!b) {
            errors.put("Päivämäärä", "Päivämäärän tulee olla muotoa pp.kk.vvvv");
        } else {
            errors.remove("Päivämäärä");
        }
        return (b);
    }

    public Integer getSuoritteenNumero() {
        return suoritteenNumero;
    }

    public Integer getLasku() {
        return lasku;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public Integer getTilaaja() {
        return tilaaja;
    }

    public Integer getTilaajanVersio() {
        return tilaajanVersio;
    }

    public String getTilaajanNimi() {
        return tilaajanNimi;
    }

    public Integer getVastaanottaja() {
        return vastaanottaja;
    }

    public Integer getVastaanottajanVersio() {
        return vastaanottajanVersio;
    }

    public BigDecimal getMaara() {
        return maara;
    }

    public String getMaaranYksikot() {
        return maaranYksikot;
    }

    public BigDecimal getaHintaVeroton() {
        return aHintaVeroton;
    }

    public BigDecimal getaHintaVerollinen() {
        BigDecimal a = new BigDecimal(0.01);
        BigDecimal b = new BigDecimal(1.0);
        if (getAlvProsentti() == null || getaHintaVeroton() == null) {
            return null;
        }
        a = a.multiply(new BigDecimal(getAlvProsentti()));
        a = a.add(b);
        BigDecimal c = a.multiply(getaHintaVeroton());
        return c;
    }

    public Integer getAlvProsentti() {
        return alvProsentti;
    }

    public Timestamp getAloitusaika() {
        return aloitusaika;
    }

    public Timestamp getLopetusaika() {
        return lopetusaika;
    }

    public BigDecimal getYht() {
        return getaHintaVerollinen().multiply(getMaara()).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setSuoritteenNumero(Integer suoritteenNumero) {
        this.suoritteenNumero = suoritteenNumero;
    }

    public void setLasku(Integer lasku) {
        this.lasku = lasku;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
        onkoKuvausOikeanlainen();
    }

    public void setTilaaja(String tilaaja) {
        try {
            this.tilaaja = Integer.parseInt(tilaaja);
        } catch (NumberFormatException numberFormatException) {
        }
    }

    public void setTilaajanVersio(Integer tilaajanVersio) {
        this.tilaajanVersio = tilaajanVersio;
    }

    public void setTilaajanNimi(String tilaajanNimi) {
        this.tilaajanNimi = tilaajanNimi;
    }

    public void setVastaanottaja(String vastaanottaja) {
        try {
            this.vastaanottaja = Integer.parseInt(vastaanottaja);
        } catch (NumberFormatException numberFormatException) {
        }
    }

    public void setVastaanottajanVersio(Integer vastaanottajanVersio) {
        this.vastaanottajanVersio = vastaanottajanVersio;
    }

    public void setMaara(String maara) {
        try {
            this.maara = new BigDecimal(Double.parseDouble(maara));
            onkoMaaraOikeanlainen();
        } catch (NumberFormatException numberFormatException) {
            errors.put("Määrä", "Määrän tulee olla enemmän kuin nolla.");
        }
    }

    public void setMaaranYksikot(String maaranYksikot) {
        this.maaranYksikot = maaranYksikot;
        onkoMaaranYksikotOikeanlainen();
    }

    public void setaHintaVeroton(String aHintaVerollinen) {
        try {
            BigDecimal a = new BigDecimal(Double.parseDouble(aHintaVerollinen));
            BigDecimal b = new BigDecimal(getAlvProsentti()).multiply(new BigDecimal(0.01));
            b = b.add(new BigDecimal(1.0));
            this.aHintaVeroton = a.divide(b, BigDecimal.ROUND_HALF_UP);
            onkoAHintaVerotonOikeanlainen();
        } catch (NumberFormatException numberFormatException) {
            errors.put("à hinta verollinen", "Hinnan tulee olla enemmän kuin nolla.");
        }
    }

    public void setAlvProsentti(String alvProsentti) {
        try {
            this.alvProsentti = Integer.parseInt(alvProsentti);
            onkoAlvProsenttiOikeanlainen();
        } catch (NumberFormatException numberFormatException) {
            errors.put("Alv-prosentti", "0 < Alv-prosentti < 100");
        }
    }

    public void setAloitusaika(String aloitusaika) {
        try {
            this.aloitusaika = new Timestamp(dateFormat1.parse(aloitusaika).getTime());
            onkoAloitusaikaOikeanlainen();
        } catch (ParseException ex) {
            errors.put("Päivämäärä", "Päivämäärän tulee olla muotoa pp.kk.vvvv");
        }
    }

    public void setLopetusaika(String lopetusaika) {
        try {
            this.lopetusaika = new Timestamp(dateFormat2.parse(lopetusaika).getTime());
        } catch (ParseException ex1) {
        }
    }

}
