package ch.zli.weatherapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class WeatherActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor pressure;

    private TextView pressureView;
    private TextView tempView;
    private TextView windView;
    private TextView descView;

    private WeatherService weatherService = new WeatherService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);

        HashMap<String, String> data = weatherService.getWeather();

        tempView = (TextView) findViewById(R.id.temperature);
        tempView.setText(data.get("temperature"));

        windView = (TextView) findViewById(R.id.wind);
        windView.setText(data.get("wind"));

        descView = (TextView) findViewById(R.id.description);
        descView.setText(data.get("description"));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public final void onSensorChanged(SensorEvent event) {

        //wird der so automatisch geupdated?
        float pressureValue = event.values[0];

        pressureView = (TextView) findViewById(R.id.pressure);
        pressureView.setText(Double.toString(pressureValue));


    }


}