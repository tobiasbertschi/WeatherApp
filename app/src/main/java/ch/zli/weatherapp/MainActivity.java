package ch.zli.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView locationView;
    public TextView descView;

    private WeatherService weatherService = new WeatherService();

    LocationManager locationManager;

    //are used to get permissions
    final static String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    final static int all_permissions = 1;

    JSONObject jsonObject = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLocation();
    }

    public void getLocation() {
        try {
            //get permissions
            requestPermissions(permissions, all_permissions);

            //get locations
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        HashMap<String, String> data = weatherService.getCondition(location.getLatitude(), location.getLongitude());

        locationView = (TextView) findViewById(R.id.location);
        locationView.setText(data.get("name"));

        descView = (TextView) findViewById(R.id.description);
        descView.setText(data.get("description"));
    }

    @Override
    public void onProviderDisabled(String provider) {
        //Toast ist eine Nachricht die den Platz ausfüllt während der Zeit wo ein Vorgang ausgeführt wird
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    public void getWeather(Location location) {
        /*Bundle bundle = new Bundle();
        bundle.putDouble("lat", location.getLatitude());
        bundle.putDouble("lon", location.getLongitude());
        Intent weather = new Intent(this, WeatherActivity.class);
        weather.putExtras(bundle);
        startActivity(weather);*/
    }
}