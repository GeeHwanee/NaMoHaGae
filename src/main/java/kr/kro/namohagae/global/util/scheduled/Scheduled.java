package kr.kro.namohagae.global.util.scheduled;

import kr.kro.namohagae.global.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Scheduled {
    @Autowired
    private BlockService blockService;

    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0 0 * * *") // Runs at midnight every day
    public void executeDelete() {
        LocalDateTime today = LocalDateTime.now();
        blockService.delete(today);
    }
}
