package org.example;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class ApartmentFinderBot extends TelegramLongPollingBot {


    @Override
    public void onUpdateReceived(Update update) {

        Long chatId = update.getMessage().getChatId();
        String cookies = update.getMessage().getText();

        try {
            boolean result = ApartmentScraper.checkRequest(chatId, cookies);
            if (result) {
                SendMessage successMessage = new SendMessage();
                successMessage.setChatId(chatId);
                successMessage.setText("Bot is working!!!");

                execute(successMessage);
                execute(ApartmentScraper.findAndSend(chatId, cookies));
            } else {
                SendMessage errorMessage = new SendMessage();
                errorMessage.setChatId(chatId);
                errorMessage.setText("Bad cookie value!!!");
                execute(errorMessage);
            }


        } catch (IOException | InterruptedException | TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return BotConstants.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BotConstants.BOT_TOKEN;
    }
}
