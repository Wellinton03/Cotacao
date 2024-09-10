package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import response.APIResponse;

public class APIService {

    private static final String API_KEY = "p3sZLxX0ZneWZUCXReh4N1TkeKKm__t1";
    private ObjectMapper mapper = new ObjectMapper();

    public List<APIResponse> getHistoricalData(String symbol) {
        String urlString = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s", symbol, API_KEY);

        List<APIResponse> responses = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

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
            JsonNode timeSeries = jsonObject.get("Time Series (Daily)");

            if (timeSeries != null && timeSeries.isObject()) {
                Iterator<String> fieldNames = timeSeries.fieldNames();
                while (fieldNames.hasNext()) {
                    String dateStr = fieldNames.next();
                    JsonNode dailyData = timeSeries.get(dateStr);

                    if (dailyData != null && dailyData.isObject()) {
                        try {
                            Date date = dateFormat.parse(dateStr);
                            APIResponse apiResponse = new APIResponse();
                            apiResponse.setDataEHora(date);
                            apiResponse.setAlta(dailyData.get("2. high").asDouble());
                            apiResponse.setBaixa(dailyData.get("3. low").asDouble());
                            apiResponse.setFechamento(dailyData.get("4. close").asDouble());

                            responses.add(apiResponse);
                        } catch (ParseException e) {
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
