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
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author Augustus58
 */
public class Laskuttaja {

    private String yrityksenNimi;
    private Integer versio;
    private String alvTunniste;
    private String nimi;
    private String katuosoite;
    private String postinumero;
    private String kaupunki;
    private String puhelinnumero;
    private String email;
    private Integer laskujaLahetetty;
    private String tilinumero;
    private String tilinumeronPankki;
    private String tilinumeronSwiftBic;
    private MerkkiJaMerkkijonoTarkistin tarkistin;
    private Map<String, String> errors;

    public Laskuttaja() {
        this.tarkistin = new MerkkiJaMerkkijonoTarkistin();
        this.errors = new HashMap<String, String>();
    }

    public Laskuttaja(String yrityksenNimi, Integer versio, String alvTunniste, String nimi, String katuosoite, String postinumero, String kaupunki, String puhelinnumero, String sahkopostiOsoite, Integer laskujaLahetetty, String tilinumero, String tilinumeronPankki, String tilinumeronSwift) {
        this.yrityksenNimi = yrityksenNimi;
        this.versio = versio;
        this.alvTunniste = alvTunniste;
        this.nimi = nimi;
        this.katuosoite = katuosoite;
        this.postinumero = postinumero;
        this.kaupunki = kaupunki;
        this.puhelinnumero = puhelinnumero;
        this.email = sahkopostiOsoite;
        this.laskujaLahetetty = laskujaLahetetty;
        this.tilinumero = tilinumero;
        this.tilinumeronPankki = tilinumeronPankki;
        this.tilinumeronSwiftBic = tilinumeronSwift;
        this.tarkistin = new MerkkiJaMerkkijonoTarkistin();
        this.errors = new HashMap<String, String>();
    }

    public static boolean isLaskuttajaAdded() throws NamingException, SQLException {
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("select distinct yrityksenNimi, versio\n"
                + "from Laskuttaja  \n"
                + "");

        boolean b = false;

        if (rs.next()) {
            b = true;
        }

        rs.close();
        st.close();
        c.close();

        return b;
    }

    public void addToDb() throws NamingException, SQLException {
        String sql = "INSERT INTO Laskuttaja (yrityksenNimi, versio, alvTunniste, nimi, katuosoite, postinumero, kaupunki, puhelinnumero, sahkopostiOsoite, laskujaLahetetty, tilinumero, tilinumeronPankki, tilinumeronSwiftBic) \n"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setString(1, this.getYrityksenNimi());
        ps.setInt(2, this.getVersio());
        ps.setString(3, this.getAlvTunniste());
        ps.setString(4, this.getNimi());
        ps.setString(5, this.getKatuosoite());
        ps.setString(6, this.getPostinumero());
        ps.setString(7, this.getKaupunki());
        ps.setString(8, this.getPuhelinnumero());
        ps.setString(9, this.getEmail());
        ps.setInt(10, this.getLaskujaLahetetty());
        ps.setString(11, this.getTilinumero());
        ps.setString(12, this.getTilinumeronPankki());
        ps.setString(13, this.getTilinumeronSwiftBic());

        ps.execute();

        ps.close();
        c.close();
    }
    
    public void update() throws NamingException, SQLException {
        String sql = "UPDATE Laskuttaja SET (yrityksenNimi, versio, alvTunniste, nimi, katuosoite, postinumero, kaupunki, puhelinnumero, sahkopostiOsoite, laskujaLahetetty, tilinumero, tilinumeronPankki, tilinumeronSwiftBic) \n"
                + "= (?,?,?,?,?,?,?,?,?,?,?,?,?)\n"
                + "where yrityksenNimi = ?\n"
                + "and versio = ?\n"
                + "";
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setString(1, this.getYrityksenNimi());
        ps.setInt(2, this.getVersio());
        ps.setString(3, this.getAlvTunniste());
        ps.setString(4, this.getNimi());
        ps.setString(5, this.getKatuosoite());
        ps.setString(6, this.getPostinumero());
        ps.setString(7, this.getKaupunki());
        ps.setString(8, this.getPuhelinnumero());
        ps.setString(9, this.getEmail());
        ps.setInt(10, this.getLaskujaLahetetty());
        ps.setString(11, this.getTilinumero());
        ps.setString(12, this.getTilinumeronPankki());
        ps.setString(13, this.getTilinumeronSwiftBic());
        ps.setString(14, this.getYrityksenNimi());
        ps.setInt(15, this.getVersio());

        ps.execute();

        ps.close();
        c.close();
    }
    
    public static Laskuttaja getLaskuttaja() throws NamingException, SQLException {
        DBConnection dbc = new DBConnection();
        Connection c = dbc.getConnection();
        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("select distinct yrityksenNimi, versio, alvTunniste, nimi, katuosoite, postinumero, kaupunki, puhelinnumero, sahkopostiosoite, laskujaLahetetty, tilinumero, tilinumeronPankki, tilinumeronSwiftBic\n"
                + "from Laskuttaja\n"
                + "where Laskuttaja.versio = (select max(versio) from Laskuttaja)\n"
                + "");

        Laskuttaja l = null;

        if (rs.next()) {
            l = new Laskuttaja(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getString(11), rs. getString(12), rs.getString(13));
        }

        rs.close();
        st.close();
        c.close();

        return l;

    }
    
    public static Integer getHighestVersion() {
        try {
            DBConnection dbc = new DBConnection();
            Connection c = dbc.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select max(versio) from Laskuttaja");
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
    
    public static Integer getLaskujaLahetettyFromDb() throws NamingException, SQLException {
    DBConnection dbc = new DBConnection();
            Connection c = dbc.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select laskujaLahetetty from Laskuttaja\n"
                    + "where versio = (select max(versio) from Laskuttaja)\n"
                    + "");
            rs.next();
            Integer i = rs.getInt(1);
            rs.close();
            st.close();
            c.close();
            return i;
    }

    /**
     * Metodi kertoo onko laskuttajan nimi oikeanlainen.
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
     * Metodi kertoo onko laskuttajan katuosoite oikeanlainen.
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
     * Metodi kertoo onko laskuttajan postinumero oikeanlainen.
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
     * Metodi kertoo onko laskuttajan kaupunki oikeanlainen.
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
     * Metodi kertoo onko laskuttajan yrityksen nimi oikeanlainen.
     *
     * @return Tieto yrityksen nimen oikeanlaisuudesta.
     */
    public Boolean onkoYrityksenNimiOikeanlainen() {
        Boolean b = !tarkistin.onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(yrityksenNimi);
        if (!b) {
            errors.put("Yrityksen nimi", "Yrityksen nimi ei saa olla tyhjä.");
        } else {
            errors.remove("Yrityksen nimi");
        }
        return (b);
    }

    /**
     * Metodi kertoo onko laskuttajan alv-tunniste oikeanlainen.
     *
     * @return Tieto alv-tunnisteen oikeanlaisuudesta.
     */
    public Boolean onkoAlvOikeanlainen() {
        Boolean b = !tarkistin.onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(alvTunniste);
        Boolean c = alvTunniste.length() == 10;
        Boolean d = false;
        Boolean e = false;
        if (c) {
            d = alvTunniste.substring(0, 2).equals("FI");
            e = tarkistin.koostuukoMerkkijonoNumeroista(alvTunniste.substring(2, 10));
        }
        if (!b || !c || !d || !e) {
            errors.put("Alv-tunniste", "Alv-tunniste ei saa olla tyhjä. Alv-tunnisteen tulee olla muotoa FI12345678.");
        } else {
            errors.remove("Alv-tunniste");
        }
        return (b);
    }

    /**
     * Metodi kertoo onko laskuttajan tilinumero oikeanlainen.
     *
     * @return Tieto tilinumeron oikeanlaisuudesta.
     */
    public Boolean onkoTilinumeroOikeanlainen() {
        Boolean b = !tarkistin.onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(tilinumero);
        Boolean c = Tilinumero.tarkistaTilinumero(tilinumero);
        if (!b || !c) {
            errors.put("Tilinumero", "Tilinumero ei saa olla tyhjä. Tilinumeron tulee olla muotoa FI1234567812345678 ja validi.");
        } else {
            errors.remove("Tilinumero");
        }
        return (b);
    }

    /**
     * Metodi kertoo onko laskuttajan pankin nimi oikeanlainen.
     *
     * @return Tieto pankin nimen oikeanlaisuudesta.
     */
    public Boolean onkoPankkiOikeanlainen() {
        Boolean b = !tarkistin.onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(tilinumeronPankki);
        if (!b) {
            errors.put("Tilinumeron pankki", "Tilinumeron pankki ei saa olla tyhjä.");
        } else {
            errors.remove("Tilinumeron pankki");
        }
        return (b);
    }

    /**
     * Metodi kertoo onko laskuttajan pankin "SWIFT BIC" oikeanlainen.
     *
     * @return Tieto pankin "SWIFT BIC"-koodin oikeanlaisuudesta.
     */
    public Boolean onkoSwiftBicOikeanlainen() {
        Boolean b = !tarkistin.onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(tilinumeronSwiftBic);
        if (!b) {
            errors.put("Tilinumeron Swift/Bic", "Tilinumeron Swift/Bic ei saa olla tyhjä.");
        } else {
            errors.remove("Tilinumeron Swift/Bic");
        }
        return (b);
    }

    /**
     * Metodi kertoo onko laskuttajan puhelinnumero oikeanlainen.
     *
     * @return Tieto puhelinnumeron oikeanlaisuudesta.
     */
    public Boolean onkoPuhelinnumeroOikeanlainen() {
        Boolean b = tarkistin.sisaltaakoMerkkijNumeroitaJaKoostuukoMerkkijNumeroistaValiviivoistaTaiValilyonneista(puhelinnumero);
        if (!b) {
            errors.put("Puhelinnumero", "Puhelinnumero ei saa olla tyhjä ja sen tulee koostua numeroista, väliviivoista tai välilyönneistä.");
        } else {
            errors.remove("Puhelinnumero");
        }
        return (b);
    }

    /**
     * Metodi kertoo onko laskuttajan sähköpostiosoite oikeanlainen.
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

    /**
     * Metodi kertoo onko laskuttajan sähköpostiosoite oikeanlainen.
     *
     * @return Tieto sähköpostiosoitteen oikeanlaisuudesta.
     */
    public Boolean onkoLaskujaLahetettyOikeanlainen() {
        Boolean b = laskujaLahetetty >= 0;

        if (!b) {
            errors.put("Laskuja lähetetty", "Lähetettyjen laskujen lukumäärän tulee olla enemmän tai samanverran kuin 0.");
        } else {
            errors.remove("Laskuja lähetetty");
        }
        return (b);
    }

    public String getYrityksenNimi() {
        return yrityksenNimi;
    }

    public Integer getVersio() {
        return versio;
    }

    public String getAlvTunniste() {
        return alvTunniste;
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

    public String getPuhelinnumero() {
        return puhelinnumero;
    }

    public String getEmail() {
        return email;
    }

    public Integer getLaskujaLahetetty() {
        return laskujaLahetetty;
    }

    public String getTilinumero() {
        return tilinumero;
    }

    public String getTilinumeronPankki() {
        return tilinumeronPankki;
    }

    public String getTilinumeronSwiftBic() {
        return tilinumeronSwiftBic;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setYrityksenNimi(String yrityksenNimi) {
        this.yrityksenNimi = yrityksenNimi;
        onkoYrityksenNimiOikeanlainen();
    }

    public void setVersio(Integer versio) {
        this.versio = versio;
    }

    public void setAlvTunniste(String alvTunniste) {
        this.alvTunniste = alvTunniste;
        onkoAlvOikeanlainen();
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

    public void setPuhelinnumero(String puhelinnumero) {
        this.puhelinnumero = puhelinnumero;
        onkoPuhelinnumeroOikeanlainen();
    }

    public void setEmail(String sahkopostiOsoite) {
        this.email = sahkopostiOsoite;
        onkoSahkopostiOikeanlainen();
    }

    public void setLaskujaLahetetty(String laskujaLahetetty) {
        try {
            this.laskujaLahetetty = Integer.parseInt(laskujaLahetetty);
            onkoLaskujaLahetettyOikeanlainen();
        } catch (NumberFormatException numberFormatException) {
            errors.put("Laskuja lähetetty", "Lähetettyjen laskujen lukumäärän tulee olla enemmän tai samanverran kuin 0.");
        }
    }

    public void setTilinumero(String tilinumero) {
        this.tilinumero = tilinumero;
        onkoTilinumeroOikeanlainen();
    }

    public void setTilinumeronPankki(String tilinumeronPankki) {
        this.tilinumeronPankki = tilinumeronPankki;
        onkoTilinumeroOikeanlainen();
    }

    public void setTilinumeronSwiftBic(String tilinumeronSwift) {
        this.tilinumeronSwiftBic = tilinumeronSwift;
        onkoTilinumeroOikeanlainen();
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

}
