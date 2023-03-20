package com.adi.pfe2023.objet.ampoule;

import com.adi.pfe2023.objet.Composant;

public abstract class Ampoule extends Composant {

    private String cheminAmpoule;

    public String getCheminAmpoule() {
        return cheminAmpoule;
    }

    public void setCheminAmpoule(String cheminAmpoule) {
        this.cheminAmpoule = cheminAmpoule;
    }
}
