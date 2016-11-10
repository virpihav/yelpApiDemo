package fi.virpihav.yelpdemo;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fi.virpihav.yelpdemo.model.Business;
import fi.virpihav.yelpdemo.model.Token;
import fi.virpihav.yelpdemo.provider.LocationProvider;
import fi.virpihav.yelpdemo.provider.TokenProvider;
import fi.virpihav.yelpdemo.service.YelpService;

public class SearchActivity extends AppCompatActivity implements LocationProvider.LocationCallback, YelpService.SearchCallback {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private EditText search;
    private Location mLastLocation;
    private LocationProvider locationProvider;
    private Token token;
    private ProgressBar progressBar;
    private ViewGroup content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search = (EditText) findViewById(R.id.search);
        LocationManager locationManager = ((LocationManager) getSystemService(Context.LOCATION_SERVICE));
        locationProvider = new LocationProvider(this, locationManager);
        content = (ViewGroup) findViewById(R.id.content);
        progressBar = ((ProgressBar) findViewById(R.id.progressBar));
        TokenProvider tokenProvider = new TokenProvider();
        tokenProvider.getToken(new TokenProvider.Callback() {
            @Override
            public void onToken(Token token) {
                hideLoading();
                SearchActivity.this.token = token;
            }
        });
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    buttonClicked(null);
                    return true;
                }
                return false;
            }
        });
        showLoading();
    }

    @Override
    protected void onStart() {
        locationProvider.startLocationUpdates(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        locationProvider.stopLocationUpdates();
        super.onStop();
    }

    public void showLoading() {
        content.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        content.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    public void buttonClicked(View view) {
        if (mLastLocation != null) {
            String input = search.getText().toString();
            showLoading();
            YelpService yelpService = new YelpService();
            yelpService.searchBusinesses(input, mLastLocation, token, this);
        } else {
            Toast.makeText(this, "Location not found. Try again soon", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void newLocation(Location location) {
        mLastLocation = location;
    }

    @Override
    public void onBusinesses(List<Business> businesses) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putParcelableArrayListExtra(ResultActivity.EXTRA_BUSINESSES, new ArrayList<Parcelable>(businesses));
        startActivityForResult(intent, 100);
    }

    @Override
    public void onFailure(String message) {
        hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            hideLoading();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            locationProvider.startLocationUpdates(this);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
