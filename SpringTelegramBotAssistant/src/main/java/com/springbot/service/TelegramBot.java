package com.springbot.service;

import com.springbot.springconfig.Parser;
import com.springbot.springconfig.SpringBotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    ReplyKeyboardMarkup replyKeyboardMarkup;
    private final SpringBotConfig botConfig;
    private Parser parser;

    public TelegramBot(SpringBotConfig botConfig, Parser parser) {
        this.botConfig = botConfig;
        this.parser = parser;
        initKeyboard();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String firstName = update.getMessage().getChat().getFirstName();

            switch (message) {
                case "/start":
                    startCommand(chatId, firstName);
                    break;
                case "Москва":
                    startCommand(chatId, Parser.moscow());
                    break;
                case "Санкт-петербург":
                    startCommand(chatId, Parser.piter());
                    break;
                case "Воронеж":
                    startCommand(chatId, Parser.voronezh());
                    break;
                case "Волгоград":
                    startCommand(chatId, Parser.volgograd());
                    break;

                default:
                    sendMessage(chatId, "Sorry, I didn't find anything");
            }
        }
    }


    private void startCommand(long chatId, String message) {

        sendMessage(chatId, message);
    }

    // Создание клавиатуры
    private void initKeyboard()
    {
        //Создаем объект будущей клавиатуры и выставляем нужные настройки
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        //подгоняем размер
        replyKeyboardMarkup.setResizeKeyboard(true);
        //скрываем после использования
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        //Создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        //Создаем 2 ряда кнопок и добавляем его в список
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardLef = new KeyboardRow();
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardLef);
        //Добавляем кнопки в наш ряд
        keyboardRow.add(new KeyboardButton("Москва"));
        keyboardRow.add(new KeyboardButton("Санкт-петербург"));

        keyboardLef.add(new KeyboardButton("Воронеж"));
        keyboardLef.add(new KeyboardButton("Волгоград"));
        //добавляем лист с рядами кнопок в главный объект
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    private void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}