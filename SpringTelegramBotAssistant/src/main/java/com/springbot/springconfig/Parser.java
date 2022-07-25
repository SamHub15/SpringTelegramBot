package com.springbot.springconfig;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.springbot.springconfig.CityParser.*;

@Component
public class Parser {

    private static Pattern pattern = Pattern.compile("[0-9]+[\\s_]+[А-Яа-я]+");

    private static String getDate(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }

    private static String printDay(Elements element) {
        String day = "";
        for (int i=0; i<4; i++) {
            // Время суток
            Element dateDay = element.select("span[class=text text_block text_bold_normal text_fixed margin_bottom_10]").get(i);
            // Градусы
            Element dayDegree = element.select("span[class=text text_block text_bold_medium margin_bottom_10]").get(i);
            // Вывод информации (ясно, пасмурно, облачно, дождь)
            Element dayInfo = element.select("span[class=text text_block text_light_normal text_fixed]").get(i);

            day += dateDay.text() + " " + dayDegree.text() + " " + dayInfo.text() + " \n";
        }
        return day;
    }

    public static String moscow()  {
        try {
            return city(moscowParser());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String piter() {
        try {
            return city(piterParser());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String voronezh() {
        try {
            return city(voronezhParser());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String volgograd()  {
        try {
            return city(volgogradParser());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String city(Document document) throws Exception {
        String city = "";
        // Выбор главного елемента страницы
        Element mainElement = document.select("div[class=layout]").first();
        // Дата и месяц
        Elements date_month = mainElement.select("div[class=hdr__wrapper]");
        // Выбор элемента в котором находится блок для времени суток
        Elements time_day = mainElement.select("div[class=p-flex p-flex_content_justify margin_bottom_20]");

        int index = 1;
        for (Element element2: time_day) {
            // Выбор из time_day каждого блока даты и месяц
            Element dateElement = date_month.select("span[class=hdr__text]").get(index);
            index ++;
            // Выбор из time_day каждого блока времени суток
            Elements elements = element2.select("div[class=p-flex__column p-flex__column_percent-16]");
            // Вывод 4 значения времени суток для каждого блока time_day
            String date = printDay(elements);
            // Использование регулярного выражения для форматирования даты из [25 июля, понедельник] в [25 июля]
            String parent = getDate(dateElement.text());
            // Форматирование вывода результата
            String day = parent + "\n" + date + "\n";
            city += day;
        }
        return city;
    }
}
