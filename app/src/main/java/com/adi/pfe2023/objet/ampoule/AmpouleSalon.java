package com.adi.pfe2023.objet.ampoule;

public class AmpouleSalon extends Ampoule{
    private final String cheminAmpouleSalon="test/leds/led_salon";

    private static AmpouleSalon ampouleSalon;

    private AmpouleSalon(){
        super();
        this.setCheminAmpoule(cheminAmpouleSalon);
        this.setNomComposant("Ampoule Salon");
    }


    public static synchronized AmpouleSalon getInstance(){
        if (ampouleSalon==null){
            ampouleSalon= new AmpouleSalon();
            return ampouleSalon;
        }
        else {
            return ampouleSalon;
        }
    }

}
