package me.jungwoo.pagealarm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import javax.annotation.PostConstruct;


//@Slf4j
@Component
public class TelegramBot {
    private final String BOT_NAME = "JW1145_alarm_bot";
    private final String AUTH_KEY = "1117557997:AAFFm9nyhfJ1VnxeLt93EyK2HWSvBv6nzv0";
    private final String CHAT_ID = "1331655045";

    @Autowired
    fbService fbServ;
    @Autowired
    Parsing Parse;

    @PostConstruct
    private void init() {
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();

        try {
            api.registerBot(new TelegramLongPollingBot() {
                @Override
                public String getBotToken() {
                    return AUTH_KEY;
                }

                @Override
                public String getBotUsername() {
                    return BOT_NAME;
                }

                @Override
                public void onUpdateReceived(Update update) {
                    String str="";

                    //메시지를 받았을 때 처리해 주는 함수
                    //텍스트를 검사해서 특정 텍스트인 경우 홈페이지에서 파싱
                    if(update.hasMessage() && update.getMessage().hasText()) {
                        String rev = update.getMessage().getText();
                        if(rev.equals("공지")) { //확인입력시 파싱
                            str = Parse.ParsePage();

                            try {
                                execute(setMessage(str, update));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        } else if(rev.equals("확인")) {

                            try {
                                str = fbServ.getAllNotice();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                execute(setMessage(str, update));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }

                        } else { //미러 텍스트 +

                            try {
                                execute(setMessage(rev + " 확인 : \n" + fbServ.getNotice(rev), update));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SendMessage setMessage(String str, Update update) {

        SendMessage message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(str);

        return message;
    }

}
