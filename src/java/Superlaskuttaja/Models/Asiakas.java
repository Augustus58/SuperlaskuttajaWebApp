/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Models;

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

    public Asiakas() {
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

    public void setAsiakasnumero(Integer asiakasnumero) {
        this.asiakasnumero = asiakasnumero;
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

    public void setLaskujaLahetetty(Integer laskujaLahetetty) {
        this.laskujaLahetetty = laskujaLahetetty;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
