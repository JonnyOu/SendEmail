package com.example.sendemail;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class MessageLoader {


    private final ObjectMapper mapper = new ObjectMapper();

    private Path msgPath = Paths.get("src/main/resources/messages.txt");
    private Path recordPath = Paths.get("src/main/resources/sent_record.json");

    public List<String> loadMessages() throws IOException {
        return Files.readAllLines(msgPath);
    }

    public SentRecord loadRecord() throws IOException {
        if (!Files.exists(recordPath)) {
            SentRecord r = new SentRecord();
            r.setLastIndex(-1);
            saveRecord(r);
            return r;
        }
        return mapper.readValue(recordPath.toFile(), SentRecord.class);
    }

    public void saveRecord(SentRecord record) throws IOException {
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(recordPath.toFile(), record);
    }

}
