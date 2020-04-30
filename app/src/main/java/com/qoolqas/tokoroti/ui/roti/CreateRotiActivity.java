package com.qoolqas.tokoroti.ui.roti;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mindorks.paracamera.Camera;
import com.qoolqas.tokoroti.R;
import com.qoolqas.tokoroti.pojo.DataRoti;
import com.qoolqas.tokoroti.sqlite.DBDataSource;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateRotiActivity extends AppCompatActivity {
    EditText nama, deskripsi, harga;
    ImageView photo;
    DBDataSource dataSource;
    Button simpan, galery, camera;
    Date currentTime;
    SimpleDateFormat sdfc;
    String edit = "0";
    String kode = "0";
    String view = "0";
    private Camera cam;
    String myFormats = "dd/MM/yyyy hh:mm:ss";
    private Bitmap bitmapPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_roti);
        dataSource = new DBDataSource(this);

        nama = findViewById(R.id.rotiNama);
        deskripsi = findViewById(R.id.rotiDeskripsi);
        harga = findViewById(R.id.rotiHarga);
        currentTime = Calendar.getInstance().getTime();
        simpan = findViewById(R.id.bt_simpan);
        camera = findViewById(R.id.bt_camera);
        galery = findViewById(R.id.bt_gallery);
        photo = findViewById(R.id.iv_Photo);

        try {
            kode = getIntent().getStringExtra("kode");
            view = getIntent().getStringExtra("view");
            edit = getIntent().getStringExtra("edit");

            Log.d("log", kode + view + edit);
        } catch (Exception e) {
            kode = "0";
            view = "0";
            edit = "0";
        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cam = new Camera.Builder()
                        .resetToCorrectOrientation(true)// it will rotate the camera bitmap to the correct orientation from meta data
                        .setTakePhotoRequestCode(1)
                        .setDirectory("pics")
                        .setName("gemar" + System.currentTimeMillis())
                        .setImageFormat(Camera.IMAGE_JPEG)
                        .setCompression(50)// it will try to achieve this height as close as possible maintaining the aspect ratio;
                        .build(CreateRotiActivity.this);

                try {
                    cam.takePicture();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        galery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 2);//one can be replaced with any action code
            }
        });


        sdfc = new SimpleDateFormat(myFormats, Locale.US);
        if (edit.equals("1")) {
            simpan.setText("Simpan Perubahan");
            setEdit();
        }
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit.equals("1")) {
                    editMember();
                } else {
                    createRoti();
                }

            }
        });
    }

    private void createRoti() {
        BitmapDrawable drawable1 = (BitmapDrawable) photo.getDrawable();
        bitmapPhoto = drawable1.getBitmap();
        DataRoti data = new DataRoti();
        data.setR_KODE(sdfc.format(currentTime).replaceAll("/", "").replace(" ", "").replaceAll(":", ""));
        data.setR_NAMA(nama.getText().toString());
        data.setR_DESKRIPSI(deskripsi.getText().toString());
        data.setR_HARGA(harga.getText().toString());
        data.setR_IMAGE(BitMapToString(bitmapPhoto));
        data.setR_SELECTION("0");

        long result = dataSource.createRoti(data);
        if (result > 0) {
            finish();
        }
    }

    private void editMember() {
        BitmapDrawable drawable1 = (BitmapDrawable) photo.getDrawable();
        bitmapPhoto = drawable1.getBitmap();
        DataRoti data = new DataRoti();
        data.setR_KODE(kode);
        data.setR_NAMA(nama.getText().toString());
        data.setR_DESKRIPSI(deskripsi.getText().toString());
        data.setR_HARGA(harga.getText().toString());
        data.setR_IMAGE(BitMapToString(bitmapPhoto));
        data.setR_SELECTION("0");
        long result = dataSource.updateRoti(data);
        if (result > 0) {
            finish();
        }

    }

    void setEdit() {
        ArrayList<DataRoti> forms = dataSource.getRotibyKode(kode);
        if (forms.size() == 1) {
            final DataRoti data = forms.get(0);
            nama.setText(data.getR_NAMA());
            deskripsi.setText(data.getR_DESKRIPSI());
            harga.setText(data.getR_HARGA());
            photo.setImageBitmap(StringToBitMap(data.getR_IMAGE()));

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .override(300, 300);
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    Bitmap bitmap = cam.getCameraBitmap();
                    if(bitmap != null) {
                        Glide.with(CreateRotiActivity.this)
                                .asBitmap()
                                .apply(myOptions)
                                .load(bitmap)
                                .into(photo);
                    }else{
                        Toast.makeText(this.getApplicationContext(),"Picture not taken!",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case 2:
                if(resultCode == RESULT_OK){

                    Uri selectedImage = data.getData();

                    Glide.with(CreateRotiActivity.this)
                            .asBitmap()
                            .apply(myOptions)
                            .load(selectedImage)
                            .into(photo);
                }
                break;
        }
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.NO_WRAP);
        return temp;
    }
}
