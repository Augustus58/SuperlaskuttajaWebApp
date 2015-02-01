/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Models;

import java.sql.Date;

/**
 *
 * @author Augustus58
 */
public class Lasku {

    private Integer laskunNumero;
    private Integer viittausAiempaanLaskuun;
    private String laskuttaja;
    private Integer laskuttajanVersio;
    private Integer maksuaika;
    private Date paivays;
    private Integer viivastysKorko;
    private String maksuehto;
    private String lisatiedot;
    private Boolean onkoMaksettu;
    private String pankkiviivakoodi;

    public Lasku() {
    }

    public Lasku(Integer laskunNumero, Integer viittausAiempaanLaskuun, String laskuttaja, Integer laskuttajanVersio, Integer maksuaika, Date paivays, Integer viivastysKorko, String maksuehto, String lisatiedot, Boolean onkoMaksettu, String pankkiviivakoodi) {
        this.laskunNumero = laskunNumero;
        this.viittausAiempaanLaskuun = viittausAiempaanLaskuun;
        this.laskuttaja = laskuttaja;
        this.laskuttajanVersio = laskuttajanVersio;
        this.maksuaika = maksuaika;
        this.paivays = paivays;
        this.viivastysKorko = viivastysKorko;
        this.maksuehto = maksuehto;
        this.lisatiedot = lisatiedot;
        this.onkoMaksettu = onkoMaksettu;
        this.pankkiviivakoodi = pankkiviivakoodi;
    }

    public Integer getLaskunNumero() {
        return laskunNumero;
    }

    public Integer getViittausAiempaanLaskuun() {
        return viittausAiempaanLaskuun;
    }

    public String getLaskuttaja() {
        return laskuttaja;
    }

    public Integer getLaskuttajanVersio() {
        return laskuttajanVersio;
    }

    public Integer getMaksuaika() {
        return maksuaika;
    }

    public Date getPaivays() {
        return paivays;
    }

    public Integer getViivastysKorko() {
        return viivastysKorko;
    }

    public String getMaksuehto() {
        return maksuehto;
    }

    public String getLisatiedot() {
        return lisatiedot;
    }

    public Boolean getOnkoMaksettu() {
        return onkoMaksettu;
    }

    public String getPankkiviivakoodi() {
        return pankkiviivakoodi;
    }

    public void setLaskunNumero(Integer laskunNumero) {
        this.laskunNumero = laskunNumero;
    }

    public void setViittausAiempaanLaskuun(Integer viittausAiempaanLaskuun) {
        this.viittausAiempaanLaskuun = viittausAiempaanLaskuun;
    }

    public void setLaskuttaja(String laskuttaja) {
        this.laskuttaja = laskuttaja;
    }

    public void setLaskuttajanVersio(Integer laskuttajanVersio) {
        this.laskuttajanVersio = laskuttajanVersio;
    }

    public void setMaksuaika(Integer maksuaika) {
        this.maksuaika = maksuaika;
    }

    public void setPaivays(Date paivays) {
        this.paivays = paivays;
    }

    public void setViivastysKorko(Integer viivastysKorko) {
        this.viivastysKorko = viivastysKorko;
    }

    public void setMaksuehto(String maksuehto) {
        this.maksuehto = maksuehto;
    }

    public void setLisatiedot(String lisatiedot) {
        this.lisatiedot = lisatiedot;
    }

    public void setOnkoMaksettu(Boolean onkoMaksettu) {
        this.onkoMaksettu = onkoMaksettu;
    }

    public void setPankkiviivakoodi(String pankkiviivakoodi) {
        this.pankkiviivakoodi = pankkiviivakoodi;
    }
    
}
