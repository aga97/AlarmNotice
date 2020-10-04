/*
package me.jungwoo.pagealarm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartUpApplicationListner {

    private final TelegramBot telegramBot = new TelegramBot();

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(event.getTimestamp()), TimeZone.getDefault().toZoneId());
        String startMsg = "\n==== Alarm ====\n"
                + "==== SERVER START === \n\n"
                + "[Active Profile] : MY-PROFILE"
                + "\n[Up-Time] : " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

        SendMessage message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(startMsg);

        execute(message);
    }

    @EventListener
    public void shutdownApplicationEvent(ContextClosedEvent event) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(event.getTimestamp()), TimeZone.getDefault().toZoneId());
        String endMsg = "\n==== Alarm ====\n"
                + "==== SERVER DOWN === \n\n"
                + "[Active Profile] : MY-PROFILE"
                + "\n[DOWN-Time] : " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

        SendMessage message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(endMsg);

        excute(message);
    }
}
 */
