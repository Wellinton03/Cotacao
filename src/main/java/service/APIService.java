package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import response.APIResponse;

public class APIService {

    private ObjectMapper mapper = new ObjectMapper();

    public List<APIResponse> getExchangeRates(List<String> moedas) {
        String moedasString = String.join(",", moedas);
        String urlString = "https://economia.awesomeapi.com.br/last/" + moedasString;

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

            JsonNode jsonObject = mapper.readTree(response.toString());
            Map<String, JsonNode> currencyMap = mapper.convertValue(jsonObject, new TypeReference<Map<String, JsonNode>>() {});

            for (JsonNode currencyNode : currencyMap.values()) {
                APIResponse apiResponse = mapper.treeToValue(currencyNode, APIResponse.class);
                responses.add(apiResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responses;
    }
}
