/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Models;

import java.util.ArrayList;

/**
 * Tämän luokan ilmentymään voi tallentaa viitteen tiedot. Luokka tarjoaa
 * metodit viitteen oikeanlaisuuden tarkistamiseen. Tämän luokan metodien
 * toiminnan perustelut löytyy osoitteesta
 * <a
 * href="http://www.fkl.fi/teemasivut/sepa/tekninen_dokumentaatio/Dokumentit/kotimaisen_viitteen_rakenneohje.pdf">http://www.fkl.fi/teemasivut/sepa/tekninen_dokumentaatio/Dokumentit/kotimaisen_viitteen_rakenneohje.pdf</a>
 *
 * @author Augustus58
 */
public class Viite {

    /**
     * Viitteen viite tarkisteella merkkijonona.
     */
    private String viiteTarkisteella;
    /**
     * Tarkistin, jolla voidaan tarkistaa viitteen tietojen oikeanlaisuuksia.
     */
    private final MerkkiJaMerkkijonoTarkistin tarkistin;

    /**
     * Muodostaa uuden Luokan Viite olion.
     *
     * @param viite Viite ilman tarkistetta.
     */
    public Viite(String viite) {
        this.viiteTarkisteella = muodostaTarkisteellinenViite(viite);
        this.tarkistin = new MerkkiJaMerkkijonoTarkistin();
    }

    /**
     * Metodi kertoo onko viite sellainen, että siitä voidaan muodostaa
     * tarkisteellinen viite.
     *
     * @param viiteIlmanTarkistetta Viite ilman tarkistetta.
     * @return Tieto viitteen ilman tarkistetta oikeanlaisuudesta.
     */
    public Boolean onkoViiteKelvollinen(String viiteIlmanTarkistetta) {
        if (viiteIlmanTarkistetta.isEmpty() || viiteIlmanTarkistetta.length() < 3 || viiteIlmanTarkistetta.length() > 19) {
            return false;
        }
        if (this.tarkistin.onkoMerkkijononEnsimmainenMerkkiNolla(viiteIlmanTarkistetta)) {
            return false;
        }
        for (int i = 0; i < viiteIlmanTarkistetta.length(); i++) {
            if (!this.tarkistin.onkoMerkkiNumero(viiteIlmanTarkistetta.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodi kertoo onko tarkisteellinen viite validi.
     *
     * @param viiteTarkisteella Tarkisteellinen viite.
     * @return Tieto tarkisteellisen viitteen validiudesta.
     */
    public static Boolean onkoViiteValidi(String viiteTarkisteella) {
        MerkkiJaMerkkijonoTarkistin tarkistin = new MerkkiJaMerkkijonoTarkistin();
        if (viiteTarkisteella.isEmpty() || viiteTarkisteella.length() < 4 || viiteTarkisteella.length() > 20) {
            return false;
        }
        if (!tarkistin.koostuukoMerkkijonoNumeroista(viiteTarkisteella)) {
            return false;
        }
        if (tarkistin.onkoMerkkijononEnsimmainenMerkkiNolla(viiteTarkisteella)) {
            return false;
        }
        Viite viite = new Viite(viiteTarkisteella.substring(0, viiteTarkisteella.length() - 1));
        if (!viite.toString().equals(viiteTarkisteella)) {
            return false;
        }
        return true;
    }

    /**
     * Metodilla voidaan muodostaa tarkisteettomasta viitteestä tarkisteellinen
     * viite.
     *
     * @param viite Tarkisteeton viite.
     * @return Tarkisteellinen viite.
     */
    public final String muodostaTarkisteellinenViite(String viite) {
        return (viite + laskeTarkiste(viite).toString());
    }

    // Tähän tulee tehdä tarkisteita huonojen syötteiden varalle.
    /**
     * Metodilla voidaan laskea tarkisteettomalle viitteelle tarkiste.
     * <p>
     * Tämän metodin toimintaan löytyy tarkat perustelut luokkakuvauksen
     * linkistä.
     *
     * @param viite Tarkisteeton viite.
     * @return Tarkiste.
     */
    private Integer laskeTarkiste(String viite) {
        Integer summa = laskeTarkisteenSumma(viite);
        int alasPyoristettyOsamaara;
        Integer tarkiste;
        alasPyoristettyOsamaara = summa / 10;
        tarkiste = (10 * (alasPyoristettyOsamaara + 1) - summa);
        if (tarkiste == 10) {
            return 0;
        }
        return (tarkiste);
    }

    /**
     * Metodilla voidaan laskea tarkisteen laskemisessa tarvittava summa.
     * <p>
     * Tämän metodin toimintaan löytyy tarkat perustelut luokkakuvauksen
     * linkistä.
     *
     * @param viite Tarkisteeton viite.
     * @return Tarkisteen laskemisessa tarvittava summa.
     */
    private Integer laskeTarkisteenSumma(String viite) {
        Integer summa = 0;
        ArrayList<Integer> numerolista = luoPainoarvonumerolista();
        for (int i = 1; i <= viite.length(); i++) {
            summa = summa + numerolista.get(i - 1) * Integer.parseInt(viite.substring(viite.length() - i, viite.length() - i + 1));
        }
        return summa;
    }

    /**
     * Metodilla voidaan muodostaa metodin laskeTarkisteenSumma tarvitsema
     * painoarvonumerolista.
     * <p>
     * Tämän metodin toimintaan löytyy tarkat perustelut luokkakuvauksen
     * linkistä.
     *
     * @return Metodin laskeTarkisteenSumma tarvitsema painoarvonumerolista.
     */
    private ArrayList<Integer> luoPainoarvonumerolista() {
        ArrayList<Integer> numerolista = new ArrayList();
        for (int i = 1; i <= 7; i++) {
            numerolista.add(7);
            numerolista.add(3);
            numerolista.add(1);
        }
        return numerolista;
    }

    /**
     * Metodi palauttaa viitteen tarkisteella etunollilla siten, että pituus on
     * 20 merkkiä.
     * <p>
     * Tämän metodin toiminnan perustelut löytyvät luokkakuvauksen linkistä.
     *
     * @return Viite tarkisteella etunollilla pituus 20.
     */
    public String viiteTarkisteellaEtunollillaPituus20() {
        String etunollat = "";
        while ((etunollat + this.viiteTarkisteella).length() < 20) {
            etunollat = etunollat + "0";
        }
        return (etunollat + this.viiteTarkisteella);
    }

    /**
     * Metodi palauttaa viitteen attribuutin viiteTarkisteella.
     *
     * @return Viite tarkisteella.
     */
    @Override
    public String toString() {
        return this.viiteTarkisteella;
    }

    /**
     * Metodilla voi asettaa oliolle uuden viitteen tarkisteella.
     *
     * @param viiteIlmanTarkistetta Viite ilman tarkistetta.
     */
    public void asetaUusiViiteViitteestaIlmanTarkistetta(String viiteIlmanTarkistetta) {
        viiteTarkisteella = muodostaTarkisteellinenViite(viiteIlmanTarkistetta);
    }

    /**
     * Metodi luokan ilmentymien samuuden selvittämiseen.
     *
     * @param olio Samuusverrattava olio.
     * @return Tieto verrattavan olion ja kutsujaolion samuudesta.
     */
    @Override
    public boolean equals(Object olio) {
        if (olio == null) {
            return false;
        }
        if (getClass() != olio.getClass()) {
            return false;
        }
        return (teeEqualsVertailut(olio));
    }

    /**
     * Metodi jossa tehdään equals-metodin samuusvertailut.
     * <p>
     * Ennen tämän metodin käyttöä tulee varmistaa, että argumentti ei ole null
     * ja että argumentin luokka on Viite.
     *
     * @param olio Samuusverrattava olio.
     * @return Tieto verrattavan olion ja kutsujaolion tietojen samuudesta.
     */
    private boolean teeEqualsVertailut(Object olio) {
        Viite verrattava = (Viite) olio;
        if (this.viiteTarkisteella.equals(verrattava.viiteTarkisteella)) {
            return true;
        }
        return false;
    }

    /**
     * Luokan Asiakas hashCode-metodi.
     * <p>
     * HashCode muodostetaan olion attribuutin viiteTarkisteella hashCodesta.
     *
     * @return Kokonaisluku.
     */
    @Override
    public int hashCode() {
        return (viiteTarkisteella.hashCode());
    }
}
