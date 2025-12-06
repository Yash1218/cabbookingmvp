package com.example.cabbookingmvp.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GeoService {

    public double[] getCoordinates(String location) {
        try {
            String url = "https://nominatim.openstreetmap.org/search?format=json&q=" +
                    location.replace(" ", "+");

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0"); // required by API

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                json.append(line);
            }

            JSONArray arr = new JSONArray(json.toString());
            if (arr.length() == 0) return null;

            JSONObject obj = arr.getJSONObject(0);

            double lat = obj.getDouble("lat");
            double lon = obj.getDouble("lon");

            return new double[]{lat, lon};

        } catch (Exception e) {
            System.out.println("GeoService Error: " + e.getMessage());
            return null;
        }
    }
}
