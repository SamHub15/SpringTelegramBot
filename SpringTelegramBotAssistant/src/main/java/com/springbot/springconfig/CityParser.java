package com.springbot.springconfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.URL;

public abstract class CityParser {

    public static Document moscowParser() {
        String url = "https://pogoda.mail.ru/prognoz/moskva/7dney/";
        Document page;
        try {
            page = Jsoup.parse(new URL(url), 5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return page;
    }

    public static Document piterParser() {
        String url = "https://pogoda.mail.ru/prognoz/sankt_peterburg/7dney/";
        Document page;
        try {
            page = Jsoup.parse(new URL(url), 5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return page;
    }

    public static Document voronezhParser() {
        String url = "https://pogoda.mail.ru/prognoz/voronezh/7dney/";
        Document page;
        try {
            page = Jsoup.parse(new URL(url), 5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return page;
    }

    public static Document volgogradParser() {
        String url = "https://pogoda.mail.ru/prognoz/volgograd/7dney/";
        Document page;
        try {
            page = Jsoup.parse(new URL(url), 5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return page;
    }
}
