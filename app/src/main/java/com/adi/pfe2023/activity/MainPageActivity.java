package com.adi.pfe2023.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.adi.pfe2023.R;
import com.adi.pfe2023.databinding.ActivityMainPageBinding;
import com.adi.pfe2023.fragment.FragmentAdmin;
import com.adi.pfe2023.fragment.FragmentHome;
import com.adi.pfe2023.fragment.FragmentProfile;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class MainPageActivity extends AppCompatActivity implements Serializable {

    DatabaseReference ref;
    FirebaseUser currentUser;
    ActivityMainPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent=getIntent();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        remplacementFragment(new FragmentHome());

        binding.bottomNavigationView.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.mnHouse:
                                remplacementFragment(new FragmentHome());
                                break;

                            case R.id.mnProfil:
                                remplacementFragment(new FragmentProfile());
                                break;

                            case R.id.mnAdmin:
                                remplacementFragment(new FragmentAdmin());
                                break;
                        }

                        return true;
                    }
                }
        );

    }


    private void remplacementFragment(Fragment fragment){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}