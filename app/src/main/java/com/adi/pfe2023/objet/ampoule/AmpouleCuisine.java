package com.adi.pfe2023.objet.ampoule;

public class AmpouleCuisine extends Ampoule{

    private final String cheminAmpouleCuisine="test/leds/led_cuisine";

    private static AmpouleCuisine ampouleCuisine;

    private AmpouleCuisine() {
        super();
        this.setCheminAmpoule(cheminAmpouleCuisine);
        this.setNomComposant("Ampoule Cuisine");
    }

    public static synchronized AmpouleCuisine getInstance(){
        if (ampouleCuisine==null){
            ampouleCuisine= new AmpouleCuisine();
            return ampouleCuisine;
        }
        else {
            return ampouleCuisine;
        }
    }
}
