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

    public static String readValuesFromJson() {
        String link = "http://api.nbp.pl/api/exchangerates/rates/c/chf/?format=json";
        String jsonContent = jsonGetRequest(link);
        String output = null;

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonContent);

       /* if (element.isJsonObject()) {
            JsonObject jsonObject = element.getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray(RATES_TABLE_NAME);
            for (int i = 0; i < jsonArray.size(); i++) { //if there's more rates
                System.out.println(((JsonObject) jsonArray.get(i)).get(ASK_EXCHANGE_RATE_NAME));
            }
        }*/

        if (element.isJsonObject()) {
            JsonObject currencyTable = element.getAsJsonObject();
            JsonArray ratesArray = currencyTable.getAsJsonArray(RATES_TABLE_NAME);
            double askEchangeRate = ((JsonObject) ratesArray.get(0)).get(ASK_EXCHANGE_RATE_NAME).getAsDouble(); //if there's only one rate (which it is)
            System.out.println("Today's CHF exchange rate = " + askEchangeRate);
            output = askEchangeRate + " ";
        }
        return output;
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

