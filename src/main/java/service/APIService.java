package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.primefaces.shaded.json.JSONObject;

import java.util.ArrayList;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import response.APIResponse;

public class APIService {

    private ObjectMapper mapper = new ObjectMapper();
    
    
    public List<String> obterMoedasDisponiveis() throws IOException {
        String url = "https://economia.awesomeapi.com.br/json/available";
        
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
        con.setRequestMethod("GET");
        
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        JSONObject jsonResponse = new JSONObject(response.toString());
        
        List<String> moedaCodes = new ArrayList<>();
        for (String key : jsonResponse.keySet()) {
            moedaCodes.add(key);
        }
        
        return moedaCodes;
    }

    public List<APIResponse> getExchangeRates(String moeda, int numeroDias) {
        String urlString = "https://economia.awesomeapi.com.br/json/daily/" + moeda + "/" + numeroDias;

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
            
            responses = mapper.readValue(response.toString(), new TypeReference<List<APIResponse>>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responses;
    }
}
