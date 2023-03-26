package com.adi.pfe2023.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.adi.pfe2023.R;
import com.adi.pfe2023.model.UserModel;

import java.util.List;

public class AdapterUsersRecherche extends BaseAdapter {
    Context context;
    List<UserModel> lesUsersRechers;
    LayoutInflater inflater;

    TextView nomUser, mailUser, dateEnregistrementUser, uidUser;

    public AdapterUsersRecherche(Context context, List<UserModel> lesUsersRechers) {
        this.context = context;
        this.lesUsersRechers = lesUsersRechers;
        this.inflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lesUsersRechers.size();
    }

    @Override
    public Object getItem(int i) {
        return lesUsersRechers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= inflater.inflate(R.layout.modele_user_recherche, null);
        init(view);

        UserModel userModel= lesUsersRechers.get(i);

        if (userModel!=null){
            if (userModel.getEmail()!=null){
                mailUser.setText(userModel.getEmail());
            }
            if (userModel.getNom()!=null){
                nomUser.setText(userModel.getNom());
            }
            if (userModel.getDateEnregistrement()!=null){
                dateEnregistrementUser.setText(userModel.getDateEnregistrement());
            }
            if (userModel.getUid()!=null){
                uidUser.setText(userModel.getUid());
            }
        }


        return view;
    }

    private void init(View view){
        nomUser= view.findViewById(R.id.nomUserSupprime);
        mailUser= view.findViewById(R.id.mailUserSupprime);
        dateEnregistrementUser= view.findViewById(R.id.dateEnregistrementUserSupprime);
        uidUser= view.findViewById(R.id.uidUserSupprime);
    }
}
