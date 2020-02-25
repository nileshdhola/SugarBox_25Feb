package com.resto.sugarbox.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.resto.sugarbox.R;
import com.resto.sugarbox.databinding.ZomatoListItemBinding;
import com.resto.sugarbox.model.Restaurant;
import com.resto.sugarbox.ui.DetailsActivity;

import java.util.ArrayList;

public class ZomatoDataAdapter extends RecyclerView.Adapter<ZomatoDataAdapter.ZomatoViewHolder> {

    private ArrayList<Restaurant> restaurantArrayList;

    @NonNull
    @Override
    public ZomatoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ZomatoListItemBinding zomatoListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.zomato_list_item, viewGroup, false);
        return new ZomatoViewHolder(zomatoListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ZomatoViewHolder zomatoViewHolder, int i) {
        final Restaurant restaurant = restaurantArrayList.get(i);
        zomatoViewHolder.zomatoListItemBinding.setRestaurant(restaurant);
        //click on web view url call
        zomatoViewHolder.linearAdapterClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(restaurant.getRestaurant().getUrl())) {
                    Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                    intent.putExtra("url", restaurant.getRestaurant().getUrl());
                    v.getContext().startActivity(intent);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        if (restaurantArrayList != null) {
            return restaurantArrayList.size();
        } else {
            return 0;
        }
    }

    //refresh data
    public void setZomatoData(ArrayList<Restaurant> restaurantS) {
        this.restaurantArrayList = restaurantS;
        notifyDataSetChanged();
    }

    //viewholder
    class ZomatoViewHolder extends RecyclerView.ViewHolder {
        private ZomatoListItemBinding zomatoListItemBinding;
        private LinearLayout linearAdapterClick;

        public ZomatoViewHolder(@NonNull ZomatoListItemBinding zomatoListItemBinding) {
            super(zomatoListItemBinding.getRoot());
            this.zomatoListItemBinding = zomatoListItemBinding;
            linearAdapterClick = itemView.findViewById(R.id.linearAdapterClick);

        }
    }
}
