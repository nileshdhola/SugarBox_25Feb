package com.resto.sugarbox;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.resto.sugarbox.fragment.HomeFragment;
import com.resto.sugarbox.utils.CommonUtils;
import com.resto.sugarbox.utils.SimpleLocation;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private Toolbar toolbar;
    private SimpleLocation simpleLocation;
    private Dataset dataset;
    private String address;
    private HomeFragment homeFragment;

    //region onCreate call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //call home fragment
        openFragment();

        //click toolbar icon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment();
            }
        });

    }
    //endregion

    //region open fragment
    private void openFragment() {
        homeFragment = new HomeFragment();
        openFragment(homeFragment);
        dataset = (Dataset) homeFragment;

        simpleLocation = new SimpleLocation(MainActivity.this);
        //toolbar
        address = CommonUtils.getAddress(MainActivity.this, simpleLocation.getLatitude(), simpleLocation.getLongitude());
        getSupportActionBar().setTitle(address.toUpperCase());
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_text));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_location_on_black_24dp));
    }
    //endregion

    //region call fragement - when search item
    public interface Dataset {
        void sendData(String data);
    }
    //endregion

    //region on create menu
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);

        final MenuItem searchViewItem = menu.findItem(R.id.action_search);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint("Search Location");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);// Do not iconify the widget; expand it by defaul

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                if (searchViewItem != null) {
                    searchViewItem.collapseActionView();
                }
                dataset.sendData(query);
                getSupportActionBar().setTitle(query.toUpperCase());
                onPrepareOptionsMenuItem(menu);

                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region hide keyboard
    public boolean onPrepareOptionsMenuItem(Menu menu) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        return true;
    }
    //endregion

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //region fragment replace
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    //endregion


}


