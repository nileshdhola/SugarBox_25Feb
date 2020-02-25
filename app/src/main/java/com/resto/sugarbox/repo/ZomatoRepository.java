package com.resto.sugarbox.repo;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.resto.sugarbox.api.RetrofitClient;
import com.resto.sugarbox.ifc.ZomatoDataService;
import com.resto.sugarbox.model.Restaurant;
import com.resto.sugarbox.model.ZomatoResponse;
import com.resto.sugarbox.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZomatoRepository {

    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private MutableLiveData<List<Restaurant>> mutableLiveData = new MutableLiveData<>();

    public ZomatoRepository() {
    }

    //get live data vertical
    public MutableLiveData<List<Restaurant>> getMutableLiveData(final Context context, Double latitude, Double longitude) {
        final ZomatoDataService userDataService = RetrofitClient.getService();
        Call<ZomatoResponse> call = userDataService.getZomatoResponse(String.valueOf(latitude), String.valueOf(longitude), 20);
        call.enqueue(new Callback<ZomatoResponse>() {
            @Override
            public void onResponse(Call<ZomatoResponse> call, Response<ZomatoResponse> response) {
                ZomatoResponse zomatoResponse = response.body();
                if (zomatoResponse != null && zomatoResponse.getRestaurants() != null) {
                    restaurants = (ArrayList<Restaurant>) zomatoResponse.getRestaurants();
                    mutableLiveData.setValue(restaurants);
                }
            }

            @Override
            public void onFailure(Call<ZomatoResponse> call, Throwable t) {
                CommonUtils.AlertDialog(context, t.getMessage());
            }
        });
        return mutableLiveData;
    }

    //get mutable data from search data
    public MutableLiveData<List<Restaurant>> getMutableLiveDataSearchItem(final Context context, String searchItem) {
        final ZomatoDataService userDataService = RetrofitClient.getService();
        Call<ZomatoResponse> call = userDataService.getSearchItem(searchItem, 20);
        call.enqueue(new Callback<ZomatoResponse>() {
            @Override
            public void onResponse(Call<ZomatoResponse> call, Response<ZomatoResponse> response) {
                ZomatoResponse zomatoResponse = response.body();
                if (zomatoResponse != null && zomatoResponse.getRestaurants() != null) {
                    restaurants = (ArrayList<Restaurant>) zomatoResponse.getRestaurants();
                    mutableLiveData.setValue(restaurants);
                }
            }

            @Override
            public void onFailure(Call<ZomatoResponse> call, Throwable t) {
                CommonUtils.AlertDialog(context, t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
