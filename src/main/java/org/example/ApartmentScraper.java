package org.example;

import org.jsoup.Connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ApartmentScraper {
    private static final String URL = "https://www.immobilienscout24.de/Suche/de/hamburg/hamburg/wohnung-mieten?numberofrooms=3.0-5.0&price=-1100.0&livingspace=50.0-&pricetype=rentpermonth&sorting=2&enteredFrom=result_list";
    //    private static final String URL = "https://www.immobilienscout24.de/meinkonto/dashboard/";
    private static final String BASE = "https://www.immobilienscout24.de";
    private static final String AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36 Google Favicon";
//    private static final String COOKIES = "";

    private static Map<String, String> apartments = new HashMap<>();

    public static boolean checkRequest(Long chatId, String cookies) throws IOException {
        if(cookies.length() < 50) {
            return false;
        }
        Document doc = Jsoup.connect(URL)
                .header("cookie", cookies)
                .userAgent(AGENT)
                .ignoreHttpErrors(true)
                .followRedirects(true)
                .ignoreContentType(true)
                .get();

        if (doc.text().contains("Wohnung mieten in Hamburg")){
            SendMessage successMessage = new SendMessage();
            successMessage.setChatId(chatId);
            successMessage.setText("Bot is working!!!");

            Elements elements = doc.select(".result-list__listing");
            for (Element el : elements) {
                String name = el.select("h2").text();
                String url = BASE + el.selectFirst("a").attr("href") + "#/basicContact/email";
                if (!apartments.containsKey(name)) {
                    apartments.put(name, url);
                }
                System.out.println(name);
                System.out.println(url);
                System.out.println("@".repeat(100));
            }
            return true;
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Bad response!!!");
        return false;
    }

    public static SendMessage findAndSend(Long chatId, String cookies) throws IOException, InterruptedException {

        int counter = 1;
        Document doc = Jsoup.connect(URL)
                .header("cookie", cookies)
                .userAgent(AGENT)
                .ignoreHttpErrors(true)
                .followRedirects(true)
                .ignoreContentType(true)
                .get();

        while (!doc.text().startsWith("Ich") && doc.text().contains("Wohnung mieten in Hamburg")) {
            Thread.sleep(60000);
            counter++;
            doc = Jsoup.connect(URL)
                    .header("cookie", cookies)
                    .userAgent(AGENT)
                    .ignoreHttpErrors(true)
                    .followRedirects(true)
                    .ignoreContentType(true)
                    .get();

            System.out.println(counter + ") " + doc.text());

            Elements elements2 = doc.select(".result-list__listing");
            for (Element el : elements2) {
                String name = el.select("h2").text();
                String url = BASE + el.selectFirst("a").attr("href") + "#/basicContact/email";
                if (apartments.containsKey(name)) {
                    continue;
                }
                SendMessage returnMessage = HttpClientExample.sendApplication(url, cookies, chatId);
                apartments.put(name, url);

                System.out.println(el.select("h2").text());
                System.out.println(BASE + el.selectFirst("a").attr("href") + "#/basicContact/email");
                System.out.println("@".repeat(100));
                return returnMessage;
            }
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("RESULT: " + counter);
        System.out.println("RESULT: " + counter);
        return sendMessage;
    }
}
