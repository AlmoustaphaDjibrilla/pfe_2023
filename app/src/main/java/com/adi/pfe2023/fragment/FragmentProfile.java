package com.adi.pfe2023.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adi.pfe2023.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentProfile extends Fragment {

    FirebaseUser currentUser;

    TextView txtNomUser1, txtNomUser2, txtMailUser1, txtMailUser2, txtDateEnregistrementUser;
    ImageView imgProfilUser, imgHistoriqueUser;
    Button btnEditerProfilUser;



    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        init(view);

        updateComponants();

        return view;
    }


    /**
     * Initialisation des composants
     * graphiques de ce fragment
     */
    private void init(View view){
        currentUser= FirebaseAuth.getInstance().getCurrentUser();

        txtNomUser1= view.findViewById(R.id.txtNomUser1);
        txtNomUser2= view.findViewById(R.id.txtNomUser2);

        txtMailUser1= view.findViewById(R.id.txtNomUser1);
        txtMailUser2= view.findViewById(R.id.txtMailUser2);

        txtDateEnregistrementUser= view.findViewById(R.id.txtDateEnregistrementUser);

        imgProfilUser= view.findViewById(R.id.imgProfilUser);
        imgHistoriqueUser= view.findViewById(R.id.imgHistoriqueUser);

        btnEditerProfilUser= view.findViewById(R.id.btnEditerProfilUser);
    }


    private void updateComponants(){
        if (this.currentUser!=null){
            if (currentUser.getPhotoUrl()!=null && !currentUser.getPhotoUrl().equals("")){
                imgProfilUser.setImageURI(currentUser.getPhotoUrl());
            }
            if (currentUser.getEmail()!= null && !currentUser.getEmail().equals("")){
                txtMailUser1.setText(currentUser.getEmail());
                txtMailUser2.setText(currentUser.getEmail());
            }
            if (currentUser.getDisplayName()!=null && !currentUser.getDisplayName().equals("")){
                txtNomUser1.setText(currentUser.getDisplayName());
                txtNomUser2.setText(currentUser.getDisplayName());
            }
        }
    }
}