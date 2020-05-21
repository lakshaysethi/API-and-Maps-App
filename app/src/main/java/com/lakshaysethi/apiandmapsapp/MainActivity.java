package com.lakshaysethi.apiandmapsapp;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> spinnerArray = new ArrayList<String>();
    //final String url1 = "https://dog.ceo/api/breeds/image/random"; //random
    final String url2 = "https://dog.ceo/api/breeds/list/all";// all breads to get them all
   // final String url3 =
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // i think references can be final
        final Spinner spinner = findViewById(R.id.spinner);
        final TextView tv1 = findViewById(R.id.textView1);
        final ImageView imgView1 = findViewById(R.id.imageView);

        tv1.setText("please select a bread to get image of a dog from the dogs api");


        final ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray); //??? why finall??
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Response.Listener repListener=new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject respObj= response;
                Log.d("response",respObj.toString());

                // get list of all breads and feed them to the adapter and refresh the adapter
                try {
                    JSONObject breedsObj = (JSONObject) respObj.get("message");
                    Log.v("this is from line 56",breedsObj.toString());

                    Iterator<String> keys = breedsObj.keys();
                    int i =0;
                    while(keys.hasNext()) {
                        String key = keys.next();
                        spinnerArray.add(key);

                        Log.v("Breed "+ i,  key);
                        i++;


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                spinner.setAdapter(adapter);


            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("err",error.toString());
                tv1.setText("err: "+ error.toString());

            }
        };


        Response.Listener repListener2=new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response",response.toString());
                tv1.setText("");
                try {

                    String imageUrl1 = response.get("message").toString();
                    Picasso.get().load(imageUrl1).into( imgView1);// i used picasso dependency for images

                } catch (JSONException e) {
                    tv1.setText(tv1.getText()+"\n\n\n could not load Image");
                    e.printStackTrace();
                }

            }
        };



        RequestQueue requestQ  = Volley.newRequestQueue(this.getApplicationContext());
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url2, null, repListener, errorListener);// get list of all breads
        requestQ.add(req);

        //JsonObjectRequest req2 = new JsonObjectRequest(Request.Method.GET, url3, null, repListener2, errorListener);
        //requestQ.add(req2);




    }
}
