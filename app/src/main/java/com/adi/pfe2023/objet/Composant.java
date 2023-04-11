package com.adi.pfe2023.objet;

import com.adi.pfe2023.action.Commande;
import com.adi.pfe2023.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class Composant {
    final String PATH_COMMANDE= "Commandes";
    private final String PATH_USER_DATABASE= "Users";
    private String nomComposant;

    public String getNomComposant() {
        return nomComposant;
    }

    public void setNomComposant(String nomComposant) {
        this.nomComposant = nomComposant;
    }

    public void enregistrerNouvelleCommande(String detailCommande, Composant composant){

        DocumentReference docRef= FirebaseFirestore.getInstance()
                .collection(PATH_USER_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        docRef.get()
                .addOnSuccessListener(
                        documentSnapshot -> {
                            Commande commande= new Commande(composant);
                            UserModel userModel= documentSnapshot.toObject(UserModel.class);
                            commande.setDetail_commande(detailCommande);
                            commande.setUserModel(userModel);
                            commande.setEmailUser(userModel.getEmail());

                            DocumentReference dfReferenceSaveCommande=
                                    FirebaseFirestore.getInstance()
                                            .collection(PATH_COMMANDE)
                                            .document();
                            dfReferenceSaveCommande.set(commande);
                        }
                );
    }
}
