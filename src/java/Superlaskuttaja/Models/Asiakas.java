/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author Augustus58
 */
public class Asiakas {

    private Integer asiakasnumero;
    private Integer versio;
    private String nimi;
    private String katuosoite;
    private String postinumero;
    private String kaupunki;
    private Integer laskujaLahetetty;
    private String email;
    private MerkkiJaMerkkijonoTarkistin tarkistin;
    private HashMap<String, String> errors = new HashMap<String, String>();

    public Asiakas() {
        this.tarkistin = new MerkkiJaMerkkijonoTarkistin();
        this.errors = new HashMap<String, String>();
    }

    public Asiakas(Integer asiakasnumero, Integer versio, String nimi, String katuosoite, String postinumero, String kaupunki, Integer laskujaLahetetty, String email) {
        this.asiakasnumero = asiakasnumero;
        this.versio = versio;
        this.nimi = nimi;
        this.katuosoite = katuosoite;
        this.postinumero = postinumero;
        this.kaupunki = kaupunki;
        this.laskujaLahetetty = laskujaLahetetty;
        this.email = email;
        this.tarkistin = new MerkkiJaMerkkijonoTarkistin();
    }

    public static List<Asiakas> getAsiakkaat() throws NamingException, SQLException {
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("select distinct asiakasnumero, versio, nimi, katuosoite, postinumero, kaupunki, laskujaLahetetty, email\n"
                + "from Asiakas A\n"
                + "where versio = (select max(versio) from Asiakas\n"
                + "where asiakasnumero = A.asiakasnumero)\n"
                + "");
        ArrayList<Asiakas> al = new ArrayList();
        while (rs.next()) {
            Asiakas a = new Asiakas(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8));
            al.add(a);
        }
        rs.close();
        st.close();
        c.close();
        return al;
    }

    public static Asiakas getAsiakasByAsiakasnumero(Integer asiakasnumero) throws NamingException, SQLException {
        String sql = "select distinct asiakasnumero, versio, nimi, katuosoite, postinumero, kaupunki, laskujaLahetetty, email\n"
                + "from Asiakas A\n"
                + "where versio = (select max(versio) from Asiakas\n"
                + "where asiakasnumero = A.asiakasnumero)\n"
                + "and asiakasnumero = ?\n"
                + "";
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setInt(1, asiakasnumero);
        
        ResultSet rs = ps.executeQuery();

        Asiakas a = null;

        if (rs.next()) {
            a = new Asiakas(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8));
        }

        rs.close();
        ps.close();
        c.close();

        return a;

    }

    public static int getHighestAsiakasnumero() {
        try {
            DBConnection dbc = new DBConnection();
            Connection c = dbc.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select max(asiakasnumero) from Asiakas");
            int i = 0;
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
        String sql = "INSERT INTO Asiakas (asiakasnumero, versio, nimi, katuosoite, postinumero, kaupunki, laskujaLahetetty, email) \n"
                + "VALUES (?,?,?,?,?,?,?,?)";
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setInt(1, this.getAsiakasnumero());
        ps.setInt(2, this.getVersio());
        ps.setString(3, this.getNimi());
        ps.setString(4, this.getKatuosoite());
        ps.setString(5, this.getPostinumero());
        ps.setString(6, this.getKaupunki());
        ps.setInt(7, this.getLaskujaLahetetty());
        ps.setString(8, this.getEmail());

        ps.execute();

        ps.close();
        c.close();
    }

    /**
     * Metodi kertoo onko asiakkaan tiedot oikeanlaiset.
     *
     * @return Tieto tietojen oikeellisuudesta.
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     */
    public Boolean onkoTiedotOikeanlaiset() throws NamingException, SQLException {
        if (onkoNimiOikeanlainen()
                && onkoKaupunkiOikeanlainen()
                && onkoKatuosoiteOikeanlainen()
                && onkoAsiakasnumeroOikeanlainen()
                && onkoPostinumeroOikeanlainen()
                && onkoLaskujaLahetettyOikeanlainen()
                && onkoSahkopostiOikeanlainen()) {
            errors.clear();
            return true;
        }
        return false;
    }

    /**
     * Metodi kertoo onko asiakkaan nimi oikeanlainen.
     *
     * @return Tieto nimen oikeanlaisuudesta.
     */
    public Boolean onkoNimiOikeanlainen() {
        Boolean b = !tarkistin.onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(nimi);
        if (!b) {
            errors.put("nimi", "Nimi ei saa olla tyhjä.");
        }
        return (b);
    }

    /**
     * Metodi kertoo onko asiakkaan kaupunki oikeanlainen.
     *
     * @return Tieto kaupungin oikeanlaisuudesta.
     */
    public Boolean onkoKaupunkiOikeanlainen() {
        Boolean b = !tarkistin.onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(kaupunki);
        if (!b) {
            errors.put("kaupunki", "Kaupunki ei saa olla tyhjä.");
        }
        return (b);
    }

    /**
     * Metodi kertoo onko asiakkaan katuosoite oikeanlainen.
     *
     * @return Tieto katuosoitteen oikeanlaisuudesta.
     */
    public Boolean onkoKatuosoiteOikeanlainen() {
        Boolean b = !tarkistin.onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(katuosoite);
        if (!b) {
            errors.put("katuosoite", "Katuosoite ei saa olla tyhjä.");
        }
        return (b);
    }

    /**
     * Metodi kertoo onko asiakkaan asiakasnumero oikeanlainen.
     * <p>
     * Asiakasnumeron tulee olla vähintään kaksi merkkiä pitkä ja siinä ei saa
     * olla etunollia, koska sitä voidaan käyttää viitteiden muodostamiseen.
     *
     * @return Tieto asiakasnumeron oikeanlaisuudesta.
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     */
    public Boolean onkoAsiakasnumeroOikeanlainen() throws NamingException, SQLException {
        Boolean b = false;
        if (asiakasnumero != null) {
            if (asiakasnumero >= 10) {
                b = true;
            }
        } else {
            b = false;
        }
        if (!b) {
            errors.put("asiakasnumero", "Asiakasnumeron tulee olla vähintään kaksi numeroa pitkä ja etunollia ei saa olla.");
        }
        if (asiakasnumero != null && getAsiakasByAsiakasnumero(asiakasnumero) != null) {
            b = false;
            errors.put("asiakasnumero", "Asiakasnumero on käytössä.");
        }
        return (b);
    }

    /**
     * Metodi kertoo onko asiakkaan postinumero oikeanlainen.
     *
     * @return Tieto postinumeron oikeanlaisuudesta.
     */
    public Boolean onkoPostinumeroOikeanlainen() {
        Boolean b = !tarkistin.koostuukoMerkkijonoNumeroista(postinumero);
        if (!b) {
            errors.put("postinumero", "Postinumeron tulee koostua numeroista.");
        }
        return (b);
    }

    /**
     * Metodi kertoo onko asiakkaan lähetettyjen laskujen lkm oikeanlainen.
     *
     * @return Tieto lähetettyjen laskujen lkm:n oikeanlaisuudesta.
     */
    public Boolean onkoLaskujaLahetettyOikeanlainen() {
        Boolean b = false;
        if (laskujaLahetetty != null) {
            if (laskujaLahetetty >= 0) {
                b = true;
            }
        } else {
            b = false;
        }
        if (!b) {
            errors.put("laskujaLahetetty", "Laskuja lähetetty - arvon tulee olla enemmän tai saman verran kuin nolla.");
        }
        return (b);
    }

    /**
     * Metodi kertoo onko asiakkaan sähköpostiosoite oikeanlainen.
     *
     * @return Tieto sähköpostiosoitteen oikeanlaisuudesta.
     */
    public Boolean onkoSahkopostiOikeanlainen() {
        Boolean b = !tarkistin.onkoEmailOsoiteValidi(email);
        if (!b) {
            errors.put("email", "Sähköpostiosoitteen tulee olla validi.");
        }
        return (b);
    }

    public Integer getAsiakasnumero() {
        return asiakasnumero;
    }

    public Integer getVersio() {
        return versio;
    }

    public String getNimi() {
        return nimi;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public String getPostinumero() {
        return postinumero;
    }

    public String getKaupunki() {
        return kaupunki;
    }

    public Integer getLaskujaLahetetty() {
        return laskujaLahetetty;
    }

    public String getEmail() {
        return email;
    }

    public Collection<String> getErrors() {
        return errors.values();
    }

    public void setAsiakasnumero(String asiakasnumero) {
        try {
            this.asiakasnumero = Integer.parseInt(asiakasnumero);
        } catch (NumberFormatException numberFormatException) {
            errors.put("asiakasnumero", "Asiakasnumeron tulee olla vähintään kaksi numeroa pitkä ja etunollia ei saa olla.");
        }
    }

    public void setVersio(Integer versio) {
        this.versio = versio;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public void setPostinumero(String postinumero) {
        this.postinumero = postinumero;
    }

    public void setKaupunki(String kaupunki) {
        this.kaupunki = kaupunki;
    }

    public void setLaskujaLahetetty(String laskujaLahetetty) {
        try {
            this.laskujaLahetetty = Integer.parseInt(laskujaLahetetty);
        } catch (NumberFormatException numberFormatException) {
            errors.put("laskujaLahetetty", "Laskuja lähetetty - arvon tulee olla enemmän tai saman verran kuin nolla.");
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
