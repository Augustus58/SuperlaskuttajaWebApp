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
public class Laskuttaja {

    private String yrityksenNimi;
    private Integer versio;
    private String alvTunniste;
    private String nimi;
    private String katuosoite;
    private String postinumero;
    private String kaupunki;
    private String puhelinnumero;
    private String sahkopostiOsoite;
    private Integer laskujaLahetetty;
    private String tilinumero;
    private String tilinumeronPankki;
    private String tilinumeronSwift;

    public Laskuttaja() {
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
        this.sahkopostiOsoite = sahkopostiOsoite;
        this.laskujaLahetetty = laskujaLahetetty;
        this.tilinumero = tilinumero;
        this.tilinumeronPankki = tilinumeronPankki;
        this.tilinumeronSwift = tilinumeronSwift;
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

    public String getSahkopostiOsoite() {
        return sahkopostiOsoite;
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

    public String getTilinumeronSwift() {
        return tilinumeronSwift;
    }

    public void setYrityksenNimi(String yrityksenNimi) {
        this.yrityksenNimi = yrityksenNimi;
    }

    public void setVersio(Integer versio) {
        this.versio = versio;
    }

    public void setAlvTunniste(String alvTunniste) {
        this.alvTunniste = alvTunniste;
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

    public void setPuhelinnumero(String puhelinnumero) {
        this.puhelinnumero = puhelinnumero;
    }

    public void setSahkopostiOsoite(String sahkopostiOsoite) {
        this.sahkopostiOsoite = sahkopostiOsoite;
    }

    public void setLaskujaLahetetty(Integer laskujaLahetetty) {
        this.laskujaLahetetty = laskujaLahetetty;
    }

    public void setTilinumero(String tilinumero) {
        this.tilinumero = tilinumero;
    }

    public void setTilinumeronPankki(String tilinumeronPankki) {
        this.tilinumeronPankki = tilinumeronPankki;
    }

    public void setTilinumeronSwift(String tilinumeronSwift) {
        this.tilinumeronSwift = tilinumeronSwift;
    }
    
}
