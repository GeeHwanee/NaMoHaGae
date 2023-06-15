package kr.kro.namohagae.global.util.scheduled;

import jakarta.annotation.PostConstruct;
import kr.kro.namohagae.global.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class Scheduled {
    @Autowired
    private BlockService blockService;

//    @PostConstruct
//    public void runDeleteImmediately() {
//        executeDelete();
//    }
    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0 0 * * *") // Runs at midnight every day
    public void executeDelete() {
        LocalDate today = LocalDate.now();
        System.out.println(today);
        blockService.delete(today);
    }
}
