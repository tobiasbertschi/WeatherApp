package ch.zli.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView locationView;
    private TextView descView;
    private ImageButton weatherImage;

    private WeatherService weatherService = new WeatherService();

    LocationManager locationManager;

    //are used to get permissions
    final static String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    final static int all_permissions = 1;

    private HashMap<String, String> data;

    JSONObject jsonObject = new JSONObject();

    private Double lat;
    private Double lon;

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
        lat = location.getLatitude();
        lon = location.getLongitude();

        data = weatherService.getCondition(lat, lon);

        locationView = (TextView) findViewById(R.id.location);
        locationView.setText(data.get("name"));

        descView = (TextView) findViewById(R.id.description);
        descView.setText(data.get("description"));

        //getIcon();
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

    public void callWeather(View view) {
        //With bundle i can give Values to another Activity
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", lat);
        bundle.putDouble("lon", lon);
        Intent weather = new Intent(this, WeatherActivity.class);
        weather.putExtras(bundle);
        startActivity(weather);
    }

    /*public void getIcon() {
        weatherImage = (ImageButton) findViewById(R.id.weatherImage);
        LinearLayout layout = (LinearLayout) findViewById(R.id.vertical);

        Picasso.get().load("http://openweathermap.org/img/wn/10d@2x.png").into(weatherImage);
        weatherImage.refreshDrawableState();
        layout.refreshDrawableState();
    }*/
}