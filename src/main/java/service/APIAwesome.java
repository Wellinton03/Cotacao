package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import response.APICurrencyResponse;

public class APIAwesome {

		private ObjectMapper mapper = new ObjectMapper();
		
	public List<APICurrencyResponse> getCurrencyHistoryData(String moeda, Integer dias) {
		String urlString = String.format("https://economia.awesomeapi.com.br/json/daily/%s/%d", moeda, dias);
		
		List<APICurrencyResponse> responses = new ArrayList<>();
		
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String inputLine;
			
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			reader.close();
			
			System.out.println("Resposta da API: " + response.toString());
			
			JsonNode jsonArray = mapper.readTree(response.toString());
			
			if(jsonArray.isArray()) {
				for(JsonNode JsonObject : jsonArray) {
					
					try {
						String highStr = JsonObject.get("high").asText();
						String lowStr = JsonObject.get("low").asText();
						String timestampStr = JsonObject.get("timestamp").asText();
						String bidStr = JsonObject.get("bid").asText();
						
						
						double high = Double.parseDouble(highStr);
						double low = Double.parseDouble(lowStr);
						long timestamp = Long.parseLong(timestampStr);
						double bid = Double.parseDouble(bidStr);
						
						LocalDateTime dateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
						
						APICurrencyResponse currencyResponse = new APICurrencyResponse();
							currencyResponse.setBid(bid);
							currencyResponse.setDataHora(dateTime);
							currencyResponse.setHigh(high);
							currencyResponse.setLow(low);
							
							responses.add(currencyResponse);
						
					}catch (Exception e) {
						System.err.println("Erro ao processar o item do JSON: " + e.getMessage());
					 }
		        }
		    } else {
		        System.err.println("Resposta JSON não é um array.");
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return responses;
	}
}
