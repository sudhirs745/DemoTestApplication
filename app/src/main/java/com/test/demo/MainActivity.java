package com.test.demo;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.test.demo.adapters.MainSliderAdapter;
import com.test.demo.adapters.RvAdapter;
import com.test.demo.models.ModelData;
import com.test.demo.receiver.ConnectivityReceiver;
import com.test.demo.utils.AppController;
import com.test.demo.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    public boolean isConnected;
    RecyclerView recyclerview;
    private Slider slider;
    public List<ModelData> modelDataList;
    RvAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Slider.init(new PicassoImageLoadingService(this));
        getAllImageList();

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        recyclerview.setNestedScrollingEnabled(false);


        getData();

    }

    private void setupViews(String responseImageView) {

        slider = findViewById(R.id.banner_slider1);


        final ArrayList<String> arrayList = new ArrayList<>();
         try {
            JSONArray jsonArray =new JSONArray(responseImageView );

            for (int i =0;i<jsonArray.length(); i++){

                JSONObject jsonObject =jsonArray.getJSONObject(i);

                String slider_image =jsonObject.getString("slider_image");
                arrayList.add(slider_image);

            }

             slider.setAdapter(new MainSliderAdapter(arrayList));
             slider.setSelectedSlide(0);


//             //delay for testing empty view functionality
//             slider.postDelayed(new Runnable() {
//                 @Override
//                 public void run() {
//                     slider.setAdapter(new MainSliderAdapter(arrayList));
//                     slider.setSelectedSlide(0);
//                 }
//             }, 1500);

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    public void getData(){
        if (checkConnectivity()){
            try {
                getAllPosts();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            showToast();
        }
    }


    private void getAllImageList() {

        String TAG = "POSTS";
        String url = Constants.BASE_URL+"get_banner";
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);

                setupViews(response);

                // parseJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, TAG);


    }

    private void getAllPosts() {

            String TAG = "POSTS";
            String url = Constants.BASE_URL+"deal_product";
            StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    parseJson(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error", error.getMessage());
                }
            });

            AppController.getInstance().addToRequestQueue(jsonObjectRequest, TAG);


    }

    private void parseJson(String responsedata) {

        modelDataList =new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(responsedata) ;

            String responce = jsonObject.getString("responce");


            if(responce.equalsIgnoreCase("true")){


                JSONArray jsonArray = jsonObject.getJSONArray("Deal_of_the_day");

                for (int i=0;i<jsonArray.length();i++){

                    JSONObject jsonObject1 =jsonArray.getJSONObject(i);

                    String product_id = jsonObject1.getString("product_id");
                    String product_name = jsonObject1.getString("product_name");
                    String product_image = jsonObject1.getString("product_image");

                    modelDataList.add(new ModelData(product_name,product_image));
                }

                adapter = new RvAdapter(MainActivity.this, modelDataList);
                recyclerview.setAdapter(adapter);

            }else {

                Toast.makeText(MainActivity.this,"oops! response  false", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }


        }



    @Override
    protected void onResume() {
        super.onResume();
        AppController.getInstance().setConnectivityReceiver(this);
    }

    @Override
    public void onNetworkChange(boolean inConnected) {

        this.isConnected = inConnected;
    }


    public void showToast() {

        Toast.makeText(MainActivity.this, getString(R.string.no_internet_connected), Toast.LENGTH_LONG).show();


    }


    public boolean checkConnectivity() {
        return ConnectivityReceiver.isConnected();
    }

}
