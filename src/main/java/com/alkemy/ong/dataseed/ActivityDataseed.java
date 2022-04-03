package com.alkemy.ong.dataseed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;

import java.time.LocalDateTime;

@Component
public class ActivityDataseed implements CommandLineRunner {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public void run(String... args) {
        loadActivities();
    }

    private void loadActivities() {
        if (activityRepository.count() == 0) {
            Activity activity1 = new Activity(1l,"Run","I'm running","image", LocalDateTime.now(),LocalDateTime.of(2010,01,12,20,02));
            Activity activity2 = new Activity(2l,"Eat","I'm eating","image", LocalDateTime.now(),LocalDateTime.of(2019,11,20,16,30));
            activityRepository.save(activity1);
            activityRepository.save(activity2);
        }
    }
}
