package com.qoolqas.tokoroti.ui.roti;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qoolqas.tokoroti.R;
import com.qoolqas.tokoroti.pojo.DataRoti;
import com.qoolqas.tokoroti.sqlite.DBDataSource;

import java.util.ArrayList;
import java.util.List;

public class SemuaFragment extends Fragment {
    private RecyclerView rv;
    private DBDataSource dataSource;
    RotiProvider provform;
    private List<RotiProvider> arraylistform = new ArrayList<>();
    private AdapterRoti adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_semua, container, false);
        dataSource = new DBDataSource(getActivity());
        adapter = new AdapterRoti(arraylistform, getActivity(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv = root.findViewById(R.id.rv);
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(adapter);
        getData();
        return root;
    }
    void getData() {
        arraylistform.clear();
        ArrayList<DataRoti> forms = dataSource.getAllRoti();
        if (forms.size() > 0) {
            for (int i = 0; i < forms.toArray().length; i++) {
                final DataRoti cv = forms.get(i);
                System.out.println("kode :" + cv.getR_KODE());
                provform = new RotiProvider(cv.getR_KODE(), cv.getR_NAMA(), cv.getR_DESKRIPSI(), cv.getR_HARGA(), cv.getR_SELECTION(), cv.getR_IMAGE());
                arraylistform.add(provform);
            }
            adapter.notifyDataSetChanged();

        } else {
            Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG).show();
            arraylistform.clear();
            adapter.notifyDataSetChanged();
        }

    }
}
