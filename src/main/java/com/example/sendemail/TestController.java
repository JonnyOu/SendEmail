package com.example.sendemail;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final DailyMailTask task;

    public TestController(DailyMailTask task) {
        this.task = task;
    }

    @GetMapping("/sendTest")
    public String test() {
        task.sendDaily();
        return "邮件已触发发送";
    }

}
