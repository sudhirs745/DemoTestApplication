package com.test.demo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.test.demo.R;
import com.test.demo.models.ModelData;
import com.test.demo.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder>  {

    public Context mContext;
    public List<ModelData> modelDataList = new ArrayList<>();



    public RvAdapter(Context mContext, List<ModelData> modelDataList) {
        this.mContext = mContext;
        this.modelDataList = modelDataList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            ModelData modelData = modelDataList.get(position);
            holder.tvName.setText(modelData.getName());


        Picasso.with(mContext)
                .load(Constants.IMAGE_URL+modelData.getImage())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image) // On error image
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {

        return modelDataList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName =  itemView.findViewById(R.id.tx_name);
            imageView =  itemView.findViewById(R.id.im_image);
        }
    }



}
