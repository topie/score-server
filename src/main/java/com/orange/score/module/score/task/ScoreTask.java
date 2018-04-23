package com.orange.score.module.score.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScoreTask {

    @Scheduled(cron = "0 0/1 * * * ?")
    public void calScore() {
        System.out.println("running！");
    }
}
