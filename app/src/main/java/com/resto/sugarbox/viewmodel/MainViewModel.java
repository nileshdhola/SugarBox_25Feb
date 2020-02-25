package com.resto.sugarbox.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.resto.sugarbox.model.Restaurant;
import com.resto.sugarbox.repo.ZomatoRepository;

import java.util.List;


public class MainViewModel extends AndroidViewModel {
    private ZomatoRepository zomatoRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        zomatoRepository = new ZomatoRepository();
    }

    //region get live data from  latitude and longitude
    public LiveData<List<Restaurant>> getItemLatLong(Context context, Double lati, Double longi) {
        return zomatoRepository.getMutableLiveData(context, lati, longi);
    }
    //endregion

    //region get live data from search item
    public LiveData<List<Restaurant>> getSearchItem(Context context, String search) {
        return zomatoRepository.getMutableLiveDataSearchItem(context, search);
    }
    //endregion
}