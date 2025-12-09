package com.example.sendemail;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessageLoader {

    private final ObjectMapper mapper = new ObjectMapper();

    // messages.txt 放在 resources 下，只读
    private final String MESSAGE_FILE = "messages.txt";

    // sent_record.json 放在 jar 同目录，可写
    private final Path recordPath = Paths.get("sent_record.json");

    /**
     * 加载 messages.txt，支持 jar 内和本地开发
     */
    public List<String> loadMessages() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(MESSAGE_FILE);
        if (is == null) {
            throw new FileNotFoundException(MESSAGE_FILE + " not found in classpath");
        }

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    /**
     * 读取已发送记录
     */
    public SentRecord loadRecord() throws IOException {
        if (!Files.exists(recordPath)) {
            SentRecord r = new SentRecord();
            r.setLastIndex(-1);
            saveRecord(r);
            return r;
        }
        return mapper.readValue(recordPath.toFile(), SentRecord.class);
    }

    /**
     * 保存记录
     */
    public void saveRecord(SentRecord record) throws IOException {
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(recordPath.toFile(), record);
    }

}
