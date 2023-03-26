package com.adi.pfe2023.objet.ampoule;

public class AmpouleChambre extends Ampoule{

    private final String cheminAmpouleChambre="test/leds/led_chambre";

    private static AmpouleChambre ampouleChambre;

    private AmpouleChambre() {
        super();
        this.setCheminAmpoule(cheminAmpouleChambre);
        this.setNomComposant("Ampoule Cuisine");
    }

    public static synchronized AmpouleChambre getInstance(){
        if (ampouleChambre==null){
            ampouleChambre= new AmpouleChambre();
            return ampouleChambre;
        }
        else {
            return ampouleChambre;
        }
    }
}
