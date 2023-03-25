package com.adi.pfe2023.objet.meteo;

public class Meteo {

    private final String cheminTemperature="test/temperature";
    private final String cheminHumidite="test/humidite";

    private static Meteo meteo;


    private Meteo() {
    }

    public static synchronized Meteo getInstance(){
        if (meteo==null){
            return new Meteo();
        }
        else
            return meteo;
    }

    public String getCheminTemperature() {
        return cheminTemperature;
    }

    public String getCheminHumidite() {
        return cheminHumidite;
    }
}
