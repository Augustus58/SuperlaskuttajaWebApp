/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Models;

import org.apache.commons.validator.GenericValidator;

/**
 * Luokan ilmentymällä voi tehdä erilaisia tarkistuksia merkeille ja
 * merkkijonoille.
 *
 * @author Augustus58
 */
public class MerkkiJaMerkkijonoTarkistin {

    /**
     * Isot aakkoset A-Z käytettäväksi mm. metodissa onkoMerkkiKirjainAZ.
     */
    private final String isotAakkosetAZ;
    /**
     * Numerot 0-9 ja välilyönti " " käytettäviksi mm. metodissa
     * onkoMerkkiNumeroValiviivaTaiValilyonti.
     */
    private final String numerotValiviivaJaValilyonti;

    public MerkkiJaMerkkijonoTarkistin() {
        this.isotAakkosetAZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.numerotValiviivaJaValilyonti = "0123456789- ";
    }

    /**
     * Metodi tarkistaa onko argumentti numero 0-9.
     *
     * @param merkki Tarkistettava merkki.
     * @return Tieto merkin numeroudesta.
     */
    public Boolean onkoMerkkiNumero(Character merkki) {
        if (Character.isDigit(merkki)) {
            return true;
        }
        return false;
    }

    /**
     * Metodi tarkistaa onko argumentti kirjain A-Z.
     *
     * @param merkki Tarkistettava merkki.
     * @return Tieto onko merkki kirjain vai ei.
     */
    public Boolean onkoMerkkiKirjainAZ(Character merkki) {
        for (int i = 0; i < this.isotAakkosetAZ.length(); i++) {
            if (merkki.equals(this.isotAakkosetAZ.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodi tarkistaa koostuuko argumentti numeroista.
     *
     * @param merkkijono Tarkistettava merkkijono.
     * @return Tieto koostuuko merkkijono numeroista vai ei.
     */
    public Boolean koostuukoMerkkijonoNumeroista(String merkkijono) {
        if (merkkijono.isEmpty()) {
            return false;
        }
        for (int i = 0; i < merkkijono.length(); i++) {
            if (!onkoMerkkiNumero(merkkijono.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodi tarkistaa onko argumentti jokin merkeistä 0-9, "-" tai " ".
     *
     * @param merkki Tarkistettava merkki.
     * @return Tieto onko merkki jokin merkeistä 0-9, "-" tai " " vai ei.
     */
    public Boolean onkoMerkkiNumeroValiviivaTaiValilyonti(Character merkki) {
        for (int i = 0; i < this.numerotValiviivaJaValilyonti.length(); i++) {
            if (merkki.equals(this.numerotValiviivaJaValilyonti.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodi tarkistaa koostuuko argumentti vähintään yhdestä numerosta ja
     * sisältääkö se lisäksi mahdollisesti vain numeroita, väliviivoja tai
     * välilyöntejä.
     *
     * @param merkkijono Tarkistettava merkkijono.
     * @return Tieto koostuuko merkkijono vähintään yhdestä numerosta ja
     * sisältääkö se lisäksi mahdollisesti vain numeroita, väliviivoja tai
     * välilyöntejä.
     */
    public Boolean sisaltaakoMerkkijNumeroitaJaKoostuukoMerkkijNumeroistaValiviivoistaTaiValilyonneista(String merkkijono) {
        if (merkkijono.isEmpty()) {
            return false;
        }
        if (!sisaltaakoMerkkijonoVahintaanYhdenNumeron(merkkijono)) {
            return false;
        }
        for (int i = 0; i < merkkijono.length(); i++) {
            if (!onkoMerkkiNumeroValiviivaTaiValilyonti(merkkijono.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodi tarkistaa koostuuko argumentti vähintään yhdestä numerosta.
     *
     * @param merkkijono Tarkistettava merkkijono.
     * @return Tieto koostuuko merkkijono vähintään yhdestä numerosta.
     */
    public Boolean sisaltaakoMerkkijonoVahintaanYhdenNumeron(String merkkijono) {
        for (int i = 0; i < merkkijono.length(); i++) {
            if (onkoMerkkiNumero(merkkijono.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodi tarkistaa voiko argumentin muuntaa kokonaisluvuksi käyttäen
     * metodia Integer-luokan metodia parseInt();
     *
     * @param merkkijono Tarkistettava merkkijono.
     * @return Tieto koostuuko merkkijono vain numeroista.
     */
    public Boolean voikoMerkkijononMuuttaaKokonaisluvuksi(String merkkijono) {
        for (int i = 0; i < merkkijono.length(); i++) {
            if (!onkoMerkkiNumero(merkkijono.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodi tarkistaa onko merkkijono tyhjä tai koostuuko se vain
     * välilyönneistä.
     *
     * @param merkkijono Tarkistettava merkkijono.
     * @return Tieto onko merkkijono tyhjä tai koostuuko se vain välilyönneistä.
     */
    public Boolean onkoMerkkijonoTyhjaTaiKoostuukoSeValilyonneista(String merkkijono) {
        if (merkkijono.isEmpty()) {
            return true;
        }
        if (!merkkijono.isEmpty()) {
            for (int i = 0; i < merkkijono.length(); i++) {
                if (!merkkijono.substring(i, i + 1).equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Metodi tarkistaa onko merkkijonona annettu email-osoite validi.
     * <p>
     * Tarkistukseen käytetään Apache Commons repon validator luokkaa
     * GenericValidator.
     *
     * @param osoite Tarkistettava email-osoite.
     * @return Tieto onko merkkijonona annettu email-osoite validi.
     */
    public Boolean onkoEmailOsoiteValidi(String osoite) {
        return (GenericValidator.isEmail(osoite));
    }

    /**
     * Metodi tarkistaa onko merkkijono muotoa "nn.nn.nnnn".
     *
     * @param merkkijono Tarkistettava merkkijono.
     * @return Tieto onko merkkijono muotoa "nn.nn.nnnn".
     */
    public Boolean onkoMerkkijonoMuotoaNnPisteNnPisteNnnn(String merkkijono) {
        if (merkkijono.length() != 10) {
            return false;
        }
        if (koostuukoMerkkijonoNumeroista(merkkijono.substring(0, 2))
                && merkkijono.substring(2, 3).equals(".")
                && koostuukoMerkkijonoNumeroista(merkkijono.substring(3, 5))
                && merkkijono.substring(5, 6).equals(".")
                && koostuukoMerkkijonoNumeroista(merkkijono.substring(6, 10))) {
            return true;
        }
        return false;
    }
    
    /**
     * Metodi tarkistaa onko merkkijono muotoa "nn.nn.nnnn nn.nn".
     *
     * @param merkkijono Tarkistettava merkkijono.
     * @return Tieto onko merkkijono muotoa "nn.nn.nnnn nn.nn".
     */
    public Boolean onkoMerkkijonoMuotoaNnPisteNnPisteNnnnValiNnPisteNn(String merkkijono) {
        if (merkkijono.length() != 16) {
            return false;
        }
        if (koostuukoMerkkijonoNumeroista(merkkijono.substring(0, 2))
                && merkkijono.substring(2, 3).equals(".")
                && koostuukoMerkkijonoNumeroista(merkkijono.substring(3, 5))
                && merkkijono.substring(5, 6).equals(".")
                && koostuukoMerkkijonoNumeroista(merkkijono.substring(6, 10))
                && merkkijono.substring(10, 11).equals(" ")
                && koostuukoMerkkijonoNumeroista(merkkijono.substring(11, 13))
                && merkkijono.substring(13, 14).equals(".")
                && koostuukoMerkkijonoNumeroista(merkkijono.substring(14, 16))) {
            return true;
        }
        return false;
    }

    /**
     * Metodi tarkistaa onko merkkijonona annettu päivämäärä muotoa "dd.MM.yyyy"
     * validi.
     * <p>
     * Tarkistukseen käytetään Apache Commons repon validator luokkaa
     * GenericValidator.
     *
     * @param merkkijono Tarkistettava päivämäärä merkkijonona.
     * @return Tieto onko merkkijonona annettu päivämäärä validi vai ei.
     */
    public Boolean onkoPvmMerkkijonoMuotoaNnPisteNnPisteNnnnValidi(String merkkijono) {
        return (GenericValidator.isDate(merkkijono, "dd.MM.yyyy", true));
    }
    
    /**
     * Metodi tarkistaa onko merkkijonona annettu päivämäärä muotoa "dd.MM.yyyy hh.mm"
     * validi.
     * <p>
     * Tarkistukseen käytetään Apache Commons repon validator luokkaa
     * GenericValidator.
     *
     * @param merkkijono Tarkistettava päivämäärä merkkijonona.
     * @return Tieto onko merkkijonona annettu päivämäärä validi vai ei.
     */
    public Boolean onkoPvmMerkkijonoMuotoaNnPisteNnPisteNnnnValiNnPisteNnValidi(String merkkijono) {
        return (GenericValidator.isDate(merkkijono, "dd.MM.yyyy HH.mm", true));
    }
    
    /**
     * Metodi tarkistaa onko merkkijonono muotoa "dd.MM.yyyy hh.mm" oleva
     * validi päivämäärä.
     * <p>
     * Tarkistukseen käytetään mm. Apache Commons repon validator luokkaa
     * GenericValidator.
     *
     * @param merkkijono Tarkistettava merkkijono.
     * @return Tieto onko merkkijono muotoa "dd.MM.yyyy hh.mm" oleva päivämäärä vai ei.
     */
    public Boolean onkoMerkkijonoMuotoaNnPisteNnPisteNnnnValiNnPisteNnOlevaPvm(String merkkijono) {
        if (!onkoMerkkijonoMuotoaNnPisteNnPisteNnnnValiNnPisteNn(merkkijono)) {
            return false;
        }
        if (!onkoPvmMerkkijonoMuotoaNnPisteNnPisteNnnnValiNnPisteNnValidi(merkkijono)) {
            return false;
        }
        return true;
    }
    
    /**
     * Metodi tarkistaa onko merkkijonono muotoa "dd.MM.yyyy" oleva
     * validi päivämäärä.
     * <p>
     * Tarkistukseen käytetään mm. Apache Commons repon validator luokkaa
     * GenericValidator.
     *
     * @param merkkijono Tarkistettava merkkijono.
     * @return Tieto onko merkkijono muotoa "dd.MM.yyyy" oleva päivämäärä vai ei.
     */
    public Boolean onkoMerkkijonoMuotoaNnPisteNnPisteNnnnOlevaPvm(String merkkijono) {
        if (!onkoMerkkijonoMuotoaNnPisteNnPisteNnnn(merkkijono)) {
            return false;
        }
        if (!onkoPvmMerkkijonoMuotoaNnPisteNnPisteNnnnValidi(merkkijono)) {
            return false;
        }
        return true;
    }

    /**
     * Metodi tarkistaa onko merkkijonon ensimmäinen merkki "0".
     * <p>
     * Tätä metodia käytetään erityisesti estämään merkillä "0" alkavat
     * asiakasnumerot.
     *
     * @param merkkijono Tarkistettava merkkijono.
     * @return Tieto onko merkkijonon ensimmäinen merkki "0".
     */
    public Boolean onkoMerkkijononEnsimmainenMerkkiNolla(String merkkijono) {
        if (merkkijono.isEmpty()) {
            return false;
        }
        if (merkkijono.substring(0, 1).equals("0")) {
            return true;
        }
        return false;
    }

    public String getIsotAakkosetAZ() {
        return this.isotAakkosetAZ;
    }
}
