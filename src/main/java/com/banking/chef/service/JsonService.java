package com.banking.chef.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class JsonService {
    private static final String RATES_TABLE_NAME = "rates";
    private static final String ASK_EXCHANGE_RATE_NAME = "ask";
    private static final String API_NBP_LINK = "http://api.nbp.pl/api/exchangerates/rates/c/chf/?format=json";

    public static double readValuesFromJson() {
        String jsonContent = jsonGetRequest(API_NBP_LINK);
        double askExchangeRate = 0.0;

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonContent);

        if (element.isJsonObject()) {
            JsonObject currencyTable = element.getAsJsonObject();
            JsonArray ratesArray = currencyTable.getAsJsonArray(RATES_TABLE_NAME);
            askExchangeRate = ((JsonObject) ratesArray.get(0)).get(ASK_EXCHANGE_RATE_NAME).getAsDouble();
        }
        return askExchangeRate;
    }

    private static String jsonGetRequest(String urlQueryString) {
        String json = null;
        try {
            URL url = new URL(urlQueryString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            json = streamToString(inputStream); // input stream to string
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private static String streamToString(InputStream inputStream) {
        return new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
    }

}

