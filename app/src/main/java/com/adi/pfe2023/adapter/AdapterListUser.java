package com.adi.pfe2023.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adi.pfe2023.R;
import com.adi.pfe2023.model.UserModel;

import java.util.List;

public class AdapterListUser extends BaseAdapter {

    Context context;
    List<UserModel> lesUsers;
    LayoutInflater inflater;

    ImageView imgProfileModelUser;
    TextView txtNomModelUser, txtMailModelUser, txtDateEnregistrementModelUser;

    public AdapterListUser(Context context, List<UserModel> lesUsers) {
        this.context = context;
        this.lesUsers = lesUsers;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lesUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return lesUsers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view= inflater.inflate(R.layout.modele_user, null);
        init(view);

        UserModel userModel= lesUsers.get(position);

        if (userModel!=null) {
            if (userModel.getNom()!=null)
                txtNomModelUser.setText(userModel.getNom());
            if (userModel.getEmail()!=null)
                txtMailModelUser.setText(userModel.getEmail());
            if (userModel.getDateEnregistrement()!=null)
                txtDateEnregistrementModelUser.setText(userModel.getDateEnregistrement());
        }


        return view;
    }



    private void init(View view){
        imgProfileModelUser= view.findViewById(R.id.profilUserTrouve);
        txtNomModelUser= view.findViewById(R.id.nomUserTrouve);
        txtMailModelUser= view.findViewById(R.id.mailUserTrouve);
        txtDateEnregistrementModelUser= view.findViewById(R.id.dateEnregistrementUserTrouve);
    }
}
