package com.example.sendemail;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DailyMailTask {

    private final MessageLoader loader;
    private final MailSenderService sender;

    public DailyMailTask(MessageLoader loader, MailSenderService sender) {
        this.loader = loader;
        this.sender = sender;
    }

    /**
     * 每天早上 9 点发送
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDaily() {
        try {
            List<String> msgs = loader.loadMessages();
            SentRecord record = loader.loadRecord();

            int next = record.getLastIndex() + 1;
            if (next >= msgs.size()) {
                next = 0; // 循环
            }

            String content = msgs.get(next);
            sender.send(content);

            record.setLastIndex(next);
            loader.saveRecord(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
