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
    private Map<String, String> errors;

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
        this.errors = new HashMap<String, String>();
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

    public static Integer getHighestAsiakasnumero() {
        try {
            DBConnection dbc = new DBConnection();
            Connection c = dbc.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select max(asiakasnumero) from Asiakas");
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
    
    public static Integer getHighestVersionByAsiakasnumero(Integer asiakasnumero) {
        try {
            String sql = "select max(versio) from Asiakas where asiakasnumero = ?";
            DBConnection dbc = new DBConnection();
            Connection c = dbc.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            
            ps.setInt(1, asiakasnumero);
            
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            
            Integer i = 0;
            i = rs.getInt(1);
            
            rs.close();
            ps.close();
            c.close();
           
            return i;
        } catch (NamingException namingException) {
            return 1;
        } catch (SQLException sQLException) {
            return 1;
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
    
    public static void removeFromDb(Integer asiakasnumero) throws SQLException, NamingException {
        String sql = "delete from Asiakas where asiakasnumero = ?";
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        
        ps.setInt(1, asiakasnumero);
        
        ps.execute();
        
        ps.close();
        c.close();
    }

    /**
     * Metodi kertoo onko asiakkaan nimi oikeanlainen.
     *
     * @return Tieto nimen oikeanlaisuudesta.
     */
    public Boolean onkoNimiOikeanlainen() {
        Boolean b = !tarkistin.onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(nimi);
        if (!b) {
            errors.put("Nimi", "Nimi ei saa olla tyhjä.");
        } else {
            errors.remove("Nimi");
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
            errors.put("Kaupunki", "Kaupunki ei saa olla tyhjä.");
        } else {
            errors.remove("Kaupunki");
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
            errors.put("Katuosoite", "Katuosoite ei saa olla tyhjä.");
        } else {
            errors.remove("Katuosoite");
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
            errors.put("Asiakasnumero", "Asiakasnumeron tulee olla vähintään kaksi numeroa pitkä ja etunollia ei saa olla.");
        } else {
            errors.remove("Asiakasnumero");
        }
        return (b);
    }

    /**
     * Metodi kertoo onko asiakkaan postinumero oikeanlainen.
     *
     * @return Tieto postinumeron oikeanlaisuudesta.
     */
    public Boolean onkoPostinumeroOikeanlainen() {
        Boolean b = tarkistin.koostuukoMerkkijonoNumeroista(postinumero);
        if (!b) {
            errors.put("Postinumero", "Postinumeron tulee koostua numeroista.");
        } else {
            errors.remove("Postinumero");
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
            errors.put("Laskuja lähetetty", "Laskuja lähetetty - arvon tulee olla enemmän tai saman verran kuin nolla.");
        } else {
            errors.remove("Laskuja lähetetty");
        }
        return (b);
    }

    /**
     * Metodi kertoo onko asiakkaan sähköpostiosoite oikeanlainen.
     *
     * @return Tieto sähköpostiosoitteen oikeanlaisuudesta.
     */
    public Boolean onkoSahkopostiOikeanlainen() {
        Boolean b = tarkistin.onkoEmailOsoiteValidi(email);
        if (!b) {
            errors.put("Sähköposti", "Sähköpostiosoitteen tulee olla validi.");
        } else {
            errors.remove("Sähköposti");
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

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setAsiakasnumero(String asiakasnumero) throws NamingException, SQLException {
        try {
            this.asiakasnumero = Integer.parseInt(asiakasnumero);
            onkoAsiakasnumeroOikeanlainen();
        } catch (NumberFormatException numberFormatException) {
            errors.put("asiakasnumero", "Asiakasnumeron tulee olla vähintään kaksi numeroa pitkä ja etunollia ei saa olla.");
        }
    }

    public void setVersio(Integer versio) {
        this.versio = versio;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
        onkoNimiOikeanlainen();
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
        onkoKatuosoiteOikeanlainen();
    }

    public void setPostinumero(String postinumero) {
        this.postinumero = postinumero;
        onkoPostinumeroOikeanlainen();
    }

    public void setKaupunki(String kaupunki) {
        this.kaupunki = kaupunki;
        onkoKaupunkiOikeanlainen();
    }

    public void setLaskujaLahetetty(String laskujaLahetetty) {
        try {
            this.laskujaLahetetty = Integer.parseInt(laskujaLahetetty);
            onkoLaskujaLahetettyOikeanlainen();
        } catch (NumberFormatException numberFormatException) {
            errors.put("laskujaLahetetty", "Laskuja lähetetty - arvon tulee olla enemmän tai saman verran kuin nolla.");
        }
    }

    public void setEmail(String email) {
        this.email = email;
        onkoSahkopostiOikeanlainen();
    }

}
