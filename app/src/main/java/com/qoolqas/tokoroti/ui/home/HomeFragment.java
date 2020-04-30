package com.qoolqas.tokoroti.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.qoolqas.tokoroti.R;
import com.qoolqas.tokoroti.ui.roti.CreateRotiActivity;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        CardView kategori = root.findViewById(R.id.cardKategori);
        CardView semua = root.findViewById(R.id.cardSemua);
        CardView catatan = root.findViewById(R.id.cardCatatan);
        CardView lokasi = root.findViewById(R.id.cardLokasi);

        kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateRotiActivity.class);
                intent.putExtra("edit", "0");
                intent.putExtra("view", "0");
                startActivity(intent);
            }
        });
        semua.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_semua, null));
        catatan.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_catatan, null));
        lokasi.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_lokasi, null));

        return root;
    }
}
