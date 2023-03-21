package com.adi.pfe2023.objet.ampoule;

public class AmpouleCuisine extends Ampoule{

    private final String cheminAmpouleCuisine="test/leds/led_cuisine";


    public AmpouleCuisine() {
        super();
        this.setCheminAmpoule(cheminAmpouleCuisine);
        this.setNomComposant("Ampoule Cuisine");
    }
}
