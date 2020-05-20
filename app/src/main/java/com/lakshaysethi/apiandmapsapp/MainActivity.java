package com.lakshaysethi.apiandmapsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String url1= "https://dog.ceo/api/breeds/image/random";
    String url2 = "https://dog.ceo/api/breeds/list/all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinner = findViewById(R.id.spinner);
        final TextView tv1 = findViewById(R.id.textView1);
        final ImageView imgView1 = findViewById(R.id.imageView);


        Response.Listener repListener=new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response",response.toString());
                tv1.setText("response from API: "+response.toString());
                try {
                    url1 = response.get("message").toString();
                    Picasso.get().load(url1).into( imgView1);// i used picasso dependency for images

                } catch (JSONException e) {
                    tv1.setText(tv1.getText()+"\n\n\n could not load Image");
                    e.printStackTrace();
                }

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("err",error.toString());
                tv1.setText("err: "+ error.toString());

            }
        };

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url1, null, repListener, errorListener);

        RequestQueue requestQ  = Volley.newRequestQueue(this.getApplicationContext());
        requestQ.add(req);



        ArrayAdapter<CharSequence> adapter = new ArrayAdapter()

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


    }
}
