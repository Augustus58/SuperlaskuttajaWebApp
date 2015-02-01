/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Models;

import java.sql.Timestamp;

/**
 *
 * @author Augustus58
 */
public class Suorite {
    private Integer suoritteenNumero;
    private Integer lasku;
    private String kuvaus;
    private Integer tilaaja;
    private Integer tilaajanVersio;
    private Integer vastaanottaja;
    private Integer vastaanottajanVersio;
    private Double maara;
    private String maaranYksikot;
    private Double aHintaVeroton;
    private Integer alvProsentti;
    private Timestamp alkuaika;
    private Timestamp loppuaika;

    public Suorite() {
    }

    public Suorite(Integer suoritteenNumero, Integer lasku, String kuvaus, Integer tilaaja, Integer tilaajanVersio, Integer vastaanottaja, Integer vastaanottajanVersio, Double maara, String maaranYksikot, Double aHintaVeroton, Integer alvProsentti, Timestamp alkuaika, Timestamp loppuaika) {
        this.suoritteenNumero = suoritteenNumero;
        this.lasku = lasku;
        this.kuvaus = kuvaus;
        this.tilaaja = tilaaja;
        this.tilaajanVersio = tilaajanVersio;
        this.vastaanottaja = vastaanottaja;
        this.vastaanottajanVersio = vastaanottajanVersio;
        this.maara = maara;
        this.maaranYksikot = maaranYksikot;
        this.aHintaVeroton = aHintaVeroton;
        this.alvProsentti = alvProsentti;
        this.alkuaika = alkuaika;
        this.loppuaika = loppuaika;
    }

    public Integer getSuoritteenNumero() {
        return suoritteenNumero;
    }

    public Integer getLasku() {
        return lasku;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public Integer getTilaaja() {
        return tilaaja;
    }

    public Integer getTilaajanVersio() {
        return tilaajanVersio;
    }

    public Integer getVastaanottaja() {
        return vastaanottaja;
    }

    public Integer getVastaanottajanVersio() {
        return vastaanottajanVersio;
    }

    public Double getMaara() {
        return maara;
    }

    public String getMaaranYksikot() {
        return maaranYksikot;
    }

    public Double getaHintaVeroton() {
        return aHintaVeroton;
    }

    public Integer getAlvProsentti() {
        return alvProsentti;
    }

    public Timestamp getAlkuaika() {
        return alkuaika;
    }

    public Timestamp getLoppuaika() {
        return loppuaika;
    }

    public void setSuoritteenNumero(Integer suoritteenNumero) {
        this.suoritteenNumero = suoritteenNumero;
    }

    public void setLasku(Integer lasku) {
        this.lasku = lasku;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public void setTilaaja(Integer tilaaja) {
        this.tilaaja = tilaaja;
    }

    public void setTilaajanVersio(Integer tilaajanVersio) {
        this.tilaajanVersio = tilaajanVersio;
    }

    public void setVastaanottaja(Integer vastaanottaja) {
        this.vastaanottaja = vastaanottaja;
    }

    public void setVastaanottajanVersio(Integer vastaanottajanVersio) {
        this.vastaanottajanVersio = vastaanottajanVersio;
    }

    public void setMaara(Double maara) {
        this.maara = maara;
    }

    public void setMaaranYksikot(String maaranYksikot) {
        this.maaranYksikot = maaranYksikot;
    }

    public void setaHintaVeroton(Double aHintaVeroton) {
        this.aHintaVeroton = aHintaVeroton;
    }

    public void setAlvProsentti(Integer alvProsentti) {
        this.alvProsentti = alvProsentti;
    }

    public void setAlkuaika(Timestamp alkuaika) {
        this.alkuaika = alkuaika;
    }

    public void setLoppuaika(Timestamp loppuaika) {
        this.loppuaika = loppuaika;
    }
    
}
