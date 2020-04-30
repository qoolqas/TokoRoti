package com.qoolqas.tokoroti.ui.roti;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.qoolqas.tokoroti.R;
import com.qoolqas.tokoroti.sqlite.DBDataSource;

import java.util.List;

public class AdapterRoti extends RecyclerView.Adapter<AdapterRoti.ViewHolder> {
    private List<RotiProvider> rotiList;
    private Context context;
    private Fragment fragment;
    private LayoutInflater mInflater;
    private DBDataSource datasource;

    public AdapterRoti(List<RotiProvider> rotiList, Context context, Fragment fragment) {
        this.rotiList = rotiList;
        this.context = context;
        this.fragment = fragment;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_card_roti, parent, false);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        datasource = new DBDataSource(fragment.getActivity());
        final RotiProvider items = rotiList.get(position);
        holder.image.setImageBitmap(StringToBitMap(items.getR_IMAGE()));
        Log.d("image", String.valueOf(items.getR_IMAGE()));
        holder.nama.setText(items.getR_NAMA());
        holder.harga.setText(items.getR_HARGA());
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(fragment.getActivity(), holder.more);
                popup.inflate(R.menu.crud);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                new AlertDialog.Builder(context)
                                        .setMessage("Apakah anda yakin akan mengedit data ini?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(context, CreateRotiActivity.class);
                                                intent.putExtra("kode", rotiList.get(position).getR_KODE());
                                                intent.putExtra("edit", "1");
                                                intent.putExtra("view", "0");
                                                context.startActivity(intent);


                                            }
                                        })

                                        // A null listener allows the button to dismiss the dialog and take no further action.
                                        .setNegativeButton(android.R.string.no, null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                                break;
                            case R.id.delete:
                                new AlertDialog.Builder(context)
                                        .setMessage("Are you sure you want to delete this data?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Continue with delete operation

                                                long a = datasource.deleteRoti(items.getR_KODE());
                                                if (a > 0) {
                                                    dialog.dismiss();
                                                    ((SemuaFragment) fragment).getData();
                                                } else {
                                                    Toast.makeText(context, "Gagal hapus data, silahkan coba lagi", Toast.LENGTH_LONG).show();
                                                    dialog.dismiss();
                                                }
                                            }
                                        })

                                        // A null listener allows the button to dismiss the dialog and take no further action.
                                        .setNegativeButton(android.R.string.no, null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return rotiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, harga, more;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.photo);
            nama  = itemView.findViewById(R.id.nama);
            harga = itemView.findViewById(R.id.harga);
            more = itemView.findViewById(R.id.more);
        }
    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
