package com.adi.pfe2023.objet.ampoule;

public class AmpouleSalon extends Ampoule{
    private final String cheminAmpouleSalon="test/leds/led_salon";

    public AmpouleSalon(){
        super();
        this.setCheminAmpoule(cheminAmpouleSalon);
        this.setNomComposant("Ampoule Salon");
    }




}
