package com.adi.pfe2023.objet.ampoule;

public class AmpouleDouche extends Ampoule{

    private final String cheminAmpouleDouche="test/leds/led_douche";

    private static AmpouleDouche ampouleDouche;

    private AmpouleDouche() {
        super();
        this.setCheminAmpoule(cheminAmpouleDouche);
        this.setNomComposant("Ampoule Douche");
    }

    public static synchronized AmpouleDouche getInstance(){
        if (ampouleDouche==null){
            ampouleDouche= new AmpouleDouche();
            return ampouleDouche;
        }
        else {
            return ampouleDouche;
        }
    }
}
