package ch.zli.weatherapp;

import android.os.StrictMode;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class WeatherService {

    private String key = "ec5645b3fdc3e91b77501154c69c9796";
    private String url = "https://api.openweathermap.org/data/2.5/weather?q=Zuerich&appid=" + key;
    private String url2 = "https://api.openweathermap.org/data/2.5/weather?";

    public HashMap<String, String> getWeather() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().url(url).get().build();

            Response response = client.newCall(request).execute();
            String jsonString = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonString);

            HashMap<String, String> data = new HashMap<>();

            JSONArray weather = jsonObject.getJSONArray("weather");
            if (weather.length() > 0) {
                JSONObject entry = weather.getJSONObject(0);
                String description = entry.getString("description");
                data.put("description", description);
            }

            JSONObject main = jsonObject.getJSONObject("main");
            if (main.length() > 0) {
                Double temp = main.getDouble("temp");
                temp = Math.round((temp - 273.15) * 100) / 100.0;
                data.put("temperature", temp.toString());
            }

            JSONObject wind = jsonObject.getJSONObject("wind");
            if (wind.length() > 0) {
                Double speed = wind.getDouble("speed");
                data.put("wind", speed.toString());
            }

            if (data.size() > 0) {
                return data;
            }

        } catch (IOException e) {
            // Handle IOException
            return null;
        } catch (JSONException e) {
            // Handle JSONException
            return null;
        }
        return null;
    }

    public HashMap<String, String> getCondition(Double lat, Double lon) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().url(url2 + "lat=" + lat + "&lon=" + lon + "&appid=" + key).get().build();

            Response response = client.newCall(request).execute();
            String jsonString = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonString);

            HashMap<String, String> data = new HashMap<>();

            JSONArray weather = jsonObject.getJSONArray("weather");
            if (weather.length() > 0) {
                JSONObject entry = weather.getJSONObject(0);
                String description = entry.getString("description");
                data.put("description", description);
            }

            if (jsonObject.length() > 0) {
                String name = jsonObject.getString("name");
                data.put("name", name);
            }

            if (data.size() > 0) {
                return data;
            }

        } catch (IOException e) {
            return null;
        } catch (JSONException e) {
            return null;
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}