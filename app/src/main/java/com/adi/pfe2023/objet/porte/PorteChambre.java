package com.adi.pfe2023.objet.porte;

public class PorteChambre extends Porte{
    private final String cheminPorteChambre="test/degre_cible_porte";

    private static PorteChambre porteChambre;

    private PorteChambre() {
        super();
        this.setCheminPorte(cheminPorteChambre);
        this.setNomComposant("Porte Chambre");
    }

    public static synchronized PorteChambre getInstance(){
        if (porteChambre==null){
            porteChambre= new PorteChambre();
            return porteChambre;
        }
        else {
            return porteChambre;
        }
    }
}
