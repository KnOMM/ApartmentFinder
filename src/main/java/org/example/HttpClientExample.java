package org.example;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.zip.GZIPInputStream;

public class HttpClientExample {
    public static SendMessage sendApplication(String urlLink, String cookie, Long chatId) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        try {
            // Create an HTTP client
            java.net.http.HttpClient httpClient = HttpClient.newHttpClient();
            // Create the JSON payload

            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlLink))
                    .header("Cookie", cookie)
                    .header("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/114.0")
                    .header("Accept", "application/json, text/plain, */*")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Content-Type", "application/json;charset=utf-8")
                    .header("Origin", "https://www.immobilienscout24.de")
                    .header("Dnt", "1")
//                    .header("Referer", "https://www.immobilienscout24.de/expose/144147579?referrer=RESULT_LIST_LISTING&searchId=6493d697-5e1e-38f5-930f-6d5c40c6e5f5&searchType=district")
                    .header("Sec-Fetch-Dest", "empty")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Site", "same-origin")
                    .header("Te", "trailers")
                    .POST(HttpRequest.BodyPublishers.ofString(BotConstants.jsonPayload))
                    .build();

            // Send the HTTP request and get the response
            java.net.http.HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            StringBuilder responseBody = new StringBuilder();
            // Process the response
            if (response.statusCode() == 200) {
                byte[] responseBodyBytes = response.body();
                try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(responseBodyBytes));
                     BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream))) {

                    // Read the decompressed response body
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBody.append(line);
                    }
                    // Print the decompressed response body
                    System.out.println("Response Body: " + responseBody);
                }
                sendMessage.setChatId(chatId);
                sendMessage.setText("Response: " + responseBody + "\n" +
                        "Url: " + urlLink.replace("basicContact/email",""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendMessage;
    }
}