package com.voicetotext.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voicetotext.auth.Authentication;
import com.voicetotext.controller.ATController;
import com.voicetotext.entity.AudioText;
import com.voicetotext.service.ATService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static com.voicetotext.conf.ATConfigurationProps.*;

@Controller
public class ATControllerImpl implements ATController {

    private static final String REQUEST_URI = "https://speech.platform.bing.com/speech/recognition/%s/cognitiveservices/v1";
    private static final String PARAMETERS = "language=%s&format=%s";

    // Default conf
    private RecognitionMode mode = RecognitionMode.Interactive;
    private Language language = Language.en_US;
    private OutputFormat format = OutputFormat.Simple;

    @Autowired
    private ATService atService;

    @Autowired
    private ObjectMapper mapper;
    private final Authentication auth;

    public ATControllerImpl(Authentication auth){
        this.auth = auth;
    }

    public RecognitionMode getMode() {
        return mode;
    }

    public void setMode(RecognitionMode mode) {
        this.mode = mode;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public OutputFormat getFormat() {
        return format;
    }

    public void setFormat(OutputFormat format) {
        this.format = format;
    }

    private URL buildRequestURL() throws MalformedURLException {
        String url = String.format(REQUEST_URI, mode.name().toLowerCase());
        String params = String.format(PARAMETERS, language.name().replace('_', '-'), format.name().toLowerCase());
        return new URL(String.format("%s?%s", url, params));
    }

    private HttpURLConnection connect() throws MalformedURLException, IOException {
        HttpURLConnection connection = (HttpURLConnection) buildRequestURL().openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-type", "audio/wav; codec=\"audio/pcm\"; samplerate=16000");
        connection.setRequestProperty("Accept", "application/json;text/xml");
        connection.setRequestProperty("Authorization", "Bearer " + auth.getToken());
        connection.setChunkedStreamingMode(0); // 0 == default chunk size
        connection.connect();

        return connection;
    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException(String.format("Something went wrong, server returned: %d (%s)",
                    connection.getResponseCode(), connection.getResponseMessage()));
        }

        String response = null;
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            response = reader.lines().collect(Collectors.joining("\n"));
        }

        if(response != null && !response.isEmpty()){
            AudioText audioText = new AudioText();
            audioText = mapper.readValue(response, AudioText.class);
            atService.saveAudio(audioText);
        }

        return response;
    }

    private HttpURLConnection upload(InputStream is, HttpURLConnection connection) throws IOException {
        try (OutputStream output = connection.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
            output.flush();
        }
        return connection;
    }

    private HttpURLConnection upload(Path filepath, HttpURLConnection connection) throws IOException {
        try (OutputStream output = connection.getOutputStream()) {
            Files.copy(filepath, output);
        }
        return connection;
    }

    public String process(InputStream is) throws IOException {
        return getResponse(upload(is, connect()));
    }

    public String process(Path filepath) throws IOException {
        return getResponse(upload(filepath, connect()));
    }
}
