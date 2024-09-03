package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import response.APIResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CurrencyService {
    private static final String AVAILABLE_CURRENCIES_API_URL = "https://economia.awesomeapi.com.br/json/available";
    private static final ObjectMapper mapper = new ObjectMapper();

    public List<String> getAvailableCurrencies() {
        List<String> currencies = new ArrayList<>();
        try {
            URL url = new URL(AVAILABLE_CURRENCIES_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonNode root = mapper.readTree(response.toString());
            root.fieldNames().forEachRemaining(currencies::add);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return currencies;
    }

    public List<APIResponse> getExchangeRates(String moeda, String startDate, String endDate) {
        String urlString = "https://economia.awesomeapi.com.br/json/daily/" + moeda;

        if (startDate != null && endDate != null) {
            urlString += "?start_date=" + startDate + "&end_date=" + endDate;
        }

        List<APIResponse> responses = new ArrayList<>();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            System.out.println("API response " + response.toString());
            responses = mapper.readValue(response.toString(), new TypeReference<List<APIResponse>>(){});

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responses;
    }
}
