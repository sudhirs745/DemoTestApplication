package com.test.demo.adapters;

import android.util.Log;

import com.test.demo.utils.Constants;

import java.util.ArrayList;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {


    ArrayList<String> imageList;

    public  MainSliderAdapter(ArrayList<String> imagename){

        this.imageList =imagename;


    }
    @Override
    public int getItemCount() {

        return imageList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {

        Log.e("data" ,Constants.SLIDER_URL+ imageList.get(position));

        viewHolder.bindImageSlide(Constants.SLIDER_URL + imageList.get(position)) ;  //"https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg");


    }
}