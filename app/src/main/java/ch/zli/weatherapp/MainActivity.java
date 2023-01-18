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

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView locationView;
    private TextView descView;

    private WeatherService weatherService = new WeatherService();

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Intent weather = new Intent(this, WeatherActivity.class);
        startActivity(weather);*/

        //getLocation();
    }

    public void getLocation() {
        try {
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

    public void getConditions() {
        //String condition = weatherService.getCondition();
    }
}