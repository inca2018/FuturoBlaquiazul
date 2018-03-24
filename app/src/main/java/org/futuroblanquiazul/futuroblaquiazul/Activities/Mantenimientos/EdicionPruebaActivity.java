package org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EdicionPruebaActivity extends AppCompatActivity {


    ImageView imagen;
    Button prueba_buscar;
    Button prueba_cargar;
    final int CODE=999;
    Bitmap bitmap;
    final String URL_API="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_prueba);
        imagen=findViewById(R.id.prueba_foto);
        prueba_buscar=findViewById(R.id.prueba_buscar);
        prueba_cargar=findViewById(R.id.prueba_cargar);

        prueba_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(EdicionPruebaActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},CODE);
            }
        });
        prueba_cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_API, new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {

                      }
                   },new Response.ErrorListener(){
                      @Override
                      public void onErrorResponse(VolleyError error) {

                     }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        String imageData=imageToString(bitmap);
                        Map<String,String> param=new HashMap<>();
                        param.put("image",imageData);
                        return param;
                    }
                };
            }
        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Image"),CODE);
            }else{
                Toast.makeText(this, "No tiene permisos", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CODE && resultCode==RESULT_OK && data !=null ){
            Uri file=data.getData();

            try {
                InputStream inputStream=getContentResolver().openInputStream(file);
                bitmap= BitmapFactory.decodeStream(inputStream);
                imagen.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes=outputStream.toByteArray();

        String encodeImage=Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return  encodeImage;
    }

}
