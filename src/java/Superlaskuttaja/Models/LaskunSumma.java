/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Models;

/**
 * Luokan ilmentymään voi tallettaa tiedot laskun summasta. Luokka tarjoaa
 * metodit tietojen oikeanlaisuuden tarkistamiseen. Tämän luokan
 * tarkistusmetodien toiminnan perustelut löytyy osoitteesta
 * <a
 * href="http://www.fkl.fi/teemasivut/sepa/tekninen_dokumentaatio/Dokumentit/Pankkiviivakoodi-opas.pdf">http://www.fkl.fi/teemasivut/sepa/tekninen_dokumentaatio/Dokumentit/Pankkiviivakoodi-opas.pdf</a>
 *
 * @author Augustus58
 */
public class LaskunSumma {

    /**
     * Summan eurot. Esim. 456.
     */
    private Integer eurot;
    /**
     * Summan sentit. Esim. 1, 99 tai 55.
     */
    private Integer sentit;

    public LaskunSumma(Integer eurot, Integer sentit) {
        this.eurot = eurot;
        this.sentit = sentit;
    }

    /**
     * Metodi antaa olion attribuutin eurot etunollilla niin, että pituus on
     * kuusi.
     * <p>
     * Tätä metodia tarvitaan erityisesti luokan Pankkiviivakoodi metodissa
     * muodostaPankkiviivakoodiIlmanTarkistettaJaLopetusta.
     *
     * @return Eurot etunollilla pituus kuusi.
     */
    public String eurotStringEtunollillaPituusKuusi() {
        String etunollat = "";
        while ((etunollat + this.eurot.toString()).length() < 6) {
            etunollat = etunollat + "0";
        }
        return (etunollat + this.eurot.toString());
    }

    /**
     * Metodi antaa olion attribuutin sentit etunollilla niin, että pituus on
     * kaksi.
     * <p>
     * Tätä metodia tarvitaan erityisesti luokan Pankkiviivakoodi metodissa
     * muodostaPankkiviivakoodiIlmanTarkistettaJaLopetusta.
     *
     * @return Sentit etunollilla pituus kaksi.
     */
    public String sentitStringEtunollillaPituusKaksi() {
        String etunollat = "";
        while ((etunollat + this.sentit.toString()).length() < 2) {
            etunollat = etunollat + "0";
        }
        return (etunollat + this.sentit.toString());
    }

    /**
     * Metodilla tarkistetaan, että argumentti täyttää
     * pankkiviivakoodistandardin.
     * <p>
     * Katso lisätietoja standardista luokkakuvauksen linkistä.
     *
     * @param eurot Tarkistettava euromäärä.
     * @return Tieto argumentin oikeanlaisuudesta pankkiviivakoodistandardia
     * ajatellen.
     */
    public static Boolean tarkistaEurot(Integer eurot) {
        if (eurot >= 0 && eurot <= 999999) {
            return true;
        }
        return false;
    }

    /**
     * Metodilla tarkistetaan, että argumentti täyttää
     * pankkiviivakoodistandardin.
     * <p>
     * Katso lisätietoja standardista luokkakuvauksen linkistä.
     *
     * @param sentit Tarkistettava senttimäärä.
     * @return Tieto argumentin oikeanlaisuudesta pankkiviivakoodistandardia
     * ajatellen.
     */
    public static Boolean tarkistaSentit(Integer sentit) {
        if (sentit >= 0 && sentit <= 99) {
            return true;
        }
        return false;
    }

    public void setEurot(Integer eurot) {
        this.eurot = eurot;
    }

    public void setSentit(Integer sentit) {
        this.sentit = sentit;
    }

    /**
     * Metodi antaa olion tiedot merkkijonona.
     *
     * @return Olion tiedot muodossa "eurot.sentit", esim. "12.89".
     */
    @Override
    public String toString() {
        if (eurot == 0 && sentit == 0) {
            return ("0.0");
        }
        if (sentit >= 10) {
            return (eurot.toString() + "." + sentit.toString());
        } else {
            return (eurot.toString() + "." + "0" + sentit.toString());
        }
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
     * ja että argumentin luokka on LaskunSumma.
     *
     * @param olio Samuusverrattava olio.
     * @return Tieto verrattavan olion ja kutsujaolion tietojen samuudesta.
     */
    private boolean teeEqualsVertailut(Object olio) {
        LaskunSumma verrattava = (LaskunSumma) olio;
        if (this.eurot.equals(verrattava.eurot)
                && this.sentit.equals(verrattava.sentit)) {
            return true;
        }
        return false;
    }

    /**
     * Luokan LaskunSumma hashCode-metodi.
     * <p>
     * HashCode muodostetaan summaamalla attribuuttien eurot ja sentit
     * hashCodet.
     *
     * @return Kokonaisluku.
     */
    @Override
    public int hashCode() {
        return (eurot.hashCode() + sentit.hashCode());
    }
}
