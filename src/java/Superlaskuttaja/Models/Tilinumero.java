/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Models;

import java.math.BigInteger;

/**
 * Luokan ilmentymään voi tallettaa tilinumeron tiedot sisältäen pankin ja
 * "SWIFT BIC"-koodin. Osaan tämän luokan metodeista löytyy toiminnan perustelut
 * osoitteesta
 * <a
 * href="http://www.fkl.fi/teemasivut/sepa/tekninen_dokumentaatio/Dokumentit/IBAN_ja_BIC_maksuliikenteessa.pdf">http://www.fkl.fi/teemasivut/sepa/tekninen_dokumentaatio/Dokumentit/IBAN_ja_BIC_maksuliikenteessa.pdf</a>
 *
 * @author Augustus58
 */
public class Tilinumero {

    /**
     * Tilinumero tilinumero merkkijonona.
     */
    private String tilinumero;
    /**
     * Tilinumeron pankki.
     */
    private String pankki;
    /**
     * Tilinumeroon liittyvä SWIFT/BIC.
     */
    private String swiftBic;
    /**
     * Tarkistin, jolla voidaan tarkistaa tilinumeron tietojen oikeanlaisuuksia.
     */
    private final MerkkiJaMerkkijonoTarkistin tarkistin;

    public Tilinumero(String tilinumero, String pankki, String swiftBic) {
        this.tilinumero = tilinumero;
        this.tarkistin = new MerkkiJaMerkkijonoTarkistin();
        this.pankki = pankki;
        this.swiftBic = swiftBic;
    }

    /**
     * Metodi tarkistaa onko annettu tilinumero validi.
     * <p>
     * Luokkakuvauksen linkistä löytyy tarkat perustelut tarkistusalgoritmille.
     *
     * @param tilinumero Tarkistettava tilinumero.
     * @return Tieto tilinumeron validiudesta.
     */
    public static Boolean tarkistaTilinumero(String tilinumero) {
        BigInteger yksi = new BigInteger("1");
        BigInteger yhdeksankymmentaseitseman = new BigInteger("97");
        if (tilinumero.length() <= 4 || tilinumero.length() > 18) {
            return false;
        }

        if (muunnaTilinumeroMaakoodiJaTarkisteSiirrettyLoppuunKokonaisluvuksi(siirraMaakodiJaTarkisteLoppuun(tilinumero)).mod(yhdeksankymmentaseitseman).equals(yksi)) {
            return true;
        }

        return false;

    }

    /**
     * Metodi siirtää annetun tilinumeron maakoodin ja tarkisteen tilinumeron
     * loppuun.
     *
     * @param tilinumero Muutettava tilinumero.
     * @return Tilinumero, jolla siirto on suoritettu.
     */
    private static String siirraMaakodiJaTarkisteLoppuun(String tilinumero) {
        return (tilinumero.substring(4) + tilinumero.substring(0, 4));
    }

    /**
     * Metodi muuntaa annetun tilinumeron, jonka maakoodi ja tarkiste on
     * siirretty tilinumeron loppuun kokonaisluvuksi.
     * <p>
     * Luokkakuvauksen linkistä löytyy tarkat perustelut muuntoalgoritmille.
     *
     * @param tilinumero Muutettava tilinumero, jonka maakoodi ja tarkiste on
     * siirretty loppuun.
     * @return Muuntoalgoritmin tulos.
     */
    private static BigInteger muunnaTilinumeroMaakoodiJaTarkisteSiirrettyLoppuunKokonaisluvuksi(String tilinumeroMaakoodiJaTarkisteSiirrettyLoppuun) {
        MerkkiJaMerkkijonoTarkistin tarkistin1 = new MerkkiJaMerkkijonoTarkistin();
        String muunnettuMerkkijono = "";
        for (int i = 0; i < tilinumeroMaakoodiJaTarkisteSiirrettyLoppuun.length(); i++) {
            if (tarkistin1.onkoMerkkiKirjainAZ(tilinumeroMaakoodiJaTarkisteSiirrettyLoppuun.charAt(i))) {
                muunnettuMerkkijono = muunnettuMerkkijono + muunnaKirjainKokonaisluvuksi(tilinumeroMaakoodiJaTarkisteSiirrettyLoppuun.charAt(i)).toString();
            } else {
                muunnettuMerkkijono = muunnettuMerkkijono + tilinumeroMaakoodiJaTarkisteSiirrettyLoppuun.charAt(i);
            }
        }
        if (tarkistin1.voikoMerkkijononMuuttaaKokonaisluvuksi(muunnettuMerkkijono)) {
            BigInteger kokonaisluku = new BigInteger(muunnettuMerkkijono);
            return (kokonaisluku);
        }
        BigInteger nolla = new BigInteger("0");
        return nolla;

    }

    /**
     * Metodi muuntaa annetun kirjaimen kokonaisluvuksi.
     * <p>
     * Luokkakuvauksen linkistä löytyy tarkat perustelut muuntoalgoritmille.
     *
     * @param kirjain Muutettava kirjain.
     * @return Muuntoalgoritmin tulos.
     */
    private static Integer muunnaKirjainKokonaisluvuksi(Character kirjain) {
        MerkkiJaMerkkijonoTarkistin tarkistin1 = new MerkkiJaMerkkijonoTarkistin();
        int k = 0;
        for (int i = 0; i < tarkistin1.getIsotAakkosetAZ().length(); i++) {
            if (tarkistin1.getIsotAakkosetAZ().substring(i, i + 1).equals(kirjain.toString())) {
                k = i + 10;
            }
        }
        return k;
    }

    /**
     * Metodi kertoo onko tilinumeron pankin nimi oikeanlainen.
     *
     * @return Tieto pankin nimen oikeanlaisuudesta.
     */
    public Boolean onkoPankkiOikeanlainen() {
        return (!tarkistin.onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(pankki));
    }

    /**
     * Metodi kertoo onko tilinumeron "SWIFT BIC"-koodi oikeanlainen.
     *
     * @return Tieto pankin "SWIFT BIC"-koodin oikeanlaisuudesta.
     */
    public Boolean onkoSwiftBicOikeanlainen() {
        return (!tarkistin.onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(swiftBic));
    }

    public String getTilinumero() {
        return this.tilinumero;
    }

    /**
     * Metodi palauttaa tilinumeron ilman maatunnusta.
     *
     * @return Tilinumero ilman maatunnusta.
     */
    public String tilinumeroIlmanMaatunnusta() {
        return this.tilinumero.substring(2);
    }

    public String getPankki() {
        return pankki;
    }

    public String getSwiftBic() {
        return swiftBic;
    }

    public void setTilinumero(String tilinumero) {
        this.tilinumero = tilinumero;
    }

    public void setPankki(String pankki) {
        this.pankki = pankki;
    }

    public void setSwiftBic(String swiftBic) {
        this.swiftBic = swiftBic;
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
     * ja että argumentin luokka on Tilinumero.
     *
     * @param olio Samuusverrattava olio.
     * @return Tieto verrattavan olion ja kutsujaolion tietojen samuudesta.
     */
    private boolean teeEqualsVertailut(Object olio) {
        Tilinumero verrattava = (Tilinumero) olio;
        if (this.tilinumero.equals(verrattava.tilinumero)
                && this.pankki.equals(verrattava.pankki)
                && this.swiftBic.equals(verrattava.swiftBic)) {
            return true;
        }
        return false;
    }

    /**
     * Luokan Asiakas hashCode-metodi.
     * <p>
     * HashCode muodostetaan summaamalla attribuuttien tilinumero, pankki ja
     * swiftBic hashCodet.
     *
     * @return Kokonaisluku.
     */
    @Override
    public int hashCode() {
        return (tilinumero.hashCode() + pankki.hashCode() + swiftBic.hashCode());
    }

}
