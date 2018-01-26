package com.voicetotext.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

@Component
public class Authentication {
  private static final String FETCH_TOKEN_URI = "https://api.cognitive.microsoft.com/sts/v1.0/issueToken";
  private String subscriptionKey = "a3bfe1f715454a7d9a1cec634fee3114";
  private String token;

  public Authentication() {
    fetchToken();
  }

  protected void setToken(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  protected void fetchToken() {
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(FETCH_TOKEN_URI).openConnection();
      connection.setDoInput(true);
      connection.setDoOutput(true);
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
      connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
      connection.setFixedLengthStreamingMode(0);
      connection.connect();

      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
          setToken(reader.lines().collect(Collectors.joining()));
        }
      } else {
        System.out.format("Something went wrong, server returned: %d (%s)", 
            connection.getResponseCode(), connection.getResponseMessage());
      }
      
    } catch (Exception e) {
      token = null;
      System.out.format("Failed to fetch an access token. Details: %s", e.getMessage());
    }
  }
}
