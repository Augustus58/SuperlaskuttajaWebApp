/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Models;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/**
 *
 * @author Augustus58
 */
public class SuoriteAdapter extends TypeAdapter<Suorite> {

    @Override
    public Suorite read(JsonReader in) throws IOException {
        Suorite suorite = new Suorite();
        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("suoritteenNumero")) {
                suorite.setSuoritteenNumero(in.nextInt());
            }
            if (name.equals("lasku")) {
                suorite.setLasku(in.nextInt());
            }
            if (name.equals("kuvaus")) {
                suorite.setKuvaus(in.nextString());
            }
            if (name.equals("tilaaja")) {
                suorite.setTilaaja(Integer.toString(in.nextInt()));
            }
            if (name.equals("tilaajanVersio")) {
                suorite.setTilaajanVersio(in.nextInt());
            }
            if (name.equals("vastaanottaja")) {
                suorite.setVastaanottaja(Integer.toString(in.nextInt()));
            }
            if (name.equals("vastaanottajanVersio")) {
                suorite.setVastaanottajanVersio(in.nextInt());
            }
            if (name.equals("maara")) {
                suorite.setMaara(in.nextString());
            }
            if (name.equals("maaranYksikot")) {
                suorite.setMaaranYksikot(in.nextString());
            }
            if (name.equals("aHintaVeroton")) {
                suorite.setaHintaVerotonFromVeroton(in.nextString());
            }
            if (name.equals("alvProsentti")) {
                suorite.setAlvProsentti(in.nextString());
            }
            if (name.equals("aloitusaika")) {
                suorite.setAloitusaikaFromTimeStampString(in.nextString());
            }
            if (name.equals("lopetusaika")) {
                suorite.setLopetusaikaFromTimeStampString(in.nextString());
            }
        }
        in.endObject();
        return suorite;
    }

    @Override
    public void write(JsonWriter out, Suorite suorite) throws IOException {
        out.beginObject();
        out.name("suoritteenNumero").value(suorite.getSuoritteenNumero());
        out.name("lasku").value(suorite.getLasku());
        out.name("kuvaus").value(suorite.getKuvaus());
        out.name("tilaaja").value(suorite.getTilaaja());
        out.name("tilaajanVersio").value(suorite.getTilaajanVersio());
        out.name("tilaajanNimi").value(suorite.getTilaajanNimi());
        out.name("vastaanottaja").value(suorite.getVastaanottaja());
        out.name("vastaanottajanVersio").value(suorite.getVastaanottajanVersio());
        out.name("maara").value(suorite.getMaara());
        out.name("maaranYksikot").value(suorite.getMaaranYksikot());
        out.name("aHintaVeroton").value(suorite.getaHintaVeroton());
        out.name("alvProsentti").value(suorite.getAlvProsentti());
        out.name("aloitusaika").value(suorite.getAloitusaika().toString());
//        out.name("lopetusaika").value(suorite.getLopetusaika().toString());

        out.endObject();
    }
}
