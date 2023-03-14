package com.adi.pfe2023.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adi.pfe2023.R;

public class FragmentAdmin extends Fragment {

    Button btnLogin;
    EditText txtMailLoginAdmin, txtPasswordLoginAdmin;
    TextView txtPasswordOublieAdmin;


    public FragmentAdmin() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin, container, false);

        //init
        init(view);

        btnLogin.setOnClickListener(v->{
            Toast.makeText(getContext(), "Oui", Toast.LENGTH_SHORT).show();
        });



        return view;
    }


    /**
     * Initialisation
     * @param view
     */
    private void init(View view){
        btnLogin= view.findViewById(R.id.btnLoginAdmin);
        txtMailLoginAdmin= view.findViewById(R.id.txtMailLoginAdmin);
        txtPasswordLoginAdmin= view.findViewById(R.id.txtPasswordLoginAdmin);
        txtPasswordOublieAdmin= view.findViewById(R.id.txtPasswordOublieAdmin);
    }
}