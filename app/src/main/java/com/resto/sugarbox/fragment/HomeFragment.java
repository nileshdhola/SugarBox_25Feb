package com.resto.sugarbox.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.resto.sugarbox.MainActivity;
import com.resto.sugarbox.R;
import com.resto.sugarbox.adapter.ZomatoDataAdapter;
import com.resto.sugarbox.adapter.ZomatoHorizontalDataAdapter;
import com.resto.sugarbox.databinding.FragmentHomeBinding;
import com.resto.sugarbox.model.Restaurant;
import com.resto.sugarbox.utils.CommonUtils;
import com.resto.sugarbox.utils.SimpleLocation;
import com.resto.sugarbox.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements MainActivity.Dataset {

    private MainViewModel mainViewModel;
    private ZomatoDataAdapter zomatoDataAdapter;
    private ZomatoHorizontalDataAdapter horizontalDataAdapter;
    private FragmentHomeBinding fragmentHomeBinding;
    private RecyclerView recyclerViewVeritcal;
    private RecyclerView recyclerViewHorizontal;
    private TextView textAddress, txtNoData;
    private SimpleLocation simpleLocation;
    private LinearLayout linearMain;
    private ProgressBar progressBar;

    public HomeFragment() {
        // Required empty public constructor
    }

    //region oncreate view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = fragmentHomeBinding.getRoot();

        //vertical
        simpleLocation = new SimpleLocation(getContext());
        //address set
        textAddress = fragmentHomeBinding.textAddress;
        textAddress.setText(CommonUtils.getAddress(getContext(), simpleLocation.getLatitude(), simpleLocation.getLongitude()));


        //linear layout
        linearMain = fragmentHomeBinding.linearMain;
        //progress
        progressBar = fragmentHomeBinding.progressCircular;
        progressBar.setVisibility(View.VISIBLE);
        txtNoData = fragmentHomeBinding.txtNoData;
        //vertical recycler view
        recyclerViewVeritcal = fragmentHomeBinding.viewVertical;
        recyclerViewVeritcal.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewVeritcal.setHasFixedSize(true);
        //set model data in adapter
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        zomatoDataAdapter = new ZomatoDataAdapter();
        recyclerViewVeritcal.setAdapter(zomatoDataAdapter);

        //horizontal recyclerview
        recyclerViewHorizontal = fragmentHomeBinding.viewHorizontal;
        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHorizontal.setHasFixedSize(true);
        //set data adapter - horizontal
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        horizontalDataAdapter = new ZomatoHorizontalDataAdapter();
        recyclerViewHorizontal.setAdapter(horizontalDataAdapter);

        //api call
        getData();

        return view;

    }
    //endregion

    //region get data from API
    private void getData() {
        mainViewModel.getItemLatLong(getContext(), simpleLocation.getLatitude(), simpleLocation.getLongitude()).observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(@Nullable List<Restaurant> restaurants) {
                linearMain.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if (restaurants.size() == 0) {
                    txtNoData.setText("No data found");
                    txtNoData.setTextColor(Color.RED);
                } else {
                    txtNoData.setText("Restaurants nearby");
                    txtNoData.setTextColor(Color.BLACK);
                }
                zomatoDataAdapter.setZomatoData((ArrayList<Restaurant>) restaurants);
            }
        });
        //pass latitude and longitude set obsever
        mainViewModel.getItemLatLong(getContext(), simpleLocation.getLatitude(), simpleLocation.getLongitude()).observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(@Nullable List<Restaurant> restaurants) {
                horizontalDataAdapter.setZomatoData((ArrayList<Restaurant>) restaurants);
            }
        });
    }
    //endregion

    //region search view get data and api call
    @Override
    public void sendData(String data) {
        if (data != null) {
            mainViewModel.getSearchItem(getContext(), data).observe(this, new Observer<List<Restaurant>>() {
                @Override
                public void onChanged(@Nullable List<Restaurant> restaurants) {
                    linearMain.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    if (restaurants.size() == 0) {
                        txtNoData.setText("No data found");
                        txtNoData.setTextColor(Color.RED);
                    } else {
                        txtNoData.setText("Restaurants nearby");
                        txtNoData.setTextColor(Color.BLACK);
                    }
                    zomatoDataAdapter.setZomatoData((ArrayList<Restaurant>) restaurants);
                }
            });
        }
    }
    //endregion
}
