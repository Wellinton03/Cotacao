package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import response.APIResponse;

public class APIAlphaVantage {

    private static final String API_KEY = "L1OP9MUM7XRKRHOU";
    private ObjectMapper mapper = new ObjectMapper();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public List<APIResponse> getStockHistoryData(String symbol) {
    	System.out.println(symbol);
        String urlString = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s", symbol, API_KEY);
        System.out.println(urlString);
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
            
            System.out.println("Resposta da API: " + response.toString());

            JsonNode jsonObject = mapper.readTree(response.toString());
            JsonNode timeSeries = jsonObject.get("Time Series (Daily)");

            if (timeSeries != null && timeSeries.isObject()) {
                Iterator<String> fieldNames = timeSeries.fieldNames();
                while (fieldNames.hasNext()) {
                    String dateStr = fieldNames.next();
                    JsonNode dailyData = timeSeries.get(dateStr);

                    if (dailyData != null && dailyData.isObject()) {
                        try {
                            LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER );
                            LocalDateTime dateTime = date.atStartOfDay();
                            APIResponse apiResponse = new APIResponse();
                            apiResponse.setDataEHora(dateTime);
                            apiResponse.setAlta(dailyData.get("2. high").asDouble());
                            apiResponse.setBaixa(dailyData.get("3. low").asDouble());
                            apiResponse.setFechamento(dailyData.get("4. close").asDouble());
                            
                            responses.add(apiResponse);
                        } catch (Exception e) {
                            System.err.println("Erro ao analisar a data: " + dateStr);
                        }
                    } else {
                        System.err.println("Dados não encontrados para a data: " + dateStr);
                    }
                }
            } else {
                System.err.println("Time Series (Daily) não encontrado na resposta.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responses;
    }
}
